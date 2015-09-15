package se.jereq.testmod.init;

import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import se.jereq.testmod.block.BlockBase;
import se.jereq.testmod.block.BlockTest;
import se.jereq.testmod.reference.Reference;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocks {

	public static final BlockBase testBlock = new BlockTest();

	private static final class Hidden {
		private static final BlockBase[] blocks = new BlockBase[]{
				testBlock,
		};
	}

	public static void registerBlocks() {
		for (BlockBase b : Hidden.blocks) {
			register(b);
		}
	}

	@SideOnly(Side.CLIENT)
	public static void registerRenders(RenderItem renderItem) {
		ItemModelMesher mesher = renderItem.getItemModelMesher();

		for (BlockBase b : Hidden.blocks) {
			registerBlock(mesher, b, 0);
		}
	}

	private static void registerBlock(ItemModelMesher mesher, BlockBase block, int meta) {
		mesher.register(Item.getItemFromBlock(block), meta, new ModelResourceLocation(Reference.MOD_ID + ":" + ((BlockTest) block).getName(), "inventory"));
	}

	@SideOnly(Side.CLIENT)
	private static void register(BlockBase block) {
		GameRegistry.registerBlock(block, block.getName());
	}
}
