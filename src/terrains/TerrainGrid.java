package terrains;

import java.util.List;

public class TerrainGrid {
	
	private final Terrain[][] grid;
	
	private final int size;
	
	public static enum PREDEFINED_SIZE {
		ONE_BY_ONE(1), 
		TWO_BY_TWO(2), 
		THREE_BY_THREE(3), 
		FOUR_BY_FOUR(4), 
		FIVE_BY_FIVE(5);
		
		private int actualSize;
		
		private PREDEFINED_SIZE(int actualSize) {
			this.actualSize = actualSize;
		}
		
		public int asInt() {
			return this.actualSize;
		}
	}
	
	public TerrainGrid(List<Terrain> terrains, PREDEFINED_SIZE predefSize) {
		size = predefSize.asInt();
		
		grid = new Terrain[size][size];
		for (int i = 0; i < size; ++i) {
			for (int j = 0; j < size; ++j) {
				grid[i][j] = terrains.get(i * size + j);
			}
		}
	}
	
	public Terrain getTerrainAt(int gridX, int gridZ) {
		/* ensure output terrain != null */
		gridX = Math.max(gridX, 0);
		gridX = Math.min(gridX, size - 1);
		gridZ = Math.max(gridZ, 0);
		gridZ = Math.min(gridZ, size - 1);
		return grid[gridX][gridZ];
	}
	
	public Terrain getTerrainAt(float worldX, float worldZ) {
		int gridX = (int) (worldX / Terrain.SIZE());
		int gridZ = (int) (worldZ / Terrain.SIZE());
		return getTerrainAt(gridX, gridZ);
	}
	
	public int getSize() {
		return size;
	}
}
