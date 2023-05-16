package com.magneticraft2.common.world.ore;

import com.magneticraft2.common.registry.FinalRegistry;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

/**
 * @author JumpWatch on 07-02-2023
 * @Project magneticraft2-1.18.2
 * v1.0.0
 */

@Mod.EventBusSubscriber
public class ConfiguredFeatureMGC2 {
    public static Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> overworld_pebble;

    public static Holder<ConfiguredFeature<OreConfiguration, ?>> overworld_CHROMITE_ORE_CONFIGURED;
    public static Holder<ConfiguredFeature<OreConfiguration, ?>> overworld_COBALTITE_DEEPSLATE_ORE_CONFIGURED;
    public static Holder<ConfiguredFeature<OreConfiguration, ?>> overworld_KIMBERLITE_DEEPSLATE_ORE_CONFIGURED;
    public static Holder<ConfiguredFeature<OreConfiguration, ?>> overworld_OSMIRIDIUM_DEEPSLATE_ORE_CONFIGURED;
    public static Holder<ConfiguredFeature<OreConfiguration, ?>> overworld_PLATINIUM_DEEPSLATE_ORE_CONFIGURED;
    public static Holder<ConfiguredFeature<OreConfiguration, ?>> overworld_SILVER_DEEPSLATE_ORE_CONFIGURED;
    public static Holder<ConfiguredFeature<OreConfiguration, ?>> overworld_TITANITE_DEEPSLATE_ORE_CONFIGURED;
    public static Holder<ConfiguredFeature<OreConfiguration, ?>> overworld_WOLFRAMITE_DEEPSLATE_ORE_CONFIGURED;
    public static Holder<ConfiguredFeature<OreConfiguration, ?>> overworld_URANINITE_DEEPSLATE_ORE_CONFIGURED;
    public static Holder<ConfiguredFeature<OreConfiguration, ?>> overworld_CINNABAR_ORE_CONFIGURED;
    public static Holder<ConfiguredFeature<OreConfiguration, ?>> overworld_GARNIERITE_ORE_CONFIGURED;
    public static Holder<ConfiguredFeature<OreConfiguration, ?>> overworld_MAGNETITE_ORE_CONFIGURED;
    public static Holder<ConfiguredFeature<OreConfiguration, ?>> overworld_QUARTZ_ORE_CONFIGURED;
    public static Holder<ConfiguredFeature<OreConfiguration, ?>> overworld_LIMONITE_ORE_CONFIGURED;
    public static Holder<ConfiguredFeature<OreConfiguration, ?>> overworld_SILICIUM_ORE_CONFIGURED;
    public static Holder<ConfiguredFeature<OreConfiguration, ?>> overworld_GALENA_ORE_CONFIGURED;
    public static Holder<ConfiguredFeature<OreConfiguration, ?>> overworld_SULFUR_ORE_CONFIGURED;
    public static Holder<ConfiguredFeature<OreConfiguration, ?>> overworld_MANGANESE_ORE_CONFIGURED;
    public static Holder<ConfiguredFeature<OreConfiguration, ?>> overworld_TANTALITE_ORE_CONFIGURED;
    public static Holder<ConfiguredFeature<OreConfiguration, ?>> overworld_BAUXITE_ORE_CONFIGURED;
    public static Holder<ConfiguredFeature<OreConfiguration, ?>> overworld_APATITE_ORE_CONFIGURED;
    public static Holder<ConfiguredFeature<OreConfiguration, ?>> overworld_LIGNITE_ORE_CONFIGURED;
    public static Holder<ConfiguredFeature<OreConfiguration, ?>> overworld_CHALCOCITE_ORE_CONFIGURED;
    public static Holder<ConfiguredFeature<OreConfiguration, ?>> overworld_CRYOLITE_ORE_CONFIGURED;
    public static Holder<ConfiguredFeature<OreConfiguration, ?>> overworld_HEMATITE_ORE_CONFIGURED;
    public static Holder<ConfiguredFeature<OreConfiguration, ?>> overworld_KAOLINITE_ORE_CONFIGURED;
    public static Holder<ConfiguredFeature<OreConfiguration, ?>> overworld_SALTPETER_ORE_CONFIGURED;
    public static Holder<ConfiguredFeature<OreConfiguration, ?>> overworld_CASSITERITE_ORE_CONFIGURED;
    public static Holder<ConfiguredFeature<OreConfiguration, ?>> overworld_SPHALERITE_ORE_CONFIGURED;
    public static Holder<ConfiguredFeature<OreConfiguration, ?>> overworld_ANTHRACITE_ORE_CONFIGURED;

