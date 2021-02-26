package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import models.TexturedModel;
import particles.Particle;
import particles.ParticleMaster;
import particles.ParticleSystem;
import particles.ParticleTexture;
import postProcessing.Fbo;
import postProcessing.PostProcessing;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import terrains.Terrain;
import terrains.TerrainGrid;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import toolbox.Clock;
import toolbox.MousePicker;
import water.WaterFrameBuffers;
import water.WaterRenderer;
import water.WaterShader;
import water.WaterTile;
import entities.Camera;
import entities.Entity;
import entities.Lamp;
import entities.Light;
import entities.Player;
import entities.Sun;
import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import fontRendering.TextMaster;
import guis.GuiRenderer;
import guis.GuiTexture;

public class MainGameLoop {

	public static void main(String[] args) {
		
		// ------------ prepare display and _renderers ------------
		
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		TextMaster.init(loader);
		
		
		TexturedModel playerModel = loader.loadTexturedModel("person", "entities/playerTexture");
		Player player = new Player(playerModel, new Vector3f(0,  0,  0), 0, 45, 0, 0.5f);
		Camera camera = new Camera(player);
		
		MasterRenderer renderer = new MasterRenderer(loader, camera);
		
		GuiRenderer guiRenderer = new GuiRenderer(loader);
		
		WaterShader waterShader = new WaterShader();
		WaterFrameBuffers waterFBOS = new WaterFrameBuffers();
		WaterRenderer waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjectionMatrix(), waterFBOS);
		
		ParticleMaster.init(loader, renderer.getProjectionMatrix());
		
		
		// ------------------ load fonts & text ---------------------
		
