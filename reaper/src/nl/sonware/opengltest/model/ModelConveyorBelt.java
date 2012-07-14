package nl.sonware.opengltest.model;

import nl.sonware.opengltest.Point2;
import nl.sonware.opengltest.Textures;
import nl.sonware.opengltest.Vector3;


public class ModelConveyorBelt extends BoxModel {

	public ModelConveyorBelt() {
		int textureBase = Textures.textureConveyorBeltBase;
		int textureTop = Textures.textureConveyorBeltTop;
		
		addBox("base", new Box(new Vector3(1,1,0.99), textureBase, new Point2(0,0), new Point2(1,1)));
		addBox("top", new Box(new Vector3(1,1,0.01), textureTop, 
				new Point2(0,0), new Point2(1,0.5f),
				new Point2(0,0), new Point2(1,0.5f),
				new Point2(0,0.5f), new Point2(1,0.75f),
				new Point2(0,0.5f), new Point2(1,0.75f),
				new Point2(0,0.5f), new Point2(1,0.75f),
				new Point2(0,0.5f), new Point2(1,0.75f))
				);
		
		getBox("base").setPosition(new Vector3(0,0,0));
		getBox("top").setPosition(new Vector3(0,0,0.5));
	}

}
