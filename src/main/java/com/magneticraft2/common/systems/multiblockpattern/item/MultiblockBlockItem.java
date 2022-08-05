package com.magneticraft2.common.systems.multiblockpattern.item;


import com.magneticraft2.common.registry.FinalRegistry;
import com.magneticraft2.common.systems.multiblockpattern.MultiblockPatterns;
import com.magneticraft2.common.systems.multiblockpattern.tile.IMultiblock;
import com.magneticraft2.common.systems.multiblockpattern.tile.MultiblockMasterTile;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;

public class MultiblockBlockItem extends BlockItem {
    private final MultiblockPatterns.MultiblockPattern pattern;

    public MultiblockBlockItem(Block blockIn, MultiblockPatterns.MultiblockPattern pattern, Properties builder) {
        super(blockIn, builder);
        this.pattern = pattern;
    }

    @Override
    protected boolean placeBlock(BlockPlaceContext context, BlockState state) {
        boolean canBePlaced = super.placeBlock(context, state);
        MultiblockMasterTile master = ((MultiblockMasterTile) context.getLevel().getBlockEntity(context.getClickedPos()));
        if (canBePlaced) {
            for (BlockPos pos : this.pattern.getPositions()) {
                pos = pos.offset(context.getClickedPos());
                if (pos.equals(context.getClickedPos()))
                    continue;
                FluidState fluid = context.getLevel().getFluidState(pos);
                context.getLevel().setBlockAndUpdate(pos, FinalRegistry.multiblock.get().defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, fluid.is(FluidTags.WATER)));
                BlockEntity te = context.getLevel().getBlockEntity(pos);
                if (te instanceof IMultiblock) {
                    ((IMultiblock) te).setMasterPos(context.getClickedPos());
                    master.addSlavePos(pos);
                }
            }
        }
        return canBePlaced;
    }

    @Override
    protected boolean canPlace(BlockPlaceContext context, BlockState state) {
        for (BlockPos raw : this.pattern.getPositions()) {
            if (!context.getLevel().getBlockState(raw.offset(context.getClickedPos())).getMaterial().isReplaceable()) {
                context.getPlayer().displayClientMessage(new TranslatableComponent("message.tardis.multiblock.invalid_place"), true);
                return false;
            }
        }
        return super.canPlace(context, state);
    }

}

