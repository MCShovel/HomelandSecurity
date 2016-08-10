package com.pvpMinecraft.display;

import org.bukkit.Location;

public class BRect {
	public final BPoint minPt, maxPt;
	
	public BRect(int x, int y, int z) {
		minPt = maxPt = new BPoint(x, y, z);
	}
	
	public BRect(int x1, int y1, int z1, int x2, int y2, int z2) {
		minPt = new BPoint(
				Math.min(x1, x2),
				Math.min(y1, y2),
				Math.min(z1, z2)
				);
		maxPt = new BPoint(
				Math.max(x1, x2),
				Math.max(y1, y2),
				Math.max(z1, z2)
				);
	}

	public BRect(BPoint pos) {
		minPt = maxPt = pos;
	}
	
	public BRect(Location pos) {
		minPt = maxPt = new BPoint(pos);
	}
	
	public BRect(BPoint pos1, BPoint pos2) {
		minPt = new BPoint(
				Math.min(pos1.x, pos2.x),
				Math.min(pos1.y, pos2.y),
				Math.min(pos1.z, pos2.z)
				);
		maxPt = new BPoint(
				Math.max(pos1.x, pos2.x),
				Math.max(pos1.y, pos2.y),
				Math.max(pos1.z, pos2.z)
				);
	}
	
	public BRect(Location pos1, Location pos2) {
		minPt = new BPoint(
				Math.min(pos1.getBlockX(), pos2.getBlockX()),
				Math.min(pos1.getBlockY(), pos2.getBlockY()),
				Math.min(pos1.getBlockZ(), pos2.getBlockZ())
				);
		maxPt = new BPoint(
				Math.max(pos1.getBlockX(), pos2.getBlockX()),
				Math.max(pos1.getBlockY(), pos2.getBlockY()),
				Math.max(pos1.getBlockZ(), pos2.getBlockZ())
				);
	}

	public boolean equals(BRect r) {
		return minPt.equals(r.minPt) && maxPt.equals(r.maxPt);
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof BRect) {
			return this.equals((BRect)other);
		}
		return false;
	}
	
	@Override
	public String toString() {
		return String.format("%s -> %s", minPt.toString(), maxPt.toString());
	}

	public boolean isEmpty() {
		return minPt.equals(maxPt);
	}

	public int width() { return maxPt.x - minPt.x; }
	public int height() { return maxPt.y - minPt.y; }
	public int length() { return maxPt.z - minPt.z; }

	public int midX() { return minPt.x + (width() >> 1); }
	public int midY() { return minPt.y + (height() >> 1); }
	public int midZ() { return minPt.z + (length() >> 1); }
	
	public boolean Contains(BPoint pt) {
		return  pt.x >= minPt.x && pt.x <= maxPt.x &&
				pt.y >= minPt.y && pt.y <= maxPt.y &&
				pt.z >= minPt.z && pt.z <= maxPt.z
				;
	}
	
	public BRect Union(BPoint other) {
		return new BRect(
				Math.min(minPt.x, other.x),
				Math.min(minPt.y, other.y),
				Math.min(minPt.z, other.z),
				Math.max(maxPt.x, other.x),
				Math.max(maxPt.y, other.y),
				Math.max(maxPt.z, other.z)
			);
	}

	public BRect Union(BRect other) {
		return new BRect(
				Math.min(minPt.x, other.minPt.x),
				Math.min(minPt.y, other.minPt.y),
				Math.min(minPt.z, other.minPt.z),
				Math.max(maxPt.x, other.maxPt.x),
				Math.max(maxPt.y, other.maxPt.y),
				Math.max(maxPt.z, other.maxPt.z)
			);
	}

	public BRect Expand(int x, int y, int z) {
		return new BRect(
				Math.min(minPt.x - x, midX()),
				Math.min(minPt.y - y, midY()),
				Math.min(minPt.z - z, midZ()),
				Math.max(maxPt.x + x, midX()),
				Math.max(maxPt.y + y, midY()),
				Math.max(maxPt.z + z, midZ())
				);
	}
	
	public BRect Expand(int value) { return Expand(value, value, value); }
	
	public BRect Expand(int value, BDirection dir) { 
		return new BRect(
				dir.isWestNegX() ? (minPt.x - value) : minPt.x,
				dir.isDownNegY() ? (minPt.y - value) : minPt.y,
				dir.isNorthNegZ() ? (minPt.z - value) : minPt.z,
				dir.isEastPosX() ? (maxPt.x + value) : maxPt.x,
				dir.isUpPosY() ? (maxPt.y + value) : maxPt.y,
				dir.isSouthPosZ() ? (maxPt.z + value) : maxPt.z
				);
	}
	
	public BPoint NorthWestBottom() {
		return minPt;
	}
	
	public BPoint NorthWestTop() {
		return new BPoint(minPt.x, maxPt.y, minPt.z);
	}
	
	public BPoint NorthEastBottom() {
		return new BPoint(maxPt.x, minPt.y, minPt.z);
	}
	
	public BPoint NorthEastTop() {
		return new BPoint(maxPt.x, maxPt.y, minPt.z);
	}

	public BPoint SouthWestBottom() {
		return new BPoint(minPt.x, minPt.y, maxPt.z);
	}
	
	public BPoint SouthWestTop() {
		return new BPoint(minPt.x, maxPt.y, maxPt.z);
	}
	
	public BPoint SouthEastBottom() {
		return new BPoint(maxPt.x, minPt.y, maxPt.z);
	}
	
	public BPoint SouthEastTop() {
		return maxPt;
	}
	
}
