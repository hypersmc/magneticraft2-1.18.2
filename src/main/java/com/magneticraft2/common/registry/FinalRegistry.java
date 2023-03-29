package com.magneticraft2.common.registry;

import com.google.common.collect.ImmutableList;
import com.magneticraft2.common.block.Multiblockfiller;
import com.magneticraft2.common.block.crops.RicePlantBlock;
import com.magneticraft2.common.block.machines.heat.CrucibleHeaterBlock;
import com.magneticraft2.common.block.machines.heat.HeatGeneratorBlock;
import com.magneticraft2.common.block.machines.solarblock;
import com.magneticraft2.common.block.ores.deepslate.*;
import com.magneticraft2.common.block.ores.normal.*;
import com.magneticraft2.common.block.stage.early.PitKilnBlock;
import com.magneticraft2.common.block.stage.early.Stick;
import com.magneticraft2.common.block.stage.early.primitive_furnace_block;
import com.magneticraft2.common.block.stage.early.stonepebble;
import com.magneticraft2.common.block.wires.BlockHVConnectorBase;
import com.magneticraft2.common.block.wires.BlockTransformerHV;
import com.magneticraft2.common.item.general_items.hammer;
import com.magneticraft2.common.item.general_items.wrench;
import com.magneticraft2.common.item.multiblock_filler_item;
import com.magneticraft2.common.item.stage.early.copper_ingots.copper_ingot_low;
import com.magneticraft2.common.item.stage.early.general.pebble;
import com.magneticraft2.common.item.stage.early.pots.ceramicPot;
import com.magneticraft2.common.item.stage.early.pots.clayPot;
import com.magneticraft2.common.item.stage.early.tools.FireStarter;
import com.magneticraft2.common.item.stage.early.tools.stoneKnife;
import com.magneticraft2.common.item.wire.itemWireCoil;
import com.magneticraft2.common.magneticraft2;
import com.magneticraft2.common.recipe.RecipeRegistry;
import com.magneticraft2.common.tile.Multiblockfiller_tile;
import com.magneticraft2.common.tile.machines.heat.CrucibleHeaterTile;
import com.magneticraft2.common.tile.machines.heat.HeatGeneratorTile;
import com.magneticraft2.common.tile.machines.solars.solatesttile;
import com.magneticraft2.common.tile.stage.early.PitKilnBlockEntity;
import com.magneticraft2.common.tile.stage.early.primitive_furnace_tile;
import com.magneticraft2.common.tile.stage.early.stonepebbleBlockEntity;
import com.magneticraft2.common.tile.wire.BlockEntityHVConnectorBase;
import com.magneticraft2.common.tile.wire.BlockEntityTransformerHV;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.magneticraft2.common.magneticraft2.MOD_ID;
@Mod.EventBusSubscriber(modid = magneticraft2.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FinalRegistry {

    private static final Logger LOGGER = LogManager.getLogger("MGC2Registry");
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    private static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MOD_ID);
    private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, MOD_ID);
    private static final DeferredRegister<StructureFeature<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, MOD_ID);


    public static void register(){
        LOGGER.info("Started to register Blocks, items etc.");
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(bus);
        ITEMS.register(bus);
        TILE_ENTITIES.register(bus);
        ENTITIES.register(bus);
        STRUCTURES.register(bus);
        RecipeRegistry.registerRecipes(bus);
        ContainerAndScreenRegistry.containerRegistry();



    }


    /**
     * GuideBook
     */


    private static <T extends IForgeRegistryEntry<T>> DeferredRegister<T> create(IForgeRegistry<T> registry) {
        return DeferredRegister.create(registry, magneticraft2.MOD_ID);
    }
    private static <T extends Block> RegistryObject<T> registerBlockWithoutBlockItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }
    public static <B extends Block> RegistryObject<Item> fromBlock(RegistryObject<B> block) {
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), ITEM_PROPERTIES));
    }
    public static <B extends Block> RegistryObject<Item> fromBlockOre(RegistryObject<B> block) {
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), ORE_PROPERTIES));
    }

    /**
     * CreativeModeTabs
     **/
    public static final CreativeModeTab MC2Blocks = new CreativeModeTab("Magneticraft2.Blocks") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Block_HVConnector_Base.get());
        }
    };
    public static final CreativeModeTab MC2Machines = new CreativeModeTab("Magneticraft2.Machines") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Block_Heat_Generator.get());
        }
    };
    public static final CreativeModeTab MC2Plants = new CreativeModeTab("Magneticraft2.Plants") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(rice_seed.get());
        }
    };
    public static final CreativeModeTab MC2Items = new CreativeModeTab("Magneticraft2.Items") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Item_Wire_Coil.get());
        }
    };
    public static final CreativeModeTab MC2Ores = new CreativeModeTab("Magneticraft2.Ores") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(MAGNETITE_ORE.get());
        }
    };

    /**
     * Premade properties for items
     **/
    public static final Item.Properties ITEM_PROPERTIES = new Item.Properties().tab(MC2Blocks);
    public static final Item.Properties ITEMTAB_PROPERTIES = new Item.Properties().tab(MC2Blocks);
    public static final Item.Properties ORE_PROPERTIES = new Item.Properties().tab(MC2Ores);


    public static final TagKey<Item> clayitem = ItemTags.create(new ResourceLocation(MOD_ID, "clay_items"));
    /**
     * Mining levels
     */
    /* Copper */
    public static final TagKey<Block> requires_copper = BlockTags.create(new ResourceLocation(MOD_ID, "needs_copper_tool"));
    public static final TagKey<Block> mineable_copper = BlockTags.create(new ResourceLocation(MOD_ID, "minable/copper_tool"));
    private static final Tier copper_tier = TierSortingRegistry.registerTier(
            new ForgeTier(1, 5000, 10, 100, 0, requires_copper, () -> Ingredient.of(Items.BEDROCK)),
            new ResourceLocation(MOD_ID, "copper_tier"),
            List.of(Tiers.STONE), List.of(Tiers.IRON)
    );
    /* Bronze */
    public static final TagKey<Block> requires_bronze = BlockTags.create(new ResourceLocation(MOD_ID, "needs_bronze_tool"));
    public static final TagKey<Block> mineable_bronze = BlockTags.create(new ResourceLocation(MOD_ID, "minable/bronze_tool"));
    private static final Tier bronze_tier = TierSortingRegistry.registerTier(
            new ForgeTier(2, 5000, 10, 100, 0, requires_bronze, () -> Ingredient.of(Items.BEDROCK)),
            new ResourceLocation(MOD_ID, "bronze_tier"),
            List.of(Tiers.IRON), List.of(Tiers.DIAMOND)
    );
    /* Steel */
    public static final TagKey<Block> requires_Steel = BlockTags.create(new ResourceLocation(MOD_ID, "needs_steel_tool"));
    public static final TagKey<Block> mineable_Steel = BlockTags.create(new ResourceLocation(MOD_ID, "minable/steel_tool"));
    private static final Tier Steel_tier = TierSortingRegistry.registerTier(
            new ForgeTier(3, 5000, 10, 100, 0, requires_Steel, () -> Ingredient.of(Items.BEDROCK)),
            new ResourceLocation(MOD_ID, "steel_tier"),
            List.of(Tiers.DIAMOND), List.of(Tiers.NETHERITE)
    );
    /**
     * Mining tools for levels
     */

    public static final RegistryObject<Item> stone_knife = ITEMS.register("stone_knife", () -> new stoneKnife(Tiers.WOOD, 0, 0, ITEMTAB_PROPERTIES));




    /**
     * Blocks
     **/
    /*Ores*/
    public static final RegistryObject<Bauxite> BAUXITE_ORE = BLOCKS.register("bauxite_ore", Bauxite::new);
    public static final RegistryObject<Apatite> APATITE_ORE = BLOCKS.register("apatite_ore", Apatite::new);
    public static final RegistryObject<Chromite> CHROMITE_ORE = BLOCKS.register("chromite_ore", Chromite::new);
    public static final RegistryObject<Chromite_deepslate> CHROMITE_DEEPSLATE_ORE = BLOCKS.register("chromite_deepslate_ore", Chromite_deepslate::new);
    public static final RegistryObject<Lignite> LIGNITE_ORE = BLOCKS.register("lignite_ore", Lignite::new);
    public static final RegistryObject<Cobaltite> COBALTITE_ORE = BLOCKS.register("cobaltite_ore", Cobaltite::new);
    public static final RegistryObject<Chalcocite> CHALCOCITE_ORE = BLOCKS.register("chalcocite_ore", Chalcocite::new);
    public static final RegistryObject<Cryolite> CRYOLITE_ORE = BLOCKS.register("cryolite_ore", Cryolite::new);
    public static final RegistryObject<Kimberlite> KIMBERLITE_ORE = BLOCKS.register("kimberlite_ore", Kimberlite::new);
    public static final RegistryObject<Graphite> GRAPHITE_ORE = BLOCKS.register("graphite_ore", Graphite::new);
    public static final RegistryObject<Magnetite> MAGNETITE_ORE = BLOCKS.register("magnetite_ore", Magnetite::new);
    public static final RegistryObject<Magnetite_deepslate> MAGNETITE_DEEPSLATE_ORE = BLOCKS.register("magnetite_deepslate_ore", Magnetite_deepslate::new);
    public static final RegistryObject<Hematite> HEMATITE_ORE = BLOCKS.register("hematite_ore", Hematite::new);
    public static final RegistryObject<Limonite> LIMONITE_ORE = BLOCKS.register("limonite_ore", Limonite::new);
    public static final RegistryObject<Limonite_deepslate> LIMONITE_DEEPSLATE_ORE = BLOCKS.register("limonite_deepslate_ore", Limonite_deepslate::new);
    public static final RegistryObject<Kaolinite> KAOLINITE_ORE = BLOCKS.register("kaolinite_ore", Kaolinite::new);
    public static final RegistryObject<Galena> GALENA_ORE = BLOCKS.register("galena_ore", Galena::new);
    public static final RegistryObject<Galena_deepslate> GALENA_DEEPSLATE_ORE = BLOCKS.register("galena_deepslate_ore", Galena_deepslate::new);
    public static final RegistryObject<Manganese> MANGANESE_ORE = BLOCKS.register("manganese_ore", Manganese::new);
    public static final RegistryObject<Manganese_deepslate> MANGANESE_DEEPSLATE_ORE = BLOCKS.register("manganese_deepslate_ore", Manganese_deepslate::new);
    public static final RegistryObject<Cinnabar> CINNABAR_ORE = BLOCKS.register("cinnabar_ore", Cinnabar::new);
    public static final RegistryObject<Cinnabar_deepslate> CINNABAR_DEEPSLATE_ORE = BLOCKS.register("cinnabar_deepslate_ore", Cinnabar_deepslate::new);
    public static final RegistryObject<Garnierite> GARNIERITE_ORE = BLOCKS.register("garnierite_ore", Garnierite::new);
    public static final RegistryObject<Garnierite_deepslate> GARNIERITE_DEEPSLATE_ORE = BLOCKS.register("garnierite_deepslate_ore", Garnierite_deepslate::new);
    public static final RegistryObject<Osmiridium> OSMIRIDIUM_ORE = BLOCKS.register("osmiridium_ore", Osmiridium::new);
    public static final RegistryObject<Platinium> PLATINIUM_ORE = BLOCKS.register("platinium_ore", Platinium::new);
    public static final RegistryObject<Quartz> QUARTZ_ORE = BLOCKS.register("quartz_ore", Quartz::new);
    public static final RegistryObject<Quartz_deepslate> QUARTZ_DEEPSLATE_ORE = BLOCKS.register("quartz_deepslate_ore", Quartz_deepslate::new);
    public static final RegistryObject<Saltpeter> SALTPETER_ORE = BLOCKS.register("saltpeter_ore", Saltpeter::new);
    public static final RegistryObject<Silicium> SILICIUM_ORE = BLOCKS.register("silicium_ore", Silicium::new);
    public static final RegistryObject<Silicium_deepslate> SILICIUM_DEEPSLATE_ORE = BLOCKS.register("silicium_deepslate_ore", Silicium_deepslate::new);
    public static final RegistryObject<Silver> SILVER_ORE = BLOCKS.register("silver_ore", Silver::new);
    public static final RegistryObject<Sulfur> SULFUR_ORE = BLOCKS.register("sulfur_ore", Sulfur::new);
    public static final RegistryObject<Sulfur_deepslate> SULFUR_DEEPSLATE_ORE = BLOCKS.register("sulfur_deepslate_ore", Sulfur_deepslate::new);
    public static final RegistryObject<Tantalite> TANTALITE_ORE = BLOCKS.register("tantalite_ore", Tantalite::new);
    public static final RegistryObject<Tantalite_deepslate> TANTALITE_DEEPSLATE_ORE = BLOCKS.register("tantalite_deepslate_ore", Tantalite_deepslate::new);
    public static final RegistryObject<Cassiterite> CASSITERITE_ORE = BLOCKS.register("cassiterite_ore", Cassiterite::new);
    public static final RegistryObject<Titanite> TITANITE_ORE = BLOCKS.register("titanite_ore", Titanite::new);
    public static final RegistryObject<Wolframite> WOLFRAMITE_ORE = BLOCKS.register("wolframite_ore", Wolframite::new);
    public static final RegistryObject<Uraninite> URANINITE_ORE = BLOCKS.register("uraninite_ore", Uraninite::new);
    public static final RegistryObject<Sphalerite> SPHALERITE_ORE = BLOCKS.register("sphalerite_ore", Sphalerite::new);
    public static final RegistryObject<Anthracite> ANTHRACITE_ORE = BLOCKS.register("anthracite_ore", Anthracite::new);

    /*Other*/
    public static final RegistryObject<HeatGeneratorBlock> Block_Heat_Generator = BLOCKS.register("heat_generator", HeatGeneratorBlock::new);
    public static final RegistryObject<CrucibleHeaterBlock> Block_Crucible_Heater = BLOCKS.register ("crucible_heater", CrucibleHeaterBlock::new);
    public static final RegistryObject<Block> rice_plant = registerBlockWithoutBlockItem("rice_plant", () -> new RicePlantBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion()));
    public static final RegistryObject<BlockHVConnectorBase> Block_HVConnector_Base = BLOCKS.register("hvconnector_base", BlockHVConnectorBase::new);
    public static final RegistryObject<BlockTransformerHV> Block_Transformer_HV = BLOCKS.register("transformer_hv", BlockTransformerHV::new);
    public static final RegistryObject<solarblock> Block_Solar = BLOCKS.register("solar_block", solarblock::new);
    public static final RegistryObject<Multiblockfiller> Block_Multiblock_filler = BLOCKS.register("multiblock_filler", Multiblockfiller::new);
    public static final RegistryObject<stonepebble> stonepebbleBlock = BLOCKS.register("stonepebble", stonepebble::new);
    public static final RegistryObject<PitKilnBlock> PitKilnblock = BLOCKS.register("pitkilnblock", PitKilnBlock::new);
    public static final RegistryObject<primitive_furnace_block> primitive_furnace_Block = BLOCKS.register("primitive_furnace_block", primitive_furnace_block::new);
    public static final RegistryObject<Stick> Stick_block = BLOCKS.register("stick", Stick::new);
    /**
     * Block-Items
     **/
    /*Ores*/
    public static final RegistryObject<Item> BAUXITE_ITEM = fromBlockOre(BAUXITE_ORE);
    public static final RegistryObject<Item> APATITE_ITEM = fromBlockOre(APATITE_ORE);
    public static final RegistryObject<Item> CHROMITE_ITEM = fromBlockOre(CHROMITE_ORE);
    public static final RegistryObject<Item> CHROMITE_DEEPSLATE_ITEM = fromBlockOre(CHROMITE_DEEPSLATE_ORE);
    public static final RegistryObject<Item> LIGNITE_ITEM = fromBlockOre(LIGNITE_ORE);
    public static final RegistryObject<Item> COBALTITE_ITEM = fromBlockOre(COBALTITE_ORE);
    public static final RegistryObject<Item> CHALCOCITE_ITEM = fromBlockOre(CHALCOCITE_ORE);
    public static final RegistryObject<Item> CRYOLITE_ITEM = fromBlockOre(CRYOLITE_ORE);
    public static final RegistryObject<Item> KIMBERLITE_ITEM = fromBlockOre(KIMBERLITE_ORE);
    public static final RegistryObject<Item> GRAPHITE_ITEM = fromBlockOre(GRAPHITE_ORE);
    public static final RegistryObject<Item> MAGNETITE_ITEM = fromBlockOre(MAGNETITE_ORE);
    public static final RegistryObject<Item> MAGNETITE_DEEPSLATE_ITEM = fromBlockOre(MAGNETITE_DEEPSLATE_ORE);
    public static final RegistryObject<Item> HEMATITE_ITEM = fromBlockOre(HEMATITE_ORE);
    public static final RegistryObject<Item> LIMONITE_ITEM = fromBlockOre(LIMONITE_ORE);
    public static final RegistryObject<Item> LIMONITE_DEEPSLATE_ITEM = fromBlockOre(LIMONITE_DEEPSLATE_ORE);
    public static final RegistryObject<Item> KAOLINITE_ITEM = fromBlockOre(KAOLINITE_ORE);
    public static final RegistryObject<Item> GALENA_ITEM = fromBlockOre(GALENA_ORE);
    public static final RegistryObject<Item> GALENA_DEEPSLATE_ITEM = fromBlockOre(GALENA_DEEPSLATE_ORE);
    public static final RegistryObject<Item> MANGANESE_ITEM = fromBlockOre(MANGANESE_ORE);
    public static final RegistryObject<Item> MANGANESE_DEEPSLATE_ITEM = fromBlockOre(MANGANESE_DEEPSLATE_ORE);
    public static final RegistryObject<Item> CINNABAR_ITEM = fromBlockOre(CINNABAR_ORE);
    public static final RegistryObject<Item> CINNABAR_DEEPSLATE_ITEM = fromBlockOre(CINNABAR_DEEPSLATE_ORE);
    public static final RegistryObject<Item> GARNIERITE_ITEM = fromBlockOre(GARNIERITE_ORE);
    public static final RegistryObject<Item> GARNIERITE_DEEPSLATE_ITEM = fromBlockOre(GARNIERITE_DEEPSLATE_ORE);
    public static final RegistryObject<Item> OSMIRIDIUM_ITEM = fromBlockOre(OSMIRIDIUM_ORE);
    public static final RegistryObject<Item> PLATINIUM_ITEM = fromBlockOre(PLATINIUM_ORE);
    public static final RegistryObject<Item> QUARTZ_ITEM = fromBlockOre(QUARTZ_ORE);
    public static final RegistryObject<Item> QUARTZ_DEEPSLATE_ITEM = fromBlockOre(QUARTZ_DEEPSLATE_ORE);
    public static final RegistryObject<Item> SALTPETER_ITEM = fromBlockOre(SALTPETER_ORE);
    public static final RegistryObject<Item> SILICIUM_ITEM = fromBlockOre(SILICIUM_ORE);
    public static final RegistryObject<Item> SILICIUM_DEEPSLATE_ITEM = fromBlockOre(SILICIUM_DEEPSLATE_ORE);
    public static final RegistryObject<Item> SILVER_ITEM = fromBlockOre(SILVER_ORE);
    public static final RegistryObject<Item> SULFUR_ITEM = fromBlockOre(SULFUR_ORE);
    public static final RegistryObject<Item> SULFUR_DEEPSLATE_ITEM = fromBlockOre(SULFUR_DEEPSLATE_ORE);
    public static final RegistryObject<Item> TANTALITE_ITEM = fromBlockOre(TANTALITE_ORE);
    public static final RegistryObject<Item> TANTALITE_DEEPSLATE_ITEM = fromBlockOre(TANTALITE_DEEPSLATE_ORE);
    public static final RegistryObject<Item> CASSITERITE_ITEM = fromBlockOre(CASSITERITE_ORE);
    public static final RegistryObject<Item> TITANITE_ITEM = fromBlockOre(TITANITE_ORE);
    public static final RegistryObject<Item> WOLFRAMITE_ITEM = fromBlockOre(WOLFRAMITE_ORE);
    public static final RegistryObject<Item> URANINITE_ITEM = fromBlockOre(URANINITE_ORE);
    public static final RegistryObject<Item> SPHALERITE_ITEM = fromBlockOre(SPHALERITE_ORE);
    public static final RegistryObject<Item> ANTHRACITE_ITEM = fromBlockOre(ANTHRACITE_ORE);

    public static final RegistryObject<Item> primitive_furnace_block_core = fromBlock(primitive_furnace_Block);
    public static final RegistryObject<Item> HEAT_ITEM = fromBlock(Block_Heat_Generator);
    public static final RegistryObject<Item> CRUCIBLE_HEATER_ITEM = fromBlock(Block_Crucible_Heater);
    public static final RegistryObject<Item> HVConnector_Base = fromBlock(Block_HVConnector_Base);
    public static final RegistryObject<Item> HV_CABLE = fromBlock(Block_Transformer_HV);
    public static final RegistryObject<Item> Block_solar_item = fromBlock(Block_Solar);
    public static final RegistryObject<multiblock_filler_item> multiblock_filler_item = ITEMS.register("multiblock_filler_item", multiblock_filler_item::new);
    public static final RegistryObject<Item> rice_seed = ITEMS.register("rice_seed", () -> new ItemNameBlockItem(rice_plant.get(), new Item.Properties().tab(MC2Plants)));
    public static final RegistryObject<Item> rice = ITEMS.register("rice", () -> new Item(new Item.Properties().tab(MC2Plants).food(ModFoods.rice)));
    public static final RegistryObject<itemWireCoil> Item_Wire_Coil = ITEMS.register("wirecoil", itemWireCoil::new);
    public static final RegistryObject<copper_ingot_low> item_copper_ingot_low = ITEMS.register("copper_ingot_low", copper_ingot_low::new);
    public static final RegistryObject<pebble> item_pebble = ITEMS.register("pebble", pebble::new);
    public static final RegistryObject<FireStarter> item_firestartter = ITEMS.register("firestarter", FireStarter::new);
    public static final RegistryObject<clayPot> item_clay_pot = ITEMS.register("clay_pot", clayPot::new);
    public static final RegistryObject<ceramicPot> item_ceramic_pot = ITEMS.register("ceramic_pot", ceramicPot::new);
    public static final RegistryObject<wrench> item_wrench = ITEMS.register("wrench", wrench::new);
    public static final RegistryObject<hammer> item_hammer = ITEMS.register("hammer", hammer::new);



    /**
     * Block-Entities
     **/
    public static final RegistryObject<BlockEntityType<stonepebbleBlockEntity>> stonepebbleBlockEntity = TILE_ENTITIES.register("stonepebbleblockentity", () -> BlockEntityType.Builder.of(stonepebbleBlockEntity::new, stonepebbleBlock.get()).build(null));
    public static final RegistryObject<BlockEntityType<PitKilnBlockEntity>> PitKilnblockEntity = TILE_ENTITIES.register("pitkilnblockentity", () -> BlockEntityType.Builder.of(PitKilnBlockEntity::new, PitKilnblock.get()).build(null));

    public static final RegistryObject<BlockEntityType<primitive_furnace_tile>> primitive_furnace_Tile = TILE_ENTITIES.register("primitive_furnace_tile", () -> BlockEntityType.Builder.of(primitive_furnace_tile::new, primitive_furnace_Block.get()).build(null));
    public static final RegistryObject<BlockEntityType<Multiblockfiller_tile>> Multiblockfiller_tile = TILE_ENTITIES.register("multiblockfiller_tile", () -> BlockEntityType.Builder.of(Multiblockfiller_tile::new, Block_Multiblock_filler.get()).build(null));

    public static final RegistryObject<BlockEntityType<HeatGeneratorTile>> Tile_Heat_Generator = TILE_ENTITIES.register("heat_generator", () -> BlockEntityType.Builder.of(HeatGeneratorTile::new, Block_Heat_Generator.get()).build(null));
    public static final RegistryObject<BlockEntityType<CrucibleHeaterTile>> Tile_Crucible_Heater = TILE_ENTITIES.register("crucible_heater", () -> BlockEntityType.Builder.of(CrucibleHeaterTile::new, Block_Crucible_Heater.get()).build(null));
    public static final RegistryObject<BlockEntityType<BlockEntityHVConnectorBase>> Tile_HVConnector_Base = TILE_ENTITIES.register("hvconnector_base", () -> BlockEntityType.Builder.of(BlockEntityHVConnectorBase::new, Block_HVConnector_Base.get()).build(null));

    public static final RegistryObject<BlockEntityType<BlockEntityTransformerHV>> Tile_HVTransformer = TILE_ENTITIES.register("transformer_hv", () -> BlockEntityType.Builder.of(BlockEntityTransformerHV::new, Block_Transformer_HV.get()).build(null));
    public static final RegistryObject<BlockEntityType<solatesttile>> Tile_solarBlock = TILE_ENTITIES.register("solar_block", () -> BlockEntityType.Builder.of(solatesttile::new, Block_Solar.get()).build(null));




    @SubscribeEvent
    public static void gatherData(GatherDataEvent event)
    {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existing = event.getExistingFileHelper();
        if (event.includeServer()){
            gen.addProvider(new BlockTagsProvider(gen, MOD_ID, existing){
                @Override
                protected void addTags() {
                    LOGGER.info("Adding tags to blocks!");
                    this.tag(mineable_Steel).add(BAUXITE_ORE.get());
                    this.tag(requires_Steel).add(BAUXITE_ORE.get());
                    this.tag(mineable_bronze).add(APATITE_ORE.get());
                    this.tag(requires_bronze).add(APATITE_ORE.get());
                    this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(CHROMITE_ORE.get());
                    this.tag(BlockTags.NEEDS_DIAMOND_TOOL).add(CHROMITE_ORE.get());
                    this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(LIGNITE_ORE.get());
                    this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(COBALTITE_ORE.get());
                    this.tag(BlockTags.NEEDS_DIAMOND_TOOL).add(COBALTITE_ORE.get());
                    this.tag(mineable_copper).add(CHALCOCITE_ORE.get());
                    this.tag(requires_copper).add(CHALCOCITE_ORE.get());
                    this.tag(mineable_copper).add(CRYOLITE_ORE.get());
                    this.tag(requires_copper).add(CRYOLITE_ORE.get());
                    this.tag(mineable_Steel).add(KIMBERLITE_ORE.get());
                    this.tag(requires_Steel).add(KIMBERLITE_ORE.get());
                    this.tag(mineable_Steel).add(Blocks.EMERALD_ORE);
                    this.tag(requires_Steel).add(Blocks.EMERALD_ORE);
                    this.tag(mineable_Steel).add(Blocks.GOLD_ORE);
                    this.tag(requires_Steel).add(Blocks.GOLD_ORE);
                    this.tag(mineable_copper).add(GRAPHITE_ORE.get());
                    this.tag(requires_copper).add(GRAPHITE_ORE.get());
                    this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(LIMONITE_ORE.get());
                    this.tag(mineable_copper).add(HEMATITE_ORE.get());
                    this.tag(requires_copper).add(HEMATITE_ORE.get());
                    this.tag(mineable_Steel).add(MAGNETITE_ORE.get());
                    this.tag(requires_Steel).add(MAGNETITE_ORE.get());
                    this.tag(mineable_copper).add(KAOLINITE_ORE.get());
                    this.tag(requires_copper).add(KAOLINITE_ORE.get());
                    this.tag(mineable_bronze).add(Blocks.LAPIS_ORE);
                    this.tag(requires_bronze).add(Blocks.LAPIS_ORE);
                    this.tag(mineable_bronze).add(GALENA_ORE.get());
                    this.tag(requires_bronze).add(GALENA_ORE.get());
                    this.tag(mineable_bronze).add(MANGANESE_ORE.get());
                    this.tag(requires_bronze).add(MANGANESE_ORE.get());
                    this.tag(mineable_Steel).add(CINNABAR_ORE.get());
                    this.tag(requires_Steel).add(CINNABAR_ORE.get());
                    this.tag(mineable_Steel).add(GARNIERITE_ORE.get());
                    this.tag(requires_Steel).add(GARNIERITE_ORE.get());
                    this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(OSMIRIDIUM_ORE.get());
                    this.tag(BlockTags.NEEDS_DIAMOND_TOOL).add(OSMIRIDIUM_ORE.get());
                    this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(PLATINIUM_ORE.get());
                    this.tag(BlockTags.NEEDS_DIAMOND_TOOL).add(PLATINIUM_ORE.get());
                    this.tag(mineable_bronze).add(QUARTZ_ORE.get());
                    this.tag(requires_bronze).add(QUARTZ_ORE.get());
                    this.tag(mineable_copper).add(SALTPETER_ORE.get());
                    this.tag(requires_copper).add(SALTPETER_ORE.get());
                    this.tag(mineable_copper).add(SILICIUM_ORE.get());
                    this.tag(requires_copper).add(SILICIUM_ORE.get());
                    this.tag(mineable_Steel).add(SILVER_ORE.get());
                    this.tag(requires_Steel).add(SILVER_ORE.get());
                    this.tag(mineable_copper).add(SULFUR_ORE.get());
                    this.tag(requires_copper).add(SULFUR_ORE.get());
                    this.tag(mineable_Steel).add(TANTALITE_ORE.get());
                    this.tag(requires_Steel).add(TANTALITE_ORE.get());
                    this.tag(mineable_copper).add(CASSITERITE_ORE.get());
                    this.tag(requires_copper).add(CASSITERITE_ORE.get());
                    this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(TITANITE_ORE.get());
                    this.tag(BlockTags.NEEDS_DIAMOND_TOOL).add(TITANITE_ORE.get());
                    this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(WOLFRAMITE_ORE.get());
                    this.tag(BlockTags.NEEDS_DIAMOND_TOOL).add(WOLFRAMITE_ORE.get());
                    this.tag(mineable_Steel).add(URANINITE_ORE.get());
                    this.tag(requires_Steel).add(URANINITE_ORE.get());
                    this.tag(mineable_copper).add(SPHALERITE_ORE.get());
                    this.tag(requires_copper).add(SPHALERITE_ORE.get());
                    this.tag(mineable_copper).add(Blocks.COAL_ORE);
                    this.tag(requires_copper).add(Blocks.COAL_ORE);
                    this.tag(mineable_bronze).add(ANTHRACITE_ORE.get());
                    this.tag(requires_bronze).add(ANTHRACITE_ORE.get());
                }
            });
            gen.addProvider(new LootTableProvider(gen){
                @Override
                protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {
                    map.forEach((name, table) -> LootTables.validate(validationtracker, name, table));
                }

                @Override
                protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
                    return ImmutableList.of(Pair.of(() -> {
                        return new BlockLoot(){
                            @Override
                            protected Iterable<Block> getKnownBlocks() {
                                return FinalRegistry.BLOCKS.getEntries().stream().map(Supplier::get).collect(Collectors.toList());
                            }

                            @Override
                            protected void addTables() {
                                this.dropSelf(BAUXITE_ORE.get());
                                this.dropSelf(APATITE_ORE.get());
                                this.dropSelf(CHROMITE_ORE.get());
                                this.dropSelf(LIGNITE_ORE.get());
                                this.dropSelf(COBALTITE_ORE.get());
                                this.dropSelf(CHALCOCITE_ORE.get());
                                this.dropSelf(CRYOLITE_ORE.get());
                                this.dropSelf(KIMBERLITE_ORE.get());
                                this.dropSelf(GRAPHITE_ORE.get());
                                this.dropSelf(MAGNETITE_ORE.get());
                                this.dropSelf(HEMATITE_ORE.get());
                                this.dropSelf(LIMONITE_ORE.get());
                                this.dropSelf(KAOLINITE_ORE.get());
                                this.dropSelf(GALENA_ORE.get());
                                this.dropSelf(MANGANESE_ORE.get());
                                this.dropSelf(CINNABAR_ORE.get());
                                this.dropSelf(GARNIERITE_ORE.get());
                                this.dropSelf(OSMIRIDIUM_ORE.get());
                                this.dropSelf(PLATINIUM_ORE.get());
                                this.dropSelf(QUARTZ_ORE.get());
                                this.dropSelf(SALTPETER_ORE.get());
                                this.dropSelf(SILICIUM_ORE.get());
                                this.dropSelf(SILVER_ORE.get());
                                this.dropSelf(SULFUR_ORE.get());
                                this.dropSelf(TANTALITE_ORE.get());
                                this.dropSelf(CASSITERITE_ORE.get());
                                this.dropSelf(TITANITE_ORE.get());
                                this.dropSelf(WOLFRAMITE_ORE.get());
                                this.dropSelf(URANINITE_ORE.get());
                                this.dropSelf(SPHALERITE_ORE.get());
                                this.dropSelf(ANTHRACITE_ORE.get());
                            }
                        };
                    }, LootContextParamSets.BLOCK));
                }
            });
        }
//        if (event.includeClient()){
//            gen.addProvider(new BlockStateProvider(gen, MOD_ID, existing){
//                @Override
//                protected void registerStatesAndModels() {
//                    //ModelFile model = models().cubeAll()
//                }
//            });
//            gen.addProvider(new ItemModelProvider(gen, MOD_ID, existing) {
//                @Override
//                protected void registerModels() {
//                    //getBuilder()
//                }
//            });
//        }
    }

}
