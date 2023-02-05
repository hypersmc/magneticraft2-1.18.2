package com.magneticraft2.common.systems;

import com.magneticraft2.common.registry.FinalRegistry;
import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class OreGen {
    public static final int OVERWORLD_VEINSIZE = 5;
    public static final int OVERWORLD_AMOUNT = 3;
    public static final int DEEPSLATE_VEINSIZE = 5;
    public static final int DEEPSLATE_AMOUNT = 3;
    public static final int NETHER_VEINSIZE = 5;
    public static final int NETHER_AMOUNT = 3;
    public static final int END_VEINSIZE = 10;
    public static final int END_AMOUNT = 6;

    public static final RuleTest IN_ENDSTONE = new TagMatchTest(Tags.Blocks.END_STONES);
    public static final RuleTest IN_STONE = new TagMatchTest(Tags.Blocks.END_STONES);

    public static Holder<PlacedFeature> MYSTERIOUS_OREGEN;
    public static Holder<PlacedFeature> OVERWORLD_OREGEN;
    public static Holder<PlacedFeature> DEEPSLATE_OREGEN;
    public static Holder<PlacedFeature> NETHER_OREGEN;
    public static Holder<PlacedFeature> END_OREGEN;

    public static void registerConfiguredFeatures() {
        //deepslate
        OreConfiguration overworld_CHROMITE_DEEPSLATE_ORE = new OreConfiguration(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                FinalRegistry.CHROMITE_DEEPSLATE_ORE.get().defaultBlockState(), DEEPSLATE_VEINSIZE);
//        OVERWORLD_OREGEN = registerPlacedFeature("overworld_CHROMITE_DEEPSLATE_ORE", Feature.ORE.configured(overworld_CHROMITE_DEEPSLATE_ORE),
//                CountPlacement.of(OVERWORLD_AMOUNT),
//                InSquarePlacement.spread(),
//                BiomeFilter.biome(),
//                HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(90)));
        OreConfiguration overworld_COBALTITE_DEEPSLATE_ORE = new OreConfiguration(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                FinalRegistry.COBALTITE_ORE.get().defaultBlockState(), DEEPSLATE_VEINSIZE);
        OreConfiguration overworld_KIMBERLITE_DEEPSLATE_ORE = new OreConfiguration(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                FinalRegistry.KIMBERLITE_ORE.get().defaultBlockState(), DEEPSLATE_VEINSIZE);
        OreConfiguration overworld_OSMIRIDIUM_DEEPSLATE_ORE = new OreConfiguration(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                FinalRegistry.OSMIRIDIUM_ORE.get().defaultBlockState(), DEEPSLATE_VEINSIZE);
        OreConfiguration overworld_PLATINIUM_DEEPSLATE_ORE = new OreConfiguration(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                FinalRegistry.PLATINIUM_ORE.get().defaultBlockState(), DEEPSLATE_VEINSIZE);
        OreConfiguration overworld_SILVER_DEEPSLATE_ORE = new OreConfiguration(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                FinalRegistry.SILVER_ORE.get().defaultBlockState(), DEEPSLATE_VEINSIZE);
        OreConfiguration overworld_TITANITE_DEEPSLATE_ORE = new OreConfiguration(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                FinalRegistry.TITANITE_ORE.get().defaultBlockState(), DEEPSLATE_VEINSIZE);
        OreConfiguration overworld_WOLFRAMITE_DEEPSLATE_ORE = new OreConfiguration(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                FinalRegistry.WOLFRAMITE_ORE.get().defaultBlockState(), DEEPSLATE_VEINSIZE);
        OreConfiguration overworld_URANINITE_DEEPSLATE_ORE = new OreConfiguration(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                FinalRegistry.URANINITE_ORE.get().defaultBlockState(), DEEPSLATE_VEINSIZE);
        OreConfiguration overworld_CINNABAR_DEEPSLATE_ORE = new OreConfiguration(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                FinalRegistry.CINNABAR_DEEPSLATE_ORE.get().defaultBlockState(), DEEPSLATE_VEINSIZE);
        OreConfiguration overworld_GARNIERITEE_DEEPSLATE_ORE = new OreConfiguration(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                FinalRegistry.GARNIERITE_DEEPSLATE_ORE.get().defaultBlockState(), DEEPSLATE_VEINSIZE);
        OreConfiguration overworld_MAGNETITE_DEEPSLATE_ORE = new OreConfiguration(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                FinalRegistry.MAGNETITE_DEEPSLATE_ORE.get().defaultBlockState(), DEEPSLATE_VEINSIZE);
        OreConfiguration overworld_QUARTZ_DEEPSLATE_ORE = new OreConfiguration(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                FinalRegistry.QUARTZ_DEEPSLATE_ORE.get().defaultBlockState(), DEEPSLATE_VEINSIZE);
        OreConfiguration overworld_LIMONITE_DEEPSLATE_ORE = new OreConfiguration(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                FinalRegistry.LIMONITE_DEEPSLATE_ORE.get().defaultBlockState(), DEEPSLATE_VEINSIZE);
        OreConfiguration overworld_SILICIUM_DEEPSLATE_ORE = new OreConfiguration(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                FinalRegistry.SILICIUM_DEEPSLATE_ORE.get().defaultBlockState(), DEEPSLATE_VEINSIZE);
        OreConfiguration overworld_GALENA_DEEPSLATE_ORE = new OreConfiguration(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                FinalRegistry.GALENA_DEEPSLATE_ORE.get().defaultBlockState(), DEEPSLATE_VEINSIZE);
        OreConfiguration overworld_SULFUR_DEEPSLATE_ORE = new OreConfiguration(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                FinalRegistry.SULFUR_DEEPSLATE_ORE.get().defaultBlockState(), DEEPSLATE_VEINSIZE);
        OreConfiguration overworld_MANGANESE_DEEPSLATE_ORE = new OreConfiguration(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                FinalRegistry.MANGANESE_DEEPSLATE_ORE.get().defaultBlockState(), DEEPSLATE_VEINSIZE);
        OreConfiguration overworld_TANTALITE_DEEPSLATE_ORE = new OreConfiguration(OreFeatures.DEEPSLATE_ORE_REPLACEABLES,
                FinalRegistry.TANTALITE_DEEPSLATE_ORE.get().defaultBlockState(), DEEPSLATE_VEINSIZE);


        //normal ores
        OreConfiguration overworld_bauzite_ore = new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES,
                FinalRegistry.BAUXITE_ORE.get().defaultBlockState(), OVERWORLD_VEINSIZE);
        OreConfiguration overworld_APATITE_ORE = new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES,
                FinalRegistry.APATITE_ORE.get().defaultBlockState(), OVERWORLD_VEINSIZE);
        OreConfiguration overworld_CHROMITE_ORE = new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES,
                FinalRegistry.CHROMITE_ORE.get().defaultBlockState(), OVERWORLD_VEINSIZE);
        OreConfiguration overworld_LIGNITE_ORE = new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES,
                FinalRegistry.LIGNITE_ORE.get().defaultBlockState(), OVERWORLD_VEINSIZE);
        OreConfiguration overworld_CHALCOCITE_ORE = new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES,
                FinalRegistry.CHALCOCITE_ORE.get().defaultBlockState(), OVERWORLD_VEINSIZE);
        OreConfiguration overworld_CRYOLITE_ORE = new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES,
                FinalRegistry.CRYOLITE_ORE.get().defaultBlockState(), OVERWORLD_VEINSIZE);
        OreConfiguration overworld_HEMATITE_ORE = new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES,
                FinalRegistry.HEMATITE_ORE.get().defaultBlockState(), OVERWORLD_VEINSIZE);
        OreConfiguration overworld_KAOLINITE_ORE = new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES,
                FinalRegistry.KAOLINITE_ORE.get().defaultBlockState(), OVERWORLD_VEINSIZE);
        OreConfiguration overworld_GALENA_ORE_ORE = new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES,
                FinalRegistry.GALENA_ORE.get().defaultBlockState(), OVERWORLD_VEINSIZE);
        OreConfiguration overworld_MAGNETITE_ORE = new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES,
                FinalRegistry.MAGNETITE_ORE.get().defaultBlockState(), OVERWORLD_VEINSIZE);
        OreConfiguration overworld_LIMONITE_ORE = new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES,
                FinalRegistry.LIMONITE_ORE.get().defaultBlockState(), OVERWORLD_VEINSIZE);
        OreConfiguration overworld_MANGANESE_ORE = new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES,
                FinalRegistry.MANGANESE_ORE.get().defaultBlockState(), OVERWORLD_VEINSIZE);
        OreConfiguration overworld_CINNABAR_ORE = new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES,
                FinalRegistry.CINNABAR_ORE.get().defaultBlockState(), OVERWORLD_VEINSIZE);
        OreConfiguration overworld_GARNIERITE_ORE = new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES,
                FinalRegistry.GARNIERITE_ORE.get().defaultBlockState(), OVERWORLD_VEINSIZE);
        OreConfiguration overworld_QUARTZ_ORE = new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES,
                FinalRegistry.QUARTZ_ORE.get().defaultBlockState(), OVERWORLD_VEINSIZE);
        OreConfiguration overworld_SALTPETER_ORE = new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES,
                FinalRegistry.SALTPETER_ORE.get().defaultBlockState(), OVERWORLD_VEINSIZE);
        OreConfiguration overworld_SILICIUM_ORE = new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES,
                FinalRegistry.SILICIUM_ORE.get().defaultBlockState(), OVERWORLD_VEINSIZE);
        OreConfiguration overworld_SULFUR_ORE = new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES,
                FinalRegistry.SULFUR_ORE.get().defaultBlockState(), OVERWORLD_VEINSIZE);
        OreConfiguration overworld_TANTALITE_ORE = new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES,
                FinalRegistry.TANTALITE_ORE.get().defaultBlockState(), OVERWORLD_VEINSIZE);
        OreConfiguration overworld_CASSITERITE_ORE = new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES,
                FinalRegistry.CASSITERITE_ORE.get().defaultBlockState(), OVERWORLD_VEINSIZE);
        OreConfiguration overworld_SPHALERITE_ORE = new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES,
                FinalRegistry.SPHALERITE_ORE.get().defaultBlockState(), OVERWORLD_VEINSIZE);
        OreConfiguration overworld_ANTHRACITE_ORE = new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES,
                FinalRegistry.ANTHRACITE_ORE.get().defaultBlockState(), OVERWORLD_VEINSIZE);


    }
    private static <C extends FeatureConfiguration, F extends Feature<C>> Holder<PlacedFeature> registerPlacedFeature(String registryName, ConfiguredFeature<C, F> feature, PlacementModifier... placementModifiers) {
        return PlacementUtils.register(registryName, Holder.direct(feature), placementModifiers);
    }
    public static void onBiomeLoadingEvent(BiomeLoadingEvent event) {
        if (event.getCategory() == Biome.BiomeCategory.NETHER) {
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, NETHER_OREGEN);
        } else if (event.getCategory() == Biome.BiomeCategory.THEEND) {
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, END_OREGEN);
        } else {
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, MYSTERIOUS_OREGEN);
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OVERWORLD_OREGEN);
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, DEEPSLATE_OREGEN);
        }
    }
}
