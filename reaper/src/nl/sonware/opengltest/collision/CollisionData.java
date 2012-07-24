package nl.sonware.opengltest.collision;

import nl.sonware.opengltest.blockmap.blocks.Block.Face;

public class CollisionData {
	public Object o;
	public Face face;
	public int x,y,z;
	public CollisionData(Object o, Face face, int x, int y, int z) {
		this.o = o;
		this.face = face;
		this.x = x;
		this.y = y;
		this.z = z;
	}
}