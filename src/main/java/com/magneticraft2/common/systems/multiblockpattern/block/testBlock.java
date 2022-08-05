package com.magneticraft2.common.systems.multiblockpattern.block;

import com.magneticraft2.common.registry.FinalRegistry;
import com.magneticraft2.common.registry.Prop;
import com.magneticraft2.common.systems.multiblockpattern.tile.testBlocktile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class testBlock extends MultiblockBlock{
    public testBlock() {
        super(Prop.Blocks.BASIC_TECH.get());
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, FinalRegistry.test_tile.get(), testBlocktile::tick);
    }
}
