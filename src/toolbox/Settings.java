package toolbox;

import org.json.simple.JSONObject;

public class Settings {
	
	private static final String SETTINGS_FILE = "settings.json";
	private static JSONObject mainJsonObj;
	
	// general
	private static int GAME_WIDTH;
	private static int GAME_HEIGHT;
	private static int FPS_CAP;
	private static int MAX_LIGHTS;
	private static float GRAVITY;
	private static float TIME_FACTOR;
	private static float CEL_SHADING_LEVELS;
	private static int ANTI_ALIASING;
	
	// player
	private static float PLAYER_WALK_SPEED;
	private static float PLAYER_SPRINT_SPEED;
	private static float PLAYER_TURN_SPEED;
	private static float PLAYER_JUMP_POWER;
	
	// camera
	private static float FAR_PLANE;
	private static float FOV;
	private static float NEAR_PLANE;
	private static float DEFAULT_CAMERA_Y_OFFSET;
	
	// skybox
	private static float SKYBOX_SIZE;
	private static float SKYBOX_LOWER_LIMIT;
	private static float SKYBOX_UPPER_LIMIT;
	private static float SKYBOX_CEL_SHADING_LEVELS;
	private static float SKYBOX_ROTATION_SPEED;
	
	// terrain
	private static float TERRAIN_TILE_SIZE;
	private static float TERRAIN_MAX_HEIGHT;
	private static float TERRAIN_MAX_PIXEL_COLOR;
	private static float TERRAIN_AMPLITUDE;
	private static int TERRAIN_OCTAVES;
	private static float TERRAIN_ROUGHNESS;
	
	// lamps
	private static float LAMP_OFFSET_MUL_FACTOR;
	private static float LAMP_ATTENUATION_X;
	private static float LAMP_ATTENUATION_Y;
	private static float LAMP_ATTENUATION_Z;
	
	// fog
	private static float FOG_DENSITY;
	private static float FOG_GRADIENT;
	
	// sun
	private static float SUN_MORNING_Y;
	private static float SUN_EVENING_Y;
	private static float SUN_DAYTIME_Y;
	private static float SUN_NIGHTTIME_Y;
	private static float SUN_MORNING_X;
	private static float SUN_MORNING_COLOR_R;
	private static float SUN_MORNING_COLOR_G;
	private static float SUN_MORNING_COLOR_B;
	private static float SUN_DAYTIME_COLOR_R;
	private static float SUN_DAYTIME_COLOR_G;
	private static float SUN_DAYTIME_COLOR_B;
	private static float SUN_EVENING_COLOR_R;
	private static float SUN_EVENING_COLOR_G;
	private static float SUN_EVENING_COLOR_B;
	private static float SUN_NIGHTTIME_COLOR_R;
	private static float SUN_NIGHTTIME_COLOR_G;
	private static float SUN_NIGHTTIME_COLOR_B;
	
	// water
	private static float WATER_TILE_SIZE;
	private static int WATER_REFLECTION_WIDTH;
	private static int WATER_REFLECTION_HEIGHT;
	private static int WATER_REFRACTION_WIDTH;
	private static int WATER_REFRACTION_HEIGHT;
	private static float WATER_WAVE_SPEED;
	private static float WATER_WAVE_STRENGTH;
	private static float WATER_SHINE_DAMPER;
	private static float WATER_REFLECTIVITY;
	private static float WATER_TILING;
	
	// shadows
	private static float SHADOW_DISTANCE;
	private static float SHADOW_TRANSITION_DISTANCE;
	private static int SHADOW_MAP_SIZE;
	private static int PCF_COUNT;
	
	// post processing effects
	private static float CONTRAST;
	private static int BLUR;
	
	// mouse picker
	private static int MOUSE_PICKER_RECURSION_COUNT;
	private static float MOUSE_PICKER_RAY_RANGE;
	
	public static void init() { 
		// must be called before creating the display
		try {
			mainJsonObj = JsonReader.read(SETTINGS_FILE);
			if (mainJsonObj == null) {
				throw new NullPointerException();
			}
			importSettingsFromJson();
		} catch (Exception e) {
			System.err.println("Error encountered while trying to import settings.\nUsing defaults instead.");
			e.printStackTrace();
			setDefaults();
		}
	}
	
