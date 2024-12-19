package com.pressuretrap.block.tileentity;

import com.pressuretrap.block.container.PressureTableContainer;
import com.pressuretrap.handler.TileEntityHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nullable;

public class PressureTableTileEntity extends TileEntity implements INamedContainerProvider, IInventory {
    private final NonNullList<ItemStack> items = NonNullList.withSize(10, ItemStack.EMPTY); // 9 slots + 1 output

    public PressureTableTileEntity() {
        super(TileEntityHandler.PRESSURE_TABLE_TILE_ENTITY.get());
    }

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent("Pressure Table");
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new PressureTableContainer(i, playerInventory, this);
    }

    @Override
    public int getSizeInventory() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        return items.stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return items.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(items, index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(items, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack itemStack) {
        items.set(index, itemStack);
        if (itemStack.getCount() > this.getInventoryStackLimit()) {
            itemStack.setCount(this.getInventoryStackLimit());
        }
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity playerEntity) {
        return this.world != null && this.world.getTileEntity(this.pos) == this && playerEntity.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
    }

    @Override
    public void clear() {
        items.clear();
    }

    @Override
    public void read(BlockState state, CompoundNBT compound) {
        super.read(state, compound);
        ItemStackHelper.loadAllItems(compound, this.items);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        ItemStackHelper.saveAllItems(compound, this.items);
        return compound;
    }
}
