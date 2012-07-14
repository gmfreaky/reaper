package nl.sonware.opengltest;

public class Point2 implements Cloneable{
	double x,y;
	
	public Point2(float x, float y) {
		setX(x);
		setY(y);
	}
	public Point2(double x, double y) {
		setX(x);
		setY(y);
	}

	public float getXF() {
		return (float)x;
	}

	public float getYF() {
		return (float)y;
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	@Override
	public Point2 clone() {
		return new Point2(x,y);
	}
	
	@Override
	public String toString() {
		return "Point2("+getX()+","+getY()+")";
	}
}
