package nl.sonware.opengltest.blockmap;

import nl.sonware.opengltest.Camera;
import nl.sonware.opengltest.Main;
import nl.sonware.opengltest.util.MathUtils;

import org.lwjgl.opengl.GL11;

public class ChunkGrid implements Grid{
	public int xSize, ySize, zSize;
	Chunk[][][] chunkArray;
	
	public ChunkGrid(int xSize, int ySize, int zSize) {
		this.xSize = xSize;
		this.ySize = ySize;
		this.zSize = zSize;
		
		chunkArray = new Chunk[xSize][ySize][zSize];
		
		for(int x=0;x<xSize;x++)
		for(int y=0;y<ySize;y++)
		for(int z=0;z<zSize;z++) {
			chunkArray[x][y][z] = new Chunk(this,x,y,z);
		}
	}
	
	public Block getBlock(int x, int y, int z) {
		Chunk c = getChunkBlock(x,y,z);
		
		if (c!=null) {
			int rX = MathUtils.modulo(x,Chunk.xSize);	// the remainder of x/chunk.xSize
			int rY = MathUtils.modulo(y,Chunk.ySize);
			int rZ = MathUtils.modulo(z,Chunk.zSize);
						
			return c.getBlock(rX, rY, rZ);
		} else {
			return null;
		}
	}
	
	public void setBlock(Block b, int x, int y, int z) {
		Chunk c = getChunkBlock(x,y,z);
		
		if (c!=null) {
			int rX = MathUtils.modulo(x,Chunk.xSize);	// the remainder of x/chunk.xSize
			int rY = MathUtils.modulo(y,Chunk.ySize);
			int rZ = MathUtils.modulo(z,Chunk.zSize);
			
			c.setBlock(b, rX, rY, rZ);
		}
	}
	
	Chunk getChunk(int x, int y, int z) { // Get chunk by index
		if (insideGrid(x,y,z)) {
			return chunkArray[x][y][z];
		}
		return null;
	}
	
	public Chunk getChunkBlock(int x, int y, int z) { // Get a chunk by the blockcoordinates
		int cX = (int)Math.floor(x/(float)Chunk.xSize);
		int cY = (int)Math.floor(y/(float)Chunk.ySize);
		int cZ = (int)Math.floor(z/(float)Chunk.zSize);
		// Look for the chunk where the block is located
		
		return getChunk(cX,cY,cZ);
	}
	
	public void render(Camera cam) {
		
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glAlphaFunc(GL11.GL_NOTEQUAL, 0);

		for(int x=0;x<xSize;x++)
		for(int y=0;y<ySize;y++)
		for(int z=0;z<zSize;z++) {
			float cX = (x+0.5f)*Chunk.xSize;
			float cY = (y+0.5f)*Chunk.ySize;
			float cZ = (z+0.5f)*Chunk.zSize;
			
			if (MathUtils.distSq(cX, cY, cZ, (float)cam.getX(), (float)cam.getY(), (float)cam.getZ())<Math.pow(Main.renderDist+Chunk.xSize,2)) {
				Chunk rc = getChunk(x,y,z);
				if (rc!=null) {
					GL11.glPushMatrix();
					GL11.glTranslatef(x*Chunk.xSize, y*Chunk.ySize, z*Chunk.zSize);
						rc.render();
					GL11.glPopMatrix();
				}
			}
		}
	}
	
	boolean insideGrid(int x, int y, int z) {
		return (x>=0 && x<xSize && y>=0 && y<ySize && z>=0 && z<zSize);
	}
	
	public boolean blockInsideGrid(int x, int y, int z) {
		return (x>=0 && x<xSize*Chunk.xSize && y>=0 && y<ySize*Chunk.ySize && z>=0 && z<zSize*Chunk.zSize);
	}

	public int getxSize() { // xsize in blocks
		return xSize*Chunk.xSize;
	}
	public int getySize() { // ysize in blocks
		return ySize*Chunk.ySize;
	}
	public int getzSize() { // zsize
		return zSize*Chunk.zSize;
	}

	@Override
	public Object get(int x, int y, int z) {
		return getBlock(x, y, z);
	}
}
