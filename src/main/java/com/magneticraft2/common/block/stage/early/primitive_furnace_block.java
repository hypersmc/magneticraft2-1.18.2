package com.magneticraft2.common.block.stage.early;

import com.magneticraft2.common.block.BlockMagneticraft2;
import com.magneticraft2.common.magneticraft2;
import com.magneticraft2.common.registry.FinalRegistry;
import com.magneticraft2.common.systems.multiblock.Multiblock;
import com.magneticraft2.common.tile.Multiblockfiller_tile;
import com.magneticraft2.common.tile.stage.early.primitive_furnace_tile;

import com.magneticraft2.common.tile.testblock;
import com.magneticraft2.common.tile.wire.BlockEntityHVConnectorBase;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

/**
 * @author JumpWatch on 26-01-2023
 * @Project magneticraft2-1.18.2
 * v1.0.0
 */
public class primitive_furnace_block extends BlockMagneticraft2 {
    public static BooleanProperty isFormed = BooleanProperty.create("isformed");
    public static BooleanProperty on = BooleanProperty.create("on");
    public static BooleanProperty full = BooleanProperty.create("full");
    public static BooleanProperty end = BooleanProperty.create("end");
    public static BooleanProperty pot = BooleanProperty.create("pot");


    public primitive_furnace_block() {
        super(BlockBehaviour.Properties.of(Material.METAL).strength(3.5F).noOcclusion().requiresCorrectToolForDrops());
        this.registerDefaultState(this.stateDefinition.any().setValue(isFormed, false).setValue(on, false).setValue(full, false).setValue(end, false).setValue(pot, false));
    }



    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (magneticraft2.devmode) {
                if (pPlayer.getItemInHand(pHand).is(Items.STICK)) {
                    if (blockEntity instanceof Multiblock) {
                        pPlayer.sendMessage(new TranslatableComponent("message.magneticraft2.multiblock.Debug", ((Multiblock) blockEntity).isFormed()), Util.NIL_UUID);
                    }
                }else if (!pPlayer.getItemInHand(pHand).is(Items.STICK)) {
                    if (blockEntity instanceof Multiblock) {
                        NetworkHooks.openGui((ServerPlayer) pPlayer, ((primitive_furnace_tile) blockEntity).menuProvider, blockEntity.getBlockPos());
                    }
                }
            }

        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(isFormed, on, full, end, pot);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new primitive_furnace_tile(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide() ? null : createTickerHelper(pBlockEntityType, FinalRegistry.primitive_furnace_Tile.get(), primitive_furnace_tile::serverTick);
    }

}
