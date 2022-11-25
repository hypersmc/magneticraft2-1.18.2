package com.magneticraft2.client.render.blocks;

import com.magneticraft2.common.tile.WireBaseTile;
import com.magneticraft2.common.tile.wire.BlockEntityHVConnectorBase;
import com.magneticraft2.common.utils.generalUtils;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.util.*;
import java.util.List;

public class WireRender extends WireBaseTile<BlockEntityHVConnectorBase> {
    public static final Logger LOGGER = LogManager.getLogger("MGC2WireRender");
    public WireRender(BlockEntityRendererProvider.Context rendererDispatcherIn){
        super(rendererDispatcherIn);
    }
    private static final Color c = new Color(56, 56, 56, 255);
    private static final Color c2 = new Color(43, 43, 43, 255);
    public static void renderWire(BlockPos sta, BlockPos end, double x, double y, double z){

        //LOGGER.info("started to get axis x: " + x + " Y: " + y + " Z: " + z + " lets see how this goes");
        if (sta.getY() > end.getY()) return;
        boolean sameLevel = sta.getY() == end.getY();
        y -= 0.97D;
        x += 0.5D;
        z += 0.5D;
        BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
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
        GlStateManager._disableTexture();
        //GlStateManager.disableLighting();
        GlStateManager._disableCull();
        RenderSystem.setShader(GameRenderer::getPositionColorLightmapShader);
        bufferBuilder.begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);
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

                bufferBuilder.vertex(x + d13 * f3, y + v + b, z + d15 * f3)
                        .color(f, f1, f2, c.getRed()).endVertex();
                bufferBuilder.vertex(x + d13 * f3 + 0.025D, y + v + b + 0.025D, z + d15 * f3)
                        .color(f, f1, f2, c.getRed()).endVertex();
            }
        }
        bufferBuilder.end();
        BufferUploader.end(bufferBuilder);
        //Tesselator.draw(); there **should** be a draw in the Tesselator?
