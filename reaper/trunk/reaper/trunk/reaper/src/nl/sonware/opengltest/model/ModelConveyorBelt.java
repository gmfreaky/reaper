package nl.sonware.opengltest.model;

import nl.sonware.opengltest.Point2;
import nl.sonware.opengltest.Textures;
import nl.sonware.opengltest.Vector3;


public class ModelConveyorBelt extends BoxModel {

	public ModelConveyorBelt() {
		int textureBase = Textures.textureConveyorBeltBase;
		int textureTop = Textures.textureConveyorBeltTop;
		addBox("base", new Box(new Vector3(1,1,0.9), textureBase, new Point2(0,0), new Point2(1,1)));
		addBox("top", new Box(new Vector3(1,1,0.1), textureTop, new Point2(0,0), new Point2(1,0.5f)));
		getBox("base").setPosition(new Vector3(0,0,-0.05));
		getBox("top").setPosition(new Vector3(0,0,0.45));
	}

}
