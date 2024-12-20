package com.pressuretrap.block.container;

import com.pressuretrap.block.container.slot.PotionSlot;
import com.pressuretrap.block.container.slot.PressureTrapSlot;
import com.pressuretrap.handler.BlockHandler;
import com.pressuretrap.handler.ContainerHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class PressureTableContainer extends Container {
    private final IInventory inventory;

    public PressureTableContainer(int id, PlayerInventory playerInventory, IInventory inventory) {
        super(ContainerHandler.PRESSURE_TABLE_CONTAINER.get(), id);
        this.inventory = inventory;

        this.addSlot(new PotionSlot(inventory, 0, 56, 17));
        this.addSlot(new PressureTrapSlot(inventory, 1, 102, 17));

        this.addSlot(new Slot(inventory, 2, 79, 51) {
            @Override
            public boolean isItemValid(ItemStack stack) {
                return false;
            }

            @Override
            public ItemStack onTake(PlayerEntity player, ItemStack stack) {
                super.onTake(player, stack);
                consumeIngredients();
                return stack;
            }
        });

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

            int containerSlotCount = 3;
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
                if (stackInSlot.getItem() == Item.getItemFromBlock(BlockHandler.PRESSURE_TRAP.get())) {
                    if (!this.mergeItemStack(stackInSlot, 1, 2, false)) { // Slot 1
                        return ItemStack.EMPTY;
                    }
                } else {
                    if (!this.mergeItemStack(stackInSlot, 0, containerSlotCount, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }

            slot.onSlotChanged();
            if (index == 2) {
                slot.onTake(player, stackInSlot);
            }

            if (stackInSlot.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }

        return originalStack;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        ItemStack potionStack = inventory.getStackInSlot(0);
        ItemStack trapStack = inventory.getStackInSlot(1);
        ItemStack outputStack = inventory.getStackInSlot(2);

        if (!potionStack.isEmpty() && !trapStack.isEmpty()) {
            ItemStack newStack = new ItemStack(BlockHandler.PRESSURE_TRAP.get());
            CompoundNBT nbt = new CompoundNBT();

            if (potionStack.hasTag() && potionStack.getTag().contains("Potion")) {
                nbt.putString("Effect", potionStack.getTag().getString("Potion"));
            }

            newStack.setTag(nbt);

            inventory.setInventorySlotContents(2, newStack);
        } else if (!outputStack.isEmpty()) {
            inventory.setInventorySlotContents(2, ItemStack.EMPTY);
        }
    }

    private void consumeIngredients() {
        ItemStack potionStack = inventory.getStackInSlot(0);
        ItemStack trapStack = inventory.getStackInSlot(1);

        if (!potionStack.isEmpty()) {
            potionStack.shrink(1);
        }
        if (!trapStack.isEmpty()) {
            trapStack.shrink(1);
        }
    }
}
