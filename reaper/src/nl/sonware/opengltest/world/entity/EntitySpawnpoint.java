package nl.sonware.opengltest.world.entity;

import nl.sonware.opengltest.Vector3;
import nl.sonware.opengltest.collision.AABB;
import nl.sonware.opengltest.model.ModelSpawnpoint;
import nl.sonware.opengltest.world.World;

public class EntitySpawnpoint extends Entity {

	public EntitySpawnpoint(World w, Vector3 position) {
		super(w, position);
		model = new ModelSpawnpoint();
		boundingBox = new AABB(new Vector3(1,1,1));
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
		rotation.setZ(w.getTimer()/10);
	}

}
