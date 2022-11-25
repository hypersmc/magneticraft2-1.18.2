package com.magneticraft2.common.systems;

import com.magneticraft2.client.gui.GUIWire;
import com.magneticraft2.common.magneticraft2;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = magneticraft2.MOD_ID)
public class EventHandler {


    @SubscribeEvent
    public static void onRenderGui(RenderGameOverlayEvent.Post event)
    {

        if (event.getType() != RenderGameOverlayEvent.ElementType.TEXT) return;
        new GUIWire(Minecraft.getInstance());
    }

}
