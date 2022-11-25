package com.magneticraft2.common.registry;

import com.magneticraft2.common.block.crops.RicePlantBlock;
import com.magneticraft2.common.block.machines.heat.CrucibleHeaterBlock;
import com.magneticraft2.common.block.machines.heat.HeatGeneratorBlock;
import com.magneticraft2.common.block.machines.solarblock;
import com.magneticraft2.common.block.wires.BlockHVConnectorBase;
import com.magneticraft2.common.item.wire.itemWireCoil;
import com.magneticraft2.common.magneticraft2;
import com.magneticraft2.common.tile.machines.heat.CrucibleHeaterTile;
import com.magneticraft2.common.tile.machines.heat.HeatGeneratorTile;
import com.magneticraft2.common.tile.machines.solars.solatesttile;
import com.magneticraft2.common.tile.wire.BlockEntityHVConnectorBase;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static com.magneticraft2.common.magneticraft2.MOD_ID;

public class FinalRegistry {
    public static List<Item> BLOCK_ITEMS_LIST = new ArrayList<Item>();

    private static final Logger LOGGER = LogManager.getLogger("MGC2Registry");
    private static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MOD_ID);
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
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
        RECIPE_SERIALIZERS.register(bus);
        ContainerAndScreenRegistry.containerRegistry();
    }
    private static <T extends IForgeRegistryEntry<T>> DeferredRegister<T> create(IForgeRegistry<T> registry) {
        return DeferredRegister.create(registry, magneticraft2.MOD_ID);
    }
    private static <T extends Block> RegistryObject<T> registerBlockWithoutBlockItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }
    public static <B extends Block> RegistryObject<Item> fromBlock(RegistryObject<B> block) {
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), ITEM_PROPERTIES));
    }

    /**
     * CreativeModeTabs
     **/
    public static final CreativeModeTab MC2Blocks = new CreativeModeTab("Magneticraft2.Blocks") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Block_Crucible_Heater.get());
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
    /**
     * Premade properties for items
     **/
    public static final Item.Properties ITEM_PROPERTIES = new Item.Properties().tab(MC2Blocks);


    /**
     * Blocks
     **/
    /*Ores*/


    public static final RegistryObject<HeatGeneratorBlock> Block_Heat_Generator = BLOCKS.register("heat_generator", HeatGeneratorBlock::new);
    public static final RegistryObject<CrucibleHeaterBlock> Block_Crucible_Heater = BLOCKS.register ("crucible_heater", CrucibleHeaterBlock::new);
    public static final RegistryObject<Block> rice_plant = registerBlockWithoutBlockItem("rice_plant", () -> new RicePlantBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noOcclusion()));
    public static final RegistryObject<BlockHVConnectorBase> Block_HVConnector_Base = BLOCKS.register("hvconnector_base", BlockHVConnectorBase::new);
    public static final RegistryObject<solarblock> Block_Solar = BLOCKS.register("solar_block", solarblock::new);

    /**
     * Block-Items
     **/

    public static final RegistryObject<Item> HEAT_ITEM = fromBlock(Block_Heat_Generator);
    public static final RegistryObject<Item> CRUCIBLE_HEATER_ITEM = fromBlock(Block_Crucible_Heater);
    public static final RegistryObject<Item> HVConnector_Base = fromBlock(Block_HVConnector_Base);
    public static final RegistryObject<Item> Block_solar_item = fromBlock(Block_Solar);
    public static final RegistryObject<Item> rice_seed = ITEMS.register("rice_seed", () -> new ItemNameBlockItem(rice_plant.get(), new Item.Properties().tab(MC2Plants)));
    public static final RegistryObject<Item> rice = ITEMS.register("rice", () -> new Item(new Item.Properties().tab(MC2Plants).food(ModFoods.rice)));
    public static final RegistryObject<itemWireCoil> Item_Wire_Coil = ITEMS.register("wirecoil", itemWireCoil::new);

    /**
     * Block-Entities
     **/

    public static final RegistryObject<BlockEntityType<HeatGeneratorTile>> Tile_Heat_Generator = TILE_ENTITIES.register("heat_generator", () -> BlockEntityType.Builder.of(HeatGeneratorTile::new, Block_Heat_Generator.get()).build(null));
    public static final RegistryObject<BlockEntityType<CrucibleHeaterTile>> Tile_Crucible_Heater = TILE_ENTITIES.register("crucible_heater", () -> BlockEntityType.Builder.of(CrucibleHeaterTile::new, Block_Crucible_Heater.get()).build(null));
    public static final RegistryObject<BlockEntityType<BlockEntityHVConnectorBase>> Tile_HVConnector_Base = TILE_ENTITIES.register("hvconnector_base", () -> BlockEntityType.Builder.of(BlockEntityHVConnectorBase::new, Block_HVConnector_Base.get()).build(null));
    public static final RegistryObject<BlockEntityType<solatesttile>> Tile_solarBlock = TILE_ENTITIES.register("solar_block", () -> BlockEntityType.Builder.of(solatesttile::new, Block_Solar.get()).build(null));





}
