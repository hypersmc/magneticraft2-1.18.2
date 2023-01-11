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

        float fX = endX-startX;
        float fY = (float) (endY - (startY - 1.33D));
        float fZ = endZ - startZ;

        drawline(pBufferSource, startX, startY, startZ, 56, 56, 56, 255, 1,1,1,1,1,matrix,normal);
        drawline(pBufferSource, endX, endY, endZ, 56, 56, 56, 255, 1,1,1,1,1,matrix,normal);





        //LOGGER.info("EndX: " + endX + " EndY: " + endY + " EndZ: " + endZ);
        //LOGGER.info("startX: " + startX + " startY: " + startY + " startZ: " + startZ);


    }
    private static void drawline(MultiBufferSource buffer, float x, float y, float z, float r, float g, float b, float alpha, int minU, int maxU, int minV, int maxV, int lmap, Matrix4f matrix, Matrix3f normal)
    {
        VertexConsumer builder = buffer.getBuffer(RenderType.LINES);
        builder.vertex(matrix, x, y, z).color(r, g, b, alpha).normal(normal, 0.0F, 1.0F, 0.0F).endVertex(); //.uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lmap).normal(normal, 0, -1, 0)

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
