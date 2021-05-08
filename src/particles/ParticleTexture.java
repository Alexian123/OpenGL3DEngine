package particles;

public class ParticleTexture {

	private int textureID;
	private int numberOfRows;
	private boolean additive;
	
	private int numberOfAtlasRows = 1;
	
	public ParticleTexture(int textureID, int numberOfRows, boolean additive) {
		this.textureID = textureID;
		this.numberOfRows = numberOfRows;
		this.additive = additive;
	}
	
	public ParticleTexture(int textureID, int numberOfRows, boolean additive, int numberOfAtlasRows) {
		this.textureID = textureID;
		this.numberOfRows = numberOfRows;
		this.additive = additive;
		this.numberOfAtlasRows = numberOfAtlasRows;
	}
	
	public boolean useAdditiveBlending() {
		return additive;
	}

	public int getTextureID() {
		return textureID;
	}

	public int getNumberOfRows() {
		return numberOfRows;
	}
	
	public int getNumberOfAtlasRows() {
		return numberOfAtlasRows;
	}
	
}
