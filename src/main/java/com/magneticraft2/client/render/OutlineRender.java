package com.magneticraft2.client.render;

import com.magneticraft2.common.systems.multiblock.Multiblock;
import com.magneticraft2.common.tile.RenderBaseTile;
import com.magneticraft2.common.tile.testblock;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

/**
 * @author JumpWatch on 26-01-2023
 * @Project magneticraft2-1.18.2
 * v1.0.0
 */
public class OutlineRender extends RenderBaseTile<Multiblock> {
    public OutlineRender(BlockEntityRendererProvider.Context rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(Multiblock te, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {

    }


}
