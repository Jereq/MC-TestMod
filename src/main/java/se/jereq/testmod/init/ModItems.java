package se.jereq.testmod.init;

import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import se.jereq.testmod.CreativeTab;
import se.jereq.testmod.item.ItemBase;
import se.jereq.testmod.item.ItemBlasterRifle;
import se.jereq.testmod.item.ItemTest;
import se.jereq.testmod.reference.Reference;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModItems {

	public static final ItemBase testItem = new ItemTest();
	public static final ItemBase blasterRifle = new ItemBlasterRifle();
	public static final ItemBase blasterAmmo = (ItemBase) new ItemBase("blasterAmmo").setCreativeTab(CreativeTab.TEST_TAB);

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
	public static void registerRenders(RenderItem renderItem) {
		ItemModelMesher mesher = renderItem.getItemModelMesher();

		for (ItemBase i : Hidden.items) {
			registerItem(mesher, i, 0);
		}
	}

	@SideOnly(Side.CLIENT)
	private static void registerItem(ItemModelMesher mesher, ItemBase item, int meta) {
		mesher.register(item, meta, new ModelResourceLocation(Reference.MOD_ID + ":" + item.getName(), "inventory"));
	}

	private static void register(ItemBase item) {
		GameRegistry.registerItem(item, item.getName());
	}
}
