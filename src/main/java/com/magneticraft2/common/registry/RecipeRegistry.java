package com.magneticraft2.common.registry;

import com.magneticraft2.common.magneticraft2;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RecipeRegistry {

    private static final Logger LOGGER = LogManager.getLogger();
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, magneticraft2.MOD_ID);

    private RecipeRegistry() {

    }

    static void register(IEventBus eventBus) {
        RECIPE_SERIALIZER.register(eventBus);
        LOGGER.info("Started registering recipe types!");

    }
}
