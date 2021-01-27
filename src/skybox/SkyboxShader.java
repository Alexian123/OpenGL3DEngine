package skybox;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import renderEngine.DisplayManager;
import shaders.ShaderProgram;
import toolbox.Maths;

public class SkyboxShader extends ShaderProgram{

	private static final String VERTEX_FILE = "/skybox/skyboxVertexShader.glsl";
	private static final String FRAGMENT_FILE = "/skybox/skyboxFragmentShader.glsl";
	
	private static final float ROTATION_SPEED = 0.3f; // original 1f
	
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_fogColor;
	private int location_cubeMap;
	private int location_cubeMap2;
	private int location_blendFactor;
	private int location_useCelShading;
	private int location_lowerLimit;
	private int location_upperLimit;
	private int location_levels;
	
	private float rotation = 0;
	
	public SkyboxShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	public void loadCelShadingVariable(boolean useCelShading) {
		super.loadBooleanAsInt(location_useCelShading, useCelShading);
	}
	
	public void loadProjectionMatrix(Matrix4f matrix){
		super.loadMatrix(location_projectionMatrix, matrix);
	}

	public void loadViewMatrix(Camera camera){
		Matrix4f matrix = Maths.createViewMatrix(camera);
		matrix.m30 = 0;
		matrix.m31 = 0;
		matrix.m32 = 0;
		rotation +=  ROTATION_SPEED * DisplayManager.getFrameTimeSeconds();
		Matrix4f.rotate((float) Math.toRadians(rotation), new Vector3f(0, 1, 0), matrix, matrix);
		super.loadMatrix(location_viewMatrix, matrix);
	}
	
	public void loadFogColor(Vector3f fogColor) {
		super.loadVector(location_fogColor, fogColor); 
	}
	
	public void loadBlendFactor(float blend) {
		super.loadFloat(location_blendFactor, blend);
	}
	
	public void connectTextureUnits() {
		super.loadInt(location_cubeMap, 0);
		super.loadInt(location_cubeMap2, 1);
	}
	
	public void loadLimits(float lower, float upper) {
		super.loadFloat(location_upperLimit, upper);
		super.loadFloat(location_lowerLimit, lower);
	}
	
	public void loadCelShadingLevels(float levels) {
		super.loadFloat(location_levels, levels);
	}
	
	@Override
	protected void getAllUniformLocations() {
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_fogColor = super.getUniformLocation("fogColor");
		location_cubeMap = super.getUniformLocation("cubeMap");
		location_cubeMap2 = super.getUniformLocation("cubeMap2");
		location_blendFactor = super.getUniformLocation("blendFactor");
		location_useCelShading = super.getUniformLocation("useCelShading");
		location_lowerLimit = super.getUniformLocation("lowerLimit");
		location_upperLimit = super.getUniformLocation("upperLimit");
		location_levels = super.getUniformLocation("levels");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

}
