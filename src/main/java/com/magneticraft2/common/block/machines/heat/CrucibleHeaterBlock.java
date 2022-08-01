package com.magneticraft2.common.block.machines.heat;

import com.magneticraft2.common.block.BlockMagneticraft2;
import com.magneticraft2.common.registry.FinalRegistry;
import com.magneticraft2.common.tile.machines.heat.CrucibleHeaterTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;

public class CrucibleHeaterBlock extends BlockMagneticraft2 implements EntityBlock {

    public CrucibleHeaterBlock() {
        super(BlockBehaviour.Properties.of(Material.METAL).strength(3.5F).noOcclusion().requiresCorrectToolForDrops());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CrucibleHeaterTile(pPos, pState);
    }


    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide() ? null : createTickerHelper(pBlockEntityType, FinalRegistry.Tile_Crucible_Heater.get(), CrucibleHeaterTile::serverTick);
    }

}
