package dragon.altarti;

import dragon.altarti.artitable.ArtificersEnchanter;
import dragon.altarti.items.Topaz;
import dragon.altarti.topaz_block.BlockTopaz;
import dragon.altarti.topaz_ore.OreTopaz;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {
    @GameRegistry.ObjectHolder("altarti:artitable")
    public static ArtificersEnchanter artiEnchanter;

    @GameRegistry.ObjectHolder("altarti:topaz_block")
    public static BlockTopaz blockTopaz;

    @GameRegistry.ObjectHolder("altarti:topaz_ore")
    public static OreTopaz oreTopaz;

    @GameRegistry.ObjectHolder("altarti:topaz")
    public static Topaz topaz;

    @SideOnly(Side.CLIENT)
    public static void initModels() {artiEnchanter.initModel(); blockTopaz.initModel(); oreTopaz.initModel(); topaz.initModel();}
}
