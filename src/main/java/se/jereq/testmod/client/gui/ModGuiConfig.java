package se.jereq.testmod.client.gui;

import cpw.mods.fml.client.config.GuiConfig;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import se.jereq.testmod.handler.ConfigurationHandler;
import se.jereq.testmod.reference.Reference;

public class ModGuiConfig extends GuiConfig {
	@SuppressWarnings("unchecked")
	public ModGuiConfig(GuiScreen parentScreen) {
		super(parentScreen,
				new ConfigElement(ConfigurationHandler.configuration.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
				Reference.MOD_ID,
				false,
				false,
				ModGuiConfig.getAbridgedConfigPath(ConfigurationHandler.configuration.toString()));
	}
}
