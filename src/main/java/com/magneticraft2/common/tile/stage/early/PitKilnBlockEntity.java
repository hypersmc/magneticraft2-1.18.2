package com.magneticraft2.common.tile.stage.early;

import com.magneticraft2.common.block.stage.early.PitKilnBlock;
import com.magneticraft2.common.registry.FinalRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
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

import static com.magneticraft2.common.block.stage.early.PitKilnBlock.LOG_COUNT;
import static com.magneticraft2.common.block.stage.early.PitKilnBlock.WHEAT_COUNT;

/**
 * @author JumpWatch on 20-03-2023
 * @Project magneticraft2-1.18.2
 * v1.0.0
 */
public class PitKilnBlockEntity extends BlockEntity {

    private static PitKilnBlockEntity self;
    private boolean isBurning = false;
    private int burnTime = 0;
    private static final Logger LOGGER = LogManager.getLogger("Pitkiln");
    private int totalTime = 0;
    private List<ItemStack> clayItems = new ArrayList<>();
    public final ItemStackHandler itemHandler = createInv(); //Item
    public final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler); //Creating LazyOptional for Item

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction dir) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }
        return LazyOptional.empty();

    }
    public PitKilnBlockEntity(BlockPos pos, BlockState state) {
        super(FinalRegistry.PitKilnblockEntity.get(), pos, state);
        self = this;
    }



    public void activate(BlockState state, Level world, BlockPos pos) {
        this.level = world;
        this.self = this;

        // Check if there are enough items in the kiln's inventory to start the firing process
        PitKilnBlockEntity blockEntity = (PitKilnBlockEntity) world.getBlockEntity(pos);
        LazyOptional<IItemHandler> optionalHandler = blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        IItemHandler itemHandler = optionalHandler.orElse(null);
        if (itemHandler.getStackInSlot(0).getItem() == Items.OAK_LOG && itemHandler.getStackInSlot(0).getCount() == 8 && itemHandler.getStackInSlot(1).getItem() == Items.WHEAT&& itemHandler.getStackInSlot(1).getCount() == 4) {
            // Remove the logs and hay from the kiln's inventory
            itemHandler.extractItem(0, 8, false);
            itemHandler.extractItem(1, 4, false);

            // Start the firing process
            BlockState currentState = level.getBlockState(pos);
            BlockState newState = currentState.setValue(PitKilnBlock.LOG_COUNT, self.getLogCount()).setValue(PitKilnBlock.WHEAT_COUNT, self.getWheatCount()).setValue(PitKilnBlock.ACTIVATED, true);
            level.setBlock(pos, newState, 3);
            isBurning = true;
            burnTime = 200;
            world.playSound(null, pos, SoundEvents.FIRE_AMBIENT, SoundSource.BLOCKS, 1.0F, 1.0F);
            setChanged();
        }
    }
    public static <E extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState estate, E e) {
        if (!level.isClientSide()) {

            if (self.isBurning) {
                // Decrease the burn time and increase the total time
                if (self.burnTime > 0) {
                    LOGGER.info(self.burnTime);
                    self.burnTime--;
                }
                self.totalTime++;
                LOGGER.info(self.totalTime);

                // Update the fire and smoke based on the burn time
                if (self.burnTime == 0 && self.totalTime <= 202) {
                    BlockPos upPos = pos.above();
                    BlockState upState = level.getBlockState(upPos);
                    if (upState.getBlock() == Blocks.FIRE) {
                        level.setBlockAndUpdate(upPos, Blocks.FIRE.defaultBlockState());
                    }
                    LOGGER.info("finished");
                    SoundEvent soundEvent = SoundEvents.FIRE_EXTINGUISH;
                    level.playSound(null, pos, soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F);

                    // Wait for the sound to finish playing
                    level.getBlockTicks().willTickThisTick(pos, level.getBlockState(pos).getBlock());

                    // Continue with the code execution after the sound has finished playing
                    return;
                }

                // Check if the firing process is complete
                if (self.totalTime >= 202) {
                    LOGGER.info("finished2");
                    for (ItemStack clayItem : self.clayItems) {
                        ItemEntity itemEntity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, clayItem);
                        level.addFreshEntity(itemEntity);
                    }
                    BlockPos upPos = pos.above();
                    BlockState upState = level.getBlockState(upPos);
                    if (upState.getBlock() == Blocks.FIRE) {
                        level.setBlockAndUpdate(upPos, Blocks.AIR.defaultBlockState());
                    }
                    self.clayItems.clear();
                    self.burnTime = 0;
                    self.totalTime = 0;
                    self.isBurning = false;
                    BlockState currentState = level.getBlockState(pos);
                    BlockState newState = currentState.setValue(PitKilnBlock.LOG_COUNT, self.getLogCount()).setValue(PitKilnBlock.WHEAT_COUNT, self.getWheatCount()).setValue(PitKilnBlock.ACTIVATED, false);
                    level.setBlock(pos, newState, 3);
                }
            }
            BlockState currentState = level.getBlockState(pos);
            BlockState newState = currentState.setValue(PitKilnBlock.LOG_COUNT, self.getLogCount()).setValue(PitKilnBlock.WHEAT_COUNT, self.getWheatCount());
            level.setBlock(pos, newState, 3);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        isBurning = tag.getBoolean("IsBurning");
        burnTime = tag.getInt("BurnTime");
        totalTime = tag.getInt("TotalTime");
        clayItems.clear();
        int count = tag.getInt("ClayItemCount");
        for (int i = 0; i < count; i++) {
            CompoundTag clayItemTag = tag.getCompound("ClayItem" + i);
            clayItems.add(ItemStack.of(clayItemTag));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("IsBurning", isBurning);
        tag.putInt("BurnTime", burnTime);
        tag.putInt("TotalTime", totalTime);

        // Save the clay items to NBT
        tag.putInt("ClayItemCount", clayItems.size());
        for (int i = 0; i < clayItems.size(); i++) {
            CompoundTag clayItemTag = new CompoundTag();
            clayItems.get(i).setTag(clayItemTag);
            tag.put("ClayItem" + i, clayItemTag);
        }
    }

    // This method is called to add a clay item to the kiln's inventory
    public void addClayItem(ItemStack clayItem) {
        clayItems.add(clayItem);
    }

    // This method is called to remove a clay item from the kiln's inventory
    public ItemStack removeClayItem(int index) {
        if (index >= 0 && index < clayItems.size()) {
            return clayItems.remove(index);
        } else {
            return ItemStack.EMPTY;
        }
    }
    private ItemStackHandler createInv() {
        return new ItemStackHandler(6) {

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

    }
    public int getLogCount() {
        return itemHandler.getStackInSlot(0).getCount();
    }

    public int getWheatCount() {
        return itemHandler.getStackInSlot(1).getCount();
    }


    // This method is called to check if the kiln is currently burning
    public boolean isBurning() {
        return isBurning;
    }

    // This method is called to set the burn state of the kiln
    public void setBurning(boolean isBurning) {
        this.isBurning = isBurning;
    }
}
