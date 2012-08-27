package nl.sonware.opengltest.gui;

import nl.sonware.opengltest.MouseInterface;

import org.lwjgl.input.Mouse;

public class GuiElement implements MouseInterface{
	Gui gui;
	public float x,y;
	public float xSize,ySize;
	boolean mouseHover;
	
	public GuiElement(Gui gui, float x, float y) {
		this.gui = gui;
		this.x = x;
		this.y = y;
	}
	public void update(float delta){
		mouseHover = (Mouse.getX()>=x && Mouse.getX()<x+xSize && Mouse.getY()>=y && Mouse.getY()<y+ySize);
	}
	
	public void onMouse() {
		if (mouseHover) {
			if (Mouse.getEventButtonState()) {
				onClick(Mouse.getEventButton());
			}
		}
	}
	
	public void onClick(int button) {
		
	}
	
	public void render(){}
}
