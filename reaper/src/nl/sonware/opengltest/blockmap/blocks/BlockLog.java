package nl.sonware.opengltest.blockmap.blocks;

import nl.sonware.opengltest.Point2;
import nl.sonware.opengltest.Vector3;
import nl.sonware.opengltest.blockmap.Chunk;

public class BlockLog extends Block{
	public BlockLog(Chunk c, Vector3 pos) {
		super(c, pos);
		setTexCoords(new Point2(7,1), new Point2(7,1), new Point2(7,0) ,new Point2(7,0), new Point2(7,0), new Point2(7,0));
	}
	
	public void update() {
		if (chunk.grid.world.isSimulatingPhysics()) {
			if (chunk.grid.get(x, y, z-1)==null) {
				chunk.grid.set(BlockList.AIR, x, y, z);
			}
		}
	}
}
