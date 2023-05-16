package com.magneticraft2.common.item.general_items;

import com.magneticraft2.common.registry.FinalRegistry;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

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
//            if (pContext.getHand().equals(InteractionHand.MAIN_HAND)){
//                BlockEntity blockEntity = pContext.getLevel().getBlockEntity(pContext.getClickedPos());
//                if (blockEntity instanceof Multiblock){
//                    Multiblock multiblock = (Multiblock) blockEntity;
//                    multiblock.tick(pContext.getLevel());
//                    InteractionResultHolder.success(pContext.getPlayer());
//                }
//            }
        }
        return super.useOn(pContext);
    }

}
