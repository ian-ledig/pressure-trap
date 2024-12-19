package com.pressuretrap.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.pressuretrap.Main;
import com.pressuretrap.block.container.PressureTableContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class PressureTableScreen extends ContainerScreen<PressureTableContainer> {
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MOD_ID, "textures/gui/container/pressure_table.png");

    public PressureTableScreen(PressureTableContainer screenContainer, PlayerInventory inv, ITextComponent title) {
        super(screenContainer, inv, title);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
        this.font.drawString(matrixStack, this.title.getString(), 8.0F, 6.0F, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float v, int i, int i1) {
        Minecraft.getInstance().getTextureManager().bindTexture(GUI_TEXTURE);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.blit(matrixStack, x, y, 0, 0, this.xSize, this.ySize);
    }
}
