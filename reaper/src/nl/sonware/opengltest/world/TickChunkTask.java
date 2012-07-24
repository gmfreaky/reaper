package nl.sonware.opengltest.world;

import nl.sonware.opengltest.Vector3;
import nl.sonware.opengltest.blockmap.BlockChange;
import nl.sonware.opengltest.blockmap.Chunk;
import nl.sonware.opengltest.blockmap.blocks.Block;

public class TickChunkTask implements Runnable {

	Chunk c;
	TickChunkTask(Chunk c) {
		this.c = c;
	}
	
	@Override
	public void run() {
		
		for(int xx=0;xx<Chunk.xSize;xx++)
		for(int yy=0;yy<Chunk.ySize;yy++)
		for(int zz=0;zz<Chunk.zSize;zz++) {
			//c.getBlock(xx, yy, zz).tick();
		}

	}

}
