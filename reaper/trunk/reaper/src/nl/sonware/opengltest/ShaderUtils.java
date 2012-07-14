package nl.sonware.opengltest;

import java.io.BufferedReader;
import java.io.FileReader;

import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.GL11;

public class ShaderUtils {
	public static int createVertShader(String filename) {

		int vertShader;
		vertShader = ARBShaderObjects.glCreateShaderObjectARB(ARBVertexShader.GL_VERTEX_SHADER_ARB);

		if (vertShader == 0) {
			return 0;
		}
		String vertexCode = "";
		String line;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			while ((line = reader.readLine()) != null) {
				vertexCode += line + "\n";
			}
		} catch (Exception e) {
			System.out.println("Failed reading vertex shading code");
			return 0;
		}
		
		ARBShaderObjects.glShaderSourceARB(vertShader, vertexCode);
		ARBShaderObjects.glCompileShaderARB(vertShader);
		// if there was a problem compiling, reset vertShader to zero
		if (ARBShaderObjects.glGetObjectParameteriARB(vertShader,
				ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL11.GL_FALSE) {
			System.out.println("Error compiling vertex shader "+vertShader);
			vertShader = 0;
		}
		return vertShader;
	}

	// same as per the vertex shader except for method syntax
	public static int createFragShader(String filename) {
		int fragShader = ARBShaderObjects.glCreateShaderObjectARB(ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
		if (fragShader == 0) {
			return 0;
		}
		String fragCode = "";
		String line;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			while ((line = reader.readLine()) != null) {
				fragCode += line + "\n";
			}
		} catch (Exception e) {
			System.out.println("Failed reading fragment shading code");
			return 0;
		}
		ARBShaderObjects.glShaderSourceARB(fragShader, fragCode);
		ARBShaderObjects.glCompileShaderARB(fragShader);
		if (ARBShaderObjects.glGetObjectParameteriARB(fragShader,
				ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL11.GL_FALSE) {
			System.out.println("Error compiling fragment shader "+fragShader);
			fragShader = 0;
		}
		return fragShader;
	}
}
