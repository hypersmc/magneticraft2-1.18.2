package com.magneticraft2.common.item.general_items;

import com.magneticraft2.common.registry.FinalRegistry;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

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
