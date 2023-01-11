package com.magneticraft2.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

public abstract class BlockHorizontalFacing extends BaseEntityBlock {
    public static final DirectionProperty FACING2 = HorizontalDirectionalBlock.FACING;


    protected BlockHorizontalFacing(Properties pProperties) {
        super(pProperties);
    }

}
