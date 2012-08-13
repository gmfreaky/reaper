package nl.sonware.opengltest.blockmap.blocks;

import java.util.ArrayList;

import nl.sonware.opengltest.Point2;
import nl.sonware.opengltest.Point3;
import nl.sonware.opengltest.PolygonData;
import nl.sonware.opengltest.Vector3;
import nl.sonware.opengltest.blockmap.Chunk;


public class Block {
	
	Chunk chunk;
	int x,y,z;
	public Point2 texBottom,texTop,texLeft,texRight,texFront,texRear;
	boolean isTransparent = false;
	boolean hasGravity;
	
	public Block(Chunk chunk, Vector3 position) {
		this.chunk = chunk;
		setTexCoords(new Point2(0,0));
		setPosition(position);
	}
	
	public BlockList getType() {
		for(BlockList b:BlockList.values()) {
			if (b.getType() == getClass()) {
				return b;
			}
		}
		return null;
	}
	
	public void update() {
		if (chunk.grid.world.isSimulatingPhysics()) {
			if (hasGravity) {
				if (chunk.grid.get(x, y, z-1)==null) {
					chunk.grid.set(BlockList.AIR, x, y, z);
					chunk.grid.set(getType(), x, y, z-1);
				}
			}
		}
	}
	
	public void tick() {
		
	}
	
