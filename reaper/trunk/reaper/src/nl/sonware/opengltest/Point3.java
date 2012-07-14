package nl.sonware.opengltest;

public class Point3 {
	double x,y,z;
	public Point3 (float x, float y, float z) {
		setX(x);
		setY(y);
		setZ(z);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public float getXF() {
		return (float)x;
	}

	public float getYF() {
		return (float)y;
	}

	public float getZF() {
		return (float)z;
	}

	public void setXF(float x) {
		this.x = x;
	}

	public void setYF(float y) {
		this.y = y;
	}

	public void setZF(float z) {
		this.z = z;
	}
	
	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setZ(double z) {
		this.z = z;
	}
	
	@Override
	public String toString() {
		return "Point3("+getX()+","+getY()+","+getZ()+")";
	}
}
