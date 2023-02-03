package com.magneticraft2.common.systems.multiblock;

import com.magneticraft2.common.tile.Multiblockfiller_tile;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static com.magneticraft2.common.tile.Multiblockfiller_tile.*;

/**
 * @author JumpWatch on 25-01-2023
 * @Project magneticraft2-1.18.2
 * v1.0.0
 */
public abstract class Multiblock extends BlockEntity {
    public abstract int invsize();
    public final ItemStackHandler itemHandler = createInv(); //Item
    public abstract boolean itemcape(); //Is the TileEntity capable of Item handling
    public final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler); //Creating LazyOptional for Item

    private static final Logger LOGGER = LogManager.getLogger("MGC2Multiblock");
    private final CustomBlockPattern pattern;
    private boolean isFormed;
    private List<BlockPos> replacedBlocks = new ArrayList<>();
    private List<BlockState> replacedStates = new ArrayList<>();
    public MenuProvider menuProvider;

    public Multiblock(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState, CustomBlockPattern pattern) {
        super(pType, pPos, pBlockState);
        this.pattern = pattern;
    }



    public void add(Level world, BlockPos pos, Block blockToReplace) {
        if (pattern.matches(world, pos)) {
            isFormed = true;
            replaceBlocks(world, pos, blockToReplace);
            assignID(world, pos);
            setChanged();
            setMulitblockListener(this);
        }
    }

    public void remove(Level world) {
        if (isFormed) {
            isFormed = false;
            restoreBlocks(world);
        }
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (itemcape()) {
            if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
                return handler.cast();
            }
        }
        return super.getCapability(cap);
    }
    private ItemStackHandler createInv() {
        if (itemcape()) {
            return new ItemStackHandler(invsize()) {
                @Override
                protected void onContentsChanged(int slot) {
                    setChanged();
                }

                @Override
                public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                    return super.isItemValid(slot, stack);
                }

                @Nonnull
                @Override
                public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                    return super.insertItem(slot, stack, simulate);
                }
            };
        }else{
            return null;
        }
    }


    public abstract void assignID(Level world, BlockPos pos);
    public void onPlacement(Level world, BlockPos pos, Block blockToReplace) {
        add(world, pos, blockToReplace);
    }

    public void onRemoval(Level world) {
        remove(world);
    }
    @Override
    protected void saveAdditional(CompoundTag tag) {
        if (itemcape()) {
            tag.put("inv", itemHandler.serializeNBT());
        }
        super.saveAdditional(tag);
    }
    @Override
    public void load( CompoundTag tag) {
        if (itemcape()) {
            itemHandler.deserializeNBT(tag.getCompound("inv"));
        }
        super.load(tag);
    }
    public int getInvSize(){
        return this.itemHandler.getSlots();
    }
    public ItemStack getItemInSlot(int size) {
        return this.itemHandler.getStackInSlot(size);
    }
    private void replaceBlocks(Level world, BlockPos pos, Block blockToReplace) {
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    BlockPos newPos = pos.offset(x, y, z);
                    BlockState state = world.getBlockState(newPos);
                    if (state.getBlock() == blockToReplace) {
                        setX(newPos.getX());
                        setY(newPos.getY());
                        setZ(newPos.getZ());
                        SetBlockState(state);
                        world.setBlockAndUpdate(newPos, getReplacementBlock().defaultBlockState());
                    }
                }
            }
        }
    }
    public BlockPos getpos(){
        return GetBlockPos();
    }
    @Override
    public void onChunkUnloaded() {
        onRemoval(level);
        super.onChunkUnloaded();
    }

    @Override
    public void onLoad() {
        tick(level);
        super.onLoad();
    }


    private void restoreBlocks(Level world) {
        if (Multiblockfiller_tile.blockState == null || Multiblockfiller_tile.x == -3000 || Multiblockfiller_tile.y == -3000 || Multiblockfiller_tile.z == -3000) {
            return;
        }

        world.setBlockAndUpdate(new BlockPos(Multiblockfiller_tile.x, Multiblockfiller_tile.y, Multiblockfiller_tile.z), Multiblockfiller_tile.blockState);
        world.setBlockAndUpdate(new BlockPos(Multiblockfiller_tile.x, Multiblockfiller_tile.y, Multiblockfiller_tile.z), Multiblockfiller_tile.blockState);

        Multiblockfiller_tile.blockState = null;
        Multiblockfiller_tile.x = -3000;
        Multiblockfiller_tile.y = -3000;
        Multiblockfiller_tile.z = -3000;
    }


    public abstract Block getReplacementBlock();

    public void tick(Level world) {
        if (world != null && !world.isClientSide) {
            if (!isFormed()) {
                handleMultiblock(level, this.getBlockPos(), pattern);
            }
        }
    }
    public void handleMultiblock(Level world, BlockPos pos, CustomBlockPattern pattern) {
        if (pattern.matches(world, pos)) {
            if (!isFormed()) {
                LOGGER.info("Multiblock is valid and not formed");
                setFormed(true);
                for (int y = 0; y < pattern.getRows().size(); y++) {
                    List<String> layer = pattern.getRows().get(y);
                    for (int x = 0; x < layer.size(); x++) {
                        String row = layer.get(x);
                        for (int z = 0; z < row.length(); z++) {
                            char c = row.charAt(z);
                            if (c == 'c') {
                                continue;
                            }
                            BlockPos offsetPos = pos.offset(x - (layer.size() - 1) / 2, y - (pattern.getRows().size() - 1) / 2, z - (row.length() - 1) / 2);
                            BlockState blockToReplace = world.getBlockState(offsetPos);
                            world.setBlockAndUpdate(offsetPos, blockToReplace);
                        }
                    }
                }
            }
        } else {
            if (isFormed()) {
                setFormed(false);
                restoreBlocks(world);
            } else {
                for (int y = 0; y < pattern.getRows().size(); y++) {
                    List<String> layer = pattern.getRows().get(y);
                    for (int x = 0; x < layer.size(); x++) {
                        String row = layer.get(x);
                        for (int z = 0; z < row.length(); z++) {
                            char c = row.charAt(z);
                            if (c == ' ') {
                                continue;
                            }
                            BlockPos offsetPos = pos.offset(x - (layer.size() - 1) / 2, y - (pattern.getRows().size() - 1) / 2, z - (row.length() - 1) / 2);
                            BlockInWorld blockInWorld = new BlockInWorld(world, offsetPos, true);
                            pattern.markInvalidBlocks(world, blockInWorld.getPos());
                        }
                    }
                }
            }
        }
    }
    public boolean isFormed() {
        return isFormed;
    }
    public void setFormed(boolean formed) {
        isFormed = formed;
    }

    public abstract ResourceLocation getID();

    public abstract boolean canBeRotated();

    public abstract boolean canBeMirrored();
}