package com.pressuretrap.handler;

import com.pressuretrap.Main;
import com.pressuretrap.screen.PressureTableScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        ScreenManager.registerFactory(ContainerHandler.PRESSURE_TABLE_CONTAINER.get(), PressureTableScreen::new);
    }
}
