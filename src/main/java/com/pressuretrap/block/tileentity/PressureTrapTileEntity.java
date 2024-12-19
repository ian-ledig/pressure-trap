package com.pressuretrap.block.tileentity;

import com.pressuretrap.handler.TileEntityHandler;
import com.pressuretrap.trap.PressureTrapState;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;

public class PressureTrapTileEntity extends TileEntity {
    private PressureTrapState state = PressureTrapState.NONE;

    public PressureTrapTileEntity() {
        super(TileEntityHandler.PRESSURE_TRAP_TILE_ENTITY.get());
    }

    public PressureTrapState getState() {
        return state;
    }

    public void setState(PressureTrapState state) {
        this.state = state;
        markDirty();
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.putString("Effect", state.name());
        return compound;
    }

    @Override
    public void read(BlockState blockState, CompoundNBT compound) {
        super.read(blockState, compound);

        if (compound.contains("Effect")) {
            state = PressureTrapState.valueOf(compound.getString("Effect"));
        }
    }
}
