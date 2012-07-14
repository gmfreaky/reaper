package nl.sonware.opengltest;

public class PolygonData {
	public Point3 vertexData;
	public Point2 texCoordData;
	
	public PolygonData(Point3 vertexData, Point2 texCoordData) {
		this.vertexData = vertexData;
		this.texCoordData = texCoordData;
	}
}
