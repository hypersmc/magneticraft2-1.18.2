package com.magneticraft2.common.systems.multiblock;

import com.magneticraft2.common.magneticraft2;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;
import java.util.function.Predicate;

import static com.magneticraft2.client.render.blocks.WireRender.BLOCK_ATLAS;
import static com.magneticraft2.client.render.blocks.WireRender.LOGGER;

/**
 * @author JumpWatch on 25-01-2023
 * @Project magneticraft2-1.18.2
 * v1.0.0
 */
@Mod.EventBusSubscriber(modid = magneticraft2.MOD_ID,value = Dist.CLIENT,  bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CustomBlockPattern {
    private final List<String[]> rows;
    private final Map<Character, Predicate<BlockInWorld >> predicates;
    public static BlockPos posrender;
    private static final Random RANDOM = new Random();
    private CustomBlockPattern(List<String[]> rows, Map<Character, Predicate<BlockInWorld >> predicates) {
        this.rows = rows;
        this.predicates = predicates;
    }


    public static CustomBlockPatternBuilder builder() {
        return new CustomBlockPatternBuilder();
    }
    public void markInvalidBlocks(Level world, BlockPos startPos) {
        for (int y = 0; y < rows.size(); y++) {
            String[] row = rows.get(y);
            for (int x = 0; x < row.length; x++) {
                String row2 = row[x];
                for (int z = 0; z < row2.length(); z++) {
                    BlockPos pos = startPos.offset(x, y, z);
                    BlockState state = world.getBlockState(pos);
                    if (!matches(world, pos)) {
                        posrender = pos;
                    }else {
                        posrender = null;
                    }
                }
            }
        }
    }
    public boolean matches(Level world, BlockPos pos) {
        for (int y = 0; y < rows.size(); y++) {
            String[] row = rows.get(y);
            for (int x = 0; x < row.length; x++) {
                String row2 = row[x];
                for (int z = 0; z < row2.length(); z++) {
                    char c = row2.charAt(z);
                    if (c == ' ') {
                        continue;
                    }
                    BlockPos offsetPos = pos.offset(x, y, z);
                    BlockInWorld blockInWorld = new BlockInWorld(world, offsetPos, true);
                    if (!predicates.getOrDefault(c, s -> false).test(blockInWorld)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    @SubscribeEvent
    public static void RenderLevelLastEvent(final RenderLevelStageEvent event){
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_SOLID_BLOCKS) {
            if (posrender == null) return;
            LOGGER.info(posrender.getX() + " " + posrender.getY() + " " + posrender.getZ());
            BlockPos pos = new BlockPos(posrender.getX(), posrender.getY(), posrender.getZ());
            if (pos != null) {
                BlockState blockState = Minecraft.getInstance().level.getBlockState(pos);
                PoseStack poseStack = event.getPoseStack();
                BlockRenderDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();
                RenderType renderType = RenderType.outline(new ResourceLocation(magneticraft2.MOD_ID, "outline"));
                IModelData modelData = blockRenderer.getBlockModel(blockState).getModelData(Minecraft.getInstance().level, pos, blockState, null);
                blockRenderer.renderBatched(blockState, pos, Minecraft.getInstance().level, poseStack, Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(renderType), false, RANDOM, modelData );
            } else {

                return;
            }
        }
    }

    public static class CustomBlockPatternBuilder {
        private final List<String[]> rows = new ArrayList<>();
        private final Map<Character, Predicate<BlockInWorld >> predicates = new HashMap<>();

        private CustomBlockPatternBuilder() { }

        public CustomBlockPatternBuilder row(String... row) {
            this.rows.add(row);
            return this;
        }

        public CustomBlockPatternBuilder where(char symbol, Predicate<BlockInWorld> pred) {
            this.predicates.put(symbol, pred);
            return this;
        }

        public CustomBlockPattern build() {
            return new CustomBlockPattern(this.rows, this.predicates);
        }
    }
}