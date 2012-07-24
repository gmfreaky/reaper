package nl.sonware.opengltest.blockmap;

import java.util.ArrayList;
import java.util.Collections;

import nl.sonware.opengltest.Vector3;
import nl.sonware.opengltest.blockmap.blocks.Block;
import nl.sonware.opengltest.blockmap.blocks.Block.Face;
import nl.sonware.opengltest.collision.CollisionData;

public class GridIterator {
	Grid grid;
	ArrayList<CollisionData> list = new ArrayList<CollisionData>();
	public GridIterator(Grid grid, Vector3 startPosition, Vector3 endPosition) {
		this(grid,startPosition,endPosition,endPosition.sub(startPosition).getLength());
	}
	public GridIterator(Grid grid, Vector3 startPosition, Vector3 endPosition, double range) {
		this.grid = grid;
		
		Vector3 vec = endPosition.sub(startPosition);
		Vector3 stepVec = new Vector3(vec);
		
		stepVec.normalize();
			
		float stepsize = 0.005f;
		
		Vector3 ray = new Vector3(startPosition);
		
		for(float i=0;i<range;i+=stepsize) {
			
			Vector3 step = stepVec.mul(new Vector3(stepsize,stepsize,stepsize));
						
			Object o = grid.get(ray.getXI(),ray.getYI(),ray.getZI());
			if (o!=null) {
				double xx = (ray.getX()%1);
				double yy = (ray.getY()%1);
				double zz = (ray.getZ()%1);
				
				Face face = Block.Face.TOP;
				
				double leftDif = 	xx;
				double rightDif = 	1-xx;
				double frontDif = 	yy;
				double rearDif = 	1-yy;
				double bottomDif = 	zz;
				double topDif = 	1-zz;
				
				if (leftDif		<rightDif&& leftDif<frontDif 	&& leftDif<rearDif 		&& leftDif<bottomDif 	&& leftDif<topDif) 		face = Block.Face.LEFT;
				if (rightDif	<leftDif &&	rightDif<frontDif 	&& rightDif<rearDif 	&& rightDif<bottomDif 	&& rightDif<topDif)	 	face = Block.Face.RIGHT;
				if (frontDif	<leftDif && frontDif<rightDif 	&& frontDif<rearDif 	&& frontDif<bottomDif 	&& frontDif<topDif) 	face = Block.Face.FRONT;
				if (rearDif		<leftDif && rearDif<rightDif 	&& rearDif<frontDif 	&& rearDif<bottomDif 	&& rearDif<topDif)		face = Block.Face.REAR;
				if (bottomDif	<leftDif && bottomDif<rightDif 	&& bottomDif<frontDif 	&& bottomDif<rearDif 	&& bottomDif<topDif) 	face = Block.Face.BOTTOM;
				if (topDif		<leftDif && topDif<rightDif 	&& topDif<frontDif 		&& topDif<rearDif 		&& topDif<bottomDif) 	face = Block.Face.TOP;
				
				list.add(new CollisionData(o, face, ray.getXI(),ray.getYI(),ray.getZI()));
				return;
			}
			
			ray = ray.add(step); // step through ray
			
			/*boolean skippedGrid = ((ray.getXI()-lastX)!=0 || (ray.getYI()-lastY)!=0 || (ray.getZI()-lastZ)!=0);  
			
			lastX = ray.getXI();
			lastY = ray.getYI();
			lastZ = ray.getZI();*/
		}
	}
	
	public ArrayList<CollisionData> getList() {
		return list;
	}
	
	public ArrayList<CollisionData> getReversedList() {
		@SuppressWarnings("unchecked")
		ArrayList<CollisionData> o = (ArrayList<CollisionData>) list.clone();
		Collections.reverse(o);
		return o;
	}
}
