package nl.sonware.opengltest.blockmap;

import nl.sonware.opengltest.Vector3;
import nl.sonware.opengltest.blockmap.blocks.Block;
import nl.sonware.opengltest.blockmap.blocks.BlockList;

public class BlockChange {
	Grid g;
	Vector3 position;
	Block block1;
	BlockList block2;
	
	public BlockChange(Vector3 position, Grid g, BlockList newblock) {
		this.g = g;
		this.position = position;
		this.block1 = (Block) g.get(position.getXI(), position.getYI(), position.getZI());
		this.block2 = newblock;
	}
	
	public void execute() {
		g.set(block2, position.getXI(), position.getYI(), position.getZI());
	}
}
