package se.jereq.testmod;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import se.jereq.testmod.client.handler.KeyInputEventHandler;
import se.jereq.testmod.entity.EntityBlasterBolt;
import se.jereq.testmod.handler.ConfigurationHandler;
import se.jereq.testmod.handler.CraftingHandler;
import se.jereq.testmod.init.ModAchievements;
import se.jereq.testmod.init.ModBlocks;
import se.jereq.testmod.init.ModItems;
import se.jereq.testmod.init.ModRecipes;
import se.jereq.testmod.proxy.IProxy;
import se.jereq.testmod.reference.Reference;
import se.jereq.testmod.util.LogHelper;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, guiFactory = Reference.GUI_FACTORY_CLASS)
public class TestMod {

	@Mod.Instance(Reference.MOD_ID)
	public static TestMod instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static IProxy proxy;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ConfigurationHandler.init(event.getSuggestedConfigurationFile());
		FMLCommonHandler.instance().bus().register(new ConfigurationHandler());
		FMLCommonHandler.instance().bus().register(new CraftingHandler());

		proxy.registerKeyBindings();

		ModBlocks.registerBlocks();
		ModItems.registerItems();
		ModAchievements.init();
		EntityRegistry.registerModEntity(EntityBlasterBolt.class, EntityBlasterBolt.name, 0, this, 160, 100, false);

		LogHelper.info("Pre Initialization Complete!");
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		FMLCommonHandler.instance().bus().register(new KeyInputEventHandler());

		proxy.registerRenders();

		ModRecipes.addRecipes();

		LogHelper.info("Initialization Complete!");
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		LogHelper.info("Post Initialization Complete!");
	}
}
