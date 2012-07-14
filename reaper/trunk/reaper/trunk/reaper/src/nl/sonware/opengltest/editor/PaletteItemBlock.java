package nl.sonware.opengltest.editor;

import org.lwjgl.opengl.GL11;

import nl.sonware.opengltest.Point2;
import nl.sonware.opengltest.Textures;
import nl.sonware.opengltest.Vector3;
import nl.sonware.opengltest.blockmap.Block;
import nl.sonware.opengltest.blockmap.ChunkGrid;
import nl.sonware.opengltest.world.World;

public class PaletteItemBlock implements PaletteItem{
	Block b;
	public PaletteItemBlock(Block b) {
		this.b = b;
	}
	
	@Override
	public void paint(World w, Vector3 position, Vector3 rotation) {
		ChunkGrid g = w.getGrid();
		if (g.getBlock(position.getXI(), position.getYI(), position.getZI())==null) {
			g.setBlock(b, position.getXI(), position.getYI(), position.getZI());
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
		
		Textures.bindTexture(Textures.terrain);
		
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2d(leftCoords.getX(),	leftCoords.getY()); 	GL11.glVertex2f(-14,-8);
			GL11.glTexCoord2d(leftCoords.getX()+c, 	leftCoords.getY()); 	GL11.glVertex2f(0,0);
			GL11.glTexCoord2d(leftCoords.getX()+c, 	leftCoords.getY()+c); 	GL11.glVertex2f(0,16);
			GL11.glTexCoord2d(leftCoords.getX(), 	leftCoords.getY()+c); 	GL11.glVertex2f(-14,8);
		GL11.glEnd();
		
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2d(rightCoords.getX(), 	rightCoords.getY()); 	GL11.glVertex2f(0,0);
			GL11.glTexCoord2d(rightCoords.getX(), 	rightCoords.getY()+c); 	GL11.glVertex2f(0,16);
			GL11.glTexCoord2d(rightCoords.getX()+c, rightCoords.getY()+c); 	GL11.glVertex2f(14,8);
			GL11.glTexCoord2d(rightCoords.getX()+c,	rightCoords.getY()); 	GL11.glVertex2f(14,-8);
		GL11.glEnd();
		
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2d(topCoords.getX(), 	topCoords.getY()); 		GL11.glVertex2f(-14,-8);
			GL11.glTexCoord2d(topCoords.getX(), 	topCoords.getY()+c); 	GL11.glVertex2f(0,-14);
			GL11.glTexCoord2d(topCoords.getX()+c, 	topCoords.getY()+c); 	GL11.glVertex2f(14,-8);
			GL11.glTexCoord2d(topCoords.getX()+c,	topCoords.getY()); 		GL11.glVertex2f(0,0);
		GL11.glEnd();
	}
}