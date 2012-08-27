package nl.sonware.opengltest.blockmap.blocks;

import nl.sonware.opengltest.Point2;
import nl.sonware.opengltest.Vector3;
import nl.sonware.opengltest.blockmap.Chunk;

public class BlockWater extends BlockLiquid{
	public BlockWater(Chunk c, Vector3 pos) {
		super(c,pos);
		setTexCoords(new Point2(5,0));
		setTransparent(true);
	}	
}
