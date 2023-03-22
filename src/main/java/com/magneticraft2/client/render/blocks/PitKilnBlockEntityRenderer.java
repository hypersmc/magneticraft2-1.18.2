package com.magneticraft2.client.render.blocks;

import com.magneticraft2.common.tile.stage.early.PitKilnBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.common.render.ItemStackRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraftforge.client.model.data.EmptyModelData;

/**
 * @author JumpWatch on 21-03-2023
 * @Project magneticraft2-1.18.2
 * v1.0.0
 */
public class PitKilnBlockEntityRenderer implements BlockEntityRenderer<PitKilnBlockEntity> {

    public PitKilnBlockEntityRenderer(BlockEntityRendererProvider.Context context) {

    }
        @Override
    public void render(PitKilnBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        pPoseStack.pushPose();
        pPoseStack.translate(0.5D, 0.5D, 0.5D);
    }
}
