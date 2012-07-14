package nl.sonware.opengltest.world.entity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import nl.sonware.opengltest.Vector3;
import nl.sonware.opengltest.model.Model;
import nl.sonware.opengltest.model.ModelConveyorBelt;
import nl.sonware.opengltest.model.ModelSpawnpoint;
import nl.sonware.opengltest.world.World;

public enum EEntity {
	ENTITY_CONVEYORBELT(EntityConveyorBelt.class, new ModelConveyorBelt()),
	ENTITY_SPAWNPOINT(EntitySpawnpoint.class, new ModelSpawnpoint()),
	;
	
	public Class<? extends Entity> entity;
	public Model model;
	
	EEntity(Class<? extends Entity> entity, Model model) {
		this.entity = entity;
		this.model = model;
	}

	public static Entity newInstance(EEntity et, World w, Vector3 position, Vector3 rotation) {
		try {
		Constructor<? extends Entity> constructor = et.entity.getDeclaredConstructor(World.class, Vector3.class);
			Entity ne = constructor.newInstance(w, position);
			return ne;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}
}
