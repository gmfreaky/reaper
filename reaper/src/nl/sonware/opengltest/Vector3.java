package nl.sonware.opengltest;

public class Vector3 {
	public Point3 point = new Point3(0,0,0);
	
	public Vector3() {}
	
	public Vector3(double x, double y, double z) {
		set(x,y,z);
	}
	public Vector3(float x, float y, float z) {
		setF(x,y,z);
	}
	public Vector3(Vector3 vec) {
		set(vec.getX(),vec.getY(),vec.getZ());
	}
	
	public double getX() {
		return point.getX();
	}
	public double getY() {
		return point.getY();
	}
	public double getZ() {
		return point.getZ();
	}
	
	public float getXF() {
		return (float)point.getX();
	}
	public float getYF() {
		return (float)point.getY();
	}
	public float getZF() {
		return (float)point.getZ();
	}
	
	public int getXI() {
		return (int) Math.floor(getX());
	}
	public int getYI() {
		return (int) Math.floor(getY());
	}
	public int getZI() {
		return (int) Math.floor(getZ());
	}
	
	public void set(int dimension, double value) {
		switch(dimension) {
			case 0: setX(value); break;
			case 1: setY(value); break;
			case 2: setZ(value); break;
		}
	}
	
	public void set(double x, double y, double z) {
		setX(x);
		setY(y);
		setZ(z);
	}
	public void setF(float x, float y, float z) {
		setXF(x);
		setYF(y);
		setZF(z);
	}
	
	public void setX(double x) {
		point.setX(x);
	}
	public void setY(double y) {
		point.setY(y);
	}
	public void setZ(double z) {
		point.setZ(z);
	}
	
	public void setXF(float x) {
		point.setXF(x);
	}
	public void setYF(float y) {
		point.setYF(y);
	}
	public void setZF(float z) {
		point.setZF(z);
	}
	
	public void normalize() {
		double l = getLength();
		point.x/=l;
		point.y/=l;
		point.z/=l;
	}
	
	public double getLength() {
		return (float) Math.sqrt(getLengthSq());
	}
	
	public double getLengthSq() {
		return (getX()*getX())+(getY()*getY())+(getZ()*getZ());
	}
	
	public Vector3 add(Vector3 v) {
		return new Vector3(getX()+v.getX(),getY()+v.getY(),getZ()+v.getZ());
	}
	public Vector3 sub(Vector3 v) {
		return new Vector3(getX()-v.getX(),getY()-v.getY(),getZ()-v.getZ());
	}
	public Vector3 mul(Vector3 v) {
		return new Vector3(getX()*v.getX(),getY()*v.getY(),getZ()*v.getZ());
	}
	public double dot(Vector3 v) {
		return (getX()*v.getX())+(getY()*v.getY())+(getZ()*v.getZ());
	}
	public float[] getArrayF() {
		return new float[]{getXF(),getYF(),getZF()};
	}
	public double[] getArray() {
		return new double[]{getX(),getY(),getZ()};
	}
	public Vector3 mul(double d) {
		return mul(new Vector3(d, d, d));
	}
	
	public String toString() {
		return "Vector3("+getX()+","+getY()+","+getZ()+")";
	}

	public Vector3 div(double division) {
		return this.mul(1/division);
	}
}
