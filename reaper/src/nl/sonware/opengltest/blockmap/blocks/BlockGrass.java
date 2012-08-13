package nl.sonware.opengltest.blockmap.blocks;

import nl.sonware.opengltest.Point2;
import nl.sonware.opengltest.Vector3;
import nl.sonware.opengltest.blockmap.Chunk;

public class BlockGrass extends Block{
	public BlockGrass(Chunk c, Vector3 pos) {
		super(c,pos);
		setTexCoords(new Point2(0,0), new Point2(2,0), new Point2(1,0), new Point2(1,0), new Point2(1,0), new Point2(1,0));
	}	
}
