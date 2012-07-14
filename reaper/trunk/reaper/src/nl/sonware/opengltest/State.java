package nl.sonware.opengltest;

public interface State {
	
	public void update(float delta);
	public void render();
	public void renderOverlay();
}
