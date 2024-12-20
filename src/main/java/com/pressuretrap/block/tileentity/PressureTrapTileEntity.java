package com.pressuretrap.block.tileentity;

import com.pressuretrap.handler.TileEntityHandler;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;

public class PressureTrapTileEntity extends TileEntity {
    private String effect = "Empty";

    public PressureTrapTileEntity() {
        super(TileEntityHandler.PRESSURE_TRAP_TILE_ENTITY.get());
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
        markDirty();
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.putString("Effect", effect);
        return compound;
    }

    @Override
    public void read(BlockState blockState, CompoundNBT compound) {
        super.read(blockState, compound);

        if (compound.contains("Effect")) {
            effect = compound.getString("Effect");
        }
    }
}
