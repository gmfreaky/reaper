package nl.sonware.opengltest.world.entity;

import nl.sonware.opengltest.model.Model;
import nl.sonware.opengltest.model.ModelConveyorBelt;

public enum EEntity {
	ENTITY_CONVEYORBELT(EntityConveyorBelt.class, new ModelConveyorBelt()),
	;
	
	public Class<? extends Entity> entity;
	public Model model;
	
	EEntity(Class<? extends Entity> entity, Model model) {
		this.entity = entity;
		this.model = model;
	}
}
