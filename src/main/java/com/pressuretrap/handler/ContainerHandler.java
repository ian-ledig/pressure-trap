package com.pressuretrap.handler;

import com.pressuretrap.Main;
import com.pressuretrap.block.container.PressureTableContainer;
import com.pressuretrap.block.tileentity.PressureTableTileEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerHandler {

    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Main.MOD_ID);

    public static final RegistryObject<ContainerType<PressureTableContainer>> PRESSURE_TABLE_CONTAINER =
            CONTAINERS.register("pressure_table", () -> IForgeContainerType.create((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                TileEntity tileEntity = inv.player.world.getTileEntity(pos);
                if (tileEntity instanceof PressureTableTileEntity) {
                    return new PressureTableContainer(windowId, inv, (IInventory) tileEntity);
                }
                return null;
            }));

    public static void register(IEventBus eventBus) {
        CONTAINERS.register(eventBus);
    }
}
