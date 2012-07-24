package nl.sonware.opengltest.blockmap;

import nl.sonware.opengltest.Vector3;
import nl.sonware.opengltest.blockmap.blocks.Block;

public class BlockChange {
	Grid g;
	Vector3 position;
	Block block1;
	Block block2;
	
	public BlockChange(Vector3 position, Grid g, Block block) {
		this.g = g;
		this.position = position;
		this.block1 = (Block) g.get(position.getXI(), position.getYI(), position.getZI());
		this.block2 = block;
	}
	
	public void execute() {
		g.set(block2, position.getXI(), position.getYI(), position.getZI());
	}
}
