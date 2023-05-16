package com.magneticraft2.common.block.stage.early;

import com.magneticraft2.common.block.BlockMagneticraft2;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;

/**
 * @author JumpWatch on 28-04-2023
 * @Project magneticraft2-1.18.2
 * v1.0.0
 */
public class CampFireBlock extends BlockMagneticraft2 {
    public CampFireBlock() {
        super(BlockBehaviour.Properties.of(Material.DIRT).noOcclusion().requiresCorrectToolForDrops());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return null;
    }
}
