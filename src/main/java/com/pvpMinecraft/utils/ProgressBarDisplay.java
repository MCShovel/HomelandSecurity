package com.pvpMinecraft.utils;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class ProgressBarDisplay {
	final Player player;
	final BossBar bbar;
	
	public ProgressBarDisplay(Player player, String Title) {
		this.player = player;
		bbar = player.getServer().createBossBar(Title, BarColor.BLUE, BarStyle.SEGMENTED_20, BarFlag.DARKEN_SKY);
		bbar.setProgress(0);
		bbar.setVisible(true);
		bbar.addPlayer(player);
	}

	public void Progress(double percent) {
		bbar.setProgress(Math.min(1.0, Math.max(0.0, percent)));
	}
	
	public void Hide() {
		bbar.setVisible(false);
		bbar.removePlayer(player);
	}
}
