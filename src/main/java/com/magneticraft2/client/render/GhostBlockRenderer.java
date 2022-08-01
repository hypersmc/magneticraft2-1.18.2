package com.magneticraft2.client.render;

import com.magneticraft2.common.magneticraft2;
import com.magneticraft2.common.utils.GhostVertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.ForgeRenderTypes;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

@Mod.EventBusSubscriber(modid = magneticraft2.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GhostBlockRenderer {
    private static final Random RANDOM = new Random();
    public static final Logger LOGGER = LogManager.getLogger();
    private static final String PROFILER_KEY = magneticraft2.MOD_ID + "_ghost_block";
    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event){
        LOGGER.info("registering ghostblock system");
        MinecraftForge.EVENT_BUS.addListener(GhostBlockRenderer::onRenderStage);
    }

    public static void onRenderStage(final RenderLevelStageEvent event){
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES) return;
        mc().getProfiler().push(PROFILER_KEY);
        mc().getProfiler().pop();
    }


    private static void doRenderGhostBlock(PoseStack mstack, MultiBufferSource buffers, BlockPos renderPos, BlockState renderState, IModelData modelData){
        mc().getProfiler().push("buffer");
        Vec3 offset = Vec3.atLowerCornerOf(renderPos).subtract(mc().gameRenderer.getMainCamera().getPosition());
        VertexConsumer builder = new GhostVertexConsumer(buffers.getBuffer(ForgeRenderTypes.TRANSLUCENT_ON_PARTICLES_TARGET.get()), 0xAA);
        mc().getProfiler().pop(); //buffer
        mc().getProfiler().push("draw");
        for (RenderType type : RenderType.chunkBufferLayers())
        {
            doRenderGhostBlockInLayer(mstack, builder, renderPos, renderState, type, offset, modelData);
        }
        mc().getProfiler().pop(); //draw
        mc().getProfiler().push("upload");
        ((MultiBufferSource.BufferSource) buffers).endBatch(ForgeRenderTypes.TRANSLUCENT_ON_PARTICLES_TARGET.get());
        ForgeHooksClient.setRenderType(null);
        mc().getProfiler().pop(); //upload
    }
    private static boolean canRenderInLayer(BlockState state, RenderType layer){
        if (state.isAir()) {
            return layer == RenderType.cutout();
        }
        return ItemBlockRenderTypes.canRenderInLayer(state, layer);
    }
    private static void doRenderGhostBlockInLayer(PoseStack mstack, VertexConsumer builder, BlockPos renderPos, BlockState renderState, RenderType layer, Vec3 offset, IModelData modelData){
        ForgeHooksClient.setRenderType(layer);

        mstack.pushPose();
        mstack.translate(offset.x, offset.y, offset.z);

        mc().getBlockRenderer().renderBatched(
                renderState,
                renderPos,
                mc().level,
                mstack,
                builder,
                false,
                RANDOM,
                modelData
        );

        mstack.popPose();
    }


    private static Minecraft mc(){
        return Minecraft.getInstance();
    }
}
