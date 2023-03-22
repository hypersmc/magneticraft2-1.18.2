package com.magneticraft2.common.item.general_items;

import com.magneticraft2.common.block.stage.early.primitive_furnace_block;
import com.magneticraft2.common.registry.FinalRegistry;
import com.magneticraft2.common.systems.multiblock.Multiblock;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * @author JumpWatch on 08-02-2023
 * @Project magneticraft2-1.18.2
 * v1.0.0
 */
public class hammer extends Item {
    public hammer() {
        super(new Properties().stacksTo(1).tab(FinalRegistry.MC2Items));
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if (pContext.getLevel().isClientSide) InteractionResultHolder.pass(pContext.getPlayer());
        else {
            if (pContext.getHand().equals(InteractionHand.MAIN_HAND)){
                BlockEntity blockEntity = pContext.getLevel().getBlockEntity(pContext.getClickedPos());
                if (blockEntity instanceof Multiblock){
                    Multiblock multiblock = (Multiblock) blockEntity;
                    multiblock.tick(pContext.getLevel());
                    InteractionResultHolder.success(pContext.getPlayer());
                }
            }
        }
        return super.useOn(pContext);
    }

}
