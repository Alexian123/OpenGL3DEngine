package textures;

public class ModelTexture {
	
	private int textureID;
	private int normalMap;
	
	private float shineDamper = 10;
	private float reflectivity = 1;
	
	private boolean hasTransparency = false;
	private boolean useFakeLighting = false;
	
	private boolean useCelShading = false;
	
	private int numberOfRows = 1;
	
	public ModelTexture(int texture){
		this.textureID = texture;
	}
	
	public int getNormalMap() {
		return normalMap;
	}

	public void setNormalMap(int normalMap) {
		this.normalMap = normalMap;
	}

	public int getNumberOfRows() {
		return numberOfRows;
	}

	public void setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
	}
	
	public boolean useCelShading() {
		return useCelShading;
	}
	
	public void setUseCelShading(boolean useCelShading) {
		this.useCelShading = useCelShading;
	}

	public boolean useFakeLighting() {
		return useFakeLighting;
	}
	
	public void setUseFakeLighting(boolean useFakeLighting) {
		this.useFakeLighting = useFakeLighting;
	}
	
	public boolean hasTransparency() {
		return hasTransparency;
	}

	public void setHasTransparency(boolean hasTransparency) {
		this.hasTransparency = hasTransparency;
	}

	public int getID(){
		return textureID;
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

}
