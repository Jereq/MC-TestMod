package se.jereq.testmod.item;

import net.minecraft.item.Item;
import se.jereq.testmod.reference.Reference;

public class ItemBase extends Item {

	protected String name;

	public ItemBase(String name) {
		super();
		setUnlocalizedName(name);
	}

	public String getName() {
		return name;
	}

	@Override
	public ItemBase setUnlocalizedName(String unlocalizedName) {
		this.name = unlocalizedName;
		super.setUnlocalizedName(Reference.MOD_ID + "_" + unlocalizedName);
		return this;
	}
}
