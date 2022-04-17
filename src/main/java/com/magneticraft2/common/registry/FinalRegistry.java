package com.magneticraft2.common.registry;

import com.magneticraft2.common.block.machines.heat.HeatGeneratorBlock;
import com.magneticraft2.common.magneticraft2;
import com.magneticraft2.common.tile.machines.heat.HeatGeneratorTile;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.ParallelDispatchEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static com.magneticraft2.common.magneticraft2.MOD_ID;
public class FinalRegistry {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, MOD_ID);
    public static final DeferredRegister<StructureFeature<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, MOD_ID);

    public static void register(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(bus);
        ITEMS.register(bus);
        TILE_ENTITIES.register(bus);
        ENTITIES.register(bus);
        STRUCTURES.register(bus);
        ContainerAndScreenRegistry.containerRegistry();

    }
    public static final BlockBehaviour.Properties BLOCK_PROPERTIES = BlockBehaviour.Properties.of(Material.STONE).strength(2f).requiresCorrectToolForDrops();
    public static final Item.Properties ITEM_PROPERTIES = new Item.Properties().tab(FinalRegistry.MC2Blocks);


    private static <T extends IForgeRegistryEntry<T>> DeferredRegister<T> create(IForgeRegistry<T> registry) {
        return DeferredRegister.create(registry, magneticraft2.MOD_ID);
    }
    public static void init(ParallelDispatchEvent ev) {

    }

    public static final CreativeModeTab MC2Blocks = new CreativeModeTab("Magneticraft2 Blocks") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Block_Heat_Generator.get());
//            return new ItemStack(BlockRegistry.Solar_Panel_T1_SubPanels.get());
        }
    };


    public static final RegistryObject<HeatGeneratorBlock> Block_Heat_Generator = BLOCKS.register("heat_generator", HeatGeneratorBlock::new);
    public static final RegistryObject<Item> HEAT_ITEM = fromBlock(Block_Heat_Generator);
    public static final RegistryObject<BlockEntityType<HeatGeneratorTile>> Tile_Heat_Generator = TILE_ENTITIES.register("heat_generator", () -> BlockEntityType.Builder.of(HeatGeneratorTile::new, Block_Heat_Generator.get()).build(null));



    public static <B extends Block> RegistryObject<Item> fromBlock(RegistryObject<B> block) {
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), ITEM_PROPERTIES));
    }
}
