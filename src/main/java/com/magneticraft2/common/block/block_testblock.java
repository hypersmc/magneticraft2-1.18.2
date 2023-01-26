package com.magneticraft2.common.block;

import com.magneticraft2.common.registry.FinalRegistry;
import com.magneticraft2.common.systems.multiblock.Multiblock;
import com.magneticraft2.common.tile.machines.heat.HeatGeneratorTile;
import com.magneticraft2.common.tile.testblock;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

/**
 * @author JumpWatch on 25-01-2023
 * @Project magneticraft2-1.18.2
 * v1.0.0
 */
public class block_testblock {// extends BlockMagneticraft2 {
//    public block_testblock() {
//        super(BlockBehaviour.Properties.of(Material.METAL).strength(3.5F).noOcclusion().requiresCorrectToolForDrops());
//    }
//
//    @Override
//    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
//        if (!pLevel.isClientSide) {
//            if (pPlayer.getItemInHand(pHand).is(Items.STICK)){
//                BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
//                if (blockEntity instanceof Multiblock) {
//                    pPlayer.sendMessage(new TranslatableComponent("message.magneticraft2.multiblock.Debug", ((Multiblock) blockEntity).isFormed()), Util.NIL_UUID);
//                }
//            }
//        }
//        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
//    }
//
//    @Nullable
//    @Override
//    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
//        return new testblock(pPos, pState);
//    }
//
//    @Nullable
//    @Override
//    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
//        return pLevel.isClientSide() ? null : createTickerHelper(pBlockEntityType, FinalRegistry.Tile_testblock.get(), testblock::serverTick);
//    }
}
