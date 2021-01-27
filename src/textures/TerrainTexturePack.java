package textures;

public class TerrainTexturePack {
	
	private TerrainTexture backgroundTexture;
	private TerrainTexture rTexture;
	private TerrainTexture gTexture;
	private TerrainTexture bTexture;
	
	private boolean useCelShading = false;
	
	private float shineDamper = 1;
	private float reflectivity = 0;

	public TerrainTexturePack(TerrainTexture backgroundTexture, TerrainTexture rTexture, TerrainTexture gTexture,
			TerrainTexture bTexture) {
		this.backgroundTexture = backgroundTexture;
		this.rTexture = rTexture;
		this.gTexture = gTexture;
		this.bTexture = bTexture;
	}
	
	public float getShineDamper() {
		return shineDamper;
	}

	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}

	public float getReflectivity() {
		return reflectivity;
	}

	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}

	public boolean useCelShading() {
		return useCelShading;
	}
	
	public void setUseCelShading(boolean useCelShading) {
		this.useCelShading = useCelShading;
	}

	public TerrainTexture getBackgroundTexture() {
		return backgroundTexture;
	}

	public TerrainTexture getRTexture() {
		return rTexture;
	}

	public TerrainTexture getGTexture() {
		return gTexture;
	}

	public TerrainTexture getBTexture() {
		return bTexture;
	}

}
