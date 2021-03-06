package renderEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.TexturedModel;
import normalMappingRenderer.NormalMappingRenderer;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import shaders.StaticShader;
import shaders.TerrainShader;
import shadows.ShadowMapMasterRenderer;
import skybox.SkyboxRenderer;
import terrains.Terrain;
import toolbox.Clock;
import toolbox.Settings;
import entities.Camera;
import entities.Entity;
import entities.Light;

public class MasterRenderer {
	
	private static final float FAR_PLANE = Settings.getFAR_PLANE();
	private static final float FOV = Settings.getFOV();
	private static final float NEAR_PLANE = Settings.getNEAR_PLANE();
	private static final float FOG_DENSITY = Settings.getFOG_DENSITY();
	private static final float FOG_GRADIENT = Settings.getFOG_GRADIENT();
	private static final float CEL_SHADING_LEVELS = Settings.getCEL_SHADING_LEVELS();
	private static final float SHADOW_DISTANCE = Settings.getSHADOW_DISTANCE();
	private static final float SHADOW_TRANSITION_DISTANCE = Settings.getSHADOW_TRANSITION_DISTANCE();
	private static final int SHADOW_MAP_SIZE = Settings.getSHADOW_MAP_SIZE();
	private static final int PCF_COUNT = Settings.getPCF_COUNT();
	
	private Matrix4f projectionMatrix;
	
	private StaticShader shader = new StaticShader();
	private EntityRenderer renderer;
	
	private TerrainRenderer terrainRenderer;
	private TerrainShader terrainShader = new TerrainShader();
	
	private NormalMappingRenderer normalMappingRenderer;
	
	
	private Map<TexturedModel,List<Entity>> entities = new HashMap<>();
	private Map<TexturedModel, List<Entity>> normalMapEntities = new HashMap<>();
	private List<Terrain> terrains = new ArrayList<>();
	
	private SkyboxRenderer skyboxRenderer;
	private ShadowMapMasterRenderer shadowMapRenderer;
	
	private Vector3f skyColor;
	
	public MasterRenderer(Loader loader, Camera camera) {
		enableCulling();
		createProjectionMatrix();
		renderer = new EntityRenderer(shader, projectionMatrix);
		terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
		skyboxRenderer = new SkyboxRenderer(loader, projectionMatrix);
		normalMappingRenderer = new NormalMappingRenderer(projectionMatrix);
		shadowMapRenderer = new ShadowMapMasterRenderer(camera);
	}
	
	public SkyboxRenderer getSkyboxRenderer() {
		return skyboxRenderer;
	}
	
	public static float NEAR_PLANE() {
		return NEAR_PLANE;
	}
	
	public static float FAR_PLANE() {
		return FAR_PLANE;
	}
	
	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}
	
	public static void enableCulling() {
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}
	
	public static void disableCulling() {
		GL11.glDisable(GL11.GL_CULL_FACE);
	}
	
	public void renderScene(List<Entity> entities, List<Entity> normalMapEntities, List<Terrain> terrains, 
			List<Light> lights, Camera camera, Vector4f clipPlane) {
		for (Terrain terrain : terrains) {
			processTerrain(terrain);
		}
		for (Entity entity : entities) {
			processEntity(entity);
		}
		for (Entity entity : normalMapEntities) {
			processNormalMapEntity(entity);
		}
		render(lights, camera, clipPlane);
	}
	
	public void render(List<Light> lights, Camera camera, Vector4f clipPlane) {
		skyColor = Clock.getSkyColor();
		prepare();
		shader.start();
		shader.loadClipPlane(clipPlane);
		shader.loadSkyColor(skyColor);
		shader.loadFog(FOG_DENSITY, FOG_GRADIENT);
		shader.loadLights(lights);
		shader.loadViewMatrix(camera);
		shader.loadCelShadingLevels(CEL_SHADING_LEVELS);
		shader.loadShadowDistance(SHADOW_DISTANCE);
		shader.loadTransitionDistance(SHADOW_TRANSITION_DISTANCE);
		shader.loadShadowMapSize((float) SHADOW_MAP_SIZE);
		shader.loadPcfCount(PCF_COUNT);
		renderer.render(entities, shadowMapRenderer.getToShadowMapSpaceMatrix());
		shader.stop();
		normalMappingRenderer.render(normalMapEntities, clipPlane, lights, camera, 
				shadowMapRenderer.getToShadowMapSpaceMatrix());
		terrainShader.start();
		terrainShader.loadClipPlane(clipPlane);
		terrainShader.loadSkyColor(skyColor);
		terrainShader.loadFog(FOG_DENSITY, FOG_GRADIENT);
		terrainShader.loadLights(lights);
		terrainShader.loadViewMatrix(camera);
		terrainShader.loadCelShadingLevels(CEL_SHADING_LEVELS);
		terrainShader.loadShadowDistance(SHADOW_DISTANCE);
		terrainShader.loadTransitionDistance(SHADOW_TRANSITION_DISTANCE);
		terrainShader.loadShadowMapSize((float) SHADOW_MAP_SIZE);
		terrainShader.loadPcfCount(PCF_COUNT);
		terrainRenderer.render(terrains, shadowMapRenderer.getToShadowMapSpaceMatrix());
		terrainShader.stop();
		skyboxRenderer.render(camera, skyColor);
		terrains.clear();
		entities.clear();
		normalMapEntities.clear();
	}
	
	public void processTerrain(Terrain terrain) {
		terrains.add(terrain);
	}
	
	public void processEntity(Entity entity) {
		TexturedModel entityModel = entity.getModel();
		List<Entity> batch = entities.get(entityModel);
		if (batch != null) {
			batch.add(entity);
		} else {
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(entityModel, newBatch);		
		}
	}
	
	public void processNormalMapEntity(Entity entity) {
		TexturedModel entityModel = entity.getModel();
		List<Entity> batch = normalMapEntities.get(entityModel);
		if (batch != null) {
			batch.add(entity);
		} else {
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			normalMapEntities.put(entityModel, newBatch);		
		}
	}
	
	public void renderShadowMap(List<Entity> allEntitiesList, Light sun) {
		for (Entity entity : allEntitiesList) {
			processEntity(entity);
		}
		shadowMapRenderer.render(entities, sun);
		entities.clear();
	}
	
	public int getShadowMapTexture() {
		return shadowMapRenderer.getShadowMap();
	}
	
	public void cleanUp() {
		shader.cleanUp();
		terrainShader.cleanUp();
		normalMappingRenderer.cleanUp();
		shadowMapRenderer.cleanUp();
	}
	
	private void prepare() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(skyColor.x, skyColor.y, skyColor.z, 1);
		
		// shadows
		GL13.glActiveTexture(GL13.GL_TEXTURE5);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, getShadowMapTexture());
		GL13.glActiveTexture(GL13.GL_TEXTURE6);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, getShadowMapTexture());
		GL13.glActiveTexture(GL13.GL_TEXTURE7);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, getShadowMapTexture());
	}
	
	private void createProjectionMatrix() {
		projectionMatrix = new Matrix4f();
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))));
		float x_scale = y_scale / aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;

		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
		projectionMatrix.m33 = 0;
	}
}
