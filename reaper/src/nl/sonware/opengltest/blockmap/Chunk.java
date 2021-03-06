package nl.sonware.opengltest.blockmap;

import java.util.ArrayList;
import java.util.Random;

import nl.sonware.opengltest.Point2;
import nl.sonware.opengltest.Point3;
import nl.sonware.opengltest.PolygonData;
import nl.sonware.opengltest.Vector3;
import nl.sonware.opengltest.blockmap.blocks.Block;
import nl.sonware.opengltest.blockmap.blocks.BlockList;
import nl.sonware.opengltest.util.BufferUtils;

import org.lwjgl.opengl.ARBBufferObject;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.GL11;

public class Chunk {
	
	public ChunkGrid grid;
	int x;
	int y;
	int z;
	
	int vertexBuffer, texCoordBuffer;
	Block blockArray[][][];
	public static final int xSize = 16, ySize = 16, zSize = 16;
	int polygonCount;
	boolean isDirty;
	
	public Chunk(ChunkGrid grid, int x, int y, int z) {
		this.grid = grid;
		this.x = x;
		this.y = y;
		this.z = z;
		
		blockArray = new Block[xSize][ySize][zSize];
		
		genBuffers();
	}
	
	public void genBuffers() {
		// generate VertexBuffers
		vertexBuffer = ARBBufferObject.glGenBuffersARB();
		texCoordBuffer = ARBBufferObject.glGenBuffersARB();
	}
	
	public void fillBuffers() {
		ArrayList<Point3> vertexData = new ArrayList<Point3>();
		ArrayList<Point2> texCoordData = new ArrayList<Point2>();

		for(int xx=0;xx<xSize;xx++)
		for(int yy=0;yy<ySize;yy++)
		for(int zz=0;zz<zSize;zz++)
		{
			Block b = getBlock(xx,yy,zz);
			if (b!=null) {
				
				ArrayList<PolygonData> dataList = b.getVertices();
				for(PolygonData d:dataList) {
					vertexData.add(d.vertexData);
					texCoordData.add(d.texCoordData);
				}
			}
		}
		
		// Get vertexdata and texcoorddata from polygondata

		int[] vertexArray = new int[vertexData.size()*3];
		int vi=0;
		for(Point3 p:vertexData) {
			vertexArray[vi] 	= (int) p.getX();
			vertexArray[vi+1] 	= (int) p.getY();
			vertexArray[vi+2] 	= (int) p.getZ();
			vi+=3;
		} // put all vertexes from vertexData in an array
		
		polygonCount = vertexData.size(); // amount of polygons (quads) to render
		
		double[] texCoordArray = new double[texCoordData.size()*2];
		int ti=0;
		for(Point2 p:texCoordData) {
			texCoordArray[ti] 	= p.getXF();
			texCoordArray[ti+1] = p.getYF();
			ti+=2;
		} // put all texCoords in an array
		
		// buffer the arrays to the buffers
		
		if (vertexArray.length!=0) {
			ARBBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, vertexBuffer); // vertex-array in de buffer stoppen
			ARBBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, BufferUtils.wrapDirectInt(vertexArray), ARBBufferObject.GL_DYNAMIC_DRAW_ARB);
			
			ARBBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, texCoordBuffer); // texcoord-array in de buffer stoppen
			ARBBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, BufferUtils.wrapDirectDouble(texCoordArray), ARBBufferObject.GL_DYNAMIC_DRAW_ARB);
		}
		
		ARBBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, 0); // Unbind buffer
	}
	
	boolean insideGrid(int x, int y, int z) {
		return (x>=0 && x<xSize && y>=0 && y<ySize && z>=0 && z<zSize);
	}
	
	public Block getBlock(int x, int y, int z) {
		if (insideGrid(x,y,z)) {
			return blockArray[x][y][z];
		} else {
			int rX = (this.x*xSize)+x;
			int rY = (this.y*ySize)+y;
			int rZ = (this.z*zSize)+z;
			return grid.getBlock(rX, rY, rZ);
		}
	}
	
	public void setBlock(BlockList b, int x, int y, int z) {
		if (insideGrid(x,y,z)) {
			if (b!=null && b!=BlockList.AIR)
			blockArray[x][y][z] = b.newInstance(this, new Vector3(this.getX()*getXSize()+x,this.y*getYSize()+y,this.z*getZSize()+z));
			else
			blockArray[x][y][z] = null;
			
			markDirty();
			
			if (x==0) 		{Chunk c = grid.getChunk(this.getX()-1, this.y, 		this.z); 	if (c!=null)c.isDirty = true;}
			if (x==xSize-1) {Chunk c = grid.getChunk(this.getX()+1, this.y, 		this.z); 	if (c!=null)c.isDirty = true;}
			if (y==0) 		{Chunk c = grid.getChunk(this.getX(), 	this.y-1, 	this.z); 	if (c!=null)c.isDirty = true;}
			if (y==ySize-1) {Chunk c = grid.getChunk(this.getX(), 	this.y+1, 	this.z); 	if (c!=null)c.isDirty = true;}
			if (z==0) 		{Chunk c = grid.getChunk(this.getX(), 	this.y, 	this.z-1); 	if (c!=null)c.isDirty = true;}
			if (z==zSize-1) {Chunk c = grid.getChunk(this.getX(), 	this.y, 	this.z+1); 	if (c!=null)c.isDirty = true;}
		}
	}
	
	public static int getXSize() {
		return xSize;
	}

	public static int getYSize() {
		return ySize;
	}

	public static int getZSize() {
		return zSize;
	}
	
	boolean isDirty() {
		return isDirty;
	}
	
	void markDirty() {
		isDirty = true;
	}
	
	void markClean() {
		isDirty = false;
	}

	public void render() {
		if (isDirty()) {
			fillBuffers();
			markClean();
		}
		
		if (polygonCount==0) {return;}
		
		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);

			ARBBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, vertexBuffer); // bind vertexbuffer
			GL11.glVertexPointer(3, GL11.GL_INT, 0,0); // make pointer to vertexbuffer
			ARBBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, texCoordBuffer); // bind texcoordbuffer
			GL11.glTexCoordPointer(2, GL11.GL_DOUBLE, 0,0); // make pointer to texcoordbuffer
			
				GL11.glDrawArrays(GL11.GL_QUADS, 0, polygonCount); // render arrays
		
		GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
		GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
		
		ARBBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, 0); // reset buffer
	}
	
	@Override
	public String toString() {
		return "Chunk("+getX()+","+y+","+z+")";
	}
	
	public Vector3 getPosition() {
		return new Vector3(getWorldX(),getWorldY(),getWorldZ());
	}
	public Vector3 getPositionCenter() {
		return new Vector3(getWorldX()+(double)Chunk.getXSize()/2d,getWorldY()+(double)Chunk.getYSize()/2d,getWorldZ()+(double)Chunk.getZSize()/2d);
	}
	public Vector3 getPositionCorner(int xc, int yc, int zc) {
		return new Vector3(getWorldX()+(xc*Chunk.getXSize()),getWorldY()+(yc*Chunk.getYSize()),getWorldZ()+(zc*Chunk.getZSize()));
	}
	
	public int getWorldX() {
		return x*Chunk.getXSize();
	}
	
	public int getWorldY() {
		return y*Chunk.getYSize();
	}

	public int getWorldZ() {
		return z*Chunk.getZSize();
	}

	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getZ() {
		return z;
	}
}
