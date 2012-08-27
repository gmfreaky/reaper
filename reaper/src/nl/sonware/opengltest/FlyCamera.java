package nl.sonware.opengltest;

import nl.sonware.opengltest.util.MathUtils;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class FlyCamera implements Camera{

	double pitch,yaw;
	
	Vector3 position	= new Vector3(0,0,0);
	Vector3 lookat		= new Vector3(0,0,0);
	Vector3 speedVec	= new Vector3(0,0,0);
	
	double speed = 1/10000f;
	double friction = 1/100f;
	
	public void update(float delta) {
		
		Vector3 moveVec = new Vector3(0,0,0);
		
		if (Mouse.isGrabbed()) {
			if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
				moveVec = moveVec.add(new Vector3(
						Math.sin(yaw)*Math.cos(pitch),
						Math.cos(yaw)*Math.cos(pitch),
						Math.sin(pitch))
						);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
				moveVec = moveVec.add(new Vector3(
						-Math.sin(yaw)*Math.cos(pitch),
						-Math.cos(yaw)*Math.cos(pitch),
						-Math.sin(pitch))
						);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
				moveVec = moveVec.add(new Vector3(
						Math.sin(yaw-Math.PI/2),
						Math.cos(yaw-Math.PI/2),
						0)
						);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_D)) {			
				moveVec = moveVec.add(new Vector3(
						Math.sin(yaw+Math.PI/2),
						Math.cos(yaw+Math.PI/2),
						0)
						);
			}
			
			if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
				moveVec.setZ(1);
			}
		
			if (moveVec.getLength()!=0) {
				moveVec.normalize();
			}
			
			if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
				moveVec = moveVec.mul(10);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
				moveVec = moveVec.div(4);
			}
		}
		
		
		speedVec = speedVec.add(moveVec.mul(speed).mul(delta));
		position = position.add(speedVec.mul(delta));
		
		lookat = new Vector3(position);
		lookat = lookat.add(new Vector3(Math.sin(yaw),Math.cos(yaw),Math.tan(pitch)));
		
		speedVec = speedVec.div(1+(friction*delta));
		
		if (Mouse.isGrabbed()) {
			yaw += ((Mouse.getDX())/600f);
			pitch += ((Mouse.getDY())/600f);
		}
		
		pitch = Math.max(Math.min(pitch,Math.PI/2-0.001f),-Math.PI/2+0.001f);
	}
	
	public void setPosition(Vector3 pos) {
		this.position = pos;
	}
	
	public Vector3 getPosition() {
		return position;
	}
	public Vector3 getLookat() {
		return lookat;
	}
	
	public void setRotation(Vector3 to) {
		this.yaw = (float) Math.atan2(to.getY()-position.getY(), position.getX()-position.getY());
		float fDist = MathUtils.dist((float)position.getX(), (float)position.getY(), 0, to.getXF(), to.getYF(), 0);
		this.pitch = (float) Math.atan2(to.getZ()-position.getZ(), fDist);
	}

	public void setRotation(Point2 rot) {
		this.yaw 	= rot.getX();
		this.pitch 	= rot.getY();
	}

	public Vector3 getRotation() {
		return new Vector3(0,pitch,yaw);
	}

}
