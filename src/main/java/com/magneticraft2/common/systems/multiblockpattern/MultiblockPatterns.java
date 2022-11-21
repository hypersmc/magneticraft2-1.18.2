package com.magneticraft2.common.systems.multiblockpattern;


import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.pattern.BlockPattern;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MultiblockPatterns {


    public static final MultiblockPattern crucible = new MultiblockPattern()
            .addLayer(0, "XXX", "XXX", "XXX")
            .addLayer(1, "XXX", "XXX", "XXX")
            .addLayer(2, "XXX", "XXX", "XXX");

    public static class MultiblockPattern {

        List<BlockPos> pattern = new ArrayList<>();
        BlockPos master;

        public MultiblockPattern addLayer(int layer, String... lines) {
            int z = -(int) Math.floor(lines.length / 2.0);
            for (String line : lines) {
                int x = -(int) Math.floor(line.length() / 2.0);
                for (char c : line.toCharArray()) {
                    if (c == 'X')
                        pattern.add(new BlockPos(x, layer, z));
                    ++x;
                }
                ++z;
            }
            return this;
        }

        public List<BlockPos> getPositions() {
            return Collections.unmodifiableList(pattern);
        }
    }
}



