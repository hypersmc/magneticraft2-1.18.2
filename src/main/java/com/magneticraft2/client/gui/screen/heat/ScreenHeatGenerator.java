package com.magneticraft2.client.gui.screen.heat;

import com.magneticraft2.client.gui.HideableSlot;
import com.magneticraft2.client.gui.container.Heat.ContainerHeatGenerator;
import com.magneticraft2.client.gui.screen.button.GenericButton;
import com.magneticraft2.common.magneticraft2;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.items.CapabilityItemHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ScreenHeatGenerator extends AbstractContainerScreen<ContainerHeatGenerator> {
    public static final Logger LOGGER = LogManager.getLogger();
    private ResourceLocation GUI = new ResourceLocation(magneticraft2.MOD_ID + ":textures/gui/heatgeneratorgui.png");
    private ResourceLocation PARTS = new ResourceLocation(magneticraft2.MOD_ID + ":textures/gui/heatgeneratorguiparts.png");
    private GenericButton button;
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
        if (this.menu.getParts()) {
            RenderSystem.setShaderTexture(0, PARTS);
        }else if (!this.menu.getParts()) {
            RenderSystem.setShaderTexture(0, GUI);
        }else {
            this.menu.setParts(false);
        }
//        this.minecraft.getTextureManager().bindForSetup(GUI);
//        LOGGER.info(GUI);
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        this.blit(matrixStack, relX, relY, 0, 0, this.imageWidth + 4, this.imageHeight + 25);
        int k = this.getEnergyStoredScaled(40);
        this.blit(matrixStack, this.leftPos + 121, this.topPos + 27, 180, 42, 16, 41 - k);
        int c = this.getHeatStoredScaled(40);
        this.blit(matrixStack, this.leftPos + 49, this.topPos + 27, 180, 0, 16 , (int) (41 - c));
        
    }

    @Override
    protected void init() {
        super.init();
        int guiWidth = (width - imageWidth) / 2;
        int guiHeight = (height - imageHeight) / 2;
        button = new GenericButton(guiWidth+ 4, guiHeight +56, GenericButton.ButtonType.Generaic, button -> {
            if (!getMenu().getParts()) {
                getMenu().setParts(true);
//                menu.moveSlot(menu.getSlot(0),0, 68, 28);
//                menu.moveSlot(menu.getSlot(1),1, 100, 28);
//                menu.moveSlot(menu.getSlot(2),2, 68, 52);
//                menu.moveSlot(menu.getSlot(3),3, 100, 52);
            }else {
                getMenu().setParts(false);
//                menu.getTile().getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
//                    menu.moveSlot(menu.getSlot(0),0, 999999, 999999);
//                    menu.moveSlot(menu.getSlot(1),1, 999999, 999999);
//                    menu.moveSlot(menu.getSlot(2),2, 999999, 999999);
//                    menu.moveSlot(menu.getSlot(3),3, 999999, 999999);
//                });

            }
            LOGGER.info("test");
            LOGGER.info(getMenu().getParts());
        });
        addRenderableWidget(button);
    }

    @Override
    public void onClose() {
        getMenu().setParts(false);
        super.onClose();

    }

    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        font.draw(pPoseStack, "Heat Generator", 10, 10, 0x404040);
        font.draw(pPoseStack, "Inventory", 12, 85, 0x404040);
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
