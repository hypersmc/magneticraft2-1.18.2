package com.magneticraft2.common.registry;

import com.magneticraft2.common.block.machines.heat.CrucibleHeaterBlock;
import com.magneticraft2.common.block.machines.heat.HeatGeneratorBlock;
import com.magneticraft2.common.magneticraft2;
import com.magneticraft2.common.tile.machines.heat.CrucibleHeaterTile;
import com.magneticraft2.common.tile.machines.heat.HeatGeneratorTile;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.ParallelDispatchEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.magneticraft2.common.magneticraft2.MOD_ID;

public class FinalRegistry {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MOD_ID);
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    private static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MOD_ID);
    private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, MOD_ID);
    private static final DeferredRegister<StructureFeature<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, MOD_ID);


    public static void register(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(bus);
        ITEMS.register(bus);
        TILE_ENTITIES.register(bus);
        ENTITIES.register(bus);
        STRUCTURES.register(bus);
        RECIPE_SERIALIZERS.register(bus);
        ContainerAndScreenRegistry.containerRegistry();
    }


    public static final CreativeModeTab MC2Blocks = new CreativeModeTab("Magneticraft2.Blocks") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Block_Heat_Generator.get());
        }
    };
    public static final Item.Properties ITEM_PROPERTIES = new Item.Properties().tab(MC2Blocks);


    private static <T extends IForgeRegistryEntry<T>> DeferredRegister<T> create(IForgeRegistry<T> registry) {
        return DeferredRegister.create(registry, magneticraft2.MOD_ID);
    }
    public static void init(ParallelDispatchEvent ev) {

    }



    public static final RegistryObject<HeatGeneratorBlock> Block_Heat_Generator = BLOCKS.register("heat_generator", HeatGeneratorBlock::new);
    public static final RegistryObject<Item> HEAT_ITEM = fromBlock(Block_Heat_Generator);
    public static final RegistryObject<BlockEntityType<HeatGeneratorTile>> Tile_Heat_Generator = TILE_ENTITIES.register("heat_generator", () -> BlockEntityType.Builder.of(HeatGeneratorTile::new, Block_Heat_Generator.get()).build(null));

    public static final RegistryObject<CrucibleHeaterBlock> Block_Crucible_Heater = BLOCKS.register("crucible_heater", CrucibleHeaterBlock::new);
    public static final RegistryObject<Item> CRUCIBLE_HEATER_ITEM = fromBlock(Block_Crucible_Heater);
    public static final RegistryObject<BlockEntityType<CrucibleHeaterTile>> Tile_Crucible_Heater = TILE_ENTITIES.register("crucible_heater", () -> BlockEntityType.Builder.of(CrucibleHeaterTile::new, Block_Crucible_Heater.get()).build(null));
    /**
     * Multiblocks blocks
     */
//    public static final RegistryObject<hgeneratorscaffoldingblock> hgenerator_scaffolding_block = BLOCKS.register("hgenerator_scaffolding_block", hgeneratorscaffoldingblock::new);
//    public static final RegistryObject<hgeneratorcontrollerblock> hgenerator_controller_block = BLOCKS.register("hgenerator_controller_block", hgeneratorcontrollerblock::new);
//    public static final RegistryObject<hgeneratorpowerblock> hgenerator_power_block = BLOCKS.register("hgenerator_power_block", hgeneratorpowerblock::new);
//    public static final RegistryObject<hgeneratorheatblock> hgenerator_heat_block = BLOCKS.register("hgenerator_heat_block", hgeneratorheatblock::new);

    /**
     * Multiblocks tiles
     */
//    public static final RegistryObject<BlockEntityType<hgeneratorscaffoldingtile>> hgenerator_scaffolding_tile = TILE_ENTITIES.register("hgenerator_scaffolding_tile", () -> BlockEntityType.Builder.of(hgeneratorscaffoldingtile::new, hgenerator_scaffolding_block.get()).build(null));
//    public static final RegistryObject<BlockEntityType<hgeneratorcontrollertile>> hgenerator_controller_tile = TILE_ENTITIES.register("hgenerator_controller_tile", () -> BlockEntityType.Builder.of(hgeneratorcontrollertile::new, hgenerator_controller_block.get()).build(null));
//    public static final RegistryObject<BlockEntityType<hgeneratorpowertile>> hgenerator_power_tile = TILE_ENTITIES.register("hgenerator_power_tile", () -> BlockEntityType.Builder.of(hgeneratorpowertile::new, hgenerator_power_block.get()).build(null));
//    public static final RegistryObject<BlockEntityType<hgeneratorheattile>> hgenerator_heat_tile = TILE_ENTITIES.register("hgenerator_heat_tile", () -> BlockEntityType.Builder.of(hgeneratorheattile::new, hgenerator_heat_block.get()).build(null));
    /**
     * Multiblocks items
     */
    //
//    public static final RegistryObject<Item> hgenerator_scaffolding_item = fromBlock(hgenerator_scaffolding_block);
//    public static final RegistryObject<Item> hgenerator_controller_item = fromBlock(hgenerator_controller_block);
//    public static final RegistryObject<Item> hgenerator_power_item = fromBlock(hgenerator_power_block);
//    public static final RegistryObject<Item> hgenerator_heat_item = fromBlock(hgenerator_heat_block);

    public static <B extends Block> RegistryObject<Item> fromBlock(RegistryObject<B> block) {
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), ITEM_PROPERTIES));
    }

}
