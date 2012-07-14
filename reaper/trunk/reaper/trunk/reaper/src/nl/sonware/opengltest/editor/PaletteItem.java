package nl.sonware.opengltest.editor;

import nl.sonware.opengltest.Vector3;
import nl.sonware.opengltest.world.World;

public interface PaletteItem{
	public void paint(World w, Vector3 position, Vector3 rotation);
	public void render();
}
