package se.jereq.testmod.init;

import cpw.mods.fml.common.registry.GameRegistry;
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

	private static void register(BlockBase block) {
		GameRegistry.registerBlock(block, block.getName());
	}
}
