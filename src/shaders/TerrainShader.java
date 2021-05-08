package shaders;

import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import toolbox.Settings;
import toolbox.Maths;
import entities.Camera;
import entities.Light;

public class TerrainShader extends ShaderProgram {
	
	private static final String VERTEX_FILE = "/shaders/terrainVertexShader.glsl";
	private static final String FRAGMENT_FILE = "/shaders/terrainFragmentShader.glsl";
	
	public static final int MAX_LIGHTS = Settings.getMAX_LIGHTS();
	
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int[] location_lightPosition;
	private int[] location_lightColor;
	private int[] location_attenuation;
	private int location_shineDamper;
	private int location_reflectivity;
	private int location_skyColor;
	private int location_backgroundTexture;
	private int location_rTexture;
	private int location_gTexture;
	private int location_bTexture;
	private int location_blendMap;
	private int location_plane;
	private int location_useCelShading;
	private int location_density;
	private int location_gradient;
	private int location_levels;
	private int location_toShadowMapSpace;
	private int location_shadowMap;
	private int location_shadowDistance;
	private int location_transitionDistance;
	private int location_shadowMapSize;
	private int location_pcfCount;
	private int location_numberOfActiveLights;

	public TerrainShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoordinates");
		super.bindAttribute(2, "normal");
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_shineDamper = super.getUniformLocation("shineDamper");
		location_reflectivity = super.getUniformLocation("reflectivity");
		location_skyColor = super.getUniformLocation("skyColor");
		location_backgroundTexture = super.getUniformLocation("backgroundTexture");
		location_rTexture = super.getUniformLocation("rTexture");
		location_gTexture = super.getUniformLocation("gTexture");
		location_bTexture = super.getUniformLocation("bTexture");
		location_blendMap = super.getUniformLocation("blendMap");
		location_plane = super.getUniformLocation("plane");
		location_useCelShading = super.getUniformLocation("useCelShading");
		location_density = super.getUniformLocation("density");
		location_gradient = super.getUniformLocation("gradient");
		location_levels = super.getUniformLocation("levels");
		location_toShadowMapSpace = super.getUniformLocation("toShadowMapSpace");
		location_shadowMap = super.getUniformLocation("shadowMap");
		location_shadowDistance = super.getUniformLocation("shadowDistance");
		location_transitionDistance = super.getUniformLocation("transitionDistance");
		location_shadowMapSize = super.getUniformLocation("shadowMapSize");
		location_pcfCount = super.getUniformLocation("pcfCount");
		location_numberOfActiveLights = super.getUniformLocation("numberOfActiveLights");
		
		location_lightPosition = new int[MAX_LIGHTS];
		location_lightColor = new int[MAX_LIGHTS];
		location_attenuation = new int[MAX_LIGHTS];
		for (int i = 0; i < MAX_LIGHTS; ++i) {
			location_lightPosition[i] = super.getUniformLocation("lightPosition[" + i + "]");
			location_lightColor[i] = super.getUniformLocation("lightColor[" + i + "]");
			location_attenuation[i] = super.getUniformLocation("attenuation[" + i + "]");
		}
	}
	
	public void loadNumberOfActiveLights(float number) {
		super.loadFloat(location_numberOfActiveLights, number);
	}
	
	public void loadPcfCount(int pcfCount) {
		super.loadInt(location_pcfCount, pcfCount);
	}
	
	public void loadShadowMapSize(float size) {
		super.loadFloat(location_shadowMapSize, size);
	}
	
	public void loadTransitionDistance(float distance) {
		super.loadFloat(location_transitionDistance, distance);
	}
	
	public void loadShadowDistance(float shadowDistance) {
		super.loadFloat(location_shadowDistance, shadowDistance);
	}
	
	public void loadToShadowSpaceMatrix(Matrix4f toShadowSpace) {
		super.loadMatrix(location_toShadowMapSpace, toShadowSpace);
	}
	
	public void loadCelShadingLevels(float levels) {
		super.loadFloat(location_levels, levels);
	}

	public void loadFog(float density, float gradient) {
		super.loadFloat(location_density, density);
		super.loadFloat(location_gradient, gradient);
	}
	
	public void loadCelShadingVariable(boolean useCelShading) {
		super.loadBooleanAsInt(location_useCelShading, useCelShading);
	}
	
	public void loadClipPlane(Vector4f plane) {
		super.loadVector(location_plane, plane);
	}
	
	public void connectTextureUnits() {
		super.loadInt(location_backgroundTexture, 0);
		super.loadInt(location_rTexture, 1);
		super.loadInt(location_gTexture, 2);
		super.loadInt(location_bTexture, 3);
		super.loadInt(location_blendMap, 4);
		super.loadInt(location_shadowMap, 5);
	}
	
	public void loadSkyColor(Vector3f skyColor) {
		super.loadVector(location_skyColor, skyColor);
	}
	
	public void loadShineVariables(float damper, float reflectivity) {
		super.loadFloat(location_shineDamper, damper);
		super.loadFloat(location_reflectivity, reflectivity);
	}
	
	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
	}
	
	public void loadLights(List<Light> lights) {
		for (int i = 0; i < MAX_LIGHTS; ++i) {
			if (i < lights.size()) {
				super.loadVector(location_lightPosition[i], lights.get(i).getPosition());
				super.loadVector(location_lightColor[i], lights.get(i).getColor());
				super.loadVector(location_attenuation[i], lights.get(i).getAttenuation());
			} else {
				super.loadVector(location_lightPosition[i], new Vector3f(0, 0, 0));
				super.loadVector(location_lightColor[i], new Vector3f(0, 0, 0));
				super.loadVector(location_attenuation[i], new Vector3f(1, 0, 0));
			}
		}
	}
	
	public void loadViewMatrix(Camera camera) {
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}
	
	public void loadProjectionMatrix(Matrix4f projection){
		super.loadMatrix(location_projectionMatrix, projection);
	}

}
