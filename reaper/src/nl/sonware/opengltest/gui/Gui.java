package nl.sonware.opengltest.gui;

import java.util.HashMap;

import nl.sonware.opengltest.KeyboardInterface;
import nl.sonware.opengltest.MouseInterface;

public class Gui implements KeyboardInterface, MouseInterface, GuiEvent{
	
	GuiEvent handler;
	
	public HashMap<String, GuiElement> guiElements = new HashMap<String, GuiElement>();
	
	public Gui() {}
	public Gui(GuiEvent handler) {
		this.handler = handler;
	}
	
	public void addElement(String name, GuiElement element) {
		guiElements.put(name, element);
	}
	
	public void removeElement(String name) {
		guiElements.remove(name);
	}
	
	public GuiElement getElement(String name) {
		return guiElements.get(name);
	}
	
	public void update(float delta) {
		for(GuiElement e:guiElements.values()) {
			e.update(delta);
		}
	}
	public void render() {
		for(GuiElement e:guiElements.values()) {
			e.render();
		}
	}

	@Override
	public void onMouse() {
		for(GuiElement e:guiElements.values()) {
			if (e instanceof MouseInterface)
			((MouseInterface) e).onMouse();
		}
	}

	@Override
	public void onKeyboard() {
		for(GuiElement e:guiElements.values()) {
			if (e instanceof KeyboardInterface)
			((KeyboardInterface) e).onKeyboard();
		}
	}

	@Override
	public void onGuiEvent(GuiElement element) {
		
	}
}
