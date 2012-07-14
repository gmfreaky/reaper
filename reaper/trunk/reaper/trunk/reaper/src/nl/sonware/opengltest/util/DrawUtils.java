package nl.sonware.opengltest.util;

import nl.sonware.opengltest.Textures;

import org.lwjgl.opengl.ARBBufferObject;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.GL11;

public class DrawUtils {
	
	public static class Cube {
		
		int vertexBuffer;
		float[] vertexArray;
		
		public Cube(boolean centered) {
			vertexBuffer = ARBBufferObject.glGenBuffersARB();
			vertexArray = new float[] {
				0,1,0,
				1,1,0,
				1,0,0,
				0,0,0,
				
				0,0,1,
				1,0,1,
				1,1,1,
				0,1,1,
				
				0,0,0,
				0,0,1,
				0,1,1,
				0,1,0,
				
				1,1,0,
				1,1,1,
				1,0,1,
				1,0,0,
				
				0,0,0,
				1,0,0,
				1,0,1,
				0,0,1,
				
				0,1,1,
				1,1,1,
				1,1,0,
				0,1,0,
			};
			if (centered) {
				for(int i=0;i<vertexArray.length;i++) {
					vertexArray[i]-=0.5f;
				}
			}
			
			ARBBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, vertexBuffer); // bind vertexbuffer
			ARBBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, BufferUtils.wrapDirectFloat(vertexArray), ARBBufferObject.GL_STATIC_DRAW_ARB);
			ARBBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, 0); 			// unbind vertexbuffer
		}
		
		public void render() {
			ARBBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, vertexBuffer); // bind vertexbuffer
			GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);
			ARBBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, 0); 			// unbind vertexbuffer
			
			GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
				
				GL11.glDrawArrays(GL11.GL_QUADS, 0, 6*4);
			
			GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
		}
	}
	
	public static void drawSprite(int sprite, float x, float y, float width, float height) {
		
		Textures.bindTexture(sprite);
		
		GL11.glBegin(GL11.GL_QUADS);
		
			GL11.glTexCoord2f(0, 0); GL11.glVertex2f(x, y);
			GL11.glTexCoord2f(1, 0); GL11.glVertex2f(x+width, y);
			GL11.glTexCoord2f(1, 1); GL11.glVertex2f(x+width, y+height);
			GL11.glTexCoord2f(0, 1); GL11.glVertex2f(x, y+height);
		
		GL11.glEnd();
	}
	
	public static void drawSpriteCentered(int sprite, float x, float y, float width, float height) {
		drawSprite(sprite,x-width/2,y-height/2,width,height);
	}

	public static void drawRectangle(float x, float y, float width, float height) {
		GL11.glBegin(GL11.GL_QUADS);
		
			GL11.glVertex2f(x, y);
			GL11.glVertex2f(x+width, y);
			GL11.glVertex2f(x+width, y+height);
			GL11.glVertex2f(x, y+height);
		
		GL11.glEnd();
	}
}
