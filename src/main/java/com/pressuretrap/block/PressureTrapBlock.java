package com.pressuretrap.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PressureTrapBlock extends PressurePlateBlock {

    public PressureTrapBlock() {
        super(Sensitivity.EVERYTHING, AbstractBlock.Properties.create(Material.IRON).hardnessAndResistance(2.5F).notSolid());
    }

    @Override
    public void onEntityCollision(BlockState blockState, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(blockState, world, pos, entity);

        entity.setFire(5);
    }
}
