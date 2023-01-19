package com.magneticraft2.client.render.blocks;

import com.magneticraft2.common.magneticraft2;
import com.magneticraft2.common.tile.WireBaseTile;
import com.magneticraft2.common.tile.wire.BlockEntityHVConnectorBase;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author JumpWatch
 * @Credits: XFactHD for remaking the wire system
 * v1.0.0
 */
public class WireRender extends WireBaseTile<BlockEntityHVConnectorBase> {
    public static final Logger LOGGER = LogManager.getLogger("MGC2WireRender");
    private static final int WIRE_SEGMENTS = 24;
    private static final int WIRE_SEGMENTS_HALF = WIRE_SEGMENTS / 2;
    private static final float WIRE_SAG = .5F;
    private static final float WIRE_WIDTH = .05F;
    private static final float WIRE_HALF_WIDTH = WIRE_WIDTH / 2F;
    private static final int NO_OVERLAY = OverlayTexture.NO_OVERLAY;
    @SuppressWarnings("deprecation")
    public static final ResourceLocation BLOCK_ATLAS = TextureAtlas.LOCATION_BLOCKS;
    public static final ResourceLocation TEXTURE = new ResourceLocation(magneticraft2.MOD_ID, "blocks/wire");
    private static final RenderType WIRE_RENDER_TYPE = RenderType.entityTranslucent(BLOCK_ATLAS, false);

    public WireRender(BlockEntityRendererProvider.Context rendererDispatcherIn){
        super(rendererDispatcherIn);
    }
    private static void renderWire(Level level, MultiBufferSource buffer, PoseStack poseStack, BlockPos start, BlockPos end, float x, float y, float z) {
        float distX = end.getX() - start.getX();
        float distY = end.getY() - start.getY();
        float distZ = end.getZ() - start.getZ();

        //Calculate yaw
        Quaternion rotY = Vector3f.YP.rotation((float) Mth.atan2(distX, distZ));

        VertexConsumer builder = buffer.getBuffer(WIRE_RENDER_TYPE);

        TextureAtlasSprite WHITE = Minecraft.getInstance().getTextureAtlas(BLOCK_ATLAS).apply(TEXTURE);
        float minU = WHITE.getU0();
        float maxU = WHITE.getU1();
        float minV = WHITE.getV0();
        float maxV = WHITE.getV1();

        int blockLightStart = level.getBrightness(LightLayer.BLOCK, start);
        int blockLightEnd = level.getBrightness(LightLayer.BLOCK, end);
        int skyLightStart = level.getBrightness(LightLayer.SKY, start);
        int skyLightEnd = level.getBrightness(LightLayer.SKY, end);

        for (int segment = 0; segment < WIRE_SEGMENTS; segment++) {
            float multStart = segment / (float) WIRE_SEGMENTS;
            float multEnd = (segment + 1) / (float) WIRE_SEGMENTS;

            float segStartX = x + Mth.lerp(multStart, 0, distX);
            float segStartY = y + Mth.lerp(multStart, 0, distY) - calculateSag(segment);
            float segStartZ = z + Mth.lerp(multStart, 0, distZ);
            float segEndX = x + Mth.lerp(multEnd, 0, distX);
            float segEndY = y + Mth.lerp(multEnd, 0, distY) - calculateSag(segment + 1);
            float segEndZ = z + Mth.lerp(multEnd, 0, distZ);

            float segLenX = segEndX - segStartX;
            float segLenY = segEndY - segStartY;
            float segLenZ = segEndZ - segStartZ;

            int lightSegStart = LightTexture.pack(
                    (int) Mth.lerp(multStart, blockLightStart, blockLightEnd),
                    (int) Mth.lerp(multStart, skyLightStart, skyLightEnd)
            );
            int lightSegEnd = LightTexture.pack(
                    (int) Mth.lerp(multEnd, blockLightStart, blockLightEnd),
                    (int) Mth.lerp(multEnd, skyLightStart, skyLightEnd)
            );

            drawWireSegment(builder, poseStack, segStartX, segStartY, segStartZ, segLenX, segLenY, segLenZ, rotY, minU, maxU, minV, maxV, lightSegStart, lightSegEnd);
        }
    }
    private static float calculateSag(int segment)
    {
        if (segment == WIRE_SEGMENTS)
        {
            return 0F;
        }

        float delta = (segment % WIRE_SEGMENTS_HALF) / (float)WIRE_SEGMENTS_HALF;
        float factor = Mth.lerp(delta, 0F, 1F);
        if (segment < WIRE_SEGMENTS_HALF)
        {
            factor = 1F - factor;
        }
        factor = 1F - factor * factor;
        return WIRE_SAG * factor;
    }