//        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
//        for (int k = 0; k < i; k++) {
//            if (!sameLevel || k < (i / 2) + 1)
//            {
//                float f4 = generalUtils.normalizeClamped(c.getRed(), 0, 255);
//                float f5 = generalUtils.normalizeClamped(c.getGreen(), 0, 255);
//                float f6 = generalUtils.normalizeClamped(c.getBlue(), 0, 255);
//
//                if (k % 2 == 0)
//                {
//                    f4 = generalUtils.normalizeClamped(c2.getRed(), 0, 255);
//                    f5 = generalUtils.normalizeClamped(c2.getGreen(), 0, 255);
//                    f6 = generalUtils.normalizeClamped(c2.getBlue(), 0, 255);
//                }
//
//                float f7 = (float) k / 24.0F;
//                double v = d14 * (f7 * f7 + f7) * 0.5D;
//                double b = ((24.0F - k) / 18.0F + 0.125F);
//
//                bufferBuilder.vertex(x + d13 * f7, y + v + b + 0.025D, z + d15 * f7)
//                        .color(f4, f5, f6, c.getTransparency()).endVertex();
//                bufferBuilder.vertex(x + d13 * f7 + 0.025D, y + v + b, z + d15 * f7 + 0.025D)
//                        .color(f4, f5, f6, c.getTransparency()).endVertex();
//            }
//        }
//        bufferBuilder.end();
//        BufferUploader.end(bufferBuilder);
        //Tesselator.draw(); there **should** be a draw in the Tesselator?
        //GlStateManager.enableLighting();
        GlStateManager._enableTexture();
        GlStateManager._enableCull();
    }
    public void drawLine( BufferBuilder t, Vec3 a, Vec3 b){
        double w = 0.0625 / 2;
        t.vertex(a.x, a.y - w, a.z).uv(0.0f, 0.0f).normal(0f, 1f, 0f).endVertex();
        t.vertex(a.x, a.y + w, a.z).uv(0.125f, 0.0f).normal(0f, 1f, 0f).endVertex();
        t.vertex(b.x, b.y + w, b.z).uv(0.125f, 1.0f).normal(0f, 1f, 0f).endVertex();
        t.vertex(b.x, b.y - w, b.z).uv(0.0f, 1.0f).normal(0f, 1f, 0f).endVertex();
        t.vertex(a.x, a.y, a.z - w).uv(0.0f, 0.0f).normal(0f, 1f, 0f).endVertex();
        t.vertex(a.x, a.y, a.z + w).uv(0.125f, 0.0f).normal(0f, 1f, 0f).endVertex();
        t.vertex(b.x, b.y, b.z + w).uv(0.125f, 1.0f).normal(0f, 1f, 0f).endVertex();
        t.vertex(b.x, b.y, b.z - w).uv(0.0f, 1.0f).normal(0f, 1f, 0f).endVertex();
        t.vertex(a.x - w, a.y, a.z).uv(0.0f, 0.0f).normal(0f, 1f, 0f).endVertex();
        t.vertex(a.x + w, a.y, a.z).uv(0.125f, 0.0f).normal(0f, 1f, 0f).endVertex();
        t.vertex(b.x + w, b.y, b.z).uv(0.125f, 1.0f).normal(0f, 1f, 0f).endVertex();
        t.vertex(b.x - w, b.y, b.z).uv(0.0f, 1.0f).normal(0f, 1f, 0f).endVertex();
        //inverted
        t.vertex(a.x, a.y + w, a.z).uv(0.0f, 0.0f).normal(0f, 1f, 0f).endVertex();
        t.vertex(a.x, a.y - w, a.z).uv(0.125f, 0.0f).normal(0f, 1f, 0f).endVertex();
        t.vertex(b.x, b.y - w, b.z).uv(0.125f, 1.0f).normal(0f, 1f, 0f).endVertex();
        t.vertex(b.x, b.y + w, b.z).uv(0.0f, 1.0f).normal(0f, 1f, 0f).endVertex();
        t.vertex(a.x, a.y, a.z + w).uv(0.0f, 0.0f).normal(0f, 1f, 0f).endVertex();
        t.vertex(a.x, a.y, a.z - w).uv(0.125f, 0.0f).normal(0f, 1f, 0f).endVertex();
        t.vertex(b.x, b.y, b.z - w).uv(0.125f, 1.0f).normal(0f, 1f, 0f).endVertex();
        t.vertex(b.x, b.y, b.z + w).uv(0.0f, 1.0f).normal(0f, 1f, 0f).endVertex();
        t.vertex(a.x + w, a.y, a.z).uv(0.0f, 0.0f).normal(0f, 1f, 0f).endVertex();
        t.vertex(a.x - w, a.y, a.z).uv(0.125f, 0.0f).normal(0f, 1f, 0f).endVertex();
        t.vertex(b.x - w, b.y, b.z).uv(0.125f, 1.0f).normal(0f, 1f, 0f).endVertex();
        t.vertex(b.x + w, b.y, b.z).uv(0.0f, 1.0f).normal(0f, 1f, 0f).endVertex();
    }
    public void drawWireBetween(Vec3 start, Vec3 end, double weight){
        BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();

        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        List<Vec3> points  = interpolateWire(start, end, weight);
        for (int p = 0; p < points.size() -2; p++) {
            drawLine(bufferBuilder, points.get(p), points.get(p + 1));

        }

    }
    public List<Vec3> interpolateWire(Vec3 start, Vec3 end, double mass){

        List<Vec3> list = new ArrayList<Vec3>();
        double distance = start.distanceTo(end);
        Vec3 middle = Vec3.upFromBottomCenterOf((start.x + end.x) / 2, (start.y + end.y) / 2 - distance * mass, (start.z + end.z) / 2); // error
        for (int i = 0; i < 10; i++) {
            double p = i / 10.0;
            double x = interpolate(start.x, middle.x, end.x, p);
            double y = interpolate(start.y, middle.y, end.y, p);
            double z = interpolate(start.z, middle.z, end.z, p);
            list.add(Vec3(x, y, z)); // error
        }
        return list;

    }
    public double interpolate(double fa, double fb, double fc, double x){
        double a = 0.0;
        double b = 0.5;
        double c = 1.0;
        double L0 = (x - b) / (a - b) * ((x - c) / (a - c));
        double L1 = (x - a) / (b - a) * ((x - c) / (b - c));
        double L2 = (x - a) / (c - a) * ((x - b) / (c - b));
        return fa * L0 + fb * L1 + fc * L2;
    }
    public double interpolate(double fa, double fb, double x){
        return fa + (fb -fa) * x;
    }
    public float interpolate(float fa, float fb, float x){
        return fa + (fb - fa) * x;
    }

    @Override
    public void render(BlockEntityHVConnectorBase te, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        BlockEntity BE = te.getLevel().getBlockEntity(te.getBlockPos());
        if (BE instanceof BlockEntityHVConnectorBase) {
            //LOGGER.info("" + ((BlockEntityHVConnectorBase) BE).getMaster());
        }

        if (te.isRightConnected()){
            //LOGGER.info("am i triggered1?");
            renderWire(te.getBlockPos(), te.rightConnectionPos, te.getBlockPos().getX(), te.getBlockPos().getY(), te.getBlockPos().getZ());

        }
        if (te.isLeftConnected()){
            //LOGGER.info("am i triggered2?");
            renderWire(te.getBlockPos(), te.leftConnectionPos, te.getBlockPos().getX(), te.getBlockPos().getY(), te.getBlockPos().getZ());

        }
    }

}
