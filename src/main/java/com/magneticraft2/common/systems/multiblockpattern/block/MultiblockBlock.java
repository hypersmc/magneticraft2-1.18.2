package com.magneticraft2.common.systems.multiblockpattern.block;

import com.magneticraft2.common.block.BlockMagneticraft2Pattern;
import com.magneticraft2.common.systems.multiblockpattern.tile.IMultiblock;
import com.magneticraft2.common.systems.multiblockpattern.tile.MultiblockMasterTile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

public class MultiblockBlock extends BlockMagneticraft2Pattern implements EntityBlock {
    public static final Logger LOGGER = LogManager.getLogger();
    public MultiblockBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return 0;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public float getDestroyProgress(BlockState state, Player player, BlockGetter worldIn, BlockPos pos) {
        if (state != this.getMasterState(worldIn, pos))
            return this.getMasterState(worldIn, pos).getDestroyProgress(player, worldIn, pos);
        return super.getDestroyProgress(state, player, worldIn, pos);
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (this.getMasterState(worldIn, pos) != state) {
            if (worldIn.getBlockEntity(pos) != null) {
                if (worldIn.getBlockEntity(pos) instanceof IMultiblock) {
                    IMultiblock multiblock = (IMultiblock)worldIn.getBlockEntity(pos);
                    BlockPos masterPos = multiblock.getMaster();
                    BlockHitResult masterResultHit = new BlockHitResult(hit.getLocation(), hit.getDirection(), masterPos, false);
                    return this.getMasterState(worldIn, pos).use(worldIn, player, handIn, masterResultHit);
                }
            }
            else {
                return this.getMasterState(worldIn, pos).use(worldIn, player, handIn, hit);
            }
        }
        return super.use(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public boolean isPossibleToRespawnInThis() {
        return false;
    }


    public BlockState getMasterState(BlockGetter world, BlockPos pos) {
        BlockEntity te = world.getBlockEntity(pos);
        if (te instanceof IMultiblock)
            return ((IMultiblock) te).getMasterState();
        return world.getBlockState(pos);
    }

    @Override
    public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
        super.playerWillDestroy(worldIn, pos, state, player);
        if (!worldIn.isClientSide() && !player.getAbilities().instabuild) {
            BlockState masterState = getMasterState(worldIn, pos);
            if (state != masterState)
                Block.dropResources(masterState, worldIn, pos);
        }
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        BlockState masterState = getMasterState(world, pos);
        ItemStack masterBlockStack = new ItemStack(masterState.getBlock().asItem());
        return masterBlockStack;
    }

    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        try {
            if (newState.getBlock() != state.getBlock()) {
                try {
                    if (worldIn.getBlockEntity(pos) instanceof MultiblockMasterTile) {
                        for (BlockPos other : ((MultiblockMasterTile) worldIn.getBlockEntity(pos)).getSlavePositions()) {
                            worldIn.setBlockAndUpdate(other, Blocks.AIR.defaultBlockState());
                        }
                    } else {
                        worldIn.setBlockAndUpdate(((IMultiblock) worldIn.getBlockEntity(pos)).getMaster(), Blocks.AIR.defaultBlockState());
                    }
                } catch (Exception ignored) {
                }
            }
        } catch (ClassCastException e) {
            LOGGER.catching(org.apache.logging.log4j.Level.DEBUG, e);
        }
        super.onRemove(state, worldIn, pos, newState, isMoving);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluid = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, fluid.is(FluidTags.WATER));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(BlockStateProperties.WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        BlockState state = stateIn;
        if (state.getValue(BlockStateProperties.WATERLOGGED)) {
            worldIn.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }
        return state;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return null;
    }
}
