package com.magneticraft2.common.systems.multiblock.core;

import com.google.gson.Gson;
import com.magneticraft2.common.systems.multiblock.json.MultiblockStructure;
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
public class MultiblockBlockEntity extends BlockEntity implements Tickable {
    public MultiblockBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    //    private MultiblockController controller;
//    private final MultiblockStructure structure;
//    private boolean isMultiblockFormed;
//    public MultiblockBlockEntity(BlockPos pos, BlockState state) {
//        super(ModBlockEntities.MULTIBLOCK_BLOCK_ENTITY, pos, state);
//        this.structure = loadStructureFromJson();
//        this.controller = null;
//        this.isMultiblockFormed = false;
//    }
    @Override
    public void tick() {

    }



}