	private static void importSettingsFromJson() {
		JSONObject section;
		Long longVal;
		Double doubleVal;
		
		// general
		section = (JSONObject) mainJsonObj.get("general");
		longVal = (Long) section.get("game width");
		GAME_WIDTH = longVal.intValue();
		longVal = (Long) section.get("game height");
		GAME_HEIGHT = longVal.intValue();
		longVal = (Long) section.get("fps cap");
		FPS_CAP = longVal.intValue();
		longVal = (Long) section.get("max lights");
		MAX_LIGHTS = longVal.intValue();
		doubleVal = (Double) section.get("gravity");
		GRAVITY = doubleVal.floatValue();
		doubleVal = (Double) section.get("time factor");
		TIME_FACTOR = doubleVal.floatValue();
		doubleVal = (Double) section.get("cel shading levels");
		CEL_SHADING_LEVELS = doubleVal.floatValue();
		longVal = (Long) section.get("anti-aliasing");
		ANTI_ALIASING = longVal.intValue();
		
		
		// player
		section = (JSONObject) mainJsonObj.get("player");
		doubleVal = (Double) section.get("walk speed");
		PLAYER_WALK_SPEED = doubleVal.floatValue();
		doubleVal = (Double) section.get("sprint speed");
		PLAYER_SPRINT_SPEED = doubleVal.floatValue();
		doubleVal = (Double) section.get("turn speed");
		PLAYER_TURN_SPEED = doubleVal.floatValue();
		doubleVal = (Double) section.get("jump power");
		PLAYER_JUMP_POWER = doubleVal.floatValue();
		
		
		// camera
		section = (JSONObject) mainJsonObj.get("camera");
		doubleVal = (Double) section.get("far plane");
		FAR_PLANE = doubleVal.floatValue();
		doubleVal = (Double) section.get("fov");
		FOV = doubleVal.floatValue();
		doubleVal = (Double) section.get("near plane");
		NEAR_PLANE = doubleVal.floatValue();
		doubleVal = (Double) section.get("default y offset");
		DEFAULT_CAMERA_Y_OFFSET = doubleVal.floatValue();
		System.out.println(FAR_PLANE + " " + FOV + " " + NEAR_PLANE + " " + DEFAULT_CAMERA_Y_OFFSET);
		
		
		// skybox
		section = (JSONObject) mainJsonObj.get("skybox");
		doubleVal = (Double) section.get("size");
		SKYBOX_SIZE = doubleVal.floatValue();
		doubleVal = (Double) section.get("lower limit");
		SKYBOX_LOWER_LIMIT = doubleVal.floatValue();
		doubleVal = (Double) section.get("upper limit");
		SKYBOX_UPPER_LIMIT = doubleVal.floatValue();
		doubleVal = (Double) section.get("cel shading levels");
		SKYBOX_CEL_SHADING_LEVELS = doubleVal.floatValue();
		doubleVal = (Double) section.get("rotation speeed");
		SKYBOX_ROTATION_SPEED = doubleVal.floatValue();
		
		
		// terrain
		section = (JSONObject) mainJsonObj.get("terrain");
		doubleVal = (Double) section.get("tile size");
		TERRAIN_TILE_SIZE = doubleVal.floatValue();
		doubleVal = (Double) section.get("max height");
		TERRAIN_MAX_HEIGHT = doubleVal.floatValue();
		doubleVal = (Double) section.get("max pixel color");
		TERRAIN_MAX_PIXEL_COLOR = doubleVal.floatValue();
		doubleVal = (Double) section.get("amplitude");
		TERRAIN_AMPLITUDE = doubleVal.floatValue();
		longVal = (Long) section.get("octaves");
		TERRAIN_OCTAVES = longVal.intValue();
		doubleVal = (Double) section.get("roughness");
		TERRAIN_ROUGHNESS = doubleVal.floatValue();
		
		
		// lamps
		section = (JSONObject) mainJsonObj.get("lamps");
		doubleVal = (Double) section.get("offset mul factor");
		LAMP_OFFSET_MUL_FACTOR = doubleVal.floatValue();
		
		section = (JSONObject) section.get("attenuation");
		doubleVal = (Double) section.get("x");
		LAMP_ATTENUATION_X = doubleVal.floatValue();
		doubleVal = (Double) section.get("y");
		LAMP_ATTENUATION_Y = doubleVal.floatValue();
		doubleVal = (Double) section.get("z");
		LAMP_ATTENUATION_Z = doubleVal.floatValue();
		
	
		// fog
		section = (JSONObject) mainJsonObj.get("fog");
		doubleVal = (Double) section.get("density");
		FOG_DENSITY = doubleVal.floatValue();
		doubleVal = (Double) section.get("gradient");
		FOG_GRADIENT = doubleVal.floatValue();
		
		
		// sun
		section = (JSONObject) mainJsonObj.get("sun");
		section = (JSONObject) section.get("y position");
		doubleVal = (Double) section.get("morning");
		SUN_MORNING_Y = doubleVal.floatValue();
		doubleVal = (Double) section.get("evening");
		SUN_EVENING_Y = doubleVal.floatValue();
		doubleVal = (Double) section.get("daytime");
		SUN_DAYTIME_Y = doubleVal.floatValue();
		doubleVal = (Double) section.get("nighttime");
		SUN_NIGHTTIME_Y = doubleVal.floatValue();
		
		section = (JSONObject) mainJsonObj.get("sun");
		doubleVal = (Double) section.get("morning x position");
		SUN_MORNING_X = doubleVal.floatValue();
		
		section = (JSONObject) section.get("color");
		section = (JSONObject) section.get("morning");
		doubleVal = (Double) section.get("r");
		SUN_MORNING_COLOR_R = doubleVal.floatValue();
		doubleVal = (Double) section.get("g");
		SUN_MORNING_COLOR_G = doubleVal.floatValue();
		doubleVal = (Double) section.get("b");
		SUN_MORNING_COLOR_B = doubleVal.floatValue();
		
		section = (JSONObject) mainJsonObj.get("sun");
		section = (JSONObject) section.get("color");
		section = (JSONObject) section.get("daytime");
		doubleVal = (Double) section.get("r");
		SUN_DAYTIME_COLOR_R = doubleVal.floatValue();
		doubleVal = (Double) section.get("g");
		SUN_DAYTIME_COLOR_G = doubleVal.floatValue();
		doubleVal = (Double) section.get("b");
		SUN_DAYTIME_COLOR_B = doubleVal.floatValue();
		
		section = (JSONObject) mainJsonObj.get("sun");
		section = (JSONObject) section.get("color");
		section = (JSONObject) section.get("evening");
		doubleVal = (Double) section.get("r");
		SUN_EVENING_COLOR_R = doubleVal.floatValue();
		doubleVal = (Double) section.get("g");
		SUN_EVENING_COLOR_G = doubleVal.floatValue();
		doubleVal = (Double) section.get("b");
		SUN_EVENING_COLOR_B = doubleVal.floatValue();
		
		section = (JSONObject) mainJsonObj.get("sun");
		section = (JSONObject) section.get("color");
		section = (JSONObject) section.get("nighttime");
		doubleVal = (Double) section.get("r");
		SUN_NIGHTTIME_COLOR_R = doubleVal.floatValue();
		doubleVal = (Double) section.get("g");
		SUN_NIGHTTIME_COLOR_G = doubleVal.floatValue();
		doubleVal = (Double) section.get("b");
		SUN_NIGHTTIME_COLOR_B = doubleVal.floatValue();
		
		
		// water
		section = (JSONObject) mainJsonObj.get("water");
		doubleVal = (Double) section.get("tile size");
		WATER_TILE_SIZE = doubleVal.floatValue();
		longVal = (Long) section.get("reflection width");
		WATER_REFLECTION_WIDTH = longVal.intValue();
		longVal = (Long) section.get("reflection height");
		WATER_REFLECTION_HEIGHT = longVal.intValue();
		longVal = (Long) section.get("refraction width");
		WATER_REFRACTION_WIDTH = longVal.intValue();
		longVal = (Long) section.get("refraction height");
		WATER_REFRACTION_HEIGHT = longVal.intValue();
		doubleVal = (Double) section.get("wave speed");
		WATER_WAVE_SPEED = doubleVal.floatValue();
		doubleVal = (Double) section.get("wave strength");
		WATER_WAVE_STRENGTH = doubleVal.floatValue();
		doubleVal = (Double) section.get("shine damper");
		WATER_SHINE_DAMPER = doubleVal.floatValue();
		doubleVal = (Double) section.get("reflectivity");
		WATER_REFLECTIVITY = doubleVal.floatValue();
		doubleVal = (Double) section.get("tiling");
		WATER_TILING = doubleVal.floatValue();
		
		
		// shadows
		section = (JSONObject) mainJsonObj.get("shadows");
		doubleVal = (Double) section.get("distance");
		SHADOW_DISTANCE = doubleVal.floatValue();
		doubleVal = (Double) section.get("transition distance");
		SHADOW_TRANSITION_DISTANCE = doubleVal.floatValue();
		longVal = (Long) section.get("map size");
		SHADOW_MAP_SIZE = longVal.intValue();
		longVal = (Long) section.get("pcf count");
		PCF_COUNT = longVal.intValue();
		
		
		// post processing effects
		section = (JSONObject) mainJsonObj.get("post processing effects");
		doubleVal = (Double) section.get("contrast");
		CONTRAST = doubleVal.floatValue();
		longVal = (Long) section.get("blur");
		BLUR = longVal.intValue();
		
		
		// mouse picker
		section = (JSONObject) mainJsonObj.get("mouse picker");
		longVal = (Long) section.get("recursion count");
		MOUSE_PICKER_RECURSION_COUNT = longVal.intValue();
		doubleVal = (Double) section.get("ray range");
		MOUSE_PICKER_RAY_RANGE = doubleVal.floatValue();
	}
	
	public static int getGAME_WIDTH() {
		return GAME_WIDTH;
	}

	public static int getGAME_HEIGHT() {
		return GAME_HEIGHT;
	}

	public static int getFPS_CAP() {
		return FPS_CAP;
	}

	public static int getMAX_LIGHTS() {
		return MAX_LIGHTS;
	}

	public static float getGRAVITY() {
		return GRAVITY;
	}

	public static float getTIME_FACTOR() {
		return TIME_FACTOR;
	}

	public static float getCEL_SHADING_LEVELS() {
		return CEL_SHADING_LEVELS;
	}
	
	public static int getANTI_ALIASING() {
		return ANTI_ALIASING;
	}

	public static float getPLAYER_WALK_SPEED() {
		return PLAYER_WALK_SPEED;
	}

	public static float getPLAYER_SPRINT_SPEED() {
		return PLAYER_SPRINT_SPEED;
	}

	public static float getPLAYER_TURN_SPEED() {
		return PLAYER_TURN_SPEED;
	}

	public static float getPLAYER_JUMP_POWER() {
		return PLAYER_JUMP_POWER;
	}

	public static float getFAR_PLANE() {
		return FAR_PLANE;
	}

	public static float getFOV() {
		return FOV;
	}

	public static float getNEAR_PLANE() {
		return NEAR_PLANE;
	}

