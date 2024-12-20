package com.pressuretrap.block.tileentity;

import com.pressuretrap.handler.TileEntityHandler;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
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

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        this.read(state, tag);
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 1, this.getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.handleUpdateTag(this.getBlockState(), pkt.getNbtCompound());
    }
}
