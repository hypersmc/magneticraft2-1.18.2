package com.magneticraft2.common.registry;

import net.minecraft.world.item.Item;
import net.minecraftforge.event.RegistryEvent;

public class ItemRegistry {
    static void register() {}
    //registries bellow

    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll();
    }
}