    private static void drawWireSegment(
            VertexConsumer builder, PoseStack mstack, float startX, float startY, float startZ, float lenX, float lenY,
            float lenZ, Quaternion rotY, float minU, float maxU, float minV, float maxV, int lightStart, int lightEnd
    )
    {
        //Calculate pitch
        float rotX = (float) Mth.atan2(lenY, Mth.sqrt(lenX * lenX + lenZ * lenZ));

        mstack.pushPose();

        //Translate to start point
        mstack.translate(startX, startY, startZ);
        //Rotate to point towards end point
        mstack.mulPose(rotY);
        mstack.mulPose(Vector3f.XN.rotation(rotX));

        //Calculate distance between points -> length of the line
        float distance = Mth.sqrt(lenX * lenX + lenY * lenY + lenZ * lenZ);
        float offset = WIRE_HALF_WIDTH;

        Matrix4f matrix = mstack.last().pose();
        Matrix3f normal = mstack.last().normal();

        //Draw horizontal quad
        builder.vertex(matrix, -offset, 0,        0).color(1F, 1F, 1F, 1F).uv(minU, minV).overlayCoords(NO_OVERLAY).uv2(lightStart).normal(normal, 0, 1, 0).endVertex();
        builder.vertex(matrix,  offset, 0,        0).color(1F, 1F, 1F, 1F).uv(maxU, minV).overlayCoords(NO_OVERLAY).uv2(lightStart).normal(normal, 0, 1, 0).endVertex();
        builder.vertex(matrix,  offset, 0, distance).color(1F, 1F, 1F, 1F).uv(maxU, maxV).overlayCoords(NO_OVERLAY).uv2(lightEnd).normal(normal, 0, 1, 0).endVertex();
        builder.vertex(matrix, -offset, 0, distance).color(1F, 1F, 1F, 1F).uv(minU, maxV).overlayCoords(NO_OVERLAY).uv2(lightEnd).normal(normal, 0, 1, 0).endVertex();

        //Draw vertical Quad
        builder.vertex(matrix, 0, -offset,        0).color(1F, 1F, 1F, 1F).uv(minU, minV).overlayCoords(NO_OVERLAY).uv2(lightStart).normal(normal, 1, 0, 0).endVertex();
        builder.vertex(matrix, 0,  offset,        0).color(1F, 1F, 1F, 1F).uv(maxU, minV).overlayCoords(NO_OVERLAY).uv2(lightStart).normal(normal, 1, 0, 0).endVertex();
        builder.vertex(matrix, 0,  offset, distance).color(1F, 1F, 1F, 1F).uv(maxU, maxV).overlayCoords(NO_OVERLAY).uv2(lightEnd).normal(normal, 1, 0, 0).endVertex();
        builder.vertex(matrix, 0, -offset, distance).color(1F, 1F, 1F, 1F).uv(minU, maxV).overlayCoords(NO_OVERLAY).uv2(lightEnd).normal(normal, 1, 0, 0).endVertex();

        mstack.popPose();
    }




    @Override
    public void render(BlockEntityHVConnectorBase te, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {

        if (te.getLevel() == null) { return; }

        if (te.isRightConnected())
        {
            renderWire(te.getLevel(), bufferSource, poseStack, te.getBlockPos(), te.rightConnectionPos, .5F, .5F, .5F);
        }
        if (te.isLeftConnected())
        {
            renderWire(te.getLevel(), bufferSource, poseStack, te.getBlockPos(), te.leftConnectionPos, .5F, .5F, .5F);
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
