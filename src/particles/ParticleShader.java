package particles;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import shaders.ShaderProgram;

public class ParticleShader extends ShaderProgram {

	private static final String VERTEX_FILE = "/particles/particleVShader.glsl";
	private static final String FRAGMENT_FILE = "/particles/particleFShader.glsl";

	private int location_numberOfRows;
	private int location_projectionMatrix;
	private int location_numberOfAtlasRows;
	
	public ParticleShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		location_numberOfRows = super.getUniformLocation("numberOfRows");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_numberOfAtlasRows = super.getUniformLocation("numberOfAtlasRows");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "modelViewMatrix");
		super.bindAttribute(5, "texOffsets");
		super.bindAttribute(6, "blendFactor");
		super.bindAttribute(7, "atlasOffset");
	}
	
	protected void loadNumberOfAtlasRows(float numberOfAtlasRows) {
		super.loadFloat(location_numberOfAtlasRows, numberOfAtlasRows);
	}

	protected void loadNumberOfRows(float numberOfRows) {
		super.loadFloat(location_numberOfRows, numberOfRows);
	}

	protected void loadProjectionMatrix(Matrix4f projectionMatrix) {
		super.loadMatrix(location_projectionMatrix, projectionMatrix);
	}

}
