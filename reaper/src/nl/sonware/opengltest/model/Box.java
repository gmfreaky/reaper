package nl.sonware.opengltest.model;

import org.lwjgl.opengl.GL11;

import nl.sonware.opengltest.Point2;
import nl.sonware.opengltest.Textures;
import nl.sonware.opengltest.Vector3;

public class Box implements Model{

	Vector3 size, position, offset, rotation;
	public Point2
	textureCoordinatesBottom1, 	textureCoordinatesBottom2,
	textureCoordinatesTop1, 	textureCoordinatesTop2,
	textureCoordinatesLeft1, 	textureCoordinatesLeft2,
	textureCoordinatesRight1, 	textureCoordinatesRight2,
	textureCoordinatesFront1, 	textureCoordinatesFront2,
	textureCoordinatesRear1, 	textureCoordinatesRear2;
	int texture;
	
	public Box(Vector3 size, int texture, Point2 textureCoordinates1, Point2 textureCoordinates2) {
		this(size, texture, textureCoordinates1, textureCoordinates2, textureCoordinates1, textureCoordinates2, textureCoordinates1, textureCoordinates2, textureCoordinates1, textureCoordinates2, textureCoordinates1, textureCoordinates2, textureCoordinates1, textureCoordinates2);
	}
	
	public Box(Vector3 size, int texture) {
		this(size, texture, new Point2(0,0), new Point2(1,1));
	}
	
	public Box(Vector3 size, int texture, Point2 textureCoordinatesBottom1,
			Point2 textureCoordinatesBottom2, Point2 textureCoordinatesTop1,
			Point2 textureCoordinatesTop2, Point2 textureCoordinatesLeft1,
			Point2 textureCoordinatesLeft2, Point2 textureCoordinatesRight1,
			Point2 textureCoordinatesRight2, Point2 textureCoordinatesFront1,
			Point2 textureCoordinatesFront2, Point2 textureCoordinatesRear1,
			Point2 textureCoordinatesRear2) {
		super();
		this.size = size;
		this.texture = texture;
		this.textureCoordinatesBottom1 = textureCoordinatesBottom1;
		this.textureCoordinatesBottom2 = textureCoordinatesBottom2;
		this.textureCoordinatesTop1 = textureCoordinatesTop1;
		this.textureCoordinatesTop2 = textureCoordinatesTop2;
		this.textureCoordinatesLeft1 = textureCoordinatesLeft1;
		this.textureCoordinatesLeft2 = textureCoordinatesLeft2;
		this.textureCoordinatesRight1 = textureCoordinatesRight1;
		this.textureCoordinatesRight2 = textureCoordinatesRight2;
		this.textureCoordinatesFront1 = textureCoordinatesFront1;
		this.textureCoordinatesFront2 = textureCoordinatesFront2;
		this.textureCoordinatesRear1 = textureCoordinatesRear1;
		this.textureCoordinatesRear2 = textureCoordinatesRear2;
	}
	
	public void setPosition(Vector3 position) {
		this.position = position;
	}
	
	public void setOffset(Vector3 offset) {
		this.offset = offset;
	}
	
	public void setRotation(Vector3 rotation) {
		this.rotation = rotation;
	}
	