	public static float getDEFAULT_CAMERA_Y_OFFSET() {
		return DEFAULT_CAMERA_Y_OFFSET;
	}

	public static float getSKYBOX_SIZE() {
		return SKYBOX_SIZE;
	}

	public static float getSKYBOX_LOWER_LIMIT() {
		return SKYBOX_LOWER_LIMIT;
	}

	public static float getSKYBOX_UPPER_LIMIT() {
		return SKYBOX_UPPER_LIMIT;
	}

	public static float getSKYBOX_CEL_SHADING_LEVELS() {
		return SKYBOX_CEL_SHADING_LEVELS;
	}

	public static float getSKYBOX_ROTATION_SPEED() {
		return SKYBOX_ROTATION_SPEED;
	}

	public static float getTERRAIN_TILE_SIZE() {
		return TERRAIN_TILE_SIZE;
	}

	public static float getTERRAIN_MAX_HEIGHT() {
		return TERRAIN_MAX_HEIGHT;
	}

	public static float getTERRAIN_MAX_PIXEL_COLOR() {
		return TERRAIN_MAX_PIXEL_COLOR;
	}

	public static float getTERRAIN_AMPLITUDE() {
		return TERRAIN_AMPLITUDE;
	}

	public static int getTERRAIN_OCTAVES() {
		return TERRAIN_OCTAVES;
	}

	public static float getTERRAIN_ROUGHNESS() {
		return TERRAIN_ROUGHNESS;
	}

