package com.pressuretrap.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.material.Material;

public class PressureTrapBlock extends PressurePlateBlock {

    public PressureTrapBlock() {
        super(Sensitivity.EVERYTHING, AbstractBlock.Properties.create(Material.IRON).hardnessAndResistance(2.5F).notSolid());
    }
}
