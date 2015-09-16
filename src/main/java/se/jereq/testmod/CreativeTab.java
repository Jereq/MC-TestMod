package se.jereq.testmod;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import se.jereq.testmod.init.ModBlocks;
import se.jereq.testmod.reference.Reference;

public class CreativeTab {

	public static final CreativeTabs TEST_TAB = new CreativeTabs(Reference.MOD_ID) {
		@Override
		public Item getTabIconItem() {
			return Item.getItemFromBlock(ModBlocks.testBlock);
		}
	};
}
