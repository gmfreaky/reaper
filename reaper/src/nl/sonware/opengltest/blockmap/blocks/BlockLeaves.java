package nl.sonware.opengltest.blockmap.blocks;

import nl.sonware.opengltest.Point2;
import nl.sonware.opengltest.Vector3;
import nl.sonware.opengltest.blockmap.Chunk;

public class BlockLeaves extends Block{
	public BlockLeaves(Chunk c, Vector3 pos) {
		super(c,pos);
		setTexCoords(new Point2(9,0));
		setTransparent(true);
	}
	
	@Override
	public void tick() {
		if (chunk.grid.world.isSimulatingPhysics()) {
			int logCount = chunk.grid.getBlockCountRadius(x, y, z, 5, BlockList.LOG);
			int leavesCount = chunk.grid.getBlockCountRadius(x, y, z, 5, BlockList.LEAVES);
			if (leavesCount>logCount*20) { // 20 leaves per log
				chunk.grid.set(BlockList.AIR, x, y, z);
			}
		}
	}
}
