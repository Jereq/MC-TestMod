package se.jereq.testmod.init;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderItem;
import se.jereq.testmod.CreativeTab;
import se.jereq.testmod.item.ItemBase;
import se.jereq.testmod.item.ItemBlasterRifle;
import se.jereq.testmod.item.ItemTest;
import se.jereq.testmod.reference.Reference;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModItems {

	public static final ItemBase testItem = new ItemTest();
	public static final ItemBase blasterRifle = new ItemBlasterRifle();
	public static final ItemBase blasterAmmo = (ItemBase) new ItemBase("blasterAmmo").setCreativeTab(CreativeTab.TEST_TAB).setTextureName("testmod:blasterAmmo");

	private static final class Hidden {
		private static final ItemBase[] items = new ItemBase[]{
				testItem,
				blasterRifle,
				blasterAmmo,
		};
	}

	public static void registerItems() {
		for (ItemBase i : Hidden.items) {
			register(i);
		}
	}

	@SideOnly(Side.CLIENT)
	public static void registerRenders() {
		for (ItemBase i : Hidden.items) {
			registerItem(i, 0);
		}
	}

	@SideOnly(Side.CLIENT)
	private static void registerItem(ItemBase item, int meta) {

	}

	private static void register(ItemBase item) {
		GameRegistry.registerItem(item, item.getName());
	}
}
