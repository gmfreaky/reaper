package nl.sonware.opengltest.world;

import java.util.Random;

import nl.sonware.opengltest.blockmap.Chunk;
import nl.sonware.opengltest.blockmap.blocks.Block;

public class TickTask implements Runnable{

	World w;
	boolean finished;
	Random random = new Random();
	TickTask(World w) {
		this.w=w;
	}
	
	@Override
	public void run() {
		while(!finished) {
			tick();
			try {
				Thread.sleep(10);  // sleep 10 milliseconds for a total of 100 ticks per second
			} catch (InterruptedException e) {
				finished = true;
				break;
			}
		}
	}
	public void tick() {
		for(int xx=0;xx<w.getGrid().xSize;xx++)
		for(int yy=0;yy<w.getGrid().ySize;yy++)
		for(int zz=0;zz<w.getGrid().zSize;zz++) {
			Chunk c = w.getGrid().getChunk(xx, yy, zz);
			if (c!=null) {
				for (int i=0;i<5;i++) {// tick 5 random blocks per chunk
					int ux = random.nextInt(Chunk.xSize);
					int uy = random.nextInt(Chunk.ySize);
					int uz = random.nextInt(Chunk.zSize);
					Block tickBlock = c.getBlock(ux, uy, uz);
					if (tickBlock!=null) {
						tickBlock.tick();
					}
				}
			}
		}
			
	}
}