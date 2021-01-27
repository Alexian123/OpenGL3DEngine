package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import renderEngine.DisplayManager;
import terrains.Terrain;
import toolbox.Constants;

public class Player extends Entity {
	
	private static final float NORMAL_RUN_SPEED = 30;
	private static final float SPRINT_RUN_SPEED = NORMAL_RUN_SPEED * 5;
	private static final float TURN_SPEED = 160;
	private static final float JUMP_POWER = 15;

	private static final float STD_TERRAIN_HEIGHT = 0;
	
	private float currentSpeed = 0;
	private float currentTurnSpeed = 0;
	private float upwardsSpeed = 0;
	
	private boolean isInAir = false;

	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
	}
	
	public void move(Terrain terrain) {
		checkInputs();
		super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		super.increasePosition(dx, 0, dz);
		upwardsSpeed += Constants.GRAVITY * DisplayManager.getFrameTimeSeconds();
		super.increasePosition(0, upwardsSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		float terrainHeight = (terrain != null) ? 
				terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z) : STD_TERRAIN_HEIGHT;
		if (super.getPosition().y < terrainHeight) {
			upwardsSpeed = 0;
			isInAir = false;
			super.getPosition().y = terrainHeight;
		}
	}

	private void jump() {
		if (!isInAir) {
			upwardsSpeed = JUMP_POWER;
			isInAir = true;
		}
	}
	
	private void checkInputs() {
		float runSpeed = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) ? SPRINT_RUN_SPEED : NORMAL_RUN_SPEED;
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			currentSpeed = runSpeed;
		} else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			currentSpeed = -runSpeed;
		} else {
			currentSpeed = 0;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			currentTurnSpeed = -TURN_SPEED;
		} else if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			currentTurnSpeed = TURN_SPEED;
		} else {
			currentTurnSpeed = 0;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			jump();
		}
	}

}
