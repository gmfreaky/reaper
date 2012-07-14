package nl.sonware.opengltest.world;

import java.util.Comparator;

import nl.sonware.opengltest.Vector3;
import nl.sonware.opengltest.world.entity.Entity;

public class DistComparator implements Comparator<Entity> {

	Vector3 origin;
	public DistComparator(Vector3 origin) {
		this.origin = origin;
	}
	@Override
	public int compare(Entity e1, Entity e2) {
		double dist1 = e1.getPosition().sub(origin).getLengthSq();
		double dist2 = e2.getPosition().sub(origin).getLengthSq();
		if (dist1==dist2) {
			return 0;
		}
		return (dist1>dist2) ? 1 : -1;
	}

}
