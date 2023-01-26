package com.magneticraft2.common.systems.multiblock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * @author JumpWatch on 25-01-2023
 * @Project magneticraft2-1.18.2
 * v1.0.0
 */
public class CustomBlockPattern {
    private final List<String> rows;
    private final Map<Character, Predicate<BlockInWorld >> predicates;

    private CustomBlockPattern(List<String> rows, Map<Character, Predicate<BlockInWorld >> predicates) {
        this.rows = rows;
        this.predicates = predicates;
    }


    public static CustomBlockPatternBuilder builder() {
        return new CustomBlockPatternBuilder();
    }
    public void markInvalidBlocks(Level world, BlockPos startPos) {
        for (int y = 0; y < rows.size(); y++) {
            for (int x = 0; x < rows.get(y).length(); x++) {
                for (int z = 0; z < rows.size(); z++) {
                    BlockPos pos = startPos.offset(x, y, z);
                    BlockState state = world.getBlockState(pos);
                    if (!matches(world, pos)) {
                        //mark the block with custom render
                    }
                }
            }
        }
    }
    public boolean matches(Level world, BlockPos pos) {
        for (int y = 0; y < rows.size(); y++) {
            String row = rows.get(y);
            for (int x = 0; x < row.length(); x++) {
                char c = row.charAt(x);
                if (c == ' ') {
                    continue;
                }
                BlockPos offsetPos = pos.offset(x, y, 0);
                BlockInWorld blockInWorld = new BlockInWorld(world, offsetPos, true);;
                if (!predicates.getOrDefault(c, s -> false).test(blockInWorld)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static class CustomBlockPatternBuilder {
        private final List<String> rows = new ArrayList<>();
        private final Map<Character, Predicate<BlockInWorld >> predicates = new HashMap<>();

        private CustomBlockPatternBuilder() { }

        public CustomBlockPatternBuilder row(String row) {
            rows.add(row);
            return this;
        }

        public CustomBlockPatternBuilder where(char c, Predicate<BlockInWorld> predicate) {
            predicates.put(c, predicate);
            return this;
        }

        public CustomBlockPattern build() {
            return new CustomBlockPattern(rows, predicates);
        }
    }
}