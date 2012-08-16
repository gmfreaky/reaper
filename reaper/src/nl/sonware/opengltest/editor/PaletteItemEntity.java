package nl.sonware.opengltest.editor;

import nl.sonware.opengltest.Vector3;
import nl.sonware.opengltest.world.World;
import nl.sonware.opengltest.world.entity.EEntity;
import nl.sonware.opengltest.world.entity.Entity;

import org.lwjgl.opengl.GL11;

public class PaletteItemEntity implements PaletteItem {

	EEntity e;
	
	PaletteItemEntity(EEntity e) {
		this.e = e;
	}
	
	@Override
	public void paint(World w, Vector3 position, Vector3 rotation) {
		Entity ne = EEntity.newInstance(e, w, position, rotation);
		if (ne!=null) {
			if (ne.getBoundingBox()!=null) {
				ne.setPosition(ne.getPosition().add(ne.getBoundingBox().getAABB().getSize().mul(0.5)));
			}
			if (w.isPlaceFree(position)) {
				rotation.setX(0);
				rotation.setY(0);
				rotation.setZ(180-Math.round(Math.toDegrees(rotation.getZ())/90)*90);
				ne.setRotation(rotation);
				w.addEntity(ne);
			}
		}
	}

	@Override
	public void render() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glColor4f(1, 1, 1, 1);
		GL11.glPushMatrix();
		GL11.glRotated(60, 1, 0, 0);
		GL11.glRotated(45*3, 0, 0, 1);
		GL11.glRotated(180, 0, 1, 0);
		GL11.glScaled(-22, 22, 22);
			e.model.render();
		GL11.glPopMatrix();
		GL11.glDisable(GL11.GL_DEPTH_TEST);
	}

}
