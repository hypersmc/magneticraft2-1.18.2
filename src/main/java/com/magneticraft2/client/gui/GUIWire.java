package com.magneticraft2.client.gui;

import com.magneticraft2.common.item.wire.itemWireCoil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class GUIWire extends Gui {
    public GUIWire(Minecraft mc)
    {
        super(mc);
        Player player = mc.player;
        ItemStack stack = player.getItemInHand(player.getUsedItemHand());
        if (stack.getItem() instanceof itemWireCoil)
        {
            String text = ((itemWireCoil) stack.getItem()).getDistanceText(player);
            if (!text.equals(""))
            {
                int width = mc.getWindow().getWidth();
                int height = mc.getWindow().getHeight();

                drawCenteredString(new PoseStack(), mc.font, text, width / 2, (height) - 50, Integer.parseInt("FFAA00", 16));
            }
        }
    }
}
