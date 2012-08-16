package nl.sonware.opengltest.editor;

import nl.sonware.opengltest.Point2;
import nl.sonware.opengltest.Textures;
import nl.sonware.opengltest.Vector3;
import nl.sonware.opengltest.blockmap.ChunkGrid;
import nl.sonware.opengltest.blockmap.blocks.Block;
import nl.sonware.opengltest.blockmap.blocks.BlockList;
import nl.sonware.opengltest.model.Box;
import nl.sonware.opengltest.model.Model;
import nl.sonware.opengltest.world.World;

import org.lwjgl.opengl.GL11;

public class PaletteItemBlock implements PaletteItem{
	Block b;
	Box m;
	
	public PaletteItemBlock(BlockList bl) {
		this.b = bl.newInstance(null, null);
		m = new Box(new Vector3(1,1,1), Textures.terrain);
		
		Point2 c = new Point2(1d/16d,1d/16d);
		
		m.textureCoordinatesBottom1 = b.texBottom.mul(c);
		m.textureCoordinatesBottom2 = b.texBottom.mul(c).add(c);
		
		m.textureCoordinatesTop1 = b.texTop.mul(c);
		m.textureCoordinatesTop2 = b.texTop.mul(c).add(c);
		
		m.textureCoordinatesLeft1 = b.texLeft.mul(c);
		m.textureCoordinatesLeft2 = b.texLeft.mul(c).add(c);
		
		m.textureCoordinatesRight1 = b.texRight.mul(c);
		m.textureCoordinatesRight2 = b.texRight.mul(c).add(c);
		
		m.textureCoordinatesFront1 = b.texFront.mul(c);
		m.textureCoordinatesFront2 = b.texFront.mul(c).add(c);
		
		m.textureCoordinatesRear1 = b.texRear.mul(c);
		m.textureCoordinatesRear2 = b.texRear.mul(c).add(c);
	}
	
	@Override
	public void paint(World w, Vector3 position, Vector3 rotation) {
		ChunkGrid g = w.getGrid();
		if (w.isPlaceFree(position)) {
			g.set(b.getType(), position.getXI(), position.getYI(), position.getZI());
		}
	}

	@Override
	public void render() {
		GL11.glColor4f(1,1,1,1);
		Point2 leftCoords 	= 	b.texLeft.clone();
		Point2 rightCoords 	= 	b.texRight.clone();
		Point2 topCoords 	=	b.texTop.clone();
		
		double c = 1/16f;
		
		leftCoords.setX	(leftCoords.getX()	*c);
		leftCoords.setY	(leftCoords.getY()	*c);
		rightCoords.setX(rightCoords.getX()	*c);
		rightCoords.setY(rightCoords.getY()	*c);
		topCoords.setX	(topCoords.getX()	*c);
		topCoords.setY	(topCoords.getY()	*c);
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glColor4f(1, 1, 1, 1);
		GL11.glPushMatrix();
		GL11.glRotated(60, 1, 0, 0);
		GL11.glRotated(-45, 0, 0, 1);
		GL11.glRotated(180, 0, 1, 0);
		GL11.glScaled(22, 22, 22);
			m.render();
		GL11.glPopMatrix();
		GL11.glDisable(GL11.GL_DEPTH_TEST);
	}
}