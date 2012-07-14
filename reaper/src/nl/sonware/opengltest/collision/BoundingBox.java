package nl.sonware.opengltest.collision;

import nl.sonware.opengltest.Vector3;

public class BoundingBox {
	Vector3 position;
	public boolean collides(BoundingBox other) {
		return false;
	}
	public boolean collidesPoint(Vector3 point) {
		return false;
	}
	public Vector3 getPosition() { 
		return position;
	}
	public void setPosition(Vector3 position) {
		this.position = position;
	}
	public AABB getAABB(){return new AABB(new Vector3(0,0,0));}
	public void render(){}
}
