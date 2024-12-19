package com.pressuretrap.block;

import com.pressuretrap.block.tileentity.PressureTableTileEntity;
import com.pressuretrap.handler.TileEntityHandler;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class PressureTableBlock extends Block {

    public PressureTableBlock() {
        super(AbstractBlock.Properties.create(Material.WOOD).hardnessAndResistance(2.5F).notSolid());
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (!world.isRemote) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof PressureTableTileEntity) {
                NetworkHooks.openGui((ServerPlayerEntity) player, (PressureTableTileEntity) tileEntity, pos);
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TileEntityHandler.PRESSURE_TABLE_TILE_ENTITY.get().create();
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBlockHarvested(world, pos, state, player);

        if (!world.isRemote) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof PressureTableTileEntity) {
                PressureTableTileEntity pressureTableTileEntity = (PressureTableTileEntity) tileEntity;

                for (int i = 0; i < pressureTableTileEntity.getSizeInventory(); ++i) {
                    ItemStack stack = pressureTableTileEntity.getStackInSlot(i);
                    if (!stack.isEmpty()) {
                        ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack);
                        world.addEntity(itemEntity);
                        pressureTableTileEntity.setInventorySlotContents(i, ItemStack.EMPTY);
                    }
                }
            }
        }
    }
}