package se.jereq.testmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import se.jereq.testmod.reference.Reference;

public class BlockBase extends Block {

	protected String name;

	protected BlockBase(Material materialIn, String name) {
		super(materialIn);
		this.name = name;
		setUnlocalizedName(Reference.MOD_ID + "_" + name);
	}

	public String getName() {
		return name;
	}
}
