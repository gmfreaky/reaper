package nl.sonware.opengltest.blockmap;

import java.util.ArrayList;

import nl.sonware.opengltest.Point2;
import nl.sonware.opengltest.Point3;
import nl.sonware.opengltest.PolygonData;


public enum Block {
	DIRT(1, new Point2(0,0), false),
	GRASS(2, new Point2(0,0), new Point2(2,0),new Point2(1,0),new Point2(1,0),new Point2(1,0),new Point2(1,0), false),
	STONE(3, new Point2(3,0)),
	SAND(4, new Point2(4,0)),
	WATER(5, new Point2(5,0)),
	GLASS(6, new Point2(6,0), true),
	LOG(7, new Point2(7,1), new Point2(7,1), new Point2(7,0), new Point2(7,0), new Point2(7,0), new Point2(7,0), true),
	WOOD(8, new Point2(8,0)),
	LEAVES(9, new Point2(9,0), true),
	IRON(10, new Point2(10,0)),
	TILEWHITE(11, new Point2(11,0)),
	TILEBLACK(12, new Point2(12,0)),
	LAVA(13, new Point2(13,0)),
	TILEGRAY(14, new Point2(14,0)),
	;
	int id;
	public Point2 texBottom,texTop,texLeft,texRight,texFront,texRear;
	boolean isTransparent;
	
	Block(int id) {
		this(id, new Point2(0,0), false);
	}
	Block(int id, Point2 texCoords) {
		this(id, texCoords, false);
	}
	Block(int id, Point2 texCoords, boolean isTransparent) {
		this(id, texCoords,texCoords,texCoords,texCoords,texCoords,texCoords, isTransparent);
	}
	
	private Block(int id, Point2 texBottom, Point2 texTop, Point2 texLeft,
			Point2 texRight, Point2 texFront, Point2 texRear, boolean isTransparent) {
		this.id = id;
		this.texBottom = texBottom;
		this.texTop = texTop;
		this.texLeft = texLeft;
		this.texRight = texRight;
		this.texFront = texFront;
		this.texRear = texRear;
		this.isTransparent = isTransparent;
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
	
	ArrayList<PolygonData> getVertices(Chunk chunk, int x, int y, int z) {
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
		
		Block bottomBlock = chunk.getBlock(x, y, z-1);
		Block topBlock = chunk.getBlock(x, y, z+1);
		Block leftBlock = chunk.getBlock(x-1, y, z);
		Block rightBlock = chunk.getBlock(x+1, y, z);
		Block frontBlock = chunk.getBlock(x, y-1, z);
		Block rearBlock = chunk.getBlock(x, y+1, z);
		
		if (bottomBlock==null || (bottomBlock.isTransparent && bottomBlock!=this)) { // bottom
			pointList.add(new PolygonData(new Point3(x,		y+1,z), 	new Point2(botUMul,				botVMul)));
			pointList.add(new PolygonData(new Point3(x+1,	y+1,z), 	new Point2(botUMul+tcMul,		botVMul)));
			pointList.add(new PolygonData(new Point3(x+1,	y,	z), 	new Point2(botUMul+tcMul,		botVMul+tcMul)));
			pointList.add(new PolygonData(new Point3(x,		y,	z), 	new Point2(botUMul,				botVMul+tcMul)));
		}
		if (topBlock==null || (topBlock.isTransparent && topBlock!=this)) { // top
			pointList.add(new PolygonData(new Point3(x,		y,	z+1), 	new Point2(topUMul,				topVMul)));
			pointList.add(new PolygonData(new Point3(x+1,	y,	z+1), 	new Point2(topUMul+tcMul,		topVMul)));
			pointList.add(new PolygonData(new Point3(x+1,	y+1,z+1), 	new Point2(topUMul+tcMul,		topVMul+tcMul)));
			pointList.add(new PolygonData(new Point3(x,		y+1,z+1), 	new Point2(topUMul,				topVMul+tcMul)));
		}
		if (leftBlock==null || (leftBlock.isTransparent && leftBlock!=this)) { // left
			pointList.add(new PolygonData(new Point3(x,y,	z), 		new Point2(leftUMul+tcMul,		leftVMul+tcMul)));
			pointList.add(new PolygonData(new Point3(x,y,	z+1), 		new Point2(leftUMul+tcMul,		leftVMul)));
			pointList.add(new PolygonData(new Point3(x,y+1,	z+1), 		new Point2(leftUMul,			leftVMul)));
			pointList.add(new PolygonData(new Point3(x,y+1,	z), 		new Point2(leftUMul,			leftVMul+tcMul)));
		}
		if (rightBlock==null || (rightBlock.isTransparent && rightBlock!=this)) { // right
			pointList.add(new PolygonData(new Point3(x+1,y+1,	z), 	new Point2(rightUMul+tcMul,		rightVMul+tcMul)));
			pointList.add(new PolygonData(new Point3(x+1,y+1,	z+1), 	new Point2(rightUMul+tcMul,		rightVMul)));
			pointList.add(new PolygonData(new Point3(x+1,y,		z+1), 	new Point2(rightUMul,			rightVMul)));
			pointList.add(new PolygonData(new Point3(x+1,y,		z), 	new Point2(rightUMul,			rightVMul+tcMul)));
		}
		if (frontBlock==null || (frontBlock.isTransparent && frontBlock!=this)) { // front
			pointList.add(new PolygonData(new Point3(x,		y,	z), 	new Point2(frontUMul,			frontVMul+tcMul)));
			pointList.add(new PolygonData(new Point3(x+1,	y,	z), 	new Point2(frontUMul+tcMul,		frontVMul+tcMul)));
			pointList.add(new PolygonData(new Point3(x+1,	y,	z+1), 	new Point2(frontUMul+tcMul,		frontVMul)));
			pointList.add(new PolygonData(new Point3(x,		y,	z+1), 	new Point2(frontUMul,			frontVMul)));
		}
		if (rearBlock==null || (rearBlock.isTransparent && rearBlock!=this)) { // rear
			pointList.add(new PolygonData(new Point3(x,		y+1,z+1),	new Point2(rearUMul+tcMul,		rearVMul)));
			pointList.add(new PolygonData(new Point3(x+1,	y+1,z+1),	new Point2(rearUMul,			rearVMul)));
			pointList.add(new PolygonData(new Point3(x+1,	y+1,z), 	new Point2(rearUMul,			rearVMul+tcMul)));
			pointList.add(new PolygonData(new Point3(x,		y+1,z), 	new Point2(rearUMul+tcMul,		rearVMul+tcMul)));
		}
		return pointList;
	}
	
}
