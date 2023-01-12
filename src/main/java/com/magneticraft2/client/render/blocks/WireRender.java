package com.magneticraft2.client.render.blocks;

import com.magneticraft2.common.tile.WireBaseTile;
import com.magneticraft2.common.tile.wire.BlockEntityHVConnectorBase;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3d;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class WireRender extends WireBaseTile<BlockEntityHVConnectorBase> {
    public static final Logger LOGGER = LogManager.getLogger("MGC2WireRender");
    public WireRender(BlockEntityRendererProvider.Context rendererDispatcherIn){
        super(rendererDispatcherIn);
    }
    public static void renderWire(BlockPos sta, BlockPos end, double x, double y, double z, MultiBufferSource pBufferSource, PoseStack poseStack){
        Matrix4f matrix = poseStack.last().pose();
        Matrix3f normal = poseStack.last().normal();
        Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(new ResourceLocation("forge:white"));
        if (sta.getY() > end.getY()) return;
        boolean sameLevel = sta.getY() == end.getY();
        y -= 0.50D;
        x += 0.5D;
        z += 0.5D;
        float endX = end.getX();
        float endY = end.getY();
        float endZ = end.getZ();

        float startX = sta.getX();
        float startY = sta.getY();
        float startZ = sta.getZ();

        float distX = endX-startX;
        float distY = (float) (endY - (startY - 1.33D));
        float distZ = endZ - startZ;
        int i = 24;
        for (int j = 0; j < i; j++) {
            if (!sameLevel || j < (i / 2) + 1){
                float f3 = (float) j / 24.0F;
                double v = (distY * (f3 * f3 + f3)) * 0.5D;
                double b = ((24.0F - j) / 18.0F + 0.125F);
                drawSurfaces(pBufferSource, (float) (x + distX * f3), (float) (y + v + b), (float) (z + distZ * f3), 56, 56, 56,1, 1,1, 1, 1, 1, matrix, normal, poseStack);
                drawSurfaces(pBufferSource, (float) (x + distX * f3 + 0.025D), (float) (y + v + b + 0.025D), (float) (z + distZ * f3), 56, 56, 56,1, 1,1, 1, 1, 1, matrix, normal, poseStack);

            }
        }


        for (int k = 0; k < i; k++) {
            if (!sameLevel || k < (i / 2) + 1)
            {

                float f7 = (float) k / 24.0F;
                double v = distY * (f7 * f7 + f7) * 0.5D;
                double b = ((24.0F - k) / 18.0F + 0.125F);
                drawSurfaces(pBufferSource, (float) (x + distX * f7), (float) (y + v + b + 0.025D), (float) (z + distZ * f7), 43, 43, 43, 1, 1,1, 1, 1, 1, matrix, normal, poseStack);
                drawSurfaces(pBufferSource, (float) (x + distX * f7 + 0.025D), (float) (y + v + b), (float) (z + distZ * f7 + 0.025D), 43, 43, 43, 1, 1,1, 1, 1, 1, matrix, normal, poseStack);


            }
        }







        //LOGGER.info("EndX: " + endX + " EndY: " + endY + " EndZ: " + endZ);
        //LOGGER.info("startX: " + startX + " startY: " + startY + " startZ: " + startZ);


    }
    private static void drawSurfaces(MultiBufferSource buffer, float x, float y, float z, float r, float g, float b, float alpha, int minU, int maxU, int minV, int maxV, int lmap, Matrix4f matrix, Matrix3f normal, PoseStack poseStack)
    {

        VertexConsumer builder = buffer.getBuffer(Sheets.translucentCullBlockSheet());
        builder.vertex(matrix, x, y, z).color(r, g, b, alpha).uv(0.0F, 0.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(normal, 0f, 1f, 0f).endVertex();
        builder.vertex(matrix, x, y, z).color(r, g, b, alpha).uv(0.125F, 0.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(normal, 0f, 1f, 0f).endVertex();
        builder.vertex(matrix, x, y, z).color(r, g, b, alpha).uv(0.125F, 1.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(normal, 0f, 1f, 0f).endVertex();
        builder.vertex(matrix, x, y, z).color(r, g, b, alpha).uv(0.0F, 1.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(normal, 0f, 1f, 0f).endVertex();

        //Top
        builder.vertex(matrix, x, y, z).color(r, g, b, alpha).uv(0.0F, 0.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(normal, 0f, 1f, 0f).endVertex();
        builder.vertex(matrix, x, y, z).color(r, g, b, alpha).uv(0.125F, 0.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(normal, 0f, 1f, 0f).endVertex();
        builder.vertex(matrix, x, y, z).color(r, g, b, alpha).uv(0.125F, 1.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(normal, 0f, 1f, 0f).endVertex();
        builder.vertex(matrix, x, y, z).color(r, g, b, alpha).uv(0.0F, 1.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(normal, 0f, 1f, 0f).endVertex();

        //North
        builder.vertex(matrix, x, y, z).color(r, g, b, alpha).uv(0.0F, 0.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(normal, 0f, 1f, 0f).endVertex();
        builder.vertex(matrix, x, y, z).color(r, g, b, alpha).uv(0.125F, 0.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(normal, 0f, 1f, 0f).endVertex();
        builder.vertex(matrix, x, y, z).color(r, g, b, alpha).uv(0.125F, 1.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(normal, 0f, 1f, 0f).endVertex();
        builder.vertex(matrix, x, y, z).color(r, g, b, alpha).uv(0.0F, 1.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(normal, 0f, 1f, 0f).endVertex();

        //South
        builder.vertex(matrix, x, y, z).color(r, g, b, alpha).uv(0.0F, 0.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(normal, 0f, 1f, 0f).endVertex();
        builder.vertex(matrix, x, y, z).color(r, g, b, alpha).uv(0.125F, 0.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(normal, 0f, 1f, 0f).endVertex();
        builder.vertex(matrix, x, y, z).color(r, g, b, alpha).uv(0.125F, 1.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(normal, 0f, 1f, 0f).endVertex();
        builder.vertex(matrix, x, y, z).color(r, g, b, alpha).uv(0.0F, 1.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(normal, 0f, 1f, 0f).endVertex();

        //West
        builder.vertex(matrix, x, y, z).color(r, g, b, alpha).uv(0.0F, 0.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(normal, 0f, 1f, 0f).endVertex();
        builder.vertex(matrix, x, y, z).color(r, g, b, alpha).uv(0.125F, 0.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(normal, 0f, 1f, 0f).endVertex();
        builder.vertex(matrix, x, y, z).color(r, g, b, alpha).uv(0.125F, 1.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(normal, 0f, 1f, 0f).endVertex();
        builder.vertex(matrix, x, y, z).color(r, g, b, alpha).uv(0.0F, 1.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(normal, 0f, 1f, 0f).endVertex();

        //East
        builder.vertex(matrix, x, y, z).color(r, g, b, alpha).uv(0.0F, 0.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(normal, 0f, 1f, 0f).endVertex();
        builder.vertex(matrix, x, y, z).color(r, g, b, alpha).uv(0.125F, 0.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(normal, 0f, 1f, 0f).endVertex();
        builder.vertex(matrix, x, y, z).color(r, g, b, alpha).uv(0.125F, 1.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(normal, 0f, 1f, 0f).endVertex();
        builder.vertex(matrix, x, y, z).color(r, g, b, alpha).uv(0.0F, 1.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(normal, 0f, 1f, 0f).endVertex();

    }


    @Override
    public void render(BlockEntityHVConnectorBase te, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {

        if (te.isRightConnected()){
            renderWire(te.getBlockPos(), te.rightConnectionPos, te.getBlockPos().getX(), te.getBlockPos().getY(), te.getBlockPos().getZ(), pBufferSource, pPoseStack);
        }
        if (te.isLeftConnected()){
            renderWire(te.getBlockPos(), te.leftConnectionPos, te.getBlockPos().getX(), te.getBlockPos().getY(), te.getBlockPos().getZ(), pBufferSource, pPoseStack);

        }
    }

    @Override
    public boolean shouldRenderOffScreen(BlockEntityHVConnectorBase pBlockEntity) {
        return true;
    }

    @Override
    public boolean shouldRender(BlockEntityHVConnectorBase pBlockEntity, Vec3 pCameraPos) {
        return true;
    }


}
