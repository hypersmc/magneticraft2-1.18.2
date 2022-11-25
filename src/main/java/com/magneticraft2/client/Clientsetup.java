package com.magneticraft2.client;

import com.magneticraft2.client.render.blocks.WireRender;
import com.magneticraft2.common.magneticraft2;
import com.magneticraft2.common.registry.FinalRegistry;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(modid = magneticraft2.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Clientsetup {
    private static final Logger LOGGER = LogManager.getLogger("magneticraft2_clientsetup");
    public static void init(FMLClientSetupEvent e){
        ItemBlockRenderTypes.setRenderLayer(FinalRegistry.rice_plant.get(), RenderType.cutout());
    }

    @SubscribeEvent
    public static void onRegisterRenderer(EntityRenderersEvent.RegisterRenderers event){
        LOGGER.info("Renders are being registered!");
        event.registerBlockEntityRenderer(FinalRegistry.Tile_HVConnector_Base.get(), WireRender::new);
        //event.registerBlockEntityRenderer(FinalRegistry.Tile_HVConnector_Base.get(), WireRender::new);

    }

    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        LOGGER.info("Models are being registered!");

    }

}