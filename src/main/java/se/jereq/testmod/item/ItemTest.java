package se.jereq.testmod.item;

import se.jereq.testmod.CreativeTab;

public class ItemTest extends ItemBase {
	public ItemTest() {
		super("testItem");
		setTextureName("testmod:testItem");
		setCreativeTab(CreativeTab.TEST_TAB);
	}
}
