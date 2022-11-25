package com.magneticraft2.common.registry;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import java.util.function.Supplier;
@Deprecated(forRemoval = true)
public class Prop {
    public static class Blocks{
        public static final Supplier<Block.Properties> BASIC_TECH = () -> Block.Properties.of(Material.METAL)
                .strength(5F);


        public static final Supplier<Block.Properties> BASIC_WOOD = () -> Block.Properties.of(Material.WOOD)
                .strength(2.0F, 3.0F)
                .sound(SoundType.WOOD);


        public static final Supplier<Block.Properties> BASIC_CRYSTAL = () -> Block.Properties.of(Material.GLASS)
                .strength(1F);


        public static final Supplier<Block.Properties> BASIC_SAND = () -> Block.Properties.of(Material.SAND, MaterialColor.SAND).strength(0.5F).sound(SoundType.SAND);

        public static final Supplier<Block.Properties> BASIC_STONE = () -> Block.Properties.of(Material.STONE).strength(2.0F, 6.0F);

        public static final Supplier<Block.Properties> BASIC_GLASS = () -> Block.Properties.of(Material.GLASS).strength(0.3F).sound(SoundType.GLASS);

        public static final Supplier<Block.Properties> UNBREAKABLE = () -> Block.Properties.of(Material.BARRIER).strength(99999F).isSuffocating((blockState, reader, blockPos) -> false);

    }
    public static class Items{
        public static final Supplier<Item.Properties> ONE = () -> new Item.Properties()
                .tab(FinalRegistry.MC2Blocks)
                .stacksTo(1);

        public static final Supplier<Item.Properties> SIXTEEN = () -> new Item.Properties()
                .tab(FinalRegistry.MC2Blocks)
                .stacksTo(16);

        public static final Supplier<Item.Properties> SIXTY_FOUR = () -> new Item.Properties()
                .tab(FinalRegistry.MC2Blocks)
                .stacksTo(64);

    }
}
