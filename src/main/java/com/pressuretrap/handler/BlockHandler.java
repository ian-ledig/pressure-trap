package com.pressuretrap.handler;

import com.pressuretrap.Main;
import com.pressuretrap.block.PressureTableBlock;
import com.pressuretrap.block.PressureTrapBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class BlockHandler {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MOD_ID);

    public static final RegistryObject<Block> PRESSURE_TABLE = registerBlock("pressure_table", PressureTableBlock::new);
    public static final RegistryObject<Block> PRESSURE_TRAP = registerBlock("pressure_trap", PressureTrapBlock::new);

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ItemHandler.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().group(ItemGroupHandler.PRESSURE_TRAP_TAB)));
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }
}
