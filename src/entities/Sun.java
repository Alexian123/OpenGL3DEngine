package entities;

import org.lwjgl.util.vector.Vector3f;

import terrains.Terrain;
import terrains.TerrainGrid;
import toolbox.Clock;
import toolbox.Settings;

public class Sun extends Light {
	
	private static final Vector3f MORNING_COLOR = new Vector3f(Settings.SUN_MORNING_COLOR_R, 
															   Settings.SUN_MORNING_COLOR_G, 
															   Settings.SUN_MORNING_COLOR_B);
	
	private static final Vector3f DAYTIME_COLOR = new Vector3f(Settings.SUN_DAYTIME_COLOR_R, 
															   Settings.SUN_DAYTIME_COLOR_G, 
															   Settings.SUN_DAYTIME_COLOR_B);
	
	private static final Vector3f EVENING_COLOR = new Vector3f(Settings.SUN_EVENING_COLOR_R, 
															   Settings.SUN_EVENING_COLOR_G, 
															   Settings.SUN_EVENING_COLOR_B);
	
	private static final Vector3f NIGHTTIME_COLOR = new Vector3f(Settings.SUN_NIGHTTIME_COLOR_R, 
																 Settings.SUN_NIGHTTIME_COLOR_G, 
																 Settings.SUN_NIGHTTIME_COLOR_B);
	
	private final Vector3f morningPos;
	private final Vector3f daytimePos;
	private final Vector3f eveningPos;
	private final Vector3f nighttimePos;
	
	public Sun(TerrainGrid grid) {
		super(new Vector3f(0, 0, 0), new Vector3f(0.7f, 0.7f, 0.7f));
		final float centerXZ = grid.getSize() * Terrain.SIZE() / 2;
		final float eveningX = grid.getSize() * Terrain.SIZE() + 100;
		morningPos = new Vector3f(Settings.SUN_MORNING_X, Settings.SUN_MORNING_Y, centerXZ);
		daytimePos = new Vector3f(centerXZ, Settings.SUN_DAYTIME_Y, centerXZ);
		eveningPos = new Vector3f(eveningX, Settings.SUN_EVENING_Y, centerXZ);
		nighttimePos = new Vector3f(centerXZ, Settings.SUN_NIGHTTIME_Y, centerXZ);
	}
	
	public void move() {
		switch (Clock.getTimeOfDay()) {
		case MORNING:
			super.setPosition(morningPos);
			super.setColor(MORNING_COLOR);
			break;
		case DAY:
			super.setPosition(daytimePos);
			super.setColor(DAYTIME_COLOR);
			break;
		case EVENING:
			super.setPosition(eveningPos);
			super.setColor(EVENING_COLOR);
			break;
		case NIGHT:
		default:
			super.setPosition(nighttimePos);
			super.setColor(NIGHTTIME_COLOR);
			break;
		}
	}

}
