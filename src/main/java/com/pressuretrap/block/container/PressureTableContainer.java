package com.pressuretrap.block.container;

import com.pressuretrap.handler.ContainerHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class PressureTableContainer extends Container {
    private final IInventory inventory;

    public PressureTableContainer(int id, PlayerInventory playerInventory, IInventory inventory) {
        super(ContainerHandler.PRESSURE_TABLE_CONTAINER.get(), id);
        this.inventory = inventory;

        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 3; ++col) {
                this.addSlot(new Slot(inventory, col + row * 3, 30 + col * 18, 17 + row * 18));
            }
        }

        this.addSlot(new Slot(inventory, 9, 124, 35));

        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerEntity) {
        return this.inventory.isUsableByPlayer(playerEntity);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int index) {
        ItemStack originalStack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack stackInSlot = slot.getStack();
            originalStack = stackInSlot.copy();

            int containerSlotCount = 10;
            int playerInventoryStart = containerSlotCount;
            int playerInventoryEnd = playerInventoryStart + 27;
            int hotbarStart = playerInventoryEnd;
            int hotbarEnd = hotbarStart + 9;

            if (index < containerSlotCount) {
                if (!this.mergeItemStack(stackInSlot, playerInventoryStart, hotbarEnd, true)) {
                    return ItemStack.EMPTY;
                }
            }
            else {
                if (!this.mergeItemStack(stackInSlot, 0, containerSlotCount, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stackInSlot.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }

        return originalStack;
    }
}