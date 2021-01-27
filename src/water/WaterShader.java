package water;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import shaders.ShaderProgram;
import toolbox.Maths;
import entities.Camera;
import entities.Light;

public class WaterShader extends ShaderProgram {

	private final static String VERTEX_FILE = "/water/waterVertex.glsl";
	private final static String FRAGMENT_FILE = "/water/waterFragment.glsl";

	private int location_modelMatrix;
	private int location_viewMatrix;
	private int location_projectionMatrix;
	private int location_reflectionTexture;
	private int location_refractionTexture;
	private int location_dudvMap;
	private int location_moveFactor;
	private int location_cameraPosition;
	private int location_normalMap;
	private int location_lightColor;
	private int location_lightPosition;
	private int location_depthMap;
	private int location_nearPlane;
	private int location_farPlane;
	private int location_skyColor;
	private int location_density;
	private int location_gradient;
	private int location_waveStrength;
	private int location_shineDamper;
	private int location_reflectivity;
	private int location_tiling;

	public WaterShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "position");
	}

	@Override
	protected void getAllUniformLocations() {
		location_projectionMatrix = getUniformLocation("projectionMatrix");
		location_viewMatrix = getUniformLocation("viewMatrix");
		location_modelMatrix = getUniformLocation("modelMatrix");
		location_reflectionTexture = getUniformLocation("reflectionTexture");
		location_refractionTexture = getUniformLocation("refractionTexture");
		location_dudvMap = getUniformLocation("dudvMap");
		location_moveFactor = getUniformLocation("moveFactor");
		location_cameraPosition = getUniformLocation("cameraPosition");
		location_normalMap = getUniformLocation("normalMap");
		location_lightColor = getUniformLocation("lightColor");
		location_lightPosition = getUniformLocation("lightPosition");
		location_depthMap = getUniformLocation("depthMap");
		location_nearPlane = getUniformLocation("nearPlane");
		location_farPlane = getUniformLocation("farPlane");
		location_skyColor = getUniformLocation("skyColor");
		location_density = getUniformLocation("density");
		location_gradient = getUniformLocation("gradient");
		location_waveStrength = getUniformLocation("waveStrength");
		location_shineDamper = getUniformLocation("shineDamper");
		location_reflectivity = getUniformLocation("reflectivity");
		location_tiling = getUniformLocation("tiling");
	}
	
	public void loadTilingCount(float tiling) {
		super.loadFloat(location_tiling, tiling);
	}
	
	public void loadWaveStrength(float strength) {
		super.loadFloat(location_waveStrength, strength);
	}
	
	public void loadShineVariables(float damper, float reflectivity) {
		super.loadFloat(location_shineDamper, damper);
		super.loadFloat(location_reflectivity, reflectivity);
	}
	
	public void loadFog(float density, float gradient) {
		super.loadFloat(location_density, density);
		super.loadFloat(location_gradient, gradient);
	}

	public void loadSkyColor(Vector3f skyColor) {
		super.loadVector(location_skyColor, skyColor);
	}
	
	public void loadFarAndNearPlanes(float near, float far) {
		super.loadFloat(location_nearPlane, near);
		super.loadFloat(location_farPlane, far);
	}
	
	public void loadLight(Light sun) {
		super.loadVector(location_lightColor, sun.getColor());
		super.loadVector(location_lightPosition, sun.getPosition());
	}
	
	public void loadMoveFactor(float moveFactor) {
		super.loadFloat(location_moveFactor, moveFactor);
	}
	
	public void connectTextureUnits() {
		super.loadInt(location_reflectionTexture, 0);
		super.loadInt(location_refractionTexture, 1);
		super.loadInt(location_dudvMap, 2);
		super.loadInt(location_normalMap, 3);
		super.loadInt(location_depthMap, 4);
	}

	public void loadProjectionMatrix(Matrix4f projection) {
		loadMatrix(location_projectionMatrix, projection);
	}
	
	public void loadViewMatrix(Camera camera){
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		loadMatrix(location_viewMatrix, viewMatrix);
		super.loadVector(location_cameraPosition, camera.getPosition());
	}

	public void loadModelMatrix(Matrix4f modelMatrix){
		loadMatrix(location_modelMatrix, modelMatrix);
	}

}
