package nl.sonware.opengltest.blockmap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import nl.sonware.opengltest.Camera;
import nl.sonware.opengltest.Main;
import nl.sonware.opengltest.Vector3;
import nl.sonware.opengltest.blockmap.blocks.Block;
import nl.sonware.opengltest.blockmap.blocks.BlockList;
import nl.sonware.opengltest.util.MathUtils;
import nl.sonware.opengltest.world.World;

import org.lwjgl.opengl.GL11;

public class ChunkGrid implements Grid{
	public int xSize, ySize, zSize;
	public World world;
	Chunk[][][] chunkArray;
	Vector3 lastPos;
	ArrayList<Chunk> renderList = new ArrayList<Chunk>();
	
	public ChunkGrid(World world, int xSize, int ySize, int zSize) {
		
		this.world = world;
		
		this.xSize = xSize;
		this.ySize = ySize;
		this.zSize = zSize;
		
		chunkArray = new Chunk[xSize][ySize][zSize];
		for(int x=0;x<xSize;x++)
		for(int y=0;y<ySize;y++)
		for(int z=0;z<zSize;z++) {
			addChunk(x, y, z);
		}
	}
	
	Block getBlock(int x, int y, int z) {
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
	
	void setBlock(BlockList b, int x, int y, int z) {
		Chunk c = getChunkBlock(x,y,z);
		
		if (c!=null) {
			int rX = MathUtils.modulo(x,Chunk.xSize);	// the remainder of x/chunk.xSize
			int rY = MathUtils.modulo(y,Chunk.ySize);
			int rZ = MathUtils.modulo(z,Chunk.zSize);
			
			c.setBlock(b, rX, rY, rZ);
			updateSurroundingBlocks(x,y,z);
		}
	}
	
	
	public BlockList getType(int x, int y, int z) {
		Block b = getBlock(x,y,z);
		if (b==null) {return BlockList.AIR;} else {return b.getType();}
	}
	
	void updateSurroundingBlocks(int x, int y, int z) {
		Block b1 = getBlock(x,y,z);
		Block b2 = getBlock(x-1,y,z);
		Block b3 = getBlock(x+1,y,z);
		Block b4 = getBlock(x,y-1,z);
		Block b5 = getBlock(x,y+1,z);
		Block b6 = getBlock(x,y,z-1);
		Block b7 = getBlock(x,y,z+1);
		if (b1!=null){b1.update();}
		if (b2!=null){b2.update();}
		if (b3!=null){b3.update();}
		if (b4!=null){b4.update();}
		if (b5!=null){b5.update();}
		if (b6!=null){b6.update();}
		if (b7!=null){b7.update();}
	}
	
	public void addChunk(int x, int y, int z) { // Add chunk by coordinates
		
		if (insideGrid(x,y,z) && getChunk(x,y,z)==null) {
			Chunk nc = new Chunk(this, x, y, z);
			chunkArray[x][y][z] = nc;
			renderList.add(nc);
		}
	}
	
	public void removeChunk(int x, int y, int z) { // Add chunk by coordinates
		if (insideGrid(x,y,z) && getChunk(x,y,z)!=null) {
			renderList.remove(getChunk(x,y,z));
			chunkArray[x][y][z] = null;
		}
	}
	
	public Chunk getChunk(int x, int y, int z) { // Get chunk by coordinates
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
	
	public void render(final Camera cam) {
		
		boolean sortChunks = false;
		
		Vector3 camPos = cam.getPosition();
		boolean camInNewChunk = true;
		if (lastPos!=null) {
			camInNewChunk = (lastPos.getXI()/16)!=(camPos.getXI()/16) || (lastPos.getYI()/16)!=(camPos.getYI()/16) || (lastPos.getZI()/16)!=(camPos.getZI()/16);
		}
		if(camInNewChunk) {
			sortChunks = true;
		}
		lastPos = cam.getPosition();
		
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glAlphaFunc(GL11.GL_NOTEQUAL, 0);
		
		class CompareChunk implements Comparator<Chunk> {

			@Override
			public int compare(Chunk a, Chunk b) {
				Vector3 camPos = cam.getPosition();
				double distA = MathUtils.distSq(a.x, a.y, a.z, camPos.getXF(), camPos.getYF(),camPos.getZF());
				double distB = MathUtils.distSq(b.x, b.y, b.z, camPos.getXF(), camPos.getYF(),camPos.getZF());
				if (distA<distB) {return -1;}
				else if (distA>distB) {return 1;}
				else {return 0;}
			} 
		}
		
		if (sortChunks) {
			Collections.sort(renderList, new CompareChunk());
		}
		
		for(Chunk chunk:renderList) {
			Vector3 chunkToCam = chunk.getPositionCenter().sub(camPos);
			
			if (chunkToCam.getLengthSq()<Math.pow(Main.renderDist+Chunk.xSize,2)) { // if chunk is within renderdistance
				Vector3 toCam1 = chunk.getPositionCorner(0,0,0).sub(camPos);
				Vector3 toCam2 = chunk.getPositionCorner(0,0,1).sub(camPos);
				Vector3 toCam3 = chunk.getPositionCorner(0,1,0).sub(camPos);
				Vector3 toCam4 = chunk.getPositionCorner(0,1,1).sub(camPos);
				Vector3 toCam5 = chunk.getPositionCorner(1,0,0).sub(camPos);
				Vector3 toCam6 = chunk.getPositionCorner(1,0,1).sub(camPos);
				Vector3 toCam7 = chunk.getPositionCorner(1,1,0).sub(camPos);
				Vector3 toCam8 = chunk.getPositionCorner(1,1,1).sub(camPos);
				
				toCam1.normalize();
				toCam2.normalize();
				toCam3.normalize();
				toCam4.normalize();
				toCam5.normalize();
				toCam6.normalize();
				toCam7.normalize();
				toCam8.normalize();
				
				Vector3 camVec = cam.getLookat().sub(cam.getPosition());
				camVec.normalize();
				
				double dot1 = toCam1.dot(camVec);
				double dot2 = toCam2.dot(camVec);
				double dot3 = toCam3.dot(camVec);
				double dot4 = toCam4.dot(camVec);
				double dot5 = toCam5.dot(camVec);
				double dot6 = toCam6.dot(camVec);
				double dot7 = toCam7.dot(camVec);
				double dot8 = toCam8.dot(camVec);
				
				if (dot1>0 || dot2>0 || dot3>0 || dot4>0 || dot5>0 || dot6>0 || dot7>0 || dot8>0) {// if chunk is not behind camera
					GL11.glPushMatrix();
					GL11.glTranslatef(chunk.x*Chunk.xSize, chunk.y*Chunk.ySize, chunk.z*Chunk.zSize);
						chunk.render();
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

	@Override
	public void set(Object o, int x, int y, int z) {
		if (o instanceof BlockList || o==null)
		setBlock((BlockList) o, x, y, z);
	}

	public int getBlockCountRadius(int xx, int yy, int zz, int radius, BlockList blockType) {
		
		int count=0;
		
		
		for(int z=zz-radius;z<zz+radius;z++)
		for(int y=yy-radius;y<yy+radius;y++)
		for(int x=xx-radius;x<xx+radius;x++){
			Object get = get(x,y,z);

			if ((get==null && blockType==BlockList.AIR) || (get!=null && ((Block) get).getType()==blockType)) {
				double dist = MathUtils.distSq(x, y, z, xx,yy,zz);
				if (dist<radius*2) {
					count++;
				}
			}
		}
		
		return count;
	}
}
