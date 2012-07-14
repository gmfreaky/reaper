package nl.sonware.opengltest.util;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class BufferUtils {
	
	public static IntBuffer wrapDirectInt(int[] array) {
		IntBuffer buffer = org.lwjgl.BufferUtils.createIntBuffer(array.length);
		buffer.put(array);
		buffer.flip();
		return buffer;
	}
	
	public static FloatBuffer wrapDirectFloat(float[] array) {
		FloatBuffer buffer = org.lwjgl.BufferUtils.createFloatBuffer(array.length);
		buffer.put(array);
		buffer.flip();
		return buffer;
	}

	public static DoubleBuffer wrapDirectDouble(double[] array) {
		DoubleBuffer buffer = org.lwjgl.BufferUtils.createDoubleBuffer(array.length);
		buffer.put(array);
		buffer.flip();
		return buffer;
	}
}
