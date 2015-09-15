package se.jereq.testmod.item;

import net.minecraft.creativetab.CreativeTabs;
import se.jereq.testmod.CreativeTab;

public class ItemTest extends ItemBase {
	public ItemTest() {
		super("testItem");
		setCreativeTab(CreativeTab.TEST_TAB);
	}
}
