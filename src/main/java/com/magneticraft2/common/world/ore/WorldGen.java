package com.magneticraft2.common.world.ore;

import com.magneticraft2.common.registry.FinalRegistry;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber
public class WorldGen {
    public static Holder<PlacedFeature> overworld_stonepebble;
    public static Holder<PlacedFeature> overworld_CHROMITE_ORE_PLACED;
    public static Holder<PlacedFeature> overworld_COBALTITE_DEEPSLATE_ORE_PLACED;
    public static Holder<PlacedFeature> overworld_KIMBERLITE_DEEPSLATE_ORE_PLACED;
    public static Holder<PlacedFeature> overworld_OSMIRIDIUM_DEEPSLATE_ORE_PLACED;
    public static Holder<PlacedFeature> overworld_PLATINIUM_DEEPSLATE_ORE_PLACED;
    public static Holder<PlacedFeature> overworld_SILVER_DEEPSLATE_ORE_PLACED;
    public static Holder<PlacedFeature> overworld_TITANITE_DEEPSLATE_ORE_PLACED;
    public static Holder<PlacedFeature> overworld_WOLFRAMITE_DEEPSLATE_ORE_PLACED;
    public static Holder<PlacedFeature> overworld_URANINITE_DEEPSLATE_ORE_PLACED;
    public static Holder<PlacedFeature> overworld_CINNABAR_ORE_PLACED;
    public static Holder<PlacedFeature> overworld_GARNIERITE_ORE_PLACED;
    public static Holder<PlacedFeature> overworld_MAGNETITE_ORE_PLACED;
    public static Holder<PlacedFeature> overworld_QUARTZ_ORE_PLACED;
    public static Holder<PlacedFeature> overworld_LIMONITE_ORE_PLACED;
    public static Holder<PlacedFeature> overworld_SILICIUM_ORE_PLACED;
    public static Holder<PlacedFeature> overworld_GALENA_ORE_PLACED;
    public static Holder<PlacedFeature> overworld_SULFUR_ORE_PLACED;
    public static Holder<PlacedFeature> overworld_MANGANESE_ORE_PLACED;
    public static Holder<PlacedFeature> overworld_TANTALITE_ORE_PLACED;
    public static Holder<PlacedFeature> overworld_BAUXITE_ORE_PLACED;
    public static Holder<PlacedFeature> overworld_APATITE_ORE_PLACED;
    public static Holder<PlacedFeature> overworld_LIGNITE_ORE_PLACED;
    public static Holder<PlacedFeature> overworld_CHALCOCITE_ORE_PLACED;
    public static Holder<PlacedFeature> overworld_CRYOLITE_ORE_PLACED;
    public static Holder<PlacedFeature> overworld_HEMATITE_ORE_PLACED;
    public static Holder<PlacedFeature> overworld_KAOLINITE_ORE_PLACED;
    public static Holder<PlacedFeature> overworld_SALTPETER_ORE_PLACED;
    public static Holder<PlacedFeature> overworld_CASSITERITE_ORE_PLACED;
    public static Holder<PlacedFeature> overworld_SPHALERITE_ORE_PLACED;
    public static Holder<PlacedFeature> overworld_ANTHRACITE_ORE_PLACED;

