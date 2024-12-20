package com.pressuretrap.block;

import com.pressuretrap.block.tileentity.PressureTrapTileEntity;
import com.pressuretrap.handler.TileEntityHandler;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
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
        super(Sensitivity.EVERYTHING, Properties.create(Material.IRON).hardnessAndResistance(2.5F).notSolid());
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

        if (!world.isRemote) {
            return;
        }

        // Apply effect to entity
        if (entity instanceof LivingEntity) {
            PressureTrapTileEntity tileEntity = (PressureTrapTileEntity) world.getTileEntity(pos);

            if (tileEntity != null) {
                String effectName = tileEntity.getEffect();
                BasicParticleType particleTypes = ParticleTypes.CAMPFIRE_COSY_SMOKE;
                
                if (effectName != null && !effectName.equals("Empty")) {
                    if(effectName.equals("Fire")){
                        particleTypes = ParticleTypes.FLAME;
                        entity.setFire(5);
                    }
                    else {
                        Effect effect = ForgeRegistries.POTIONS.getValue(new ResourceLocation(effectName));
                        if (effect != null) {
                            particleTypes = ParticleTypes.EFFECT;
                            EffectInstance effectInstance = new EffectInstance(effect, 100, 0);
                            ((LivingEntity) entity).addPotionEffect(effectInstance);
                        }
                    }
                }

                // Draw particles
                for (int i = 0; i <= 2; i++) {
                    double offsetX = world.rand.nextDouble();
                    double offsetZ = world.rand.nextDouble();
                    world.addParticle(particleTypes,
                            pos.getX() + offsetX,
                            pos.getY() + i,
                            pos.getZ() + offsetZ,
                            0.0,
                            0.02,
                            0.0
                    );
                }
            }
        }
    }
}
