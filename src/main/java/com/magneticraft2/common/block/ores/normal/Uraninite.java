package com.magneticraft2.common.block.ores.normal;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class Uraninite extends Block {
    public Uraninite() {
        super(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(1.5F, 6F));
    }
}
