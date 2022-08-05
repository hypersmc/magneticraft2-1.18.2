package com.magneticraft2.common.systems.multiblockpattern.tile;

import com.magneticraft2.common.registry.FinalRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class testBlocktile extends MultiblockMasterTile{
    public static final int TAKE_OFF_TIME = 120;


    public float rotation = 0;
    public float prevRotation = 0;

    private AABB renderBox;

    public testBlocktile(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }
    public testBlocktile(BlockPos pos, BlockState state) {
        super(FinalRegistry.test_tile.get(), pos, state);
    }
    public static void tick(Level level, BlockPos pos, BlockState state, testBlocktile blockEntity) {

    }
    @Override
    public AABB getRenderBoundingBox() {
        return renderBox == null ? new AABB(this.getBlockPos()) : renderBox;
    }


}
