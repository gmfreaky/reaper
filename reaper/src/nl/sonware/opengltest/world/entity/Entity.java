package nl.sonware.opengltest.world.entity;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import nl.sonware.opengltest.Vector3;
import nl.sonware.opengltest.collision.BoundingBox;
import nl.sonware.opengltest.model.Model;
import nl.sonware.opengltest.world.World;

public abstract class Entity {
	World w;
	Vector3 position = new Vector3();
	Vector3 velocity = new Vector3();
	Vector3 rotation = new Vector3();
	Vector3 rotationVelocity = new Vector3();
	BoundingBox boundingBox;
	Model model;
	boolean isSolid = true;
	boolean isStatic = false;
	boolean isRemoved = false;
	
	public Entity(World w, Vector3 position) {
		this.w = w;
		this.position = position;
	}
	
	public BoundingBox getBoundingBox() {
		return boundingBox;
	}
	
	public Vector3 getPosition() {
		return position;
	}
	public Vector3 getRotation() {
		return rotation;
	}
	
	public void update(float delta) {
		if (getBoundingBox()!=null)
		getBoundingBox().setPosition(position); // update boundingbox with current position
		if (w.isSimulatingPhysics() && !isStatic()) {
			handlePhysics(delta);
		}
	}
	
	public boolean isStatic() {
		return isStatic;
	}
	
	public void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}
	
	public void handlePhysics(float delta) {
		if (getBoundingBox()!=null) {
			Vector3 xVector = new Vector3(velocity.getX()*delta,0,0);
			Vector3 yVector = new Vector3(0,velocity.getY()*delta,0);
			Vector3 zVector = new Vector3(0,0,velocity.getZ()*delta);
			
			ArrayList<Entity> xList = w.getCollidingEntities(this, xVector);
			if (xList.isEmpty()) {position.add(xVector);}
			ArrayList<Entity> yList = w.getCollidingEntities(this, yVector);
			if (yList.isEmpty()) {position.add(yVector);}
			ArrayList<Entity> zList = w.getCollidingEntities(this, zVector);
			if (zList.isEmpty()) {position.add(zVector);}
			
			boundingBox.setPosition(position); // update boundingbox with new position
			
			ArrayList<Entity> collidingList = new ArrayList<Entity>();
			collidingList.addAll(xList);
			collidingList.addAll(yList);
			collidingList.addAll(zList);
			ArrayList<Entity> doneList = new ArrayList<Entity>();
			
			for(Entity e:collidingList) {
				if(!doneList.contains(e)) {
					onCollide(e);
					doneList.add(e);
				} else {
					System.out.println("Doubles in entitylist.");
				}
			}
		}
	}
	
	public boolean isRemoved() {
		return isRemoved;
	}
	
	public void remove() {
		this.isRemoved = true;
		w.removeEntity(this);
	}
	
	public void onCollide(Entity e) {
		
	}
	
	public void render() {
		GL11.glPushMatrix();
		if (position!=null)
		GL11.glTranslated(position.getX(), position.getY(), position.getZ());
		if (rotation!=null) {
			GL11.glRotated(rotation.getZ(), 0, 0, 1);
			GL11.glRotated(rotation.getY(), 0, 1, 0);
			GL11.glRotated(rotation.getX(), 1, 0, 0);
		}
		GL11.glColor4f(1,1,1,1);
		model.render();
		GL11.glPopMatrix();
		if (w.isDebugging()) {
			GL11.glColor4f(0, 0.5f, 0, 0.5f);
			if (getBoundingBox()!=null)
			getBoundingBox().render();
		}
	}

	public void setPosition(Vector3 position) { // teleport entity to position
		this.position = position;
	}

	public boolean isSolid() {
		return isSolid;
	}

	public void setRotation(Vector3 rotation) {
		this.rotation = rotation;
	}
}
