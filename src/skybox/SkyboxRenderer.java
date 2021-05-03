package skybox;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import models.RawModel;
import renderEngine.Loader;
import toolbox.Clock;
import toolbox.Settings;

public class SkyboxRenderer {
	
	private static final float SIZE = Settings.SKYBOX_SIZE;
	
	private static final String DAY_TEX_PREFIX = "skybox/day/1/";
	private static final String NIGHT_TEX_PREFIX = "skybox/night/1/";

	private static final String[] TEX_FILES = { DAY_TEX_PREFIX + "right", DAY_TEX_PREFIX + "left", DAY_TEX_PREFIX + "up", 
			DAY_TEX_PREFIX + "down", DAY_TEX_PREFIX + "back", DAY_TEX_PREFIX + "front" };
	
	private static final String[] NIGHT_TEX_FILES = { NIGHT_TEX_PREFIX + "right", NIGHT_TEX_PREFIX + "left", NIGHT_TEX_PREFIX + "up", 
			NIGHT_TEX_PREFIX + "down", NIGHT_TEX_PREFIX + "back", NIGHT_TEX_PREFIX +"front" };
	
	private RawModel cube;
	private int texture;
	private int nightTexture;
	private SkyboxShader shader;
	
	private boolean useCelShading = false;
		
	public SkyboxRenderer(Loader loader, Matrix4f projectionMatrix) {
		cube = loader.loadToVAO(VERTICES, 3);
		texture = loader.loadCubeMap(TEX_FILES);
		nightTexture = loader.loadCubeMap(NIGHT_TEX_FILES);
		shader = new SkyboxShader();
		shader.start();
		shader.connectTextureUnits();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}
	
	public void setUseCelShading(boolean useCelShading) {
		this.useCelShading = useCelShading;
	}
	
	public void render(Camera camera, Vector3f fogColor) {
		shader.start();
		shader.loadViewMatrix(camera);
		shader.loadFogColor(fogColor);
		shader.loadCelShadingVariable(useCelShading);
		shader.loadLimits(Settings.SKYBOX_LOWER_LIMIT, Settings.SKYBOX_UPPER_LIMIT);
		shader.loadCelShadingLevels(Settings.SKYBOX_CEL_SHADING_LEVELS);
		GL30.glBindVertexArray(cube.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		bindTextures();
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, cube.getVertexCount());
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shader.stop();
	}
	
	private void bindTextures() {
		int texture1;
		int texture2;
		float blendFactor;
		
		switch (Clock.getTimeOfDay()) {
		case NIGHT:
			texture1 = nightTexture;
			texture2 = nightTexture;
			blendFactor = (Clock.getExactTime() - Clock.TWELVE_AM) / (Clock.SIX_AM - Clock.TWELVE_AM);
			break;
		case MORNING:
			texture1 = nightTexture;
			texture2 = texture;
			blendFactor = (Clock.getExactTime() - Clock.SIX_AM) / (Clock.EIGHT_AM - Clock.SIX_AM);
			break;
		case DAY:
			texture1 = texture;
			texture2 = texture;
			blendFactor = (Clock.getExactTime() - Clock.EIGHT_AM) / (Clock.SIX_PM - Clock.EIGHT_AM );
			break;
		case EVENING:
		default:
			texture1 = texture;
			texture2 = nightTexture;
			blendFactor = (Clock.getExactTime() - Clock.SIX_PM) / (Clock.MAX_TIME - Clock.SIX_PM);
			break;
		}
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texture1);
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texture2);
		shader.loadBlendFactor(blendFactor);
	}
	
	private static final float[] VERTICES = {        
		    -SIZE,  SIZE, -SIZE,
		    -SIZE, -SIZE, -SIZE,
		    SIZE, -SIZE, -SIZE,
		     SIZE, -SIZE, -SIZE,
		     SIZE,  SIZE, -SIZE,
		    -SIZE,  SIZE, -SIZE,

		    -SIZE, -SIZE,  SIZE,
		    -SIZE, -SIZE, -SIZE,
		    -SIZE,  SIZE, -SIZE,
		    -SIZE,  SIZE, -SIZE,
		    -SIZE,  SIZE,  SIZE,
		    -SIZE, -SIZE,  SIZE,

		     SIZE, -SIZE, -SIZE,
		     SIZE, -SIZE,  SIZE,
		     SIZE,  SIZE,  SIZE,
		     SIZE,  SIZE,  SIZE,
		     SIZE,  SIZE, -SIZE,
		     SIZE, -SIZE, -SIZE,

		    -SIZE, -SIZE,  SIZE,
		    -SIZE,  SIZE,  SIZE,
		     SIZE,  SIZE,  SIZE,
		     SIZE,  SIZE,  SIZE,
		     SIZE, -SIZE,  SIZE,
		    -SIZE, -SIZE,  SIZE,

		    -SIZE,  SIZE, -SIZE,
		     SIZE,  SIZE, -SIZE,
		     SIZE,  SIZE,  SIZE,
		     SIZE,  SIZE,  SIZE,
		    -SIZE,  SIZE,  SIZE,
		    -SIZE,  SIZE, -SIZE,

		    -SIZE, -SIZE, -SIZE,
		    -SIZE, -SIZE,  SIZE,
		     SIZE, -SIZE, -SIZE,
		     SIZE, -SIZE, -SIZE,
		    -SIZE, -SIZE,  SIZE,
		     SIZE, -SIZE,  SIZE
		};

}
