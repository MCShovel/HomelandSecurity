package com.pvpMinecraft.display;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

public final class BPoint {
	public final int x, y, z;
	
	public BPoint(int x, int y, int z) {
		this.x = x;
		this.y = Math.min(255, Math.max(0, y));
		this.z = z;
	}
	
	public BPoint(Location loc) {
		this.x = loc.getBlockX();
		this.y = loc.getBlockY();
		this.z = loc.getBlockZ();
	}
	
	public Location ToLocation(World world) {
		return new Location(world, x + 0.5, y + 0.5, z + 0.5);
	}

	public Block getBlock(World world) {
		return world.getBlockAt(x, y, z);
	}

	public boolean equals(BPoint p) {
		return p.x == x && p.y == y && p.z == z;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof BPoint) {
			return this.equals((BPoint)other);
		}
		return false;
	}
	
	@Override
	public String toString() {
		return String.format("(%d, %d, %d)", x, y, z);
	}

	public BPoint Offset(int offX, int offY, int offZ) {
		return new BPoint(x + offX, y + offY, z + offZ);
	}
}
