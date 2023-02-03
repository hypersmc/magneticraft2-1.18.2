package com.magneticraft2.common.systems.multiblock;

import com.magneticraft2.common.magneticraft2;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.function.Predicate;

/**
 * @author JumpWatch on 25-01-2023
 * @Project magneticraft2-1.18.2
 * v1.0.0
 */
@Mod.EventBusSubscriber(modid = magneticraft2.MOD_ID,value = Dist.CLIENT,  bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CustomBlockPattern {

    private final List<List<String>> rows;
    private static final Random RANDOM = new Random();
    private final Map<Character, Predicate<BlockInWorld >> predicates;
    public static BlockPos posrender;
    private static Set<BlockPos> invalidBlocks = new HashSet<>();
    private static Set<BlockPos> renderedBlocks = new HashSet<>();
    private static final Logger LOGGER = LogManager.getLogger("MGC2PatternBuilder");
    public static final ResourceLocation TEXTURE_LINE = new ResourceLocation("magneticraft2:outlinestuff/outline");

    private CustomBlockPattern(List<List<String>> rows, Map<Character, Predicate<BlockInWorld >> predicates) {
        this.rows = rows;
        this.predicates = predicates;
    }



    public static CustomBlockPatternBuilder builder() {
        return new CustomBlockPatternBuilder();
    }
    public void markInvalidBlocks(Level world, BlockPos startPos) {

        if (!invalidBlocks.contains(startPos)) {
            //LOGGER.info("added " + startPos + " to invalid blocks");
            invalidBlocks.add(startPos);
        }

    }
    @SubscribeEvent
    public static void RenderLevelLastEvent(final RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_SOLID_BLOCKS || invalidBlocks == null || invalidBlocks.isEmpty()) {
            return;
        }

        for (Iterator<BlockPos> iterator = invalidBlocks.iterator(); iterator.hasNext(); iterator.remove()) {
            if (iterator == null) {
                return;
            }
            BlockPos pos = iterator.next();
            if (pos != null && !renderedBlocks.contains(pos)) {
                BlockState blockState = Minecraft.getInstance().level.getBlockState(pos);
                PoseStack poseStack = event.getPoseStack();
                BlockRenderDispatcher BLOCK_RENDERER = Minecraft.getInstance().getBlockRenderer();
                RenderType renderType = RenderType.outline(TEXTURE_LINE);
                IModelData modelData = BLOCK_RENDERER.getBlockModel(blockState).getModelData(Minecraft.getInstance().level, pos, blockState, null);
                Vec3 offset = Vec3.atLowerCornerOf(pos).subtract(Minecraft.getInstance().gameRenderer.getMainCamera().getPosition());

                for (RenderType type : RenderType.chunkBufferLayers()) {
                    ForgeHooksClient.setRenderType(type);
                    poseStack.pushPose();
                    poseStack.translate(offset.x(), offset.y(), offset.z());
                    BLOCK_RENDERER.renderBatched(blockState, pos, Minecraft.getInstance().level, poseStack, Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(renderType), false, RANDOM, modelData);
                    poseStack.popPose();
                }
                renderedBlocks.add(pos);
            }

        }
        renderedBlocks.clear();
    }
    public boolean matches(Level world, BlockPos pos) {
        for (int y = 0; y < rows.size(); y++) {
            List<String> rowList = rows.get(y);
            for (int z = 0; z < rowList.size(); z++) {
                String row = rowList.get(z);
                for (int x = 0; x < row.length(); x++) {
                    char c = row.charAt(x);
                    if (c == ' ') {
                        continue;
                    }
                    BlockPos offsetPos = pos.offset(x - (row.length() - 1) / 2, y - (rowList.size() - 1) / 2, z + 1 - (rows.size() - 1) / 2);
                    BlockInWorld blockInWorld = new BlockInWorld(world, offsetPos, true);
                    //LOGGER.info("checking " + offsetPos + " for " + c + " should be " + predicates.get(c).test(blockInWorld) + " is " + world.getBlockState(offsetPos));

                    if (!(world.getBlockEntity(blockInWorld.getPos()) instanceof Multiblock)) {
                        if (!predicates.containsKey(c) || !predicates.get(c).test(blockInWorld)) {
                            return false;
                        }
                    }else {
                        return true;
                    }
                }
            }
        }
        return true;
    }
    public List<List<String>> getPattern() {
        return rows;
    }

    public List<List<String>> getRows() {
        return this.rows;
    }

    public static class CustomBlockPatternBuilder {
        private final List<List<String>> rows = new ArrayList<>();
        private final Map<Character, Predicate<BlockInWorld >> predicates = new HashMap<>();

        private CustomBlockPatternBuilder() { }

        public CustomBlockPatternBuilder row(String... row) {
            rows.add(Arrays.asList(row));
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