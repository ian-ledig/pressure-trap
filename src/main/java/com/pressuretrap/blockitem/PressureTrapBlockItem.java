package com.pressuretrap.blockitem;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Potion;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class PressureTrapBlockItem extends BlockItem {
    public PressureTrapBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);

        CompoundNBT nbt = stack.getTag();
        if (nbt != null && nbt.contains("Effect")) {
            String state = Potion.getPotionTypeForName(nbt.getString("Effect")).getNamePrefixed("");
            tooltip.add(new StringTextComponent("Effect: " + state).mergeStyle(TextFormatting.GREEN));
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
        super.inventoryTick(stack, world, entity, itemSlot, isSelected);

        if (stack.getTag() == null) {
            stack.setTag(new CompoundNBT());
        }

        if (!stack.getTag().contains("Effect")) {
            stack.getTag().putString("Effect", "Empty");
        }
    }
}
