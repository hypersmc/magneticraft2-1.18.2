package com.magneticraft2.common.registry;

import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties rice = (new FoodProperties.Builder())
            .fast()
            .nutrition(1)
            .saturationMod(0.2F)
            .build();
}
