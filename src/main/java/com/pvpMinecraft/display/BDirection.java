package com.pvpMinecraft.display;

public class BDirection {
	public static final BDirection
	NONE  = new BDirection(0x00),
	SOUTH = new BDirection(0x01),
	WEST  = new BDirection(0x02),
	NORTH = new BDirection(0x04),
	EAST  = new BDirection(0x08),
	UP    = new BDirection(0x10),
	DOWN  = new BDirection(0x20),
	ALL   = new BDirection(0x3F);
	
	private final int flags;
	private BDirection(int flags) {
		this.flags = flags;
	}
	
	public boolean isSouthPosZ() { return (this.flags & SOUTH.flags) != 0; }
	public boolean isWestNegX() { return (this.flags & WEST.flags) != 0; }
	public boolean isNorthNegZ() { return (this.flags & NORTH.flags) != 0; }
	public boolean isEastPosX() { return (this.flags & EAST.flags) != 0; }
	public boolean isUpPosY() { return (this.flags & UP.flags) != 0; }
	public boolean isDownNegY() { return (this.flags & DOWN.flags) != 0; }
	
	public BDirection And(BDirection other) {
		return new BDirection(this.flags | other.flags);
	}

	public BDirection FromPitchYaw(double pitch, double yaw) {
		int flags = 0;
		if (yaw >= -60.0 && yaw <= 60.0) {
			flags |= SOUTH.flags;
		}
		if (yaw >= 30.0 && yaw <= 150.0) {
			flags |= EAST.flags;
		}
		if (yaw >= -150.0 && yaw <= -30.0) {
			flags |= WEST.flags;
		}
		if (yaw <= -120.0 || yaw >= 120.0) {
			flags |= NORTH.flags;
		}
		if (pitch <= -30) {
			flags |= UP.flags;
		}
		if (pitch >= 30) {
			flags |= DOWN.flags;			
		}
		return new BDirection(flags);
	}
}
