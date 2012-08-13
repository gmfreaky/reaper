package nl.sonware.opengltest.blockmap;

import java.io.File;

public class SaveChunkGrid {
	SaveChunkGrid(ChunkGrid grid, String name) {
		
		File file = new File("maps/"+name+"/");
		
		file.mkdirs();
		
		for(int x=0;x<grid.xSize;x++)
		for(int y=0;y<grid.ySize;y++)
		for(int z=0;z<grid.zSize;z++) {
			
		}
	}
}
