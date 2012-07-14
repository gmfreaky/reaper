package nl.sonware.opengltest;

public class Vertex {
	Vector3 position;
	Color color;
	Point2 texCoords;
	
	public Vertex(Vector3 position) {
		this(position, new Color(), new Point2(0,0));
	}
	public Vertex(Vector3 position, Color color, Point2 texCoords) {
		this.position = position;
		this.color = color;
		this.texCoords = texCoords;
	}
	
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	
	public Vector3 getPosition() {
		return position;
	}
	public void setPosition(Vector3 position) {
		this.position = position;
	}
}
