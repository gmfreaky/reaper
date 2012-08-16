package nl.sonware.opengltest.world.entity;

import nl.sonware.opengltest.Point2;
import nl.sonware.opengltest.Vector3;
import nl.sonware.opengltest.collision.AABB;
import nl.sonware.opengltest.model.BoxModel;
import nl.sonware.opengltest.model.ModelConveyorBelt;
import nl.sonware.opengltest.world.World;

public class EntityConveyorBelt extends Entity {

	float anim;
	
	public EntityConveyorBelt(World w, Vector3 position) {
		super(w, position);
		model = new ModelConveyorBelt();
		boundingBox = new AABB(new Vector3(1,1,1));
		boundingBox.setPosition(position);
	}
	
	public void update(float delta) {
		super.update(delta);
		if (model!=null) {
			float xCoord = 1-(w.getTimer()/1000)%0.5f;
			((BoxModel) model).getBox("top").textureCoordinatesTop1 = new Point2(xCoord,0); 
			((BoxModel) model).getBox("top").textureCoordinatesTop2 = new Point2(xCoord+0.5f,0.5f);
		}
	}

}
