package com.pressuretrap.block.container.slot;

import com.pressuretrap.handler.BlockHandler;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PressureTrapSlot extends Slot {
    public PressureTrapSlot(IInventory inventory, int index, int xPosition, int yPosition) {
        super(inventory, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack.getItem() == Item.getItemFromBlock(BlockHandler.PRESSURE_TRAP.get());
    }
}
