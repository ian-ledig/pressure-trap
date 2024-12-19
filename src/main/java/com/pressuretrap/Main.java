package com.pressuretrap;

import com.pressuretrap.handler.BlockHandler;
import com.pressuretrap.handler.ItemHandler;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

@Mod(Main.MOD_ID)
public class Main
{
    public static final String MOD_ID = "pressuretrap";
    private static final Logger LOGGER = LogManager.getLogger();


    public Main() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ItemHandler.register(eventBus);
        BlockHandler.register(eventBus);
    }
}
