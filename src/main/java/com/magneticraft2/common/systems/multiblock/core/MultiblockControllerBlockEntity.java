package com.magneticraft2.common.systems.multiblock.core;

import net.minecraft.client.renderer.texture.Tickable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * @author JumpWatch on 14-05-2023
 * @Project magneticraft2-1.18.2
 * v1.0.0
 */
public class MultiblockControllerBlockEntity extends BlockEntity  {
    private MultiblockController multiblockController;

    public MultiblockControllerBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
        this.multiblockController = null;
    }
    public MultiblockController getMultiblockController() {
        return multiblockController;
    }

    public void setMultiblockController(MultiblockController multiblockController) {
        this.multiblockController = multiblockController;
    }

    // Override necessary block entity methods as per your requirements


}
