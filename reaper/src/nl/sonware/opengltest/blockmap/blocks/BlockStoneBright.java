package nl.sonware.opengltest.blockmap.blocks;

import nl.sonware.opengltest.Point2;
import nl.sonware.opengltest.Vector3;
import nl.sonware.opengltest.blockmap.Chunk;

public class BlockStoneBright extends Block {
	public BlockStoneBright(Chunk c, Vector3 position) {
		super(c, position);
		setTexCoords(new Point2(3,1));
	}

}
