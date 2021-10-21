package dragon.altarti.artitable;

import dragon.altarti.util.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiArtificersEnchanter extends GuiContainer {
    public static final int WIDTH = 176;
    public static final int HEIGHT = 166;

    private static final ResourceLocation background = new ResourceLocation(Reference.MODID, "textures/gui/artitable_gui.png");

    public GuiArtificersEnchanter(TileArtificersEnchanter tileEntity, ContainerArtificersEnchanter container) {
        super(container);

        xSize = WIDTH;
        ySize = HEIGHT;
    }

    //Adds darkened background (like most in-game guis).
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    //Adds item tooltips.
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }
}