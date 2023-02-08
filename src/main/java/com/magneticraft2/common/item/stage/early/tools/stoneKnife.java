package com.magneticraft2.common.item.stage.early.tools;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

/**
 * @author JumpWatch on 08-02-2023
 * @Project magneticraft2-1.18.2
 * v1.0.0
 */
public class stoneKnife extends SwordItem {
    public stoneKnife(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }


    @Override
    public boolean isValidRepairItem(ItemStack pToRepair, ItemStack pRepair) {
        return pRepair.getItem() == Items.COBBLESTONE.asItem();
    }
}
