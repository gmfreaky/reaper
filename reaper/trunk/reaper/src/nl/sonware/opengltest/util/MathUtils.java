package nl.sonware.opengltest.util;

public class MathUtils {
	public static int modulo(int a, int b) {
		int result = a%b;
		if(result<0){result+=b;}
		return result;
	}
	
	public static float distSq(float x1, float y1, float z1, float x2, float y2, float z2) {
		float xdif = x2-x1;
		float ydif = y2-y1;
		float zdif = z2-z1;
		
		return ((xdif*xdif)+(ydif*ydif)+(zdif*zdif));
	}
	
	public static float dist(float x1, float y1, float z1, float x2, float y2, float z2) {
		return (float) Math.sqrt(distSq(x1,y1,z1,x2,y2,z2));
	}
}