    public static void registerPlacedFeatures(){
        overworld_stonepebble = PlacementUtils.register("stonepebble", ConfiguredFeatureMGC2.overworld_pebble, RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
        overworld_CHROMITE_ORE_PLACED = PlacementUtils.register("chromite_ore", ConfiguredFeatureMGC2.overworld_CHROMITE_ORE_CONFIGURED, commonOrePlacement(35, HeightRangePlacement.uniform(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(380))));
        overworld_COBALTITE_DEEPSLATE_ORE_PLACED = PlacementUtils.register("cobaltite_ore", ConfiguredFeatureMGC2.overworld_COBALTITE_DEEPSLATE_ORE_CONFIGURED, commonOrePlacement(35, HeightRangePlacement.uniform(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(380))));
        overworld_KIMBERLITE_DEEPSLATE_ORE_PLACED = PlacementUtils.register("kimberlite_ore", ConfiguredFeatureMGC2.overworld_KIMBERLITE_DEEPSLATE_ORE_CONFIGURED, commonOrePlacement(35, HeightRangePlacement.uniform(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(380))));
        overworld_OSMIRIDIUM_DEEPSLATE_ORE_PLACED = PlacementUtils.register("osmiridium_ore", ConfiguredFeatureMGC2.overworld_OSMIRIDIUM_DEEPSLATE_ORE_CONFIGURED, commonOrePlacement(35, HeightRangePlacement.uniform(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(380))));
        overworld_PLATINIUM_DEEPSLATE_ORE_PLACED = PlacementUtils.register("platinium_ore", ConfiguredFeatureMGC2.overworld_PLATINIUM_DEEPSLATE_ORE_CONFIGURED, commonOrePlacement(35, HeightRangePlacement.uniform(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(380))));
        overworld_SILVER_DEEPSLATE_ORE_PLACED = PlacementUtils.register("silver_ore", ConfiguredFeatureMGC2.overworld_SILVER_DEEPSLATE_ORE_CONFIGURED, commonOrePlacement(35, HeightRangePlacement.uniform(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(380))));
        overworld_TITANITE_DEEPSLATE_ORE_PLACED = PlacementUtils.register("titanite_ore", ConfiguredFeatureMGC2.overworld_TITANITE_DEEPSLATE_ORE_CONFIGURED, commonOrePlacement(35, HeightRangePlacement.uniform(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(380))));
        overworld_WOLFRAMITE_DEEPSLATE_ORE_PLACED = PlacementUtils.register("wolframite_ore", ConfiguredFeatureMGC2.overworld_WOLFRAMITE_DEEPSLATE_ORE_CONFIGURED, commonOrePlacement(35, HeightRangePlacement.uniform(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(380))));
        overworld_GARNIERITE_ORE_PLACED = PlacementUtils.register("garnierite_ore", ConfiguredFeatureMGC2.overworld_GARNIERITE_ORE_CONFIGURED, commonOrePlacement(35, HeightRangePlacement.uniform(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(380))));
        overworld_LIMONITE_ORE_PLACED = PlacementUtils.register("limonite_ore", ConfiguredFeatureMGC2.overworld_LIMONITE_ORE_CONFIGURED, commonOrePlacement(35, HeightRangePlacement.uniform(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(380))));
        overworld_MANGANESE_ORE_PLACED = PlacementUtils.register("manganese_ore", ConfiguredFeatureMGC2.overworld_MANGANESE_ORE_CONFIGURED, commonOrePlacement(35, HeightRangePlacement.uniform(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(380))));
        overworld_URANINITE_DEEPSLATE_ORE_PLACED = PlacementUtils.register("uraninite_ore", ConfiguredFeatureMGC2.overworld_URANINITE_DEEPSLATE_ORE_CONFIGURED, commonOrePlacement(35, HeightRangePlacement.uniform(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(380))));
        overworld_CINNABAR_ORE_PLACED = PlacementUtils.register("cinnabar_ore", ConfiguredFeatureMGC2.overworld_CINNABAR_ORE_CONFIGURED, commonOrePlacement(35, HeightRangePlacement.uniform(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(380))));
        overworld_CASSITERITE_ORE_PLACED = PlacementUtils.register("cassiterite_ore", ConfiguredFeatureMGC2.overworld_CASSITERITE_ORE_CONFIGURED, commonOrePlacement(35, HeightRangePlacement.uniform(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(380))));
        overworld_BAUXITE_ORE_PLACED = PlacementUtils.register("bauxite_ore", ConfiguredFeatureMGC2.overworld_BAUXITE_ORE_CONFIGURED, commonOrePlacement(35, HeightRangePlacement.uniform(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(380))));
        overworld_GALENA_ORE_PLACED = PlacementUtils.register("galena_ore", ConfiguredFeatureMGC2.overworld_GALENA_ORE_CONFIGURED, commonOrePlacement(35, HeightRangePlacement.uniform(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(380))));
        overworld_SPHALERITE_ORE_PLACED = PlacementUtils.register("sphalerite_ore", ConfiguredFeatureMGC2.overworld_SPHALERITE_ORE_CONFIGURED, commonOrePlacement(35, HeightRangePlacement.uniform(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(380))));
        overworld_MAGNETITE_ORE_PLACED = PlacementUtils.register("magnetite_ore", ConfiguredFeatureMGC2.overworld_MAGNETITE_ORE_CONFIGURED, commonOrePlacement(35, HeightRangePlacement.uniform(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(380))));
        overworld_QUARTZ_ORE_PLACED = PlacementUtils.register("quartz_ore", ConfiguredFeatureMGC2.overworld_QUARTZ_ORE_CONFIGURED, commonOrePlacement(35, HeightRangePlacement.uniform(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(380))));
        overworld_SILICIUM_ORE_PLACED = PlacementUtils.register("silicium_ore", ConfiguredFeatureMGC2.overworld_SILICIUM_ORE_CONFIGURED, commonOrePlacement(35, HeightRangePlacement.uniform(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(380))));
        overworld_SULFUR_ORE_PLACED = PlacementUtils.register("sulfur_ore", ConfiguredFeatureMGC2.overworld_SULFUR_ORE_CONFIGURED, commonOrePlacement(35, HeightRangePlacement.uniform(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(380))));
        overworld_TANTALITE_ORE_PLACED = PlacementUtils.register("tantalite_ore", ConfiguredFeatureMGC2.overworld_TANTALITE_ORE_CONFIGURED, commonOrePlacement(35, HeightRangePlacement.uniform(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(380))));
        overworld_APATITE_ORE_PLACED = PlacementUtils.register("apatite_ore", ConfiguredFeatureMGC2.overworld_APATITE_ORE_CONFIGURED, commonOrePlacement(35, HeightRangePlacement.uniform(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(380))));
        overworld_LIGNITE_ORE_PLACED = PlacementUtils.register("lignite_ore", ConfiguredFeatureMGC2.overworld_LIGNITE_ORE_CONFIGURED, commonOrePlacement(35, HeightRangePlacement.uniform(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(380))));
        overworld_CHALCOCITE_ORE_PLACED = PlacementUtils.register("chalococite_ore", ConfiguredFeatureMGC2.overworld_CHALCOCITE_ORE_CONFIGURED, commonOrePlacement(35, HeightRangePlacement.uniform(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(380))));
        overworld_CRYOLITE_ORE_PLACED = PlacementUtils.register("cryolite_ore", ConfiguredFeatureMGC2.overworld_CRYOLITE_ORE_CONFIGURED, commonOrePlacement(35, HeightRangePlacement.uniform(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(380))));
        overworld_HEMATITE_ORE_PLACED = PlacementUtils.register("hematite_ore", ConfiguredFeatureMGC2.overworld_HEMATITE_ORE_CONFIGURED, commonOrePlacement(35, HeightRangePlacement.uniform(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(380))));
        overworld_KAOLINITE_ORE_PLACED = PlacementUtils.register("kaolinite_ore", ConfiguredFeatureMGC2.overworld_KAOLINITE_ORE_CONFIGURED, commonOrePlacement(35, HeightRangePlacement.uniform(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(380))));
        overworld_SALTPETER_ORE_PLACED = PlacementUtils.register("saltpeter_ore", ConfiguredFeatureMGC2.overworld_SALTPETER_ORE_CONFIGURED, commonOrePlacement(35, HeightRangePlacement.uniform(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(380))));
        overworld_ANTHRACITE_ORE_PLACED = PlacementUtils.register("anthracite_ore", ConfiguredFeatureMGC2.overworld_ANTHRACITE_ORE_CONFIGURED, commonOrePlacement(35, HeightRangePlacement.uniform(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(380))));


    }




    @SubscribeEvent
    public static void registerBiomeModification(final BiomeLoadingEvent event) {
        event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Holder.direct(overworld_CHROMITE_ORE_PLACED.value()));
        event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Holder.direct(overworld_COBALTITE_DEEPSLATE_ORE_PLACED.value()));
        event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Holder.direct(overworld_KIMBERLITE_DEEPSLATE_ORE_PLACED.value()));
        event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Holder.direct(overworld_OSMIRIDIUM_DEEPSLATE_ORE_PLACED.value()));
        event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Holder.direct(overworld_PLATINIUM_DEEPSLATE_ORE_PLACED.value()));
        event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Holder.direct(overworld_SILVER_DEEPSLATE_ORE_PLACED.value()));
        event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Holder.direct(overworld_TITANITE_DEEPSLATE_ORE_PLACED.value()));
        event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Holder.direct(overworld_WOLFRAMITE_DEEPSLATE_ORE_PLACED.value()));
        event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Holder.direct(overworld_URANINITE_DEEPSLATE_ORE_PLACED.value()));
        event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Holder.direct(overworld_CINNABAR_ORE_PLACED.value()));
        event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Holder.direct(overworld_GARNIERITE_ORE_PLACED.value()));
        event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Holder.direct(overworld_MAGNETITE_ORE_PLACED.value()));
        event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Holder.direct(overworld_QUARTZ_ORE_PLACED.value()));
        event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Holder.direct(overworld_LIMONITE_ORE_PLACED.value()));
        event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Holder.direct(overworld_SILICIUM_ORE_PLACED.value()));
        event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Holder.direct(overworld_GALENA_ORE_PLACED.value()));
        event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Holder.direct(overworld_SULFUR_ORE_PLACED.value()));
        event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Holder.direct(overworld_MANGANESE_ORE_PLACED.value()));
        event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Holder.direct(overworld_TANTALITE_ORE_PLACED.value()));
        event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Holder.direct(overworld_BAUXITE_ORE_PLACED.value()));
        event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Holder.direct(overworld_APATITE_ORE_PLACED.value()));
        event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Holder.direct(overworld_LIGNITE_ORE_PLACED.value()));
        event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Holder.direct(overworld_CHALCOCITE_ORE_PLACED.value()));
        event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Holder.direct(overworld_CRYOLITE_ORE_PLACED.value()));
        event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Holder.direct(overworld_HEMATITE_ORE_PLACED.value()));
        event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Holder.direct(overworld_KAOLINITE_ORE_PLACED.value()));
        event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Holder.direct(overworld_SALTPETER_ORE_PLACED.value()));
        event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Holder.direct(overworld_CASSITERITE_ORE_PLACED.value()));
        event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Holder.direct(overworld_SPHALERITE_ORE_PLACED.value()));
        event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, Holder.direct(overworld_ANTHRACITE_ORE_PLACED.value()));
        if (!event.getCategory().equals(Biome.BiomeCategory.OCEAN) && !event.getCategory().equals(Biome.BiomeCategory.RIVER)) {
            event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, Holder.direct(overworld_stonepebble.value()));
        }
    }
    // Just here because the vanilla ones are private
    private static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_) {
        return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int p_195344_, PlacementModifier p_195345_) {
        return orePlacement(CountPlacement.of(p_195344_), p_195345_);
    }

    private static List<PlacementModifier> rareOrePlacement(int p_195350_, PlacementModifier p_195351_) {
        return orePlacement(RarityFilter.onAverageOnceEvery(p_195350_), p_195351_);
    }
}
