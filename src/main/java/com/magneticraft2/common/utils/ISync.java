package com.magneticraft2.common.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;

public interface ISync
{
    Level getThisWorld();

    BlockPos getThisPosition();

    CompoundTag sync();
}