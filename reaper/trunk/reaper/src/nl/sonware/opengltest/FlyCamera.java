package nl.sonware.opengltest;

import nl.sonware.opengltest.util.MathUtils;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class FlyCamera implements Camera{

	double x=-1,y=-1,z=1;
	double xTo, yTo, zTo;
	double pitch,yaw;
	double xSpeed,ySpeed,zSpeed;
	double speed = 1/10000f;
	double friction = 1/100f;
	
	public void update(float delta) {

		Vector3f moveVec = new Vector3f(0,0,0);
		
		if (Mouse.isGrabbed()) {
			if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
				moveVec.x+=(Math.sin(yaw)*Math.cos(pitch));
				moveVec.y+=(Math.cos(yaw)*Math.cos(pitch));
				moveVec.z+=Math.sin(pitch);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
				moveVec.x-=(Math.sin(yaw)*Math.cos(pitch));
				moveVec.y-=(Math.cos(yaw)*Math.cos(pitch));
				moveVec.z-=Math.sin(pitch);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_A)) {			
				moveVec.x+=(	Math.sin(yaw-Math.PI/2)	);
				moveVec.y+=(	Math.cos(yaw-Math.PI/2)	);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_D)) {			
				moveVec.x+=(	Math.sin(yaw+Math.PI/2)	);
				moveVec.y+=(	Math.cos(yaw+Math.PI/2)	);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
				moveVec.z += 1;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
				moveVec.z -= 1;
			}
		}
		
		if (moveVec.length()!=0) {
			moveVec.normalise();
		}
		
		xSpeed+=moveVec.x*speed*delta;
		ySpeed+=moveVec.y*speed*delta;
		zSpeed+=moveVec.z*speed*delta;
		
		x+=xSpeed*delta;
		y+=ySpeed*delta;
		z+=zSpeed*delta;
		
		xTo = x + Math.sin(yaw);
		yTo = y + Math.cos(yaw);
		zTo = z + Math.tan(pitch);
		
		xSpeed/=1+(friction*delta);
		ySpeed/=1+(friction*delta);
		zSpeed/=1+(friction*delta);
		
		if (Mouse.isGrabbed()) {
			yaw += (Mouse.getDX())/500f;
			pitch += (Mouse.getDY())/500f;
		}
		
		pitch = Math.max(Math.min(pitch,Math.PI/2-0.001f),-Math.PI/2+0.001f);
	}
	
	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public double getZ() {
		return z;
	}

	@Override
	public double getXTo() {
		return xTo;
	}

	@Override
	public double getYTo() {
		return yTo;
	}

	@Override
	public double getZTo() {
		return zTo;
	}
	
	public void setPos(Vector3 pos) {
		this.x = pos.getX();
		this.y = pos.getY();
		this.z = pos.getZ();
	}
	
	public void setRotation(Vector3 to) {
		this.yaw = (float) Math.atan2(to.getY()-getY(), to.getX()-getY());
		float fDist = MathUtils.dist((float)getX(), (float)getY(), 0, to.getXF(), to.getYF(), 0);
		this.pitch = (float) Math.atan2(to.getZ()-getZ(), fDist);
	}

	public void setRotation(Point2 rot) {
		this.yaw 	= rot.getX();
		this.pitch 	= rot.getY();
	}

	public Vector3 getPosition() {
		return new Vector3(x,y,z);
	}

}
