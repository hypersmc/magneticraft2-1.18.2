package com.magneticraft2.common.block.ores.deepslate;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;

public class Garnierite_deepslate extends Block {
    public Garnierite_deepslate() {
        super(Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(1.5F, 6F));
    }
}
