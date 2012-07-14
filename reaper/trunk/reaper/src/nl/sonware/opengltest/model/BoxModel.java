package nl.sonware.opengltest.model;

import java.util.HashMap;

public class BoxModel implements Model {

	HashMap<String,Box> boxMap = new HashMap<String, Box>();
	
	public void addBox(String name, Box box) {
		boxMap.put(name, box);
	}
	public Box getBox(String name) {
		return boxMap.get(name);
	}
	
	@Override
	public void render() {
		for(Box b:boxMap.values()) {
			b.render();
		}
	}

}
