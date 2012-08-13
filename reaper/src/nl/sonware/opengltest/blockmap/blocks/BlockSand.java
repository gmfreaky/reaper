package nl.sonware.opengltest.blockmap.blocks;

import nl.sonware.opengltest.Point2;
import nl.sonware.opengltest.Vector3;
import nl.sonware.opengltest.blockmap.Chunk;

public class BlockSand extends Block {
	public BlockSand(Chunk c, Vector3 position) {
		super(c, position);
		setTexCoords(new Point2(4,0));
		hasGravity = true;
	}

}