		FontType font = loader.loadFont("candara");
		GUIText text = new GUIText("Move with WASD, jump with Spacebar, hold Shift for super sprint, hold Right-click to move the camera and zoom with the scroll wheel.",
				1.2f, font, new Vector2f(0.01f, 0.01f), 1f, false);
		text.setColor(1f, 0, 0);
		
		
		// ------------ load models and textures ------------
		
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("terrain/lightGreenGrass"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("terrain/grassFlowers"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("terrain/mud"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("terrain/lightGreenGrass"));
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("maps/terrainBlend"));
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		
		// regular models
		TexturedModel pineTreeModel = loader.loadTexturedModel("pineTree", "entities/pineTree");
		pineTreeModel.getTexture().setReflectivity(0f);
		pineTreeModel.getTexture().setShineDamper(1f);
		pineTreeModel.getTexture().setHasTransparency(true);
		
		/*
		TexturedModel rocks = loader.loadTexturedModel("floatingRocks", "entities/floatingRocks");
		TexturedModel lamp = loader.loadTexturedModel("lamp", "entities/lamp");
		*/
		
		// normal mapping models
		
		TexturedModel barrel = loader.loadTexturedModelNM("barrel", "entities/barrel");
		TexturedModel crate = loader.loadTexturedModelNM("crate", "entities/crate");
		TexturedModel boulder = loader.loadTexturedModelNM("boulder", "entities/boulder");
		
		
		barrel.getTexture().setNormalMap(loader.loadTexture("maps/barrelNormal"));
		crate.getTexture().setNormalMap(loader.loadTexture("maps/crateNormal"));
		boulder.getTexture().setNormalMap(loader.loadTexture("maps/boulderNormal"));
		
		
		// particle textures
		ParticleTexture fire = new ParticleTexture(loader.loadTexture("particles/fire"), 8, true);
		/*
		ParticleTexture cosmic = new ParticleTexture(loader.loadTexture("particles/cosmic"), 4, false);
		ParticleTexture orange = new ParticleTexture(loader.loadTexture("particles/orange"), 4, true);
		ParticleTexture star = new ParticleTexture(loader.loadTexture("particles/star"), 1, false);
		ParticleTexture smoke = new ParticleTexture(loader.loadTexture("particles/smoke"), 8, false);
		*/
	
		
		// ------------ add terrain grid ------------
		
		List<Terrain> terrains = new ArrayList<>();
		Random random = new Random();
		int seed = random.nextInt(1_000_000_000);
		final int gridSize = 2;
		for (int i = 0; i < gridSize; ++i) {
			for (int j = 0; j < gridSize; ++j) {
				terrains.add(new Terrain(i, j, loader, texturePack, blendMap, seed));
			}
		}
		TerrainGrid terrainGrid = new TerrainGrid(terrains, gridSize);
		float a = terrains.get(0).getCenterX();
		float b = terrains.get(0).getCenterZ();
		player.setPosition(new Vector3f(a, terrains.get(0).getHeightOfTerrain(a, b), b));
		
		
		// ------------ add water ------------
		
		List<WaterTile> waters = new ArrayList<>();
		for (Terrain terrain : terrains) {
			waters.add(new WaterTile(terrain.getCenterX(), terrain.getCenterZ(), -5f));
		}
		
		
		// ------------ add entities ------------
		
		List<Entity> entities = new ArrayList<>();
		entities.add(player);
		float upperLimit = terrainGrid.getSize() * Terrain.SIZE();
		float x, y, z;
		for (int i = 0; i < 100; i++) {
			do {
				x = getRandomFloat(random, 0, upperLimit);
				z = getRandomFloat(random, 0, upperLimit);
			} while ((y = terrainGrid.getTerrainAt(x, z).getHeightOfTerrain(x, z)) < 0);
			entities.add(new Entity(pineTreeModel, new Vector3f(x, y-0.3f, z), 0, getRandomFloat(random, 0, 1), 0, 1));
		}
		/*
		entities.add(new Entity(rocks, new Vector3f(100,  4.7f,  100), 0, 0, 0, 100));
		Lamp lampEntity = new Lamp(lamp, new Vector3f(58, terrains.get(0).getHeightOfTerrain(58, 46) - 0.1f, 46), 0, 0, 0, 1);
		entities.add(lampEntity);
		*/
		
		
		List<Entity> normalMapEntities = new ArrayList<>();
		for (int i = 0; i < 50; i++) {
			do {
				x = getRandomFloat(random, 0, upperLimit);
				z = getRandomFloat(random, 0, upperLimit);
			} while ((y = terrainGrid.getTerrainAt(x, z).getHeightOfTerrain(x, z) + 5 ) >= 0);
			normalMapEntities.add(new Entity(boulder, new Vector3f(x, y, z), 0, getRandomFloat(random, 0, 1), 0, 1));
		}
		
		//normalMapEntities.add(new Entity(barrel, new Vector3f(126, terrains.get(0).getHeightOfTerrain(126,  71) + 20, 71), 0, 0, 0, 1f));
		//normalMapEntities.add(new Entity(boulder, new Vector3f(110, terrains.get(0).getHeightOfTerrain(110,  89) + 20, 89), 0, 0, 0, 1f));
		//normalMapEntities.add(new Entity(crate, new Vector3f(80, terrains.get(0).getHeightOfTerrain(80,  108) + 20, 108), 0, 0, 0, 0.1f));
		
		
		List<Entity> allEntitiesList = new ArrayList<>(entities);
		allEntitiesList.addAll(normalMapEntities);
		
		
		// ------------ add lights ------------
		
		List<Light> lights = new ArrayList<>();
		Sun sun = new Sun(terrainGrid);
		//sun.setPosition(new Vector3f(1_000_000, 1_500_000, -1_000_000));
		lights.add(sun);
		//lights.add(lampEntity.getLight());
		
		
		// ------------ add GUI ------------
		
		List<GuiTexture> guis = new ArrayList<>();
		//guis.add(new GuiTexture(loader.loadTexture("guis/health"), new Vector2f(-0.75f, -0.85f), new Vector2f(0.20f, 0.30f)));
		
		
		// ------------ track the cursor ------------
		
		MousePicker picker = new MousePicker(camera, renderer.getProjectionMatrix(), terrainGrid);
		
		
		// ------------ set time speed ------------
		
		Clock.setTimeSpeed(100);
		
		
		// ------------ add particles -------------
		ParticleSystem system = new ParticleSystem(fire, 100, 2, 0.1f, 1.5f, 30f);
		system.randomizeRotation();
		/*
		ParticleSystem[] system = new ParticleSystem[5];
		system[0] = new ParticleSystem(cosmic, 200, 10, 0.2f, 2, 3.6f);
		system[1] = new ParticleSystem(orange, 200, 10, 0.2f, 2, 3.6f);
		system[2] = new ParticleSystem(star, 200, 10, 0.2f, 2, 3.6f);
		system[2].randomizeRotation();
		system[3] = new ParticleSystem(fire, 50, 2, 0.1f, 2, 30f);
		system[4] = new ParticleSystem(smoke, 25, 2, 0.1f, 2, 30f);
		*/
		
		
		// ------ FBO and PP effects  -------
		Fbo fbo = new Fbo(Display.getWidth(), Display.getHeight(), Fbo.DEPTH_RENDER_BUFFER);
		PostProcessing.init(loader);
		
		
		// ------------ game loop ------------
		
		while(!Display.isCloseRequested()) {
			Clock.tick();
			
			player.move(terrainGrid.getTerrainAt(player.getPosition().x, player.getPosition().z));
			camera.move();
			sun.move();
			picker.update();
			
			/*
			final float yOffset = 30;
			system[0].generateParticles(new Vector3f(173, 5 + yOffset, 142));
			system[1].generateParticles(new Vector3f(185, 3 + yOffset, 185));
			system[2].generateParticles(new Vector3f(137, 1 + yOffset, 180));
			system[3].generateParticles(new Vector3f(92, 6 + yOffset, 172));
			system[4].generateParticles(new Vector3f(49, 3 + yOffset, 161));
			*/
			if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
				system.generateParticles(new Vector3f(player.getPosition()));
			}
			
			ParticleMaster.update(camera);
			
			/*
			normalMapEntities.get(0).increaseRotation(0, 1f, 0);
			normalMapEntities.get(1).increaseRotation(0, 1f, 0);
			normalMapEntities.get(2).increaseRotation(0, 1f, 0);
			*/
			
			logCoords(player.getPosition());
			
			
			renderer.renderShadowMap(allEntitiesList, sun);
			
			GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
			
			// water reflection
			waterFBOS.bindReflectionFrameBuffer();
			float distance = 2 * (camera.getPosition().y - waters.get(0).getHeight());
			camera.getPosition().y -= distance;
			camera.invertPitch();
			renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, 1, 0, -waters.get(0).getHeight()+1f));
			camera.getPosition().y += distance;
			camera.invertPitch();
			
			// water refraction
			waterFBOS.bindRefractionFrameBuffer();
			renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, -1, 0, waters.get(0).getHeight()));
			
			
			// screen rendering
			GL11.glDisable(GL30.GL_CLIP_DISTANCE0); // may not work with some drivers
			waterFBOS.unbindCurrentFrameBuffer();
			
			fbo.bindFrameBuffer();
			renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, -1, 0, 100000)); // set to high value to be safe
			waterRenderer.render(waters, camera, sun);
			ParticleMaster.renderParticles(camera);
			fbo.unbindFrameBuffer();
			PostProcessing.doPostProcessing(fbo.getColourTexture());
			
			//guiRenderer.render(guis);
			
			TextMaster.render();
			
			DisplayManager.updateDisplay();
		}
		
		
		// ------------ clean up ------------
		PostProcessing.cleanUp();
		fbo.cleanUp();
		ParticleMaster.cleanUp();
		TextMaster.cleanUp();
		waterFBOS.cleanUp();
		waterShader.cleanUp();
		guiRenderer.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		
		DisplayManager.closeDisplay();
	}
	
	private static void logCoords(Vector3f pos) {
		System.out.println("x: " + pos.x + ", y: " + pos.y + ", z: " + pos.z + "\n");
	}
	
	private static float getRandomFloat(Random random, float lowerLimit, float upperLimit) {
		return random.nextFloat() * (upperLimit - lowerLimit) + lowerLimit;
	}
}