    public static void registerConfiguredFeatures() {
        overworld_pebble = FeatureUtils.register("stonepebble", Feature.FLOWER, new RandomPatchConfiguration(10, 40, 0, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(FinalRegistry.stonepebbleBlock.get())))));
        overworld_CHROMITE_ORE_CONFIGURED = FeatureUtils.register("chromite_ore", Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES,
                FinalRegistry.CHROMITE_ORE.get().defaultBlockState()),
                OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                        FinalRegistry.CHROMITE_DEEPSLATE_ORE.get().defaultBlockState())), 8));
        overworld_CINNABAR_ORE_CONFIGURED = FeatureUtils.register("cinnabar_ore", Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES,
                FinalRegistry.CINNABAR_ORE.get().defaultBlockState()),
                OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                        FinalRegistry.CINNABAR_DEEPSLATE_ORE.get().defaultBlockState())), 8));
        overworld_GARNIERITE_ORE_CONFIGURED = FeatureUtils.register("garnierite_ore", Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES,
                FinalRegistry.GARNIERITE_ORE.get().defaultBlockState()),
                OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                        FinalRegistry.GARNIERITE_DEEPSLATE_ORE.get().defaultBlockState())), 8));
        overworld_MAGNETITE_ORE_CONFIGURED = FeatureUtils.register("magnetite_ore", Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES,
                FinalRegistry.MAGNETITE_ORE.get().defaultBlockState()),
                OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                        FinalRegistry.MAGNETITE_DEEPSLATE_ORE.get().defaultBlockState())), 8));
        overworld_QUARTZ_ORE_CONFIGURED = FeatureUtils.register("quartz_ore", Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES,
                FinalRegistry.QUARTZ_ORE.get().defaultBlockState()),
                OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                        FinalRegistry.QUARTZ_DEEPSLATE_ORE.get().defaultBlockState())), 8));
        overworld_LIMONITE_ORE_CONFIGURED = FeatureUtils.register("limonite_ore", Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES,
                FinalRegistry.LIMONITE_ORE.get().defaultBlockState()),
                OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                        FinalRegistry.LIMONITE_DEEPSLATE_ORE.get().defaultBlockState())), 8));
        overworld_SILICIUM_ORE_CONFIGURED = FeatureUtils.register("silicium_ore", Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES,
                FinalRegistry.SILICIUM_ORE.get().defaultBlockState()),
                OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                        FinalRegistry.SILICIUM_DEEPSLATE_ORE.get().defaultBlockState())), 8));
        overworld_GALENA_ORE_CONFIGURED = FeatureUtils.register("galena_ore", Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES,
                FinalRegistry.GALENA_ORE.get().defaultBlockState()),
                OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                        FinalRegistry.GALENA_DEEPSLATE_ORE.get().defaultBlockState())), 8));
        overworld_SULFUR_ORE_CONFIGURED = FeatureUtils.register("sulfur_ore", Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES,
                FinalRegistry.SULFUR_ORE.get().defaultBlockState()),
                OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                        FinalRegistry.SULFUR_DEEPSLATE_ORE.get().defaultBlockState())), 8));
        overworld_MANGANESE_ORE_CONFIGURED = FeatureUtils.register("manganese_ore", Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES,
                FinalRegistry.MANGANESE_ORE.get().defaultBlockState()),
                OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                        FinalRegistry.MANGANESE_DEEPSLATE_ORE.get().defaultBlockState())), 8));
        overworld_TANTALITE_ORE_CONFIGURED = FeatureUtils.register("tantalite_ore", Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES,
                FinalRegistry.TANTALITE_ORE.get().defaultBlockState()),
                OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                        FinalRegistry.TANTALITE_DEEPSLATE_ORE.get().defaultBlockState())), 8));


        overworld_BAUXITE_ORE_CONFIGURED = FeatureUtils.register("bauxite_ore", Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES,
                FinalRegistry.BAUXITE_ORE.get().defaultBlockState())), 8));
        overworld_APATITE_ORE_CONFIGURED = FeatureUtils.register("apatite_ore", Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES,
                FinalRegistry.APATITE_ORE.get().defaultBlockState())), 8));
        overworld_LIGNITE_ORE_CONFIGURED = FeatureUtils.register("lignite_ore", Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES,
                FinalRegistry.LIGNITE_ORE.get().defaultBlockState())), 8));
        overworld_COBALTITE_DEEPSLATE_ORE_CONFIGURED = FeatureUtils.register("cobaltite_ore", Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                FinalRegistry.COBALTITE_ORE.get().defaultBlockState())), 8));
        overworld_KIMBERLITE_DEEPSLATE_ORE_CONFIGURED = FeatureUtils.register("kimberlite_ore", Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                FinalRegistry.KIMBERLITE_ORE.get().defaultBlockState())), 8));
        overworld_OSMIRIDIUM_DEEPSLATE_ORE_CONFIGURED = FeatureUtils.register("osmiridium_ore", Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                FinalRegistry.OSMIRIDIUM_ORE.get().defaultBlockState())), 8));
        overworld_PLATINIUM_DEEPSLATE_ORE_CONFIGURED = FeatureUtils.register("platinium_ore", Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                FinalRegistry.PLATINIUM_ORE.get().defaultBlockState())), 8));
        overworld_SILVER_DEEPSLATE_ORE_CONFIGURED = FeatureUtils.register("silver_ore", Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                FinalRegistry.SILVER_ORE.get().defaultBlockState())), 8));
        overworld_TITANITE_DEEPSLATE_ORE_CONFIGURED = FeatureUtils.register("titanite_ore", Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                FinalRegistry.TITANITE_ORE.get().defaultBlockState())), 8));
        overworld_WOLFRAMITE_DEEPSLATE_ORE_CONFIGURED = FeatureUtils.register("wolframite_ore", Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                FinalRegistry.WOLFRAMITE_ORE.get().defaultBlockState())), 8));
        overworld_URANINITE_DEEPSLATE_ORE_CONFIGURED = FeatureUtils.register("uraninite_ore", Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                FinalRegistry.URANINITE_ORE.get().defaultBlockState())), 8));
        overworld_CHALCOCITE_ORE_CONFIGURED = FeatureUtils.register("chalococite_ore", Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES,
                FinalRegistry.CHALCOCITE_ORE.get().defaultBlockState())), 8));
        overworld_CRYOLITE_ORE_CONFIGURED = FeatureUtils.register("cryolite_ore", Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES,
                FinalRegistry.CRYOLITE_ORE.get().defaultBlockState())), 8));
        overworld_HEMATITE_ORE_CONFIGURED = FeatureUtils.register("hematite_ore", Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES,
                FinalRegistry.HEMATITE_ORE.get().defaultBlockState())), 8));
        overworld_KAOLINITE_ORE_CONFIGURED = FeatureUtils.register("kaolinite_ore", Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES,
                FinalRegistry.KAOLINITE_ORE.get().defaultBlockState())), 8));
        overworld_SALTPETER_ORE_CONFIGURED = FeatureUtils.register("saltpeter_ore", Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES,
                FinalRegistry.SALTPETER_ORE.get().defaultBlockState())), 8));
        overworld_CASSITERITE_ORE_CONFIGURED = FeatureUtils.register("cassiterite_ore", Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES,
                FinalRegistry.CASSITERITE_ORE.get().defaultBlockState())), 8));
        overworld_SPHALERITE_ORE_CONFIGURED = FeatureUtils.register("sphalerite_ore", Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES,
                FinalRegistry.SPHALERITE_ORE.get().defaultBlockState())), 8));
        overworld_ANTHRACITE_ORE_CONFIGURED = FeatureUtils.register("anthracite_ore", Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES,
                FinalRegistry.ANTHRACITE_ORE.get().defaultBlockState())), 8));

    }

}
