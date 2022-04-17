package com.magneticraft2.client.gui.screen;

import com.magneticraft2.client.gui.container.ContainerHeatGenerator;
import com.magneticraft2.common.magneticraft2;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ScreenHeatGenerator extends AbstractContainerScreen<ContainerHeatGenerator> {
    private ResourceLocation GUI = new ResourceLocation(magneticraft2.MOD_ID + ":textures/gui/heatgeneratorgui.png");
    public ScreenHeatGenerator(ContainerHeatGenerator container, Inventory inv, Component name) {
        super(container, inv, name);
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
        if (mouseX > leftPos + 49 && mouseX < leftPos + 62 && mouseY > topPos + 27 && mouseY < topPos + 67)
            this.renderTooltip(matrixStack, new TextComponent( this.menu.getHeat() + " c"), mouseX, mouseY);
        if (mouseX > leftPos + 121 && mouseX < leftPos + 134 && mouseY > topPos + 27 && mouseY < topPos + 67)
            this.renderTooltip(matrixStack, new TextComponent(this.menu.getEnergy() + " RF"), mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindForSetup(GUI);
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        this.blit(matrixStack, relX, relY, 0, 0, this.imageWidth + 4, this.imageHeight + 25);
        int k = this.getEnergyStoredScaled(40);
        this.blit(matrixStack, this.leftPos + 121, this.topPos + 27, 180, 42, 16, 41 - k);
        int c = this.getHeatStoredScaled(40);
        this.blit(matrixStack, this.leftPos + 49, this.topPos + 27, 180, 0, 16 , (int) (41 - c));
    }

    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {

    }

    private int getEnergyStoredScaled(int pixels) {
        int i = this.menu.getEnergy();
        int j = this.menu.getEnergylimit();
        return i != 0 && j != 0 ? i * pixels / j : 0;
    }
    private int getHeatStoredScaled(int pixels) {
        int i = this.menu.getHeat();
        int j = this.menu.getHeatLimit();
        return i != 0 && j != 0 ? i * pixels / j : 0;
    }
}
