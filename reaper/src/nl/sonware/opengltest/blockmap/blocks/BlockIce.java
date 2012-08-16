package nl.sonware.opengltest.blockmap.blocks;

import nl.sonware.opengltest.Point2;
import nl.sonware.opengltest.Vector3;
import nl.sonware.opengltest.blockmap.Chunk;

public class BlockIce extends Block{

	public BlockIce(Chunk chunk, Vector3 position) {
		super(chunk, position);
		setTexCoords(new Point2(15,0));
		setTransparent(true);
	}

}
