package terrains;

import java.util.List;

public class TerrainGrid {
	
	private final Terrain[][] grid;
	
	private final int size;
	
	public TerrainGrid(List<Terrain> terrains, int size) {
		this.size = size;
		
		grid = new Terrain[size][size];
		for (int i = 0; i < size; ++i) {
			for (int j = 0; j < size; ++j) {
				grid[i][j] = terrains.get(i * size + j);
			}
		}
	}
	
	public Terrain getTerrainAt(float worldX, float worldZ) {
		int gridX = (int) (worldX / Terrain.SIZE());
		gridX = Math.max(gridX, 0);
		gridX = Math.min(gridX, size - 1);
		
		int gridZ = (int) (worldZ / Terrain.SIZE());
		gridZ = Math.max(gridZ, 0);
		gridZ = Math.min(gridZ, size - 1);
		
		return grid[gridX][gridZ];
	}
	
	public int getSize() {
		return size;
	}

}
