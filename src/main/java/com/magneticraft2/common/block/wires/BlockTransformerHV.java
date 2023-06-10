package com.magneticraft2.common.block.wires;

import com.magneticraft2.common.block.BlockMultiBlockBase;
import com.magneticraft2.common.registry.FinalRegistry;
import com.magneticraft2.common.tile.wire.BlockEntityTransformerHV;
import com.magneticraft2.common.utils.MachinesUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlockTransformerHV extends BlockMultiBlockBase<BlockEntityTransformerHV> {
    public static final IntegerProperty OUTPUT = IntegerProperty.create("output", 0, 2);
    public BlockTransformerHV() {
        super(BlockBehaviour.Properties.of(Material.METAL).strength(3.5F).noOcclusion().requiresCorrectToolForDrops());
        this.registerDefaultState(this.stateDefinition.any().setValue(OUTPUT, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(OUTPUT);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {

        if (!pState.is(pNewState.getBlock())) {
            BlockEntity blockentity = pLevel.getBlockEntity(pPos);
            if (blockentity instanceof BlockEntityTransformerHV) {
                ((BlockEntityTransformerHV) blockentity).onBlockBreak();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new BlockEntityTransformerHV(pPos, pState);
    }


    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide() ? null : createTickerHelper(pBlockEntityType, FinalRegistry.Tile_HVTransformer.get(), BlockEntityTransformerHV::serverTick);
    }

    @Override
    public List<BlockPos> getMachineBlockPosList(BlockPos masterPos, Direction facing) {
        return MachinesUtils.getBlocksIn3x2x3CenteredPlus1OnTop(masterPos);
    }
}
