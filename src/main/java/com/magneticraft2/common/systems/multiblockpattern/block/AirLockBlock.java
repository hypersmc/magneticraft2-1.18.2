package com.magneticraft2.common.systems.multiblockpattern.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class AirLockBlock extends Block{

    public static BooleanProperty TOP = BooleanProperty.create("top");
    public static BooleanProperty OPEN = BooleanProperty.create("open");

    public static VoxelShape TOP_SHAPE = Block.box(0, 0, 0, 16, 1, 16);
    public static VoxelShape BOTTOM_SHAPE = Block.box(0, 15, 0, 16, 16, 16);

    public AirLockBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(OPEN, false).setValue(TOP, false));
    }

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(TOP, OPEN);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        if (!state.getValue(OPEN))
            return Shapes.block();

        if (state.getValue(TOP))
            return BOTTOM_SHAPE;

        return TOP_SHAPE;
    }

    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {


        if (!worldIn.isClientSide  && state.getBlock() != newState.getBlock()) {
            BlockPos other = null;
            if (state.getValue(TOP))
                other = pos.below();
            else other = pos.above();
            worldIn.setBlockAndUpdate(other, Blocks.AIR.defaultBlockState());
        }
        super.onRemove(state, worldIn, pos, newState, isMoving);

    }

    @Override
    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        boolean flag = worldIn.hasNeighborSignal(pos) || worldIn.hasNeighborSignal(pos.relative(state.getValue(TOP) == false ? Direction.UP : Direction.DOWN));
        if (blockIn != this && flag != state.getValue(OPEN)) {
            worldIn.setBlock(pos, state.setValue(TOP, state.getValue(TOP)).setValue(OPEN, Boolean.valueOf(flag)), 2);
            if (worldIn.getBlockState(pos.above()).hasProperty(TOP))
                worldIn.setBlock(pos.above(), state.setValue(TOP, true).setValue(OPEN, Boolean.valueOf(flag)), 2);
            if (worldIn.getBlockState(pos.below()).hasProperty(TOP)) {
                worldIn.setBlock(pos.below(), state.setValue(TOP, false).setValue(OPEN, Boolean.valueOf(flag)), 2);
            }
            worldIn.playSound(null, pos, state.getValue(OPEN) ? SoundEvents.PISTON_EXTEND : SoundEvents.PISTON_CONTRACT, SoundSource.BLOCKS, 0.7F, 1F);
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        BlockPos blockpos = pos.below();
        BlockState blockstate = worldIn.getBlockState(blockpos);
        return state.getValue(TOP) == false ? blockstate.isFaceSturdy(worldIn, blockpos, Direction.UP) : blockstate.is(this);
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter worldIn, BlockPos pos, PathComputationType type) {
        switch(type) {
            case LAND:
                return state.getValue(OPEN);
            case WATER:
                return false;
            case AIR:
                return state.getValue(OPEN);
            default:
                return false;
        }
    }

    public void setDoorState(Level world, BlockPos pos, boolean open) {
        BlockState state = world.getBlockState(pos);

        world.setBlockAndUpdate(pos, state.setValue(OPEN, open));
        if (state.getValue(TOP))
            world.setBlock(pos.below(), world.getBlockState(pos.below()).setValue(OPEN, open), 2);
        else
            world.setBlock(pos.above(), world.getBlockState(pos.above()).setValue(OPEN, open), 2);
        world.playSound(null, pos, state.getValue(OPEN) ? SoundEvents.PISTON_EXTEND : SoundEvents.PISTON_CONTRACT, SoundSource.BLOCKS, 0.7F, 1F);
    }

    public static class AirLockBlockItem extends BlockItem {

        public AirLockBlockItem(Block blockIn, Properties builder) {
            super(blockIn, builder);
        }

        @Override
        public InteractionResult place(BlockPlaceContext context) {
            return context.getLevel().getBlockState(context.getClickedPos().above()).canBeReplaced(context) ? super.place(context) : InteractionResult.FAIL;
        }

        @Override
        protected boolean updateCustomBlockEntityTag(BlockPos pos, Level worldIn, Player player, ItemStack stack, BlockState state) {
            worldIn.setBlockAndUpdate(pos.above(), state.setValue(TOP, true));
            return super.updateCustomBlockEntityTag(pos, worldIn, player, stack, state);
        }

    }

}
