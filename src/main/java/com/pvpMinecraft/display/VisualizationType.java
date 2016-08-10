package com.pvpMinecraft.display;

import org.bukkit.Material;

public enum VisualizationType {
	Claim (Material.GLOWSTONE, Material.GOLD_BLOCK),
	SubClaim (Material.SEA_LANTERN, Material.IRON_BLOCK),
	Error (Material.GLOWING_REDSTONE_ORE, Material.REDSTONE_BLOCK);
	
	public final Material corner, accent;
	VisualizationType(Material corner, Material accent) {
		this.corner = corner;
		this.accent = accent;
	}
}
