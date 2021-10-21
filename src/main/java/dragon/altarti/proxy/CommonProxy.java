package dragon.altarti.proxy;

import dragon.altarti.AlternateArtifice;
import dragon.altarti.EnchantmentInit;
import dragon.altarti.ModBlocks;
import dragon.altarti.artitable.ArtificersEnchanter;
import dragon.altarti.artitable.TileArtificersEnchanter;
import dragon.altarti.items.Topaz;
import dragon.altarti.topaz_block.BlockTopaz;
import dragon.altarti.topaz_ore.OreTopaz;
import dragon.altarti.util.Reference;
import dragon.altarti.worldgen.OreGenerator;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

//This is server side, what is taking place in world. This also handles player data.
@Mod.EventBusSubscriber
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {

    }

    public void init(FMLInitializationEvent event) {

        NetworkRegistry.INSTANCE.registerGuiHandler(AlternateArtifice.instance, new GuiHandler());
        GameRegistry.registerWorldGenerator(new OreGenerator(), 50000);
        MinecraftForge.EVENT_BUS.register(new OreGenerator());
    }

    public void postInit(FMLPostInitializationEvent event) {

        GameRegistry.addSmelting(ModBlocks.oreTopaz, new ItemStack(ModBlocks.topaz, 1), 7.0f);
        OreDictionary.registerOre("oreTopaz", ModBlocks.oreTopaz);
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
            event.getRegistry().registerAll(
                    new ArtificersEnchanter(),
                    new BlockTopaz(),
                    new OreTopaz()
            );
            GameRegistry.registerTileEntity(TileArtificersEnchanter.class, Reference.MODID + "_artitable");
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock(ModBlocks.artiEnchanter).setRegistryName(ArtificersEnchanter.ENCHANTERNAME));
        event.getRegistry().register(new ItemBlock(ModBlocks.blockTopaz).setRegistryName(BlockTopaz.TOPAZBLOCK));
        event.getRegistry().register(new ItemBlock(ModBlocks.oreTopaz).setRegistryName(OreTopaz.TOPAZORE));
        event.getRegistry().register(new Topaz());
    }

    @SubscribeEvent
    public static void registerEnchantments(RegistryEvent.Register<Enchantment> event) {
        event.getRegistry().registerAll(EnchantmentInit.ENCHANTMENTS.toArray(new Enchantment[0]));
    }
}