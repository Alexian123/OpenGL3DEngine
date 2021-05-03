package entities;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import toolbox.Settings;

public class Lamp extends Entity {
	
	private static final Vector3f ATTENUATION = new Vector3f(Settings.LAMP_ATTENUATION_X, 
															 Settings.LAMP_ATTENUATION_Y, 
															 Settings.LAMP_ATTENUATION_Z);
	
	private float lightYOffset;
	
	private Light light;
	private Vector3f lightColor = new Vector3f(1, 1, 0);
	
	public Lamp(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
		lightYOffset = scale * Settings.LAMP_OFFSET_MUL_FACTOR;
		light = new Light(new Vector3f(position.x, position.y + lightYOffset, position.z), lightColor, ATTENUATION);
		model.getTexture().setUseFakeLighting(true);
	}
	
	public Lamp(TexturedModel model, int textureIndex, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, textureIndex, position, rotX, rotY, rotZ, scale);
		lightYOffset = scale * Settings.LAMP_OFFSET_MUL_FACTOR;
		light = new Light(new Vector3f(position.x, position.y + lightYOffset, position.z), lightColor, ATTENUATION);
		model.getTexture().setUseFakeLighting(true);
	}
	
	public Light getLight() {
		return light;
	}
	
	public void setPosition(Vector3f position) {
		super.setPosition(position);
		light.setPosition(new Vector3f(position.x, position.y + lightYOffset, position.z));
	}

}
