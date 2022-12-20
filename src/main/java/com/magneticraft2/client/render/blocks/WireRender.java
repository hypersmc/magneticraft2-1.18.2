package com.magneticraft2.client.render.blocks;

import com.magneticraft2.common.tile.WireBaseTile;
import com.magneticraft2.common.tile.wire.BlockEntityHVConnectorBase;
import com.magneticraft2.common.utils.generalUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;

public class WireRender extends WireBaseTile<BlockEntityHVConnectorBase> {
    public static final Logger LOGGER = LogManager.getLogger("MGC2WireRender");
    public WireRender(BlockEntityRendererProvider.Context rendererDispatcherIn){
        super(rendererDispatcherIn);
    }
    private static final Color c = new Color(56, 56, 56, 255);
    private static final Color c2 = new Color(43, 43, 43, 255);
    public static void renderWire(BlockPos sta, BlockPos end, double x, double y, double z, MultiBufferSource pBufferSource){
        mc().getProfiler().push("buffer");
        VertexConsumer vertexConsumer = pBufferSource.getBuffer(Sheets.translucentCullBlockSheet());
        mc().getProfiler().pop(); //buffer
        mc().getProfiler().push("draw");
        Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(new ResourceLocation("forge:white"));
        //LOGGER.info("started to get axis x: " + x + " Y: " + y + " Z: " + z + " lets see how this goes");
        if (sta.getY() > end.getY()) return;
        boolean sameLevel = sta.getY() == end.getY();
        y -= 0.97D;
        x += 0.5D;
        z += 0.5D;
        double d6 = end.getX();
        double d7 = end.getY();
        double d8 = end.getZ();
        //LOGGER.info("End XYZ X: " + end.getX() + " Y: " + end.getY() + " Z: " + end.getZ());
        double d10 = sta.getX();
        double d11 = sta.getY();
        double d12 = sta.getZ();
        //LOGGER.info("start XYZ X: " + sta.getX() + " Y: " + sta.getY() + " Z: " + sta.getZ());
        double d13 = d6-d10;
        double d14 = d7 - (d11 - 1.33D);
        double d15 = d8 - d12;

        int i = 24;
        for (int j = 0; j < i; j++) {
            if (!sameLevel || j < (i / 2) + 1){
                float f = generalUtils.normalizeClamped(c.getRed(), 0, 255);
                float f1 = generalUtils.normalizeClamped(c.getGreen(), 0, 255);
                float f2 = generalUtils.normalizeClamped(c.getBlue(), 0, 255);
                if (j % 2 == 0){
                    f = generalUtils.normalizeClamped(c2.getRed(), 0, 255);
                    f1 = generalUtils.normalizeClamped(c2.getGreen(), 0, 255);
                    f2 = generalUtils.normalizeClamped(c2.getBlue(), 0, 255);
                }
                float f3 = (float) j / 24.0F;
                double v = (d14 * (f3 * f3 + f3)) * 0.5D;
                double b = ((24.0F - j) / 18.0F + 0.125F);
                drawSurfaces(pBufferSource,x + d13 * f3, y + v + b, z + d15 * f3, f, f1, f2, c.getRed(), 1,1, 1, 1, 1);
                drawSurfaces(pBufferSource,x + d13 * f3 + 0.025D, y + v + b + 0.025D, z + d15 * f3, f, f1, f2, c.getRed(), 1,1, 1, 1, 1);

                mc().getProfiler().pop(); //draw
                mc().getProfiler().push("upload");
            }
        }


        for (int k = 0; k < i; k++) {
            if (!sameLevel || k < (i / 2) + 1)
            {
                float f4 = generalUtils.normalizeClamped(c.getRed(), 0, 255);
                float f5 = generalUtils.normalizeClamped(c.getGreen(), 0, 255);
                float f6 = generalUtils.normalizeClamped(c.getBlue(), 0, 255);

                if (k % 2 == 0)
                {
                    f4 = generalUtils.normalizeClamped(c2.getRed(), 0, 255);
                    f5 = generalUtils.normalizeClamped(c2.getGreen(), 0, 255);
                    f6 = generalUtils.normalizeClamped(c2.getBlue(), 0, 255);
                }

                float f7 = (float) k / 24.0F;
                double v = d14 * (f7 * f7 + f7) * 0.5D;
                double b = ((24.0F - k) / 18.0F + 0.125F);
                drawSurfaces(pBufferSource,x + d13 * f7, y + v + b + 0.025D, z + d15 * f7, f4, f5, f6, c.getTransparency(), 1,1, 1, 1, 1);
                drawSurfaces(pBufferSource,x + d13 * f7 + 0.025D, y + v + b, z + d15 * f7 + 0.025D, f4, f5, f6, c.getTransparency(), 1,1, 1, 1, 1);
                mc().getProfiler().pop(); //draw
                mc().getProfiler().push("upload");

            }
        }
        mc().getProfiler().pop(); //upload
    }
    private static void drawSurfaces(MultiBufferSource buffer, double x, double y, double z, float r, float g, float b, float alpha, int minU, int maxU, int minV, int maxV, int lmap)
    {
        VertexConsumer builder = buffer.getBuffer(Sheets.translucentCullBlockSheet());
        builder.vertex(x, y, z).color(r, g, b, alpha).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(0, -1, 0).endVertex();
        builder.vertex(x, y, z).color(r, g, b, alpha).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(0, -1, 0).endVertex();
        builder.vertex(x, y, z).color(r, g, b, alpha).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(0, -1, 0).endVertex();
        builder.vertex(x, y, z).color(r, g, b, alpha).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(0, -1, 0).endVertex();

        //Top
        builder.vertex(x, y, z).color(r, g, b, alpha).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(0, 1, 0).endVertex();
        builder.vertex(x, y, z).color(r, g, b, alpha).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(0, 1, 0).endVertex();
        builder.vertex(x, y, z).color(r, g, b, alpha).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(0, 1, 0).endVertex();
        builder.vertex(x, y, z).color(r, g, b, alpha).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(0, 1, 0).endVertex();

        //North
        builder.vertex(x, y, z).color(r, g, b, alpha).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(0, 0, -1).endVertex();
        builder.vertex(x, y, z).color(r, g, b, alpha).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(0, 0, -1).endVertex();
        builder.vertex(x, y, z).color(r, g, b, alpha).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(0, 0, -1).endVertex();
        builder.vertex(x, y, z).color(r, g, b, alpha).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(0, 0, -1).endVertex();

        //South
        builder.vertex(x, y, z).color(r, g, b, alpha).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(0, 0, 1).endVertex();
        builder.vertex(x, y, z).color(r, g, b, alpha).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(0, 0, 1).endVertex();
        builder.vertex(x, y, z).color(r, g, b, alpha).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(0, 0, 1).endVertex();
        builder.vertex(x, y, z).color(r, g, b, alpha).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(0, 0, 1).endVertex();

        //West
        builder.vertex(x, y, z).color(r, g, b, alpha).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(-1, 0, 0).endVertex();
        builder.vertex(x, y, z).color(r, g, b, alpha).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(-1, 0, 0).endVertex();
        builder.vertex(x, y, z).color(r, g, b, alpha).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(-1, 0, 0).endVertex();
        builder.vertex(x, y, z).color(r, g, b, alpha).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(-1, 0, 0).endVertex();

        //East
        builder.vertex(x, y, z).color(r, g, b, alpha).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(1, 0, 0).endVertex();
        builder.vertex(x, y, z).color(r, g, b, alpha).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(1, 0, 0).endVertex();
        builder.vertex(x, y, z).color(r, g, b, alpha).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(1, 0, 0).endVertex();
        builder.vertex(x, y, z).color(r, g, b, alpha).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(1, 0, 0).endVertex();

    }


    @Override
    public void render(BlockEntityHVConnectorBase te, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        BlockEntity BE = te.getLevel().getBlockEntity(te.getBlockPos());
        if (BE instanceof BlockEntityHVConnectorBase) {
            //LOGGER.info("" + ((BlockEntityHVConnectorBase) BE).getMaster());
        }

        if (te.isRightConnected()){
            //LOGGER.info("am i triggered1?");
            renderWire(te.getBlockPos(), te.rightConnectionPos, te.getBlockPos().getX(), te.getBlockPos().getY(), te.getBlockPos().getZ(), pBufferSource);

        }
        if (te.isLeftConnected()){
            //LOGGER.info("am i triggered2?");
            renderWire(te.getBlockPos(), te.leftConnectionPos, te.getBlockPos().getX(), te.getBlockPos().getY(), te.getBlockPos().getZ(), pBufferSource);

        }
    }
    private static Minecraft mc(){
        return Minecraft.getInstance();
    }

}
