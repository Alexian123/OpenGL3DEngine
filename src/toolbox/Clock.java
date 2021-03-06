package toolbox;

import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;

public class Clock {
	
	private static final float TIME_FACTOR = Settings.getTIME_FACTOR();
	
	private static float time = 10000;
	
	public static final float MAX_TIME = 24 * TIME_FACTOR;
	public static final float TWELVE_AM = 0;
	public static final float SIX_AM = 6 * TIME_FACTOR;
	public static final float EIGHT_AM = 8 * TIME_FACTOR;
	public static final float TWELVE_PM = 12 * TIME_FACTOR;
	public static final float SIX_PM = 18 * TIME_FACTOR;
	
	private static Vector3f skyColor = new Vector3f(0.5f, 0.5f, 0.5f);
	
	private static float timeSpeed = 1000;
	
	public static enum TimeOfDay {
		NIGHT,
		MORNING,
		DAY,
		EVENING;
	}
	
	public static void setExactTime(float time) {
		Clock.time = time;
	}
	
	public static void setTimeOfDay(TimeOfDay timeOfDay) {
		switch (timeOfDay) {
		case NIGHT:
			time = TWELVE_AM;
			break;
		case MORNING:
			time = SIX_AM;
			break;
		case DAY:
			time = TWELVE_PM;
			break;
		case EVENING:
			time = SIX_PM;
			break;
		}
	}
	
	public static void setTimeSpeed(float speed) {
		timeSpeed = speed;
	}
	
	public static Vector3f getSkyColor() {
		return skyColor;
	}
	
	public static void tick() {
		time += DisplayManager.getFrameTimeSeconds() * timeSpeed;
		time %= MAX_TIME;
		calculateSkyColor();
	}

	public static TimeOfDay getTimeOfDay() {	
		if (time >= TWELVE_AM && time < SIX_AM) {
			return TimeOfDay.NIGHT;
		} else if (time >= SIX_AM && time < EIGHT_AM) {
			return TimeOfDay.MORNING;
		} else if (time >= EIGHT_AM && time < SIX_PM) {
			return TimeOfDay.DAY;
		} else {
			return TimeOfDay.EVENING;
		}
	}
	
	public static float getExactTime() {
		return time;
	}
	
	public static boolean isDayTime() {
		return (time >= SIX_AM && time < SIX_PM);
	}
	
	public static boolean isAMTime() {
		return (time >= TWELVE_AM && time < TWELVE_PM);
	}
	
	private static void calculateSkyColor() {
		if (Clock.isAMTime()) {
			skyColor.x = time / MAX_TIME - 0.2f + 0.0444f;
			skyColor.y = time / MAX_TIME - 0.2f + 0.12f;
			skyColor.z = time / MAX_TIME - 0.2f + 0.19f;
		} else {
			skyColor.x = 0.9f - (time / MAX_TIME ) + 0.0444f;
			skyColor.y = 0.9f - (time / MAX_TIME) + 0.12f;
			skyColor.z = 0.9f - (time / MAX_TIME) + 0.19f;
		}
	}
}
