package com.magneticraft2.common.systems.multiblock;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import org.intellij.lang.annotations.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author JumpWatch on 25-01-2023
 * @Project magneticraft2-1.18.2
 * v1.0.0
 */
public abstract class Multiblock extends BlockEntity {
    private final CustomBlockPattern pattern;
    private boolean isFormed;
    private List<BlockPos> replacedBlocks = new ArrayList<>();
    private List<BlockState> replacedStates = new ArrayList<>();
    public MenuProvider menuProvider;

    public Multiblock(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState, CustomBlockPattern pattern) {
        super(pType, pPos, pBlockState);
        this.pattern = pattern;
    }



    public void detect(Level world, BlockPos pos, Block blockToReplace) {
        isFormed = pattern.matches(world, pos);
        if (isFormed()) {
            replaceBlocks(world, pos, blockToReplace);
            assignID(world, pos);
            setChanged();
        } else {
            restoreBlocks(world);

        }
    }
    public abstract void assignID(Level world, BlockPos pos);
    @Override
    protected void saveAdditional(CompoundTag tag) {
        ListTag blocksTag = new ListTag();
        for (int i = 0; i < replacedBlocks.size(); i++) {
            BlockPos blockPos = replacedBlocks.get(i);
            CompoundTag blockTag = new CompoundTag();
            blockTag.putInt("x", blockPos.getX());
            blockTag.putInt("y", blockPos.getY());
            blockTag.putInt("z", blockPos.getZ());
            NbtUtils.writeBlockState(replacedStates.get(i));
            blocksTag.add(blockTag);
        }
        super.saveAdditional(tag);
    }
    public void onPlacement(Level world, BlockPos pos, Block blockToReplace) {
        detect(world, pos, blockToReplace);
    }

    public void onRemoval(Level world, BlockPos pos, Block blockToReplace) {
        detect(world, pos, blockToReplace);
    }

    @Override
    public void load(CompoundTag tag) {
        ListTag blocksTag = tag.getList("replaced_blocks", 10);
        for (int i = 0; i < blocksTag.size(); i++) {
            CompoundTag blockTag = blocksTag.getCompound(i);
            replacedStates.add(NbtUtils.readBlockState(blockTag));
            replacedBlocks.add(new BlockPos(blockTag.getInt("x"), blockTag.getInt("y"), blockTag.getInt("z")));
        }
        super.load(tag);
    }
    private boolean isValidMultiblockStructure(Level world, BlockPos pos) {
        return pattern.matches(world, pos);
    }
    private void replaceBlocks(Level world, BlockPos pos, Block blockToReplace) {
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    BlockPos newPos = pos.offset(x, y, z);
                    BlockState state = world.getBlockState(newPos);
                    if (state.getBlock() == blockToReplace) {
                        replacedBlocks.add(newPos);
                        world.setBlockAndUpdate(newPos, getReplacementBlock().defaultBlockState());
                    }
                }
            }
        }
    }



    private void restoreBlocks(Level world) {
        for (int i = 0; i < replacedBlocks.size(); i++) {
            world.setBlockAndUpdate(replacedBlocks.get(i), replacedStates.get(i));
        }
        replacedBlocks.clear();
        replacedStates.clear();
    }

    public abstract Block getReplacementBlock();
    public abstract <E extends BlockEntity> void serverTick(Level level, BlockPos pos, BlockState state, E e);

    public void tick(Level world) {
        if (world != null && !world.isClientSide) {
            handleMultiblock(level, worldPosition, getBlockState());
        }
    }

    private void handleMultiblock(Level world, BlockPos pos, BlockState state) {
        if (isValidMultiblockStructure(world, pos)) {
            if (!isFormed()) {
                setFormed(true);
                replaceBlocks(world, pos, Blocks.STONE);
            }
        } else {
            if (isFormed()) {
                setFormed(false);
                restoreBlocks(world);
            }
        }
    }
    public boolean isFormed() {
        return isFormed;
    }
    public void setFormed(boolean formed) {
        isFormed = formed;
    }

    public abstract Identifier getID();

    public abstract boolean canBeRotated();

    public abstract boolean canBeMirrored();
}