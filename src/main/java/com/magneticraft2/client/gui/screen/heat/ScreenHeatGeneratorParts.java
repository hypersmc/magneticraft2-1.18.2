package com.magneticraft2.client.gui.screen.heat;

import com.magneticraft2.client.gui.container.Heat.ContainerHeatGeneratorParts;
import com.magneticraft2.client.gui.screen.button.GenericButton;
import com.magneticraft2.common.magneticraft2;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ScreenHeatGeneratorParts extends AbstractContainerScreen<ContainerHeatGeneratorParts> {
    public static final Logger LOGGER = LogManager.getLogger();
    private ResourceLocation GUI = new ResourceLocation(magneticraft2.MOD_ID + ":textures/gui/heatgeneratorpartsgui.png");
    private GenericButton button;

    public ScreenHeatGeneratorParts(ContainerHeatGeneratorParts container, Inventory inv, Component name) {
        super(container, inv, name);
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI);
    }
}
