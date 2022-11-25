package com.magneticraft2.common.block.machines.heat;

import com.magneticraft2.common.block.BlockMagneticraft2;
import com.magneticraft2.common.registry.FinalRegistry;
import com.magneticraft2.common.tile.TileEntityMagneticraft2;
import com.magneticraft2.common.tile.machines.heat.HeatGeneratorTile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.function.ToIntFunction;

public class HeatGeneratorBlock extends BlockMagneticraft2 {


    public HeatGeneratorBlock() {
        super(BlockBehaviour.Properties.of(Material.METAL).strength(3.5F).noOcclusion().requiresCorrectToolForDrops().lightLevel(litBlockEmission(20)));
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, false).setValue(FACING, Direction.NORTH).setValue(assembled, false).setValue(iscore, false));
    }
    private static ToIntFunction<BlockState> litBlockEmission(int pLightValue) {
        return (p_50763_) -> {
            return p_50763_.getValue(BlockStateProperties.LIT) ? pLightValue : 0;
        };
    }


    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite());
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING).add(LIT).add(assembled).add(iscore);
    }



    public BlockPattern createpattern() {
        if (pattern == null){
            pattern = BlockPatternBuilder.start()
                    .aisle("sss","sss","sgs")
                    .aisle("bbb","bbb","bbb")
                    .where('s', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.STONE)))
                    .where('b', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.STONE_BRICKS)))
                    .where('g', BlockInWorld.hasState(BlockStatePredicate.forBlock(FinalRegistry.Block_Heat_Generator.get())))
                    .build();
        }else {
            LOGGER.info("Couldn't build pattern!");
        }
        return pattern;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new HeatGeneratorTile(pPos, pState);
    }



    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {
        if (!world.isClientSide) {

            BlockEntity tileEntity = world.getBlockEntity(pos);
            if (player.isCrouching()){
                LOGGER.info("X: " + blockHitResult.getLocation().x());
                LOGGER.info("Y: " + blockHitResult.getLocation().y());
                LOGGER.info("Z: " + blockHitResult.getLocation().z());
                LOGGER.info("");
                try {
                    BlockPattern.BlockPatternMatch matched = createpattern().find(world,pos);
                    if (matched != null){
                        for (int w = 0; w < createpattern().getWidth(); ++w){
                            for (int h = 0; h < createpattern().getHeight(); ++h){
                                for (int d = 0; d < createpattern().getDepth(); ++d){
                                    LOGGER.info("Width: " + w);
                                    LOGGER.info("Height: " + h);
                                    LOGGER.info("Depth: " + d);
                                    BlockInWorld biw = matched.getBlock(w, h, d);
                                    biw.getState().setValue(assembled, true);
                                    LOGGER.info("SUCCESS");
                                }
                            }
                        }

                    }
                } catch (Exception e) {
                    LOGGER.info("Failed!");
                }
                return InteractionResult.PASS;
            }
            if (tileEntity instanceof TileEntityMagneticraft2) {
                NetworkHooks.openGui((ServerPlayer) player, ((HeatGeneratorTile) tileEntity).menuProvider, tileEntity.getBlockPos());
            }

        }

        return InteractionResult.SUCCESS;
    }


    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide() ? null : createTickerHelper(pBlockEntityType, FinalRegistry.Tile_Heat_Generator.get(), HeatGeneratorTile::serverTick);
    }
    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        if (state.hasProperty(FACING)) {

            return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
        }
        return super.rotate(state, rot);
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        if (state.hasProperty(FACING)) {
            return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
        }
        return super.mirror(state, mirrorIn);
    }
}
