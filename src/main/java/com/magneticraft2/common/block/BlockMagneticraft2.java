package com.magneticraft2.common.block;

import com.magneticraft2.common.systems.heat.CapabilityHeat;
import com.magneticraft2.common.systems.pressure.CapabilityPressure;
import com.magneticraft2.common.systems.watt.CapabilityWatt;
import com.magneticraft2.common.tile.TileEntityMagneticraft2;
import com.magneticraft2.common.utils.BlockModule;
import com.magneticraft2.common.utils.IModularBlock;
import com.magneticraft2.compatibility.TOP.TOPDriver;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


public abstract class BlockMagneticraft2 extends BaseEntityBlock implements TOPDriver, EntityBlock, IModularBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final Logger LOGGER = LogManager.getLogger();
    private Int2ObjectMap<BlockModule<?>> modules;
    private List<BlockModule<?>> moduleList;


    public BlockMagneticraft2(Properties p_49795_) {
        super(p_49795_);


    }
    @Override
    public BlockModule<?> module(Class<? extends IModularBlock> interfaceClazz) {
        return modules.get(interfaceClazz.hashCode());
    }

    @Override
    public List<BlockModule<?>> modules() {
        return moduleList;
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




}
