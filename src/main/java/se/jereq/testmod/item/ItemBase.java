package se.jereq.testmod.item;

import net.minecraft.item.Item;
import se.jereq.testmod.reference.Reference;

public class ItemBase extends Item {

	protected String name;

	public ItemBase(String name) {
		super();
		this.name = name;
		setUnlocalizedName(Reference.MOD_ID + "_" + name);
	}

	public String getName() {
		return name;
	}
}
