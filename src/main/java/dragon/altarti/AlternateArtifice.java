package dragon.altarti;

import dragon.altarti.proxy.CommonProxy;
import dragon.altarti.util.Reference;
import dragon.altarti.worldgen.OreGenerator;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.Logger;

//Registers the mod.
@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, useMetadata = true)
public class AlternateArtifice {
    //Lets the program reference the mod.
    @Mod.Instance
    public static AlternateArtifice instance;

    //Creates Sided Proxy (Lets client and server side be known to each other.)
    @SidedProxy(clientSide = Reference.CLIENT, serverSide = Reference.SERVER)
    public static CommonProxy proxy;

    //Creates the logger.
    public static Logger logger;

    //Happens before mod loads.
    @EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    //Happens during mod loading sequence.
    @EventHandler
    public static void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    //Happens after mod loading sequence.
    @EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
