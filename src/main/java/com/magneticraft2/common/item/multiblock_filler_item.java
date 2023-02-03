package com.magneticraft2.common.item;

import com.magneticraft2.common.registry.FinalRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author JumpWatch on 02-02-2023
 * @Project magneticraft2-1.18.2
 * v1.0.0
 */
public class multiblock_filler_item extends BlockItem {


    public multiblock_filler_item() {
        super(FinalRegistry.Block_Multiblock_filler.get(), new Properties().stacksTo(64).setNoRepair().tab(FinalRegistry.MC2Items));
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(new TranslatableComponent("tooltip.magneticraft2.how"));
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }
}
