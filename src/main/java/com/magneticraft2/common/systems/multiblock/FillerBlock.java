package com.magneticraft2.common.systems.multiblock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * @author JumpWatch on 03-02-2023
 * @Project magneticraft2-1.18.2
 * v1.0.0
 */
public abstract class FillerBlock extends Multiblock {

    public FillerBlock(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState, null);
    }
}
