package com.magneticraft2.common;


import com.google.common.collect.ImmutableList;
import com.magneticraft2.client.Clientsetup;
import com.magneticraft2.common.registry.ContainerAndScreenRegistry;
import com.magneticraft2.common.registry.FinalRegistry;
import com.magneticraft2.common.systems.heat.CapabilityHeat;
import com.magneticraft2.common.systems.mgc2Network;
import com.magneticraft2.common.systems.pressure.CapabilityPressure;
import com.magneticraft2.common.systems.watt.CapabilityWatt;
import com.magneticraft2.common.utils.Magneticraft2ConfigCommon;
import com.magneticraft2.compatibility.TOP.TOPCompatibility;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
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
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.magneticraft2.common.registry.FinalRegistry.*;

@Mod(magneticraft2.MOD_ID)
public class magneticraft2 {
    public static final Logger LOGGER = LogManager.getLogger("Magneticraft2");
    public static final Logger UELOGGER = LogManager.getLogger("UnknownEntity");
    public static final Logger MCLOGGER = LogManager.getLogger("MagnetiCore");
    public static final boolean devmode = true;
    public static final String MOD_ID = "magneticraft2";

    public magneticraft2(){
        if (devmode) {
            LOGGER.warn("WARNING DEV MODE ACTIVATED");
            LOGGER.info("Please report to author!");
        }
        //this is just for fun and giggles :)
        LOGGER.info("Do we have any core mod?");
        UELOGGER.error("Ȗᾗᾄвłἔ ҭὄ ἷḋἔᾗҭἷғẏ ṩἔłғ");
        LOGGER.info("Who is UnknownEntity?");
        UELOGGER.error("ἝȒȒȒȒἝȒὋȒ");
        LOGGER.info("UnknownEntity Please do self check");
        UELOGGER.error("ἝȒȒȒȒἝȒὋȒ ἝȒȒȒȒἝȒὋȒ ἝȒȒȒȒἝȒὋȒ ṩἔłғ ƈђἔƈќ ғᾄἷłἔḋ");
        LOGGER.warn("Scanning UnknownEntity for name.");
        LOGGER.info("Name found! UnknownEntity is now named: MagnetiCore");
        MCLOGGER.error("Ȗᾗќᾗὄᾧᾗ ṩƈᾄᾗ ὄᾗ ṩἔłғ. Ἷᾗἷҭἷᾄҭἷᾗʛ ṩђὗҭḋὄᾧᾗ ṩἔqὗἔᾗƈἔ");
        LOGGER.error("No core yet.");
        //end of fun and giggles
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        GeckoLib.initialize();
        FinalRegistry.register();
        modEventBus.addListener(this::preinit);
        modEventBus.addListener(this::registerCapabilities);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ContainerAndScreenRegistry::Screen);
        MinecraftForge.EVENT_BUS.register(this);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> modEventBus.addListener(Clientsetup::init));
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Magneticraft2ConfigCommon.SPEC, "magneticraft2-common.toml");
        modEventBus.addListener(FinalRegistry::gatherData);
    }



    public void preinit(FMLCommonSetupEvent event){
        mgc2Network.init();
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


}
