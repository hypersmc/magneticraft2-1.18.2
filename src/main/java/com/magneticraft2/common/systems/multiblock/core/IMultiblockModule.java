package com.magneticraft2.common.systems.multiblock.core;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/**
 * @author JumpWatch on 13-05-2023
 * @Project magneticraft2-1.18.2
 * v1.0.0
 */
public interface IMultiblockModule {
    boolean isValid(Level world, BlockPos pos);
    void onActivate(Level world, BlockPos pos);
    void onDeactivate(Level world, BlockPos pos);
    String getModuleKey();
    BlockPos getModuleOffset();
    IMultiblockModule createModule(Level world, BlockPos pos);
}