package com.pressuretrap.blockitem;

import com.pressuretrap.block.tileentity.PressureTrapTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

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
            String effectName = nbt.getString("Effect");

            if (effectName.equals("Fire")) {
                tooltip.add(new StringTextComponent("Effect: Fire").mergeStyle(TextFormatting.GREEN));
            }
            else if (!effectName.equals("Empty")){
                Effect effect = ForgeRegistries.POTIONS.getValue(new ResourceLocation(effectName));

                if (effect != null) {
                    String localizedName = effect.getDisplayName().getString();
                    tooltip.add(new StringTextComponent("Effect: " + localizedName).mergeStyle(TextFormatting.GREEN));
                }
            } else {
                tooltip.add(new StringTextComponent("Effect: Empty").mergeStyle(TextFormatting.RED));
            }
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

    @Override
    protected boolean placeBlock(BlockItemUseContext context, BlockState state) {
        boolean placed = super.placeBlock(context, state);

        if (placed) {
            World world = context.getWorld();
            BlockPos pos = context.getPos();
            ItemStack stack = context.getItem();

            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof PressureTrapTileEntity) {
                PressureTrapTileEntity pressureTrapTileEntity = (PressureTrapTileEntity) tileEntity;

                if (stack.hasTag() && stack.getTag().contains("Effect")) {
                    String effect = stack.getTag().getString("Effect");
                    pressureTrapTileEntity.setEffect(effect);
                }
            }
        }

        return placed;
    }
}
