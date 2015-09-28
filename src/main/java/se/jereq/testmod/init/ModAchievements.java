package se.jereq.testmod.init;

import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;

public class ModAchievements {

	public static final Achievement test = new Achievement("achievement.testmod.test", "testmod.test", -1, 2, ModBlocks.testBlock, AchievementList.openInventory).registerStat();

	public static void init() {}
}
