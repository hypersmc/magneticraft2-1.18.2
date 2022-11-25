package com.magneticraft2.client.render.blocks;

import com.magneticraft2.common.magneticraft2;
import com.magneticraft2.common.tile.machines.solars.ModelSolarPanel;
import com.magneticraft2.common.tile.machines.solars.solatesttile;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.DimensionDataStorage;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class solartest implements BlockEntityRenderer<solatesttile> {

    private static final ResourceLocation solartexture = new ResourceLocation(magneticraft2.MOD_ID + "textures/model/testsolartex.png");
    public ModelSolarPanel model = new ModelSolarPanel();

    @Override
    public void render(solatesttile pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        boolean doSkyRotation = false;
        RenderSystem.setShaderTexture(0, solartexture);
        doSkyRotation = pBlockEntity.getLevel() instanceof Level;
        GL11.glPushMatrix();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glTranslatef(0.5f, 1.0f, 0.5f);
        if (doSkyRotation) {
            GL11.glPushMatrix();
            GL11.glRotatef(((Level)pBlockEntity.getLevel()).getSunAngle(pPartialTick), 0.0f, 1.0f, 0.0f);
            this.model.renderPole();
            GL11.glPopMatrix();
        }else{
            this.model.renderPole();
        }
        GL11.glTranslatef(0.0f, 1.5f, 0.0f);
        GL11.glRotatef(180.0F, 0, 0, 1);
        GL11.glRotatef(-90.0F, 0, 1, 0);

        float celestialAngle = (pBlockEntity.getLevel().getTimeOfDay(pPartialTick) - 0.784690560F) * 360.0F;
        float celestialAngle2 = pBlockEntity.getLevel().getTimeOfDay(pPartialTick) * 360.0F;
        if (doSkyRotation)
            GL11.glRotatef(((Level)pBlockEntity.getLevel()).getSunAngle(pPartialTick), 0.0f, -1.0f, 0.0f);
        GL11.glRotatef(pBlockEntity.currentAngle - (celestialAngle - celestialAngle2), 1.0f, 0.0f, 0.0f);
        this.model.renderPanel();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

}
