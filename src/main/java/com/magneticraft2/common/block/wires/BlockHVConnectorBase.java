package com.magneticraft2.common.block.wires;

import com.magneticraft2.common.block.BlockMagneticraft2;
import com.magneticraft2.common.tile.wire.BlockEntityHVConnectorBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;
public class BlockHVConnectorBase extends BlockMagneticraft2 {

    private static final VoxelShape shape_down = Stream.of(Block.box(7.5, 6.25, 7.5, 8.5, 8.5, 8.5), Block.box(7, 1, 7, 9, 6, 9), Block.box(6.5, 6, 6.5, 9.5, 6.5, 9.5), Block.box(6.5, 5, 6.5, 9.5, 5.5, 9.5), Block.box(6.5, 4, 6.5, 9.5, 4.5, 9.5), Block.box(6.5, 3, 6.5, 9.5, 3.5, 9.5), Block.box(6.5, 2, 6.5, 9.5, 2.5, 9.5), Block.box(6.5, 1, 6.5, 9.5, 1.5, 9.5), Block.box(5, 0, 5, 11, 1, 11)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    protected static final VoxelShape NORTH_AABB = Block.box(0.3125D, 0.3125D, 0.0D, 0.6875D, 0.6875D, 0.5D);
    protected static final VoxelShape SOUTH_AABB = Block.box(0.3125D, 0.3125D, 0.5D, 0.6875D, 0.6875D, 1.0);
    protected static final VoxelShape EAST_AABB = Block.box(0.5D, 0.3125D, 0.3125D, 1.0, 0.6875D, 0.6875D);
    protected static final VoxelShape WEST_AABB = Block.box(0.0D, 0.3125D, 0.3125D, 0.5D, 0.6875D, 0.6875D);
    protected static final VoxelShape UP_AABB = Block.box(0.3125D, 0.5D, 0.3125D, 0.6875D, 1.0D, 0.6875D);
    protected static final VoxelShape DOWN_AABB = Block.box(0.3125D, 0.0D, 0.3125D, 0.6875D, 0.5D, 0.6875D);
    public static final DirectionProperty FACING = DirectionProperty.create("facing");

    public BlockHVConnectorBase() {
        super(BlockBehaviour.Properties.of(Material.METAL).strength(3.5F).noOcclusion().requiresCorrectToolForDrops());
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.DOWN));
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pState.is(pNewState.getBlock())) {
            BlockEntity blockentity = pLevel.getBlockEntity(pPos);
            if (blockentity instanceof BlockEntityHVConnectorBase) {
                ((BlockEntityHVConnectorBase) blockentity).onBlockBreak();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(FACING);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Direction dir = pState.getValue(FACING);
        return switch (dir){
            case DOWN -> shape_down;
            case UP -> UP_AABB;
            case NORTH -> NORTH_AABB;
            case SOUTH -> SOUTH_AABB;
            case WEST -> WEST_AABB;
            case EAST -> EAST_AABB;
        };

    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide) {

        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new BlockEntityHVConnectorBase(pPos, pState);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }
}
