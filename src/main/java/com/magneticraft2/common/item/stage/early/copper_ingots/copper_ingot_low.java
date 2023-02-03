package com.magneticraft2.common.item.stage.early.copper_ingots;

import com.magneticraft2.common.registry.FinalRegistry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author JumpWatch on 02-02-2023
 * @Project magneticraft2-1.18.2
 * v1.0.0
 */
public class copper_ingot_low extends Item {
    public copper_ingot_low() {
        super(new Properties().stacksTo(64).setNoRepair().tab(FinalRegistry.MC2Items));
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (Screen.hasShiftDown()){
            pTooltipComponents.add(new TranslatableComponent("tooltip.magneticraft2.copper_ingot_low"));
        } else {
            pTooltipComponents.add(new TranslatableComponent("tooltip.magneticraft2.press_shift"));
        }

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

}
