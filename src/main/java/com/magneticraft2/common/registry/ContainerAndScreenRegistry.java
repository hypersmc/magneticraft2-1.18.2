package com.magneticraft2.common.registry;

import com.magneticraft2.client.gui.container.Heat.ContainerHeatGenerator;
import com.magneticraft2.client.gui.container.Heat.ContainerHeatGeneratorParts;
import com.magneticraft2.client.gui.container.primitive_furnace.containerPrimitive_Furnace;
import com.magneticraft2.client.gui.screen.heat.ScreenHeatGenerator;
import com.magneticraft2.client.gui.screen.heat.ScreenHeatGeneratorParts;
import com.magneticraft2.client.gui.screen.primitive_furnace.primitive_furnace_Screen;
import com.magneticraft2.common.magneticraft2;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(modid = magneticraft2.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ContainerAndScreenRegistry {
    private static final Logger LOGGER = LogManager.getLogger("MGC2-Container&ScreenRegistry");
    private static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, magneticraft2.MOD_ID);
    public static void containerRegistry(){
        CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<MenuType<ContainerHeatGenerator>> HEAT_GENERATOR_CONTAINER = CONTAINERS.register("heat_generator", () -> IForgeMenuType.create((((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.getCommandSenderWorld();
        return new ContainerHeatGenerator(windowId,world,pos,inv,inv.player);
    }))));
    public static final RegistryObject<MenuType<ContainerHeatGeneratorParts>> HEAT_GENERATOR_CONTAINER_PARTS = CONTAINERS.register("heat_generator_parts", () -> IForgeMenuType.create((((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.getCommandSenderWorld();
        return new ContainerHeatGeneratorParts(windowId,world,pos,inv,inv.player);
    }))));
    public static final RegistryObject<MenuType<containerPrimitive_Furnace>> PRIMITIVE_FURNACE_CONTAINER = CONTAINERS.register("primitive_furnace", () -> IForgeMenuType.create((((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Level world = inv.player.getCommandSenderWorld();
        return new containerPrimitive_Furnace(windowId,world,pos,inv,inv.player);
    }))));

    public static void Screen(final FMLClientSetupEvent event) {
        MenuScreens.register(HEAT_GENERATOR_CONTAINER.get(), ScreenHeatGenerator::new);
        MenuScreens.register(HEAT_GENERATOR_CONTAINER_PARTS.get(), ScreenHeatGeneratorParts::new);
        MenuScreens.register(PRIMITIVE_FURNACE_CONTAINER.get(), primitive_furnace_Screen::new);
    }
}