	public static float getLAMP_OFFSET_MUL_FACTOR() {
		return LAMP_OFFSET_MUL_FACTOR;
	}

	public static float getLAMP_ATTENUATION_X() {
		return LAMP_ATTENUATION_X;
	}

	public static float getLAMP_ATTENUATION_Y() {
		return LAMP_ATTENUATION_Y;
	}

	public static float getLAMP_ATTENUATION_Z() {
		return LAMP_ATTENUATION_Z;
	}

	public static float getFOG_DENSITY() {
		return FOG_DENSITY;
	}

	public static float getFOG_GRADIENT() {
		return FOG_GRADIENT;
	}

	public static float getSUN_MORNING_Y() {
		return SUN_MORNING_Y;
	}

	public static float getSUN_EVENING_Y() {
		return SUN_EVENING_Y;
	}

	public static float getSUN_DAYTIME_Y() {
		return SUN_DAYTIME_Y;
	}

	public static float getSUN_NIGHTTIME_Y() {
		return SUN_NIGHTTIME_Y;
	}

	public static float getSUN_MORNING_X() {
		return SUN_MORNING_X;
	}

	public static float getSUN_MORNING_COLOR_R() {
		return SUN_MORNING_COLOR_R;
	}

	public static float getSUN_MORNING_COLOR_G() {
		return SUN_MORNING_COLOR_G;
	}

	public static float getSUN_MORNING_COLOR_B() {
		return SUN_MORNING_COLOR_B;
	}

	public static float getSUN_DAYTIME_COLOR_R() {
		return SUN_DAYTIME_COLOR_R;
	}

	public static float getSUN_DAYTIME_COLOR_G() {
		return SUN_DAYTIME_COLOR_G;
	}

	public static float getSUN_DAYTIME_COLOR_B() {
		return SUN_DAYTIME_COLOR_B;
	}

	public static float getSUN_EVENING_COLOR_R() {
		return SUN_EVENING_COLOR_R;
	}

	public static float getSUN_EVENING_COLOR_G() {
		return SUN_EVENING_COLOR_G;
	}

	public static float getSUN_EVENING_COLOR_B() {
		return SUN_EVENING_COLOR_B;
	}

	public static float getSUN_NIGHTTIME_COLOR_R() {
		return SUN_NIGHTTIME_COLOR_R;
	}

	public static float getSUN_NIGHTTIME_COLOR_G() {
		return SUN_NIGHTTIME_COLOR_G;
	}

	public static float getSUN_NIGHTTIME_COLOR_B() {
		return SUN_NIGHTTIME_COLOR_B;
	}

	public static float getWATER_TILE_SIZE() {
		return WATER_TILE_SIZE;
	}

	public static int getWATER_REFLECTION_WIDTH() {
		return WATER_REFLECTION_WIDTH;
	}

	public static int getWATER_REFLECTION_HEIGHT() {
		return WATER_REFLECTION_HEIGHT;
	}

	public static int getWATER_REFRACTION_WIDTH() {
		return WATER_REFRACTION_WIDTH;
	}

	public static int getWATER_REFRACTION_HEIGHT() {
		return WATER_REFRACTION_HEIGHT;
	}

	public static float getWATER_WAVE_SPEED() {
		return WATER_WAVE_SPEED;
	}

	public static float getWATER_WAVE_STRENGTH() {
		return WATER_WAVE_STRENGTH;
	}

	public static float getWATER_SHINE_DAMPER() {
		return WATER_SHINE_DAMPER;
	}

	public static float getWATER_REFLECTIVITY() {
		return WATER_REFLECTIVITY;
	}

	public static float getWATER_TILING() {
		return WATER_TILING;
	}

	public static float getSHADOW_DISTANCE() {
		return SHADOW_DISTANCE;
	}

	public static float getSHADOW_TRANSITION_DISTANCE() {
		return SHADOW_TRANSITION_DISTANCE;
	}

	public static int getSHADOW_MAP_SIZE() {
		return SHADOW_MAP_SIZE;
	}

	public static int getPCF_COUNT() {
		return PCF_COUNT;
	}

	public static float getCONTRAST() {
		return CONTRAST;
	}
	
	public static int getBLUR() {
		return BLUR;
	}

	public static int getMOUSE_PICKER_RECURSION_COUNT() {
		return MOUSE_PICKER_RECURSION_COUNT;
	}

