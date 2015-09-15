package se.jereq.testmod.block;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import se.jereq.testmod.CreativeTab;

public class BlockTest extends BlockBase {

	public BlockTest() {
		super(Material.ice, "testBlock");
		setCreativeTab(CreativeTab.TEST_TAB);
		setLightLevel(1.f);
		setHardness(0.3f);
	}
}
