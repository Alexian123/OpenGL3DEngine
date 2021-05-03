package toolbox;

public class Settings {
	
	// general
	public static final int GAME_WIDTH = 1280;
	public static final int GAME_HEIGHT = 720;
	public static final int FPS_CAP = 60;
	public static final int MAX_LIGHTS = 4; // do not change!
	public static final float GRAVITY = -50f;
	public static final float TIME_FACTOR = 1000;
	public static final float CEL_SHADING_LEVELS = 3.0f;
	
	// player
	public static final float PLAYER_NORMAL_RUN_SPEED = 30;
	public static final float PLAYER_SPRINT_RUN_SPEED = PLAYER_NORMAL_RUN_SPEED * 5;
	public static final float PLAYER_TURN_SPEED = 160;
	public static final float PLAYER_JUMP_POWER = 15;
	
	// camera
	public static final float FAR_PLANE = 1000;
	public static final float FOV = 70;
	public static final float NEAR_PLANE = 0.1f;
	public static final float DEFAULT_CAMERA_Y_OFFSET = 5;
	
	// sky box
	public static final float SKYBOX_SIZE = 500f;
	public static final float SKYBOX_LOWER_LIMIT = 0.0f;
	public static final float SKYBOX_UPPER_LIMIT = 50.0f;
	public static final float SKYBOX_CEL_SHADING_LEVELS = 10.0f;
	public static final float SKYBOX_ROTATION_SPEED = 0.3f;
	
	// terrain
	public static final float TERRAIN_TILE_SIZE = 400f;
	public static final float TERRAIN_MAX_HEIGHT = 40;
	public static final float TERRAIN_MAX_PIXEL_COLOR = 256 * 256 * 256;
	public static final float TERRAIN_AMPLITUDE = 50f;
	public static final int TERRAIN_OCTAVES = 4;
	public static final float TERRAIN_ROUGHNESS = 0.3f;
	
	// lamps
	public static final float LAMP_OFFSET_MUL_FACTOR = 15;
	public static final float LAMP_ATTENUATION_X = 1;
	public static final float LAMP_ATTENUATION_Y = 0.01f;
	public static final float LAMP_ATTENUATION_Z = 0.002f;
	
	// fog
	public static final float FOG_DENSITY = 0.0025f;
	public static final float FOG_GRADIENT = 3.0f;
	
	// sun
	public static final float SUN_MORNING_Y = 500;
	public static final float SUN_EVENING_Y = SUN_MORNING_Y;
	public static final float SUN_DAYTIME_Y = 1000;
	public static final float SUN_NIGHTTIME_Y = -SUN_DAYTIME_Y;
	public static final float SUN_MORNING_X = -100;
	public static final float SUN_MORNING_COLOR_R = 0.6f;
	public static final float SUN_MORNING_COLOR_G = 0.6f;
	public static final float SUN_MORNING_COLOR_B = 0.6f;
	public static final float SUN_DAYTIME_COLOR_R = 0.9f;
	public static final float SUN_DAYTIME_COLOR_G = 0.9f;
	public static final float SUN_DAYTIME_COLOR_B = 0.9f;
	public static final float SUN_EVENING_COLOR_R = 0.4f;
	public static final float SUN_EVENING_COLOR_G = 0.4f;
	public static final float SUN_EVENING_COLOR_B = 0.4f;
	public static final float SUN_NIGHTTIME_COLOR_R = 0.1f;
	public static final float SUN_NIGHTTIME_COLOR_G = 0.1f;
	public static final float SUN_NIGHTTIME_COLOR_B = 0.1f;
	
	// water
	public static final float WATER_TILE_SIZE = TERRAIN_TILE_SIZE / 2f;
	public static final int WATER_REFLECTION_WIDTH = 640;
	public static final int WATER_REFLECTION_HEIGHT = 360;
	public static final int WATER_REFRACTION_WIDTH = 1280;
	public static final int WATER_REFRACTION_HEIGHT = 720;
	public static final float WATER_WAVE_SPEED = 0.03f;
	public static final float WATER_WAVE_STRENGTH = 0.04f;
	public static final float WATER_SHINE_DAMPER = 20.0f;
	public static final float WATER_REFLECTIVITY = 0.5f;
	public static final float WATER_TILING = 6.0f;
	
	// shadows
	public static final float SHADOW_DISTANCE = 150;
	public static final float SHADOW_TRANSITION_DISTANCE = 10;
	public static final int SHADOW_MAP_SIZE = 4096;
	public static final int PCF_COUNT = 2;
	
	// post processing effects
	public static final float CONTRAST = 0.1f;
	
	// mouse picker
	public static final int MOUSE_PICKER_RECURSION_COUNT = 200;
	public static final float MOUSE_PICKER_RAY_RANGE = 600;
}
