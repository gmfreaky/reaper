package nl.sonware.opengltest.gui;

import java.util.ArrayList;

import nl.sonware.opengltest.KeyboardInterface;
import nl.sonware.opengltest.MouseInterface;

public class Gui implements KeyboardInterface, MouseInterface, GuiEvent{
	
	GuiEvent handler;
	
	public ArrayList<GuiElement> guiElements = new ArrayList<GuiElement>();
	
	public Gui() {
	}
	public Gui(GuiEvent handler) {
		this.handler = handler;
	}
	
	public void addElement(GuiElement element) {
		guiElements.add(element);
	}
	
	public void removeElement(GuiElement element) {
		guiElements.remove(element);
	}
	
	public void update(float delta) {
		for(GuiElement e:guiElements) {
			e.update(delta);
		}
	}
	public void render() {
		for(GuiElement e:guiElements) {
			e.render();
		}
	}

	@Override
	public void onMouse() {
		for(GuiElement e:guiElements) {
			if (e instanceof MouseInterface)
			((MouseInterface) e).onMouse();
		}
	}

	@Override
	public void onKeyboard() {
		for(GuiElement e:guiElements) {
			if (e instanceof KeyboardInterface)
			((KeyboardInterface) e).onKeyboard();
		}
	}

	@Override
	public void onGuiEvent(GuiElement element) {
		
	}
}
