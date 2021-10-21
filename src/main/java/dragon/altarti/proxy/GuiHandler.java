package dragon.altarti.proxy;

import dragon.altarti.artitable.ContainerArtificersEnchanter;
import dragon.altarti.artitable.GuiArtificersEnchanter;
import dragon.altarti.artitable.TileArtificersEnchanter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {
    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileArtificersEnchanter) {
            return new ContainerArtificersEnchanter(player.inventory, (TileArtificersEnchanter) te);
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileArtificersEnchanter) {
            TileArtificersEnchanter containerTileEntity = (TileArtificersEnchanter) te;
            return new GuiArtificersEnchanter(containerTileEntity, new ContainerArtificersEnchanter(player.inventory, containerTileEntity));
        }
        return null;
    }
}
