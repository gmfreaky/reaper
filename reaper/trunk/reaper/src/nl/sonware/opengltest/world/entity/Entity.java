package nl.sonware.opengltest.world.entity;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import nl.sonware.opengltest.Vector3;
import nl.sonware.opengltest.collision.BoundingBox;
import nl.sonware.opengltest.model.Model;
import nl.sonware.opengltest.world.World;

public abstract class Entity {
	World w;
	Vector3 position;
	Vector3 velocity;
	Vector3 rotation;
	Vector3 rotationVelocity;
	BoundingBox boundingBox;
	Model model;
	boolean isStatic;
	boolean isRemoved;
	
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
		boundingBox.setPosition(position); // update boundingbox with current position
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
		boundingBox.setPosition(position); // update boundingbox with current position
		
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
			GL11.glRotated(rotation.getZ(), 1, 0, 0);
			GL11.glRotated(rotation.getY(), 1, 0, 0);
			GL11.glRotated(rotation.getX(), 1, 0, 0);
		}
		GL11.glColor4f(1,1,1,1);
		model.render();
		GL11.glPopMatrix();
		getBoundingBox().render();
	}
}
