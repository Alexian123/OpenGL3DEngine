package postProcessing;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import toolbox.Settings;

public class ContrastChanger {
	
	private static final float CONTRAST = Settings.getCONTRAST();
	
	private ImageRenderer renderer;
	private ContrastShader shader;
	
	public ContrastChanger() {
		renderer = new ImageRenderer();
		shader = new ContrastShader();
	}
	
	public void render(int texture) {
		shader.start();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		shader.loadContrast(CONTRAST);
		renderer.renderQuad();
		shader.stop();
	}
	
	public void cleanUp() {
		renderer.cleanUp();
		shader.cleanUp();
	}

}
