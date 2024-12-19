package com.pressuretrap.handler;

import com.pressuretrap.Main;
import com.pressuretrap.block.tileentity.PressureTableTileEntity;
import com.pressuretrap.block.tileentity.PressureTrapTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityHandler {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Main.MOD_ID);

    public static final RegistryObject<TileEntityType<PressureTableTileEntity>> PRESSURE_TABLE_TILE_ENTITY =
            TILE_ENTITIES.register("pressure_table_tile_entity",
                    () -> TileEntityType.Builder.create(PressureTableTileEntity::new, BlockHandler.PRESSURE_TABLE.get()).build(null));

    public static final RegistryObject<TileEntityType<PressureTrapTileEntity>> PRESSURE_TRAP_TILE_ENTITY =
            TILE_ENTITIES.register("pressure_trap_tile_entity",
                    () -> TileEntityType.Builder.create(PressureTrapTileEntity::new, BlockHandler.PRESSURE_TRAP.get()).build(null));

    public static void register(IEventBus eventBus) {
        TILE_ENTITIES.register(eventBus);
    }
}
