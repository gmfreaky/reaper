package nl.sonware.opengltest.model;

import nl.sonware.opengltest.Point2;
import nl.sonware.opengltest.Textures;
import nl.sonware.opengltest.Vector3;

public class ModelSpawnpoint extends BoxModel{
	public ModelSpawnpoint() {
		addBox("base", new Box(new Vector3(0.5f,0.5f,0.5f), Textures.spawnpoint, new Point2(0,0), new Point2(1,1)));
	}
}
