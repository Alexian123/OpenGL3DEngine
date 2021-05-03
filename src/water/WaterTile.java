package water;

import toolbox.Settings;

public class WaterTile {
	
	public static final float TILE_SIZE = Settings.getWATER_TILE_SIZE();
	
	private float height;
	private float x,z;
	
	public WaterTile(float centerX, float centerZ, float height){
		this.x = centerX;
		this.z = centerZ;
		this.height = height;
	}

	public float getHeight() {
		return height;
	}

	public float getX() {
		return x;
	}

	public float getZ() {
		return z;
	}
}
