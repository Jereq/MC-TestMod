package se.jereq.testmod.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;
import se.jereq.testmod.handler.ConfigurationHandler;
import se.jereq.testmod.reference.Reference;

public class ModGuiConfig extends GuiConfig {
	public ModGuiConfig(GuiScreen parentScreen) {
		super(parentScreen,
				new ConfigElement(ConfigurationHandler.configuration.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
				Reference.MOD_ID,
				false,
				false,
				ModGuiConfig.getAbridgedConfigPath(ConfigurationHandler.configuration.toString()));
	}
}
