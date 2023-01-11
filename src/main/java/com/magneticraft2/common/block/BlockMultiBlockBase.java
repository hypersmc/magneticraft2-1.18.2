package com.magneticraft2.common.block;

import com.magneticraft2.common.systems.heat.CapabilityHeat;
import com.magneticraft2.common.systems.pressure.CapabilityPressure;
import com.magneticraft2.common.systems.watt.CapabilityWatt;
import com.magneticraft2.common.tile.BlockEntityMultiBlockBase;
import com.magneticraft2.compatibility.TOP.TOPDriver;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class BlockMultiBlockBase<TE extends BlockEntityMultiBlockBase> extends BlockHorizontalFacing implements TOPDriver{
    public static BooleanProperty MASTER  = BooleanProperty.create("master");

    protected BlockMultiBlockBase(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        pLevel.setBlock(getMasterPosBasedOnPlace(pPos, pState.getValue(FACING2)), pState.setValue(MASTER, true), 3);
    }




    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pState.is(pNewState.getBlock())) {
            BlockEntity blockentity = pLevel.getBlockEntity(pPos);
            if (blockentity instanceof BlockEntityMultiBlockBase) {
                ((BlockEntityMultiBlockBase) blockentity).breakMultiBlocks();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    

    protected BlockPos getMasterPosBasedOnPlace(BlockPos pos, Direction facing)
    {
        return pos.above();
    }
    public abstract List<BlockPos> getMachineBlockPosList(BlockPos masterPos, Direction facing);


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING2).add(MASTER);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return super.getStateForPlacement(pContext).setValue(MASTER, false).setValue(FACING2, pContext.getHorizontalDirection());
    }
    public TE getBlockEntity(Level level, BlockPos pos){
        return (TE) level.getBlockEntity(pos);
    }


    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, Player player, Level world, BlockState blockState, IProbeHitData data) {
        BlockEntity te = world.getBlockEntity(data.getPos());
        if (te instanceof BlockEntityMultiBlockBase){
            BlockEntityMultiBlockBase tile = (BlockEntityMultiBlockBase) te;
            te.getCapability(CapabilityHeat.HEAT).ifPresent(h -> {
                probeInfo.horizontal(probeInfo.defaultLayoutStyle()).progress(h.getHeatStored() + 0 % 100, 100, probeInfo.defaultProgressStyle().suffix(" HEAT").borderColor(0xFF555555));
            });
            te.getCapability(CapabilityWatt.WATT).ifPresent(h -> {
                probeInfo.horizontal(probeInfo.defaultLayoutStyle()).progress(h.getWattStored() + 0 % 100, 100, probeInfo.defaultProgressStyle().suffix(" WATT").borderColor(0xFF555555));
            });
            te.getCapability(CapabilityPressure.PRESSURE).ifPresent(h -> {
                probeInfo.horizontal(probeInfo.defaultLayoutStyle()).progress(h.getPressureStored() + 0 % 100, 100, probeInfo.defaultProgressStyle().suffix(" PSI").borderColor(0xFF555555));
            });
        }
    }


}
