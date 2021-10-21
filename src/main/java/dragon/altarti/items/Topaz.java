package dragon.altarti.items;

import dragon.altarti.util.Reference;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Topaz extends Item {

    public static final ResourceLocation TOPAZ = new ResourceLocation(Reference.MODID, "topaz");

    public Topaz() {
        setCreativeTab(CreativeTabs.MISC);
        setUnlocalizedName("topaz");
        setRegistryName(TOPAZ);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {

        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(this.getRegistryName(), "inventory"));
    }
}
