package com.magneticraft2.client.gui.screen.primitive_furnace;

import com.magneticraft2.client.gui.container.primitive_furnace.containerPrimitive_Furnace;
import com.magneticraft2.client.gui.screen.button.GenericButton;
import com.magneticraft2.common.magneticraft2;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author JumpWatch on 03-02-2023
 * @Project magneticraft2-1.18.2
 * v1.0.0
 */
public class primitive_furnace_Screen extends AbstractContainerScreen<containerPrimitive_Furnace>{
    public static final Logger LOGGER = LogManager.getLogger("MGC2Primitive_furnace_screen_Logger");
    private ResourceLocation GUI = new ResourceLocation(magneticraft2.MOD_ID + ":textures/gui/primitive_furnace_gui.png");
    private GenericButton button;
    public primitive_furnace_Screen(containerPrimitive_Furnace container, Inventory inv, Component name) {
        super(container, inv, name);
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI);

        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        this.blit(matrixStack, relX, relY, 0, 0, this.imageWidth, this.imageHeight + 25);

    }

    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        font.draw(pPoseStack, new TranslatableComponent("container.magneticraft2.inventory"), 8, 75, 0x404040);
    }

}
