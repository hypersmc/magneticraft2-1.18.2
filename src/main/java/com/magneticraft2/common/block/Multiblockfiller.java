package com.magneticraft2.common.block;

import com.magneticraft2.common.tile.Multiblockfiller_tile;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;


/**
 * @author JumpWatch on 25-01-2023
 * @Project magneticraft2-1.18.2
 * v1.0.0
 */
public class Multiblockfiller extends BaseEntityBlock {
    private static final Logger LOGGER = LogManager.getLogger("MGC2MultiblockFiller");
    public Multiblockfiller() {
        super(BlockBehaviour.Properties.of(Material.METAL).strength(3.5F).noOcclusion().requiresCorrectToolForDrops());
    }


    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
//        if (!pLevel.isClientSide) {
//            if (getMultiblockListener() == null) {
//                pPlayer.displayClientMessage(new TranslatableComponent("message.magneticraft2.feature_not_ready_yet"), true);
////                LOGGER.error("Multiblock is null");
//                return InteractionResult.FAIL;
//            }
//            if (getMultiblockListener().isFormed()) {
//                BlockPos corePos = getMultiblockListener().getpos();
//                BlockEntity coreBlockEntity = pLevel.getBlockEntity(corePos);
//                if (coreBlockEntity != null) {
//                    try {
//                        pPlayer.displayClientMessage(new TranslatableComponent("message.magneticraft2.feature_not_ready_yet"), true);
////                        NetworkHooks.openGui((ServerPlayer) pPlayer, ((Multiblock) coreBlockEntity).menuProvider, coreBlockEntity.getBlockPos());
//                    } catch (Exception ignored) {
//                    }
//                }
//            }
//        }

        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new Multiblockfiller_tile(pPos, pState);
    }
}
