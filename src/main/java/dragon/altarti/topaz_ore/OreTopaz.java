package dragon.altarti.topaz_ore;

import dragon.altarti.items.Topaz;
import dragon.altarti.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class OreTopaz extends Block {
    //Sets name of the mod for easy reference.
    public static final ResourceLocation TOPAZORE = new ResourceLocation(Reference.MODID, "topaz_ore");

    public OreTopaz() {
        //Provides info on how block will sound.
        super(Material.ROCK);

        //Creates registry for the block.
        setRegistryName(TOPAZORE);
        //Name the player sees.
        setUnlocalizedName("topaz_ore");
        //Tool and mining level needed to acquire block. (2 = Iron)
        setHarvestLevel("pickaxe", 2);
        //Creative tab the item will show up in.
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        //Sets explosion resistance to proper levels.
        setHardness(3);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        Item returnItem = Item.REGISTRY.getObject(Topaz.TOPAZ);
        return returnItem;
    }

    @Override
    public int quantityDroppedWithBonus(int fortune, Random rand) {
        return (rand.nextInt(fortune + 1) + 1);
    }

    @Override
    public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune)
    {
        return 7;
    }

    //Renders the block in the user inventory.
    @SideOnly(Side.CLIENT)
    public void initModel() {

        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(this.getRegistryName(), "inventory"));
    }
}