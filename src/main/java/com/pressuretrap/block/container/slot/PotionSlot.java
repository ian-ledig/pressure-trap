package com.pressuretrap.block.container.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class PotionSlot extends Slot {
    public PotionSlot(IInventory inventory, int index, int xPosition, int yPosition) {
        super(inventory, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        if (stack != null && stack.getItem() == Items.SPLASH_POTION || stack.getItem() == Items.FIRE_CHARGE) {
            return true;
        }
        return false;
    }

}
