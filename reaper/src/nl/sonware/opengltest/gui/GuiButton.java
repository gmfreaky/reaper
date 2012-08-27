package nl.sonware.opengltest.gui;

import nl.sonware.opengltest.Color;
import nl.sonware.opengltest.Textures;
import nl.sonware.opengltest.util.DrawUtils;
import nl.sonware.opengltest.util.Text;

import org.lwjgl.opengl.GL11;

public class GuiButton extends GuiElement{

	String text;
	float alpha=0.5f;
	public GuiButton(Gui gui, float x, float y, int width, String text) {
		super(gui, x, y);
		this.text = text;
		this.xSize = width; 
		this.ySize = 24;
	}
	
	@Override
	public void onClick(int button) {
		gui.onGuiEvent(this);
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
		if (mouseHover) {
			alpha+=((1-alpha)/50f)*delta;
		} else {
			alpha+=((0.5f-alpha)/100f)*delta;
		}
	}
	
	@Override
	public void render() {
		Textures.bindTexture(0);
		float blue = alpha-0.5f;
		GL11.glColor4f(blue, blue*1.5f, blue*2, alpha);
		DrawUtils.drawRectangle(x, y, xSize, ySize);
		
		Text.render(text, (int)(x+4+(blue*8)), (int)y+4, 16);
	}
}
