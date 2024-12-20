package com.pressuretrap.block;

import com.pressuretrap.block.tileentity.PressureTrapTileEntity;
import com.pressuretrap.handler.TileEntityHandler;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

public class PressureTrapBlock extends PressurePlateBlock {

    public PressureTrapBlock() {
        super(Sensitivity.EVERYTHING, AbstractBlock.Properties.create(Material.IRON).hardnessAndResistance(2.5F).notSolid());
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TileEntityHandler.PRESSURE_TRAP_TILE_ENTITY.get().create();
    }

    @Override
    public void onEntityCollision(BlockState blockState, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(blockState, world, pos, entity);

        //entity.setFire(5);

        if (!world.isRemote() && entity instanceof LivingEntity) {
            PressureTrapTileEntity tileEntity = (PressureTrapTileEntity) world.getTileEntity(pos);

            if (tileEntity != null) {
                String effectName = tileEntity.getEffect();

                if (effectName != null && !effectName.equals("Empty")) {
                    Effect effect = ForgeRegistries.POTIONS.getValue(new ResourceLocation(effectName));
                    if (effect != null) {
                        EffectInstance effectInstance = new EffectInstance(effect, 200, 0);
                        ((LivingEntity) entity).addPotionEffect(effectInstance);
                    }
                }
            }
        }
    }
}
