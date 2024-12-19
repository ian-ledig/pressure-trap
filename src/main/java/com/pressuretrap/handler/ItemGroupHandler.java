package com.pressuretrap.handler;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ItemGroupHandler {
    public static final ItemGroup PRESSURE_TRAP_TAB = new ItemGroup("pressuretraptab") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(BlockHandler.PRESSURE_TABLE.get());
        }
    };
}
