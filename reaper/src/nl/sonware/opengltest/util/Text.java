package nl.sonware.opengltest.util;

import org.lwjgl.opengl.GL11;

import nl.sonware.opengltest.Color;
import nl.sonware.opengltest.Textures;

public class Text {
	
	public static final int texture_font = Textures.loadTexture("res/font.png");
	public static final int size = 16; // 16 characters width/height
	public static final int spacing = -4;
	public static final int shadowDist = 2;
	
	public static void render(String text, int x, int y, int px) {
		render(text, x, y, px, new Color(1,1,1));
	}
	public static void render(String text, int x, int y, int px, Color color) {
		Textures.bindTexture(texture_font);
		float c = 1f/size;
		
		for(int i=0;i<text.length();i++) {
			String ch = text.substring(i,i+1);
			if (ch!=" " && ch!=null) {
				
				int cx=0;
				int cy=0;
				
				int charcode = ch.charAt(0);
				
				if (charcode>32) {
					if (charcode>=65 && charcode<=90) { // Capital letters
						cx=(charcode-65)%size;
						cy=(int) ((charcode-65)/size);
					}
					if (charcode>=97 && charcode<=122) { // Capital letters
						cx=(charcode-97)%size;
						cy=2+(int) ((charcode-97)/size);
					}
					if (charcode>=48 && charcode<=57) { // Digits
						cx=(charcode-48)%size;
						cy=4+(int) ((charcode-48)/size);
					}
					if (charcode>=33 && charcode<=47) { // Special characters
						cx=(charcode-33);
						cy=5;
					}
					if (charcode>=58 && charcode<=64) { // Special characters
						cx=(charcode-58);
						cy=6;
					}
					if (charcode>=91 && charcode<=96) { // Special characters
						cx=(charcode-91);
						cy=7;
					}
					if (charcode>=123 && charcode<=126) { // Special characters
						cx=(charcode-123);
						cy=8;
					}
					
					int offset = i*(px+spacing);
					
					for(int s=1;s>=0;s--) {
						if (s==1)
						GL11.glColor4f(0,0,0,color.getAlpha());
						else
						GL11.glColor4f(color.getRed(),color.getGreen(),color.getBlue(),color.getAlpha());
						
						int sh = s*shadowDist;
						
						GL11.glBegin(GL11.GL_QUADS);
							GL11.glTexCoord2f(cx*c, 	cy*c+c);	GL11.glVertex2f(x+offset+sh, 	y-sh);
							GL11.glTexCoord2f(cx*c+c, 	cy*c+c);	GL11.glVertex2f(x+px+offset+sh, y-sh);
							GL11.glTexCoord2f(cx*c+c, 	cy*c);		GL11.glVertex2f(x+px+offset+sh, y+px-sh);
							GL11.glTexCoord2f(cx*c, 	cy*c);		GL11.glVertex2f(x+offset+sh, 	y+px-sh);
						GL11.glEnd();
					}
				
				}
			}
		}
	}
}
