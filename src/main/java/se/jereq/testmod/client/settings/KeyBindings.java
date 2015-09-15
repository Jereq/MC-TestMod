package se.jereq.testmod.client.settings;

import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import se.jereq.testmod.reference.Names;

public class KeyBindings {

	public static KeyBinding ask = new KeyBinding(Names.Keys.ASK, Keyboard.KEY_Y, Names.Keys.CATEGORY);
}
