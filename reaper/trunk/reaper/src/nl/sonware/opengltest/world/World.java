package nl.sonware.opengltest.world;

import java.util.ArrayList;
import java.util.Collections;

import nl.sonware.opengltest.Camera;
import nl.sonware.opengltest.State;
import nl.sonware.opengltest.Textures;
import nl.sonware.opengltest.Vector3;
import nl.sonware.opengltest.blockmap.Chunk;
import nl.sonware.opengltest.blockmap.ChunkGrid;
import nl.sonware.opengltest.blockmap.Grid;
import nl.sonware.opengltest.collision.AABB;
import nl.sonware.opengltest.collision.BoundingBox;
import nl.sonware.opengltest.world.entity.Entity;
import nl.sonware.opengltest.world.entity.EntityGrid;

import org.lwjgl.opengl.GL11;

public class World implements State{

	ChunkGrid grid;
	ArrayList<Entity> entityList;
	boolean simulatePhysics = false;
	public Camera renderCamera;
	public float timer;
	
	public World(int xSize, int ySize, int zSize) {
		entityList = new ArrayList<Entity>();
		grid = new ChunkGrid(xSize,ySize,zSize);
	}
	
	public ChunkGrid getGrid() {
		return grid;
	}
	
	public ArrayList<Entity> getEntitiesRay(Vector3 startVector, Vector3 endVector, Entity ignore) {
		return getEntitiesRay(startVector, endVector, endVector.sub(startVector).getLength(), ignore);
	}
	public ArrayList<Entity> getEntitiesRay(Vector3 startVector, Vector3 endVector, double range, Entity ignore) {
		ArrayList<Entity> list = new ArrayList<Entity>();
		for(Entity e:getEntityList()) {
			if (e.getBoundingBox()!=null && e!=ignore) {
				if (e.getBoundingBox() instanceof AABB) {
					AABB aabb = (AABB) e.getBoundingBox();
					if (aabb.intersectsRay(startVector, endVector)!=null) {
						list.add(e);
					}
				}
			}
		}
		Collections.sort(list, new DistComparator(startVector));
		return list;
	}
	
	public ArrayList<Entity> getCollidingEntities(Entity a, Vector3 offset) {
		ArrayList<Entity> list = new ArrayList<Entity>();
		for(Entity b:entityList) {
			if (a!=b) {
				BoundingBox ba = a.getBoundingBox();
				if (ba!=null && b.getBoundingBox()!=null) { // check if both objects have a boundingbox
					ba.setPosition(ba.getPosition().add(new Vector3(offset)));
					if (ba.collides(b.getBoundingBox())) {
						list.add(b);
					}
				}
			}
		}
		return list;
	}
	
	public boolean isSimulatingPhysics() {
		return simulatePhysics;
	}
	
	public void setSimulatePhysics(boolean enable) {
		this.simulatePhysics = enable; 
	}
	
	@Override
	public void update(float delta) {
		if (entityList!=null) {
			for(Entity e:entityList) {
				e.update(delta);
			}
		}
		timer+=delta;
	}
	
	public void addEntity(Entity e) {
		if (entityList!=null)
		entityList.add(e);
	}
	
	public void removeEntity(Entity e) {
		if (entityList!=null)
		entityList.remove(e);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Entity> getEntityList() {
		return (ArrayList<Entity>) entityList.clone();
	}

	@Override
	public void render() {
		GL11.glEnable(GL11.GL_FOG);
		
		GL11.glColor3f(1, 1, 1);

		Textures.bindTexture(0);
		Textures.bindTexture(Textures.terrain);
				
		getGrid().render(renderCamera);
		if (entityList!=null) {
			for(Entity e:entityList) {
				e.render();
			}
		}
	}

	@Override
	public void renderOverlay() {
		
	}

	public float getTimer() {
		return timer;
	}

	public boolean getEntityAtPosition(Vector3 position) {
		return false;
	}

}
