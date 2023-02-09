package com.magneticraft2.common.item.general_items;

import com.magneticraft2.common.block.stage.early.primitive_furnace_block;
import com.magneticraft2.common.registry.FinalRegistry;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

/**
 * @author JumpWatch on 08-02-2023
 * @Project magneticraft2-1.18.2
 * v1.0.0
 */
public class wrench extends Item {
    public wrench() {
        super(new Properties().stacksTo(1).tab(FinalRegistry.MC2Items));
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        return super.useOn(pContext);
    }
}
