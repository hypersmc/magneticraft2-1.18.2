package com.magneticraft2.client.gui.screen.button;

import com.magneticraft2.common.magneticraft2;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class GenericButton extends Button {
    private static final ResourceLocation BUTTONS = new ResourceLocation(magneticraft2.MOD_ID + ":textures/gui/buttons/buttons.png");
    private ButtonType type;

    public GenericButton(int x, int y, ButtonType type, Component pMessage, OnPress pOnPress, OnTooltip pOnTooltip) {
        super(x, y, type.width, type.height, pMessage, pOnPress, pOnTooltip);
        this.type = type;
    }
    public GenericButton(int x, int y, ButtonType type, OnPress pOnPress) {
        this(x, y, type, TextComponent.EMPTY, pOnPress, Button.NO_TOOLTIP);
        this.type = type;
    }

    @Override
    public void renderButton(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BUTTONS);
        int x = type.xOffset;
        int y = type.yOffset;

        if (isHoveredOrFocused()) {
            x = type.xOffsetHover;
            y = type.yOffsetHover;
        }

        this.blit(pPoseStack, this.x, this.y, x, y, type.width, type.height);
    }

    @Override
    public void playDownSound(SoundManager pHandler) {
        if (type.playSound) {
            pHandler.play(SimpleSoundInstance.forUI(type.event, 1.0F));
        }
    }

    public enum ButtonType {

        Generaic(54, 2, 72, 2, 16, 16, false, null);

        public final int xOffset;
        public final int yOffset;
        public final int xOffsetHover;
        public final int yOffsetHover;
        public final int width;
        public final int height;
        public final boolean playSound;
        public final SoundEvent event;

        ButtonType(int xOffset, int yOffset, int xOffsetHover, int yOffsetHover, int width, int height,
                   boolean playSound, SoundEvent event) {
            this.xOffset = xOffset;
            this.yOffset = yOffset;
            this.xOffsetHover = xOffsetHover;
            this.yOffsetHover = yOffsetHover;
            this.width = width;
            this.height = height;
            this.playSound = playSound;
            this.event = event;
        }

    }
}
