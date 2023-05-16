package com.magneticraft2.common.systems.multiblock.json;

import net.minecraft.world.level.block.Block;

import java.util.Map;

/**
 * @author JumpWatch on 13-05-2023
 * @Project magneticraft2-1.18.2
 * v1.0.0
 */
public class MultiblockData {
    private final String name;
    private final MultiblockStructure structure;
    private final Map<String, Block> blocks;
    private final MultiblockSettings settings;

    public MultiblockData(String name, MultiblockStructure structure, Map<String, Block> blocks, MultiblockSettings settings) {
        this.name = name;
        this.structure = structure;
        this.blocks = blocks;
        this.settings = settings;
    }

    public String getName(){
        return name;
    }
    public MultiblockStructure getStructure() {
        return structure;
    }

    public Map<String, Block> getBlocks() {
        return blocks;
    }

    public MultiblockSettings getSettings() {
        return settings;
    }
}