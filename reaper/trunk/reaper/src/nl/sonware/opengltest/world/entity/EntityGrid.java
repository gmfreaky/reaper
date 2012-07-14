package nl.sonware.opengltest.world.entity;

import nl.sonware.opengltest.Vector3;
import nl.sonware.opengltest.blockmap.Grid;
import nl.sonware.opengltest.collision.AABB;

public class EntityGrid implements Grid{
	Entity[][][] grid;
	int xSize,ySize,zSize;
	public EntityGrid(int xSize, int ySize, int zSize) {
		this.xSize = xSize;
		this.ySize = ySize;
		this.zSize = zSize;
		generateGrid();
	}
	
	boolean insideGrid(int x, int y, int z) {
		return (x>=0 && x<xSize && y>=0 && y<ySize && z>=0 && z<zSize);
	}
	void set(Entity e, int x, int y, int z) {
		if (insideGrid(x,y,z)) {
			grid[x][y][z] = e;
		}
	}
	public void setEntity(Entity e) {
		if (e.boundingBox!=null && e.boundingBox instanceof AABB){
			AABB box = (AABB) e.boundingBox;
			Vector3 pos = new Vector3(e.boundingBox.getPosition());
			Vector3 size = box.getSize();
			Vector3 halfSize = size.mul(new Vector3(0.5f,0.5f,0.5f));
			pos = pos.sub(halfSize);
			for(int xx=pos.getXI();xx<pos.getXI()+size.getXI();xx++)
			for(int yy=pos.getYI();yy<pos.getYI()+size.getYI();yy++)
			for(int zz=pos.getZI();zz<pos.getZI()+size.getZI();zz++) {
				set(e,xx,yy,zz);
			}
		}
		else {
			set(e,e.getPosition().getXI(),e.getPosition().getYI(),e.getPosition().getZI());
		}
	}
	public Entity getEntity(Vector3 position) {
		if (insideGrid(position.getXI(),position.getYI(),position.getZI()))
		return grid[position.getXI()][position.getYI()][position.getZI()];
		else
		return null;
	}
	
	public void generateGrid() {
		grid = new Entity[xSize][ySize][zSize];
	}

	@Override
	public Object get(int x, int y, int z) {
		return getEntity(new Vector3(x,y,z));
	}
}
