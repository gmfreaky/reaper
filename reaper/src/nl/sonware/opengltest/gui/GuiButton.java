package nl.sonware.opengltest.gui;

public class GuiButton extends GuiElement{

	GuiButton(Gui gui, float x, float y) {
		super(gui, x, y);
	}
	
	@Override
	public void onClick(int button) {
		gui.onGuiEvent(this);
	}
}
