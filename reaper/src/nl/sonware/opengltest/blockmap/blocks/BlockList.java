package nl.sonware.opengltest.blockmap.blocks;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import nl.sonware.opengltest.Vector3;
import nl.sonware.opengltest.blockmap.Chunk;

public enum BlockList {
	AIR(null),
	DIRT(BlockDirt.class),
	GRASS(BlockGrass.class),
	STONE(BlockStone.class),
	SAND(BlockSand.class),
	WATER(BlockWater.class),
	GLASS(BlockGlass.class),
	LOG(BlockLog.class),
	WOOD(BlockWood.class),
	LEAVES(BlockLeaves.class),
	IRON(BlockIron.class),
	TILEWHITE(BlockTileWhite.class),
	TILEBLACK(BlockTileBlack.class),
	LAVA(BlockLava.class),
	TILEGRAY(BlockTileGray.class),
	;
	
	Class<? extends Block> c;
	BlockList(Class<? extends Block> c) {
		this.c = c;
	}
	public Class<? extends Block> getType() {
		return c;
	}
	public Block newInstance(Chunk chunk, Vector3 pos) {
		if (c==null){return null;}
		Constructor<? extends Block> con;
		try {
			con = c.getConstructor(Chunk.class, Vector3.class);
			if (con!=null) {
				try {
					return con.newInstance(chunk, pos);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		}
		
		return null;
	}
	
	public static BlockList getBlockById(int id) {
		return BlockList.values()[id];
	}
}
