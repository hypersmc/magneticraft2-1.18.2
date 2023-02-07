package com.magneticraft2.common.recipe;

import com.magneticraft2.common.magneticraft2;
import com.magneticraft2.common.recipe.recipes.primitive_furnace_recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * @author JumpWatch on 06-02-2023
 * @Project magneticraft2-1.18.2
 * v1.0.0
 */
public class RecipeRegistry {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, magneticraft2.MOD_ID);

    public static final RegistryObject<RecipeSerializer<primitive_furnace_recipe>> PRIMITIVE_FURNACE_SERIALIZER =
            RECIPE_SERIALIZERS.register("primitive_furnace_crafting", () -> primitive_furnace_recipe.Serializer.INSTANCE);


    public static void registerRecipes(IEventBus eventBus) {
        RECIPE_SERIALIZERS.register(eventBus);
    }
}
