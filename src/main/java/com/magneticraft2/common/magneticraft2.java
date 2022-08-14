package com.magneticraft2.common;


import com.magneticraft2.common.registry.ContainerAndScreenRegistry;
import com.magneticraft2.common.registry.FinalRegistry;
import com.magneticraft2.common.registry.registry;
import com.magneticraft2.common.systems.heat.CapabilityHeat;
import com.magneticraft2.common.systems.pressure.CapabilityPressure;
import com.magneticraft2.common.systems.watt.CapabilityWatt;
import com.magneticraft2.common.utils.Magneticraft2ConfigCommon;
import com.magneticraft2.common.utils.ReloadDataEvent;
import com.magneticraft2.common.utils.mbthings.Queues;
import com.magneticraft2.common.utils.mbthings.Util;
import com.magneticraft2.compatibility.TOP.TOPCompatibility;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

import java.util.ArrayList;
import java.util.HashMap;

@Mod(magneticraft2.MOD_ID)
public class magneticraft2 {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final boolean devmode = true;
    // used to ensure i dont tick things twice
    private static long tick = 0;
    public static final String MOD_ID = "magneticraft2";

    public magneticraft2(){
        if (devmode) {
            LOGGER.warn("WARNING DEV MODE ACTIVATED");
            LOGGER.info("Please report to author!");
        }
        LOGGER.info("Starting Registry");
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        GeckoLib.initialize();
        FinalRegistry.register();
        modEventBus.addListener(this::preinit);
        modEventBus.addListener(this::registerCapabilities);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ContainerAndScreenRegistry::Screen);
        MinecraftForge.EVENT_BUS.register(this);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(magneticraft2.this::clientSetup);
        });
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Magneticraft2ConfigCommon.SPEC, "magneticraft2-common.toml");

    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void clientSetup(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(FinalRegistry.rice_plant.get(), RenderType.cutout());
    }

    public void preinit(FMLCommonSetupEvent event){
        if (ModList.get().isLoaded("theoneprobe")){
            TOPCompatibility.register();
            LOGGER.info("The one probe compatibility done!");
        }
    }
    public void registerCapabilities(RegisterCapabilitiesEvent event){
        CapabilityHeat.register(event);
        CapabilityPressure.register(event);
        CapabilityWatt.register(event);
    }




    //Multiblockhandling DON'T TOUCH

}
