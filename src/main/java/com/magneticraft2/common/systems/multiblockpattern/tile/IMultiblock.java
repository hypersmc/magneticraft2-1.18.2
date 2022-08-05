package com.magneticraft2.common.systems.multiblockpattern.tile;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;

public interface IMultiblock {

    BlockPos getMaster();
    BlockState getMasterState();
    MultiblockMasterTile getMasterTile();
    void setMasterPos(BlockPos pos);
}