	void setPosition(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	void setPosition(Vector3 position) {
		if (position!=null) {
			this.x = position.getXI();
			this.y = position.getYI();
			this.z = position.getZI();
		}
	}
	
	public Vector3 getPosition() {
		return new Vector3(x,y,z);
	}
	
	void setTransparent(boolean isTransparent) {
		this.isTransparent = isTransparent;
	}
	
	void setTexCoords(Point2 tex) {
		setTexCoords(tex, tex, tex, tex, tex, tex);
	}
	
	void setTexCoords(Point2 texBottom, Point2 texTop, Point2 texLeft,
			Point2 texRight, Point2 texFront, Point2 texRear) {
		this.texBottom = texBottom;
		this.texTop = texTop;
		this.texLeft = texLeft;
		this.texRight = texRight;
		this.texFront = texFront;
		this.texRear = texRear;
	}
	
	public enum Face {
		BOTTOM	(0,0,-1),
		TOP		(0,0,1),
		LEFT	(-1,0,0),
		RIGHT	(1,0,0),
		FRONT	(0,-1,0),
		REAR	(0,1,0),
		;
		
		Face(int x, int y, int z) {
			this.x=x;
			this.y=y;
			this.z=z;
		}
		public int x,y,z;
	}
	
	public ArrayList<PolygonData> getVertices() {
		ArrayList<PolygonData> pointList = new ArrayList<PolygonData>();
		
		double tcMul = 1d/16d;
		
		double botUMul = (texBottom.getX()*tcMul);
		double botVMul = (texBottom.getY()*tcMul);
		
		double topUMul = (texTop.getX()*tcMul);
		double topVMul = (texTop.getY()*tcMul);
		
		double leftUMul = (texLeft.getX()*tcMul);
		double leftVMul = (texLeft.getY()*tcMul);
		
		double rightUMul = (texRight.getX()*tcMul);
		double rightVMul = (texRight.getY()*tcMul);
		
		double frontUMul = (texFront.getX()*tcMul);
		double frontVMul = (texFront.getY()*tcMul);

		double rearUMul = (texRear.getX()*tcMul);
		double rearVMul = (texRear.getY()*tcMul);		
		
		int rx = x-(chunk.getX()*Chunk.xSize);
		int ry = y-(chunk.getY()*Chunk.ySize);
		int rz = z-(chunk.getZ()*Chunk.zSize);
		
		Block bottomBlock = chunk.getBlock(rx, ry, rz-1);
		Block topBlock = chunk.getBlock(rx, ry, rz+1);
		Block leftBlock = chunk.getBlock(rx-1, ry, rz);
		Block rightBlock = chunk.getBlock(rx+1, ry, rz);
		Block frontBlock = chunk.getBlock(rx, ry-1, rz);
		Block rearBlock = chunk.getBlock(rx, ry+1, rz);
		
		if (bottomBlock==null || (bottomBlock.isTransparent && bottomBlock.getType()!=getType())) { // bottom
			pointList.add(new PolygonData(new Point3(rx,		ry+1,rz), 	new Point2(botUMul,				botVMul)));
			pointList.add(new PolygonData(new Point3(rx+1,	ry+1,rz), 	new Point2(botUMul+tcMul,		botVMul)));
			pointList.add(new PolygonData(new Point3(rx+1,	ry,	rz), 	new Point2(botUMul+tcMul,		botVMul+tcMul)));
			pointList.add(new PolygonData(new Point3(rx,		ry,	rz), 	new Point2(botUMul,				botVMul+tcMul)));
		}
		if (topBlock==null || (topBlock.isTransparent && topBlock.getType()!=getType())) { // top
			pointList.add(new PolygonData(new Point3(rx,		ry,	rz+1), 	new Point2(topUMul,				topVMul)));
			pointList.add(new PolygonData(new Point3(rx+1,	ry,	rz+1), 	new Point2(topUMul+tcMul,		topVMul)));
			pointList.add(new PolygonData(new Point3(rx+1,	ry+1,rz+1), 	new Point2(topUMul+tcMul,		topVMul+tcMul)));
			pointList.add(new PolygonData(new Point3(rx,		ry+1,rz+1), 	new Point2(topUMul,				topVMul+tcMul)));
		}
		if (leftBlock==null || (leftBlock.isTransparent && leftBlock.getType()!=getType())) { // left
			pointList.add(new PolygonData(new Point3(rx,ry,	rz), 		new Point2(leftUMul+tcMul,		leftVMul+tcMul)));
			pointList.add(new PolygonData(new Point3(rx,ry,	rz+1), 		new Point2(leftUMul+tcMul,		leftVMul)));
			pointList.add(new PolygonData(new Point3(rx,ry+1,	rz+1), 		new Point2(leftUMul,			leftVMul)));
			pointList.add(new PolygonData(new Point3(rx,ry+1,	rz), 		new Point2(leftUMul,			leftVMul+tcMul)));
		}
		if (rightBlock==null || (rightBlock.isTransparent && rightBlock.getType()!=getType())) { // right
			pointList.add(new PolygonData(new Point3(rx+1,ry+1,	rz), 	new Point2(rightUMul+tcMul,		rightVMul+tcMul)));
			pointList.add(new PolygonData(new Point3(rx+1,ry+1,	rz+1), 	new Point2(rightUMul+tcMul,		rightVMul)));
			pointList.add(new PolygonData(new Point3(rx+1,ry,		rz+1), 	new Point2(rightUMul,			rightVMul)));
			pointList.add(new PolygonData(new Point3(rx+1,ry,		rz), 	new Point2(rightUMul,			rightVMul+tcMul)));
		}
		if (frontBlock==null || (frontBlock.isTransparent && frontBlock.getType()!=getType())) { // front
			pointList.add(new PolygonData(new Point3(rx,		ry,	rz), 	new Point2(frontUMul,			frontVMul+tcMul)));
			pointList.add(new PolygonData(new Point3(rx+1,	ry,	rz), 	new Point2(frontUMul+tcMul,		frontVMul+tcMul)));
			pointList.add(new PolygonData(new Point3(rx+1,	ry,	rz+1), 	new Point2(frontUMul+tcMul,		frontVMul)));
			pointList.add(new PolygonData(new Point3(rx,		ry,	rz+1), 	new Point2(frontUMul,			frontVMul)));
		}
		if (rearBlock==null || (rearBlock.isTransparent && rearBlock.getType()!=getType())) { // rear
			pointList.add(new PolygonData(new Point3(rx,		ry+1,rz+1),	new Point2(rearUMul+tcMul,		rearVMul)));
			pointList.add(new PolygonData(new Point3(rx+1,	ry+1,rz+1),	new Point2(rearUMul,			rearVMul)));
			pointList.add(new PolygonData(new Point3(rx+1,	ry+1,rz), 	new Point2(rearUMul,			rearVMul+tcMul)));
			pointList.add(new PolygonData(new Point3(rx,		ry+1,rz), 	new Point2(rearUMul+tcMul,		rearVMul+tcMul)));
		}
		return pointList;
	}
}