	@Override
	public void render() {
		Textures.bindTexture(texture);
		GL11.glPushMatrix();
			if (position!=null) {
				GL11.glTranslated(position.getX(), position.getY(), position.getZ());
			}
			if (rotation!=null) {
				GL11.glRotated(rotation.getZ(), 1, 0, 0);
				GL11.glRotated(rotation.getY(), 0, 1, 0);
				GL11.glRotated(rotation.getX(), 0, 0, 1);
			}
			if (offset!=null) {
				GL11.glTranslated(offset.getX(),offset.getY(),offset.getZ());
			}
			
			GL11.glBegin(GL11.GL_QUADS);
			
				// Bottom
				GL11.glTexCoord2d(textureCoordinatesBottom2.getX(), textureCoordinatesBottom1.getY()); GL11.glVertex3d( size.getX()/2, -size.getY()/2, -size.getZ()/2);
				GL11.glTexCoord2d(textureCoordinatesBottom1.getX(), textureCoordinatesBottom1.getY()); GL11.glVertex3d(-size.getX()/2, -size.getY()/2, -size.getZ()/2);
				GL11.glTexCoord2d(textureCoordinatesBottom1.getX(), textureCoordinatesBottom2.getY()); GL11.glVertex3d(-size.getX()/2,  size.getY()/2, -size.getZ()/2);
				GL11.glTexCoord2d(textureCoordinatesBottom2.getX(), textureCoordinatesBottom2.getY()); GL11.glVertex3d( size.getX()/2,  size.getY()/2, -size.getZ()/2);
				
				// Top
				GL11.glTexCoord2d(textureCoordinatesTop2.getX(), textureCoordinatesTop1.getY()); GL11.glVertex3d(-size.getX()/2, -size.getY()/2, size.getZ()/2);
				GL11.glTexCoord2d(textureCoordinatesTop1.getX(), textureCoordinatesTop1.getY()); GL11.glVertex3d(size.getX()/2, 	-size.getY()/2, size.getZ()/2);
				GL11.glTexCoord2d(textureCoordinatesTop1.getX(), textureCoordinatesTop2.getY()); GL11.glVertex3d(size.getX()/2, 	 size.getY()/2, size.getZ()/2);
				GL11.glTexCoord2d(textureCoordinatesTop2.getX(), textureCoordinatesTop2.getY()); GL11.glVertex3d(-size.getX()/2,  size.getY()/2, size.getZ()/2);
				
				// Left
				GL11.glTexCoord2d(textureCoordinatesLeft1.getX(), textureCoordinatesLeft2.getY()); GL11.glVertex3d(-size.getX()/2,  size.getY()/2, -size.getZ()/2);
				GL11.glTexCoord2d(textureCoordinatesLeft2.getX(), textureCoordinatesLeft2.getY()); GL11.glVertex3d(-size.getX()/2, -size.getY()/2, -size.getZ()/2);
				GL11.glTexCoord2d(textureCoordinatesLeft2.getX(), textureCoordinatesLeft1.getY()); GL11.glVertex3d(-size.getX()/2, -size.getY()/2,  size.getZ()/2);
				GL11.glTexCoord2d(textureCoordinatesLeft1.getX(), textureCoordinatesLeft1.getY()); GL11.glVertex3d(-size.getX()/2,  size.getY()/2,  size.getZ()/2);
				
				// Right
				GL11.glTexCoord2d(textureCoordinatesRight1.getX(), textureCoordinatesRight2.getY()); GL11.glVertex3d(size.getX()/2, -size.getY()/2, -size.getZ()/2);
				GL11.glTexCoord2d(textureCoordinatesRight2.getX(), textureCoordinatesRight2.getY()); GL11.glVertex3d(size.getX()/2,  size.getY()/2, -size.getZ()/2);
				GL11.glTexCoord2d(textureCoordinatesRight2.getX(), textureCoordinatesRight1.getY()); GL11.glVertex3d(size.getX()/2,  size.getY()/2,  size.getZ()/2);
				GL11.glTexCoord2d(textureCoordinatesRight1.getX(), textureCoordinatesRight1.getY()); GL11.glVertex3d(size.getX()/2, -size.getY()/2,  size.getZ()/2);
				
				// Front
				GL11.glTexCoord2d(textureCoordinatesFront2.getX(), textureCoordinatesFront2.getY()); GL11.glVertex3d(-size.getX()/2,  size.getY()/2, -size.getZ()/2);
				GL11.glTexCoord2d(textureCoordinatesFront2.getX(), textureCoordinatesFront1.getY()); GL11.glVertex3d(-size.getX()/2,  size.getY()/2,  size.getZ()/2);
				GL11.glTexCoord2d(textureCoordinatesFront1.getX(), textureCoordinatesFront1.getY()); GL11.glVertex3d( size.getX()/2,  size.getY()/2,  size.getZ()/2);
				GL11.glTexCoord2d(textureCoordinatesFront1.getX(), textureCoordinatesFront2.getY()); GL11.glVertex3d( size.getX()/2,  size.getY()/2, -size.getZ()/2);
				
				// Rear
				GL11.glTexCoord2d(textureCoordinatesRear1.getX(), textureCoordinatesRear1.getY()); GL11.glVertex3d(-size.getX()/2,  -size.getY()/2,  size.getZ()/2);
				GL11.glTexCoord2d(textureCoordinatesRear1.getX(), textureCoordinatesRear2.getY()); GL11.glVertex3d(-size.getX()/2,  -size.getY()/2, -size.getZ()/2);
				GL11.glTexCoord2d(textureCoordinatesRear2.getX(), textureCoordinatesRear2.getY()); GL11.glVertex3d( size.getX()/2,  -size.getY()/2, -size.getZ()/2);
				GL11.glTexCoord2d(textureCoordinatesRear2.getX(), textureCoordinatesRear1.getY()); GL11.glVertex3d( size.getX()/2,  -size.getY()/2,  size.getZ()/2);
			
			GL11.glEnd();
		GL11.glPopMatrix();
	}

}
