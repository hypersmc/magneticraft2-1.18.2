package com.magneticraft2.common.item.stage.early.general;

import com.magneticraft2.common.registry.FinalRegistry;
import net.minecraft.world.item.Item;

/**
 * @author JumpWatch on 26-03-2023
 * @Project magneticraft2-1.18.2
 * v1.0.0
 */
public class pebble extends Item {
    public pebble() {
        super(new Properties().stacksTo(64).setNoRepair().tab(FinalRegistry.MC2Items));
    }
}
