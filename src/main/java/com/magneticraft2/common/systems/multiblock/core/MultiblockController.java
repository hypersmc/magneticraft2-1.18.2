package com.magneticraft2.common.systems.multiblock.core;

import com.magneticraft2.common.systems.multiblock.json.MultiblockStructure;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author JumpWatch on 13-05-2023
 * @Project magneticraft2-1.18.2
 * v1.0.0
 */
public class MultiblockController {
    private final MultiblockStructure structure;
    private final Map<BlockPos, BlockState> blockMap;
    private BlockPos masterPos;
    private final Map<String, IMultiblockModule> modules;

    public MultiblockController(MultiblockStructure structure) {
        this.structure = structure;
        this.blockMap = new HashMap<>();
        this.masterPos = null;
        this.modules = new HashMap<>();
    }

    public boolean isValidStructure(Level world, BlockPos pos) {
        // Check if the structure is valid at the given position
        Map<BlockPos, BlockState> tempMap = new HashMap<>();

        for (String layer : structure.getLayout().keySet()) {
            int layerIndex = Integer.parseInt(layer) - 1;
            List<String> rowList = structure.getLayout().get(layer);

            for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++) {
                String row = rowList.get(rowIndex);

                for (int columnIndex = 0; columnIndex < row.length(); columnIndex++) {
                    String blockId = row.substring(columnIndex, columnIndex + 1);

                    if (blockId.equals(" ")) {
                        continue;
                    }

                    BlockPos offsetPos = new BlockPos(columnIndex, layerIndex, rowIndex);
                    BlockPos checkPos = pos.offset(offsetPos);
                    BlockState checkState = world.getBlockState(checkPos);

                    if (checkState.isAir()) {
                        return false;
                    }

                    if (!structure.getBlocks().get(blockId).equals(checkState.getBlock())) {
                        return false;
                    }

                    tempMap.put(offsetPos, checkState);
                }
            }
        }

        blockMap.clear();
        blockMap.putAll(tempMap);
        masterPos = pos;

        // Initialize modules
        modules.clear();
        for (IMultiblockModule module : structure.getModules()) {
            String moduleKey = module.getModuleKey();
            BlockPos modulePos = pos.offset(module.getModuleOffset());
            if (module.isValid(world, modulePos)) {
                modules.put(moduleKey, (IMultiblockModule) module.createModule(world, modulePos));
            } else {
                return false;
            }
        }

        return true;
    }

    public boolean createStructure(Level world, BlockPos pos) {
        if (!isValidStructure(world, pos)) {
            return false;
        }

        // Create the structure at the given position
        for (Map.Entry<BlockPos, BlockState> entry : blockMap.entrySet()) {
            BlockPos offsetPos = entry.getKey();
            BlockPos placePos = pos.offset(offsetPos);
            world.setBlock(placePos, entry.getValue(), 2);
        }

        // Enable modules
        for (IMultiblockModule module : modules.values()) {
            module.onActivate(world, masterPos);
        }

        return true;
    }

    public boolean destroyStructure(Level world) {
        if (masterPos == null) {
            return false;
        }

        // Disable modules
        for (IMultiblockModule module : modules.values()) {
            module.onDeactivate(world, masterPos);
        }

        // Destroy the structure
        for (Map.Entry<BlockPos, BlockState> entry : blockMap.entrySet()) {
            BlockPos offsetPos = entry.getKey();
            BlockPos destroyPos = masterPos.offset(offsetPos);
            world.setBlock(destroyPos, Blocks.AIR.defaultBlockState(), 2);
        }

        blockMap.clear();
        masterPos = null;

        return true;
    }

    public MultiblockStructure getStructure() {
        return structure;
    }

    public Map<BlockPos, BlockState> getBlockMap() {
        return blockMap;
    }

    public BlockPos getMasterPos() {
        return masterPos;
    }
}