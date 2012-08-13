package nl.sonware.opengltest;

import nl.sonware.opengltest.editor.LevelEditor;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class StateManager {
	public State s;
	
	StateManager() {
		setState(new LevelEditor(8,8,8));
	}
	
	public void setState(State s) {
		this.s = s;
	}
	
	public void update(float delta) {
		s.update(delta);
		while(Keyboard.next()) {
			if (s instanceof KeyboardInterface) {
				((KeyboardInterface) s).onKeyboard();
			}
		}
		while(Mouse.next()) {
			if (s instanceof MouseInterface) {
				((MouseInterface) s).onMouse();
			}
		}
	}
	
	public void render() {
		if (s!=null)
		s.render();
	}
	public void renderOverlay() {
		if (s!=null)
		s.renderOverlay();
	}
}
