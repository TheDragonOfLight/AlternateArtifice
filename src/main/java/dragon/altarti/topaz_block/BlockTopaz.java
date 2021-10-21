package dragon.altarti.topaz_block;

import dragon.altarti.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTopaz extends Block {
    //Sets name of the mod for easy reference.
    public static final ResourceLocation TOPAZBLOCK = new ResourceLocation(Reference.MODID, "topaz_block");

    public BlockTopaz() {
        //Provides info on how block will sound.
        super(Material.ROCK);

        //Creates registry for the block.
        setRegistryName(TOPAZBLOCK);
        //Name the player sees.
        setUnlocalizedName("topaz_block");
        //Tool and mining level needed to acquire block. (2 = Iron)
        setHarvestLevel("pickaxe", 2);
        //Creative tab the item will show up in.
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        //Sets explosion resistance to proper levels.
        setHardness(5);
    }

    //Renders the block in the user inventory.
    @SideOnly(Side.CLIENT)
    public void initModel() {

        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(this.getRegistryName(), "inventory"));
    }
}
