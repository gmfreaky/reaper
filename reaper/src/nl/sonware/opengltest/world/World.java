package nl.sonware.opengltest.world;

import java.util.ArrayList;
import java.util.Collections;

import nl.sonware.opengltest.Camera;
import nl.sonware.opengltest.Color;
import nl.sonware.opengltest.KeyboardInterface;
import nl.sonware.opengltest.Main;
import nl.sonware.opengltest.State;
import nl.sonware.opengltest.Textures;
import nl.sonware.opengltest.Vector3;
import nl.sonware.opengltest.blockmap.ChunkGrid;
import nl.sonware.opengltest.collision.AABB;
import nl.sonware.opengltest.collision.BoundingBox;
import nl.sonware.opengltest.world.entity.Entity;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Sphere;

public class World implements State, KeyboardInterface{

	ChunkGrid grid;
	ArrayList<Entity> entityList;
	boolean simulatePhysics = true;
	public Camera renderCamera;
	public float timer;
	public float lastTick;
	boolean debugging;
	Thread tickThread;
	Sphere sun = new Sphere();
	
	public World(int xSize, int ySize, int zSize) {
		entityList = new ArrayList<Entity>();
		grid = new ChunkGrid(this, xSize,ySize,zSize);
		
		tickThread = new Thread(new TickTask(this));
		tickThread.start();
		tickThread.setPriority(Thread.MAX_PRIORITY);
		
		Main.fogColor = new Color(0.5f,0.7f,1f);
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
			if (a!=b && b.isSolid()) {
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
	
	public void enableSimulatePhysics(boolean enable) {
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
		GL11.glDisable(GL11.GL_FOG);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		// skybox
		GL11.glColor3f(1, 1, 1);

		Textures.bindTexture(0);
		Textures.bindTexture(Textures.terrain);
		GL11.glEnable(GL11.GL_FOG);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
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
	
	public boolean isPlaceFree(Vector3 position) {
		Vector3 rposition = new Vector3(position.getXI(),position.getYI(),position.getZI());
		rposition = rposition.add(new Vector3(0.5,0.5,0.5));
		return (getEntityAtPosition(rposition,null)==null && getGrid().get(position.getXI(), position.getYI(), position.getZI())==null);
	}

	public Entity getEntityAtPosition(Vector3 position, Entity ignore) {
		for(Entity e:getEntityList()) {
			if (e!=ignore && e!=null) {
				if (e.getBoundingBox()!=null && e.getBoundingBox().collidesPoint(position)) {
					return e;
				}
			}
		}
		return null;
	}

	public boolean isDebugging() {
		return debugging;
	}
	public void enableDebugging(boolean debugging) {
		this.debugging = debugging;
		System.out.println("Debug mode "+(debugging ? "activated" : "deactivated"));
	}

	@Override
	public void onKeyboard() {
		if (!Keyboard.isRepeatEvent() && Keyboard.getEventKeyState() && Keyboard.getEventKey() == Keyboard.KEY_F1) {
			this.enableDebugging(!isDebugging());
		}
	}

}