	public static float getMOUSE_PICKER_RAY_RANGE() {
		return MOUSE_PICKER_RAY_RANGE;
	}

	private static void setDefaults() {
		// general
		GAME_WIDTH = 1280;
		GAME_HEIGHT = 720;
		FPS_CAP = 60;
		MAX_LIGHTS = 4; // can't be >= 21
		GRAVITY = -50f;
		TIME_FACTOR = 1000;
		CEL_SHADING_LEVELS = 3.0f;
		ANTI_ALIASING = 4;
		
		// player
		PLAYER_WALK_SPEED = 30;
		PLAYER_SPRINT_SPEED = PLAYER_WALK_SPEED * 5;
		PLAYER_TURN_SPEED = 160;
		PLAYER_JUMP_POWER = 15;
		
		// camera
		FAR_PLANE = 1000;
		FOV = 70;
		NEAR_PLANE = 0.1f;
		DEFAULT_CAMERA_Y_OFFSET = 5;
		
		// skybox
		SKYBOX_SIZE = 500f;
		SKYBOX_LOWER_LIMIT = 0.0f;
		SKYBOX_UPPER_LIMIT = 50.0f;
		SKYBOX_CEL_SHADING_LEVELS = 10.0f;
		SKYBOX_ROTATION_SPEED = 0.3f;
		
		// terrain
		TERRAIN_TILE_SIZE = 400f;
		TERRAIN_MAX_HEIGHT = 40;
		TERRAIN_MAX_PIXEL_COLOR = 256 * 256 * 256;
		TERRAIN_AMPLITUDE = 50f;
		TERRAIN_OCTAVES = 4;
		TERRAIN_ROUGHNESS = 0.3f;
		
		// lamps
		LAMP_OFFSET_MUL_FACTOR = 15;
		LAMP_ATTENUATION_X = 1;
		LAMP_ATTENUATION_Y = 0.01f;
		LAMP_ATTENUATION_Z = 0.002f;
		
		// fog
		FOG_DENSITY = 0.0025f;
		FOG_GRADIENT = 3.0f;
		
		// sun
		SUN_MORNING_Y = 500;
		SUN_EVENING_Y = SUN_MORNING_Y;
		SUN_DAYTIME_Y = 1000;
		SUN_NIGHTTIME_Y = -SUN_DAYTIME_Y;
		SUN_MORNING_X = -100;
		SUN_MORNING_COLOR_R = 0.6f;
		SUN_MORNING_COLOR_G = 0.6f;
		SUN_MORNING_COLOR_B = 0.6f;
		SUN_DAYTIME_COLOR_R = 0.9f;
		SUN_DAYTIME_COLOR_G = 0.9f;
		SUN_DAYTIME_COLOR_B = 0.9f;
		SUN_EVENING_COLOR_R = 0.4f;
		SUN_EVENING_COLOR_G = 0.4f;
		SUN_EVENING_COLOR_B = 0.4f;
		SUN_NIGHTTIME_COLOR_R = 0.1f;
		SUN_NIGHTTIME_COLOR_G = 0.1f;
		SUN_NIGHTTIME_COLOR_B = 0.1f;
		
		// water
		WATER_TILE_SIZE = TERRAIN_TILE_SIZE / 2f;
		WATER_REFLECTION_WIDTH = 640;
		WATER_REFLECTION_HEIGHT = 360;
		WATER_REFRACTION_WIDTH = 1280;
		WATER_REFRACTION_HEIGHT = 720;
		WATER_WAVE_SPEED = 0.03f;
		WATER_WAVE_STRENGTH = 0.04f;
		WATER_SHINE_DAMPER = 20.0f;
		WATER_REFLECTIVITY = 0.5f;
		WATER_TILING = 6.0f;
		
		// shadows
		SHADOW_DISTANCE = 150;
		SHADOW_TRANSITION_DISTANCE = 10;
		SHADOW_MAP_SIZE = 4096;
		PCF_COUNT = 2;
		
		// post processing effects
		CONTRAST = 0.1f;
		BLUR = 8; // can't be <= 0
		
		// mouse picker
		MOUSE_PICKER_RECURSION_COUNT = 200;
		MOUSE_PICKER_RAY_RANGE = 600;
	}
}
