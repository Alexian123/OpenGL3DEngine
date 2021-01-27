package toolbox;

public class Constants {
	
	public static final float GRAVITY = -50f;
	public static final float TERRAIN_TILE_SIZE = 400f;
	public static final float WATER_TILE_SIZE = TERRAIN_TILE_SIZE / 2f;
	public static final float FOG_DENSITY = 0.0025f;
	public static final float FOG_GRADIENT = 3.0f;
	public static final float FOV = 70;
	public static final float NEAR_PLANE = 0.1f;
	public static final float SHADOW_DISTANCE = 150;
	public static final float SHADOW_TRANSITION_DISTANCE = 10;
	public static final int PCF_COUNT = 2;
	public static final int SHADOW_MAP_SIZE = 4096;
}
