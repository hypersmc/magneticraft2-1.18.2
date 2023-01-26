package com.magneticraft2.common.block;

import com.magneticraft2.common.registry.FinalRegistry;
import com.magneticraft2.common.systems.heat.CapabilityHeat;
import com.magneticraft2.common.systems.multiblock.Multiblock;
import com.magneticraft2.common.systems.pressure.CapabilityPressure;
import com.magneticraft2.common.systems.watt.CapabilityWatt;
import com.magneticraft2.common.tile.TileEntityMagneticraft2;
import com.magneticraft2.compatibility.TOP.TOPDriver;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public abstract class BlockMagneticraft2 extends BaseEntityBlock implements TOPDriver, EntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static BooleanProperty assembled = BooleanProperty.create("assembled");
    public static BooleanProperty iscore = BooleanProperty.create("iscore");
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final Logger LOGGER = LogManager.getLogger("BlockMGC2Core");
    public BlockPattern pattern;



    public BlockMagneticraft2(Properties p_49795_) {
        super(p_49795_);
    }


    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, Player player, Level world, BlockState blockState, IProbeHitData data) {
        BlockEntity te = world.getBlockEntity(data.getPos());
        if (te instanceof TileEntityMagneticraft2){
            TileEntityMagneticraft2 tile = (TileEntityMagneticraft2) te;
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

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        if (!pState.is(pOldState.getBlock())) {
            BlockEntity blockentity = pLevel.getBlockEntity(pPos);
            if (blockentity instanceof Multiblock) {
                LOGGER.info("Multiblock placed");
                ((Multiblock) blockentity).onPlacement(pLevel, pPos, Blocks.STONE);
            }
        }
        super.onPlace(pState, pLevel, pPos, pOldState, pIsMoving);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pState.is(pNewState.getBlock())) {
            BlockEntity blockentity = pLevel.getBlockEntity(pPos);
            if (blockentity instanceof Multiblock) {
                LOGGER.info("onRemove");
                ((Multiblock) blockentity).onRemoval(pLevel, pPos, FinalRegistry.Block_Multiblock_filler.get());
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }
    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }
}
