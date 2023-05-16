package com.magneticraft2.common.tile;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class RenderBaseTile<T extends BlockEntity> implements BlockEntityRenderer<T> {
    public double xPos = 0D;
    public double zPos = 0D;

    public RenderBaseTile(BlockEntityRendererProvider.Context rendererDispatcherIn) {

    }





    @Override
    public abstract void render(T te, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay);

    //@Override
    //public  void render(T te, double x, double y, double z, float pTicks, int destroyStage, float alpha);

}
