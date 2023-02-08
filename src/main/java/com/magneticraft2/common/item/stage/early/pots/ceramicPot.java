package com.magneticraft2.common.item.stage.early.pots;

import com.magneticraft2.common.registry.FinalRegistry;
import net.minecraft.world.item.Item;

/**
 * @author JumpWatch on 08-02-2023
 * @Project magneticraft2-1.18.2
 * v1.0.0
 */
public class ceramicPot extends Item {
    public ceramicPot() {
        super(new Properties().stacksTo(1).setNoRepair().tab(FinalRegistry.MC2Items));
    }
}
