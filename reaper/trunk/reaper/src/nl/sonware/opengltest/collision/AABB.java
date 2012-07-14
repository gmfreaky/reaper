package nl.sonware.opengltest.collision;

import org.lwjgl.opengl.GL11;

import nl.sonware.opengltest.Vector3;
import nl.sonware.opengltest.model.Box;

public class AABB extends BoundingBox {
	Vector3 size;
	Box b;

	public AABB(Vector3 size) {
		setSize(size);
		b = new Box(size.mul(1.001), 0);
	}
	
	public Vector3 getSize() {
		return size;
	}
	public void setSize(Vector3 size) {
		this.size = size;
	}
	
	@Override
	public boolean collides(BoundingBox other) {
		
		if (other==null) {return false;}
		
		if (other instanceof AABB) {
			AABB o = (AABB) other;
			Vector3 diffVec = other.getPosition().sub(getPosition());
			if (diffVec.getX()>getSize().getX()/2+o.getSize().getX()/2) {return false;}
			if (diffVec.getY()>getSize().getY()/2+o.getSize().getY()/2) {return false;}
			if (diffVec.getZ()>getSize().getZ()/2+o.getSize().getZ()/2) {return false;}
			
			return true;
		}
		return false;
	}
	
	public Vector3 intersectsRay(Vector3 startVector, Vector3 endVector)
	{
		if (position==null){return null;}
		
		Vector3 dirVector = endVector.sub(startVector);
		dirVector.normalize();
		
		boolean inside = true;
		Side quadrant[] = new Side[3];
		int i;
		int whichPlane;
		double maxT[] = new double[3];
		double candidatePlane[] = new double[3];
		
		Vector3 minBox = getPosition().sub(getSize().mul(0.5));
		Vector3 maxBox = getPosition().add(getSize().mul(0.5));

		/* Find candidate planes; this loop can be avoided if
	   	rays cast all from the eye(assume perpsective view) */
		for (i=0; i<3; i++)
			if(startVector.getArray()[i] < minBox.getArray()[i]) {
				quadrant[i] = Side.LEFT;
				candidatePlane[i] = minBox.getArray()[i];
				inside = false;
			}else if (startVector.getArray()[i] > maxBox.getArray()[i]) {
				quadrant[i] = Side.RIGHT;
				candidatePlane[i] = maxBox.getArray()[i];
				inside = false;
			}else	{
				quadrant[i] = Side.MIDDLE;
			}

		/* Ray origin inside bounding box */
		if(inside)	{
			return startVector;
		}


		/* Calculate T distances to candidate planes */
		for (i = 0; i < 3; i++)
			if (quadrant[i] != Side.MIDDLE && dirVector.getArray()[i]!=0)
				maxT[i] = (candidatePlane[i]-startVector.getArray()[i]) / dirVector.getArray()[i];
			else
				maxT[i] = -1.;

		/* Get largest of the maxT's for final choice of intersection */
		whichPlane = 0;
		for (i = 1; i < 3; i++)
			if (maxT[whichPlane] < maxT[i])
				whichPlane = i;

		Vector3 result = new Vector3();
		
		/* Check final candidate actually inside box */
		if (maxT[whichPlane] < 0.) return null;
		for (i = 0; i < 3; i++)
			if (whichPlane != i) {
				result.set(i,startVector.getArray()[i] + maxT[whichPlane] * dirVector.getArray()[i]);
				if (result.getArray()[i] < minBox.getArray()[i] || result.getArray()[i] > maxBox.getArray()[i])
					return null;
			} else {
				result.set(i,candidatePlane[i]);
			}
		return result;				/* ray hits box */
	}
	
	public void render() {
		GL11.glPushMatrix();
		if (getPosition()!=null)
		GL11.glTranslated(getPosition().getX(), getPosition().getY(), getPosition().getZ());
		GL11.glColor4f(0,0,1,0.2f);
		b.render();
		GL11.glPopMatrix();
	}
}
