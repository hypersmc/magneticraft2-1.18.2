package com.magneticraft2.common.utils;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

/**
 * @author JumpWatch on 20-03-2023
 * @Project magneticraft2-1.18.2
 * v1.0.0
 */
public class MGC2ItemTags {
    public static final TagKey<Item> Clay = bind("ClayItem");


    private MGC2ItemTags() {
    }

    private static TagKey<Item> bind(String pName) {
        return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(pName));
    }

    public static TagKey<Item> create(final ResourceLocation name) {
        return TagKey.create(Registry.ITEM_REGISTRY, name);
    }
}
