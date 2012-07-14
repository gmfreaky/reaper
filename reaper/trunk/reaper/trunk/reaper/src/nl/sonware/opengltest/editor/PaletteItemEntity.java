package nl.sonware.opengltest.editor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import nl.sonware.opengltest.Vector3;
import nl.sonware.opengltest.world.World;
import nl.sonware.opengltest.world.entity.Entity;
import nl.sonware.opengltest.world.entity.EEntity;

import org.lwjgl.opengl.GL11;

public class PaletteItemEntity implements PaletteItem {

	EEntity e;
	
	PaletteItemEntity(EEntity e) {
		this.e = e;
	}
	
	@Override
	public void paint(World w, Vector3 position, Vector3 rotation) {
		try {
			//if (w.getEntityAtPosition()) {
				
			//}
			Constructor<? extends Entity> constructor = e.entity.getDeclaredConstructor(World.class, Vector3.class);
			position = position.add(new Vector3(0.5f,0.5f,0.5f));
			Entity ne = constructor.newInstance(w, position);
			w.addEntity(ne);
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
	}

	@Override
	public void render() {
		GL11.glColor4f(1, 1, 1, 1);
		GL11.glPushMatrix();
		GL11.glRotated(60, 1, 0, 0);
		GL11.glRotated(45, 0, 0, 1);
		GL11.glScaled(20, 20, 20);
			e.model.render();
		GL11.glPopMatrix();
	}

}
