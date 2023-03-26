package com.magneticraft2.client.render.blocks;

import com.magneticraft2.common.tile.stage.early.PitKilnBlockEntity;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import mezz.jei.common.render.ItemStackRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author JumpWatch on 21-03-2023
 * @Project magneticraft2-1.18.2
 * v1.0.0
 */
public class PitKilnBlockEntityRenderer implements BlockEntityRenderer<PitKilnBlockEntity> {
    private static final Logger LOGGER = LogManager.getLogger("Pitrender");
    public PitKilnBlockEntityRenderer(BlockEntityRendererProvider.Context context) {

    }
    @Override
    public void render(PitKilnBlockEntity blockEntity, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {

            matrixStack.pushPose();
            matrixStack.translate(0.5D, 0.5D, 0.5D);
            for (int i = 2; i <= 5; i++) {
                IItemHandler itemHandler = blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).orElse(null);
                ItemStack stack = itemHandler.getStackInSlot(i);
                if (!stack.isEmpty()) {
//                    LOGGER.info("Aware of " + stack + " in slot " + i);
                    matrixStack.pushPose();
                    matrixStack.translate((i - 2) % 2 * 0.6D - 0.3D, -0.25D, (i - 2) / 2 * 0.6D - 0.3D);
                    matrixStack.scale(0.5F, 0.5F, 0.5F);
                    Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.FIXED, combinedLight, combinedOverlay, matrixStack, buffer, 0);
                    matrixStack.popPose();
                }
            }
            matrixStack.popPose();
    }
}
