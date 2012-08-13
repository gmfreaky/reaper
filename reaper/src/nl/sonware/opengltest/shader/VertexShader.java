package nl.sonware.opengltest.shader;

import java.io.BufferedReader;
import java.io.FileReader;

import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class VertexShader{

	public int id;
	public String filename;
	public VertexShader(String filename) {

		this.filename = filename;
		int vertShader;
		vertShader = GL20.glCreateShader(ARBVertexShader.GL_VERTEX_SHADER_ARB);

		String vertexCode = "";
		String line;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			while ((line = reader.readLine()) != null) {
				vertexCode += line + "\n";
			}
			reader.close();
		} catch (Exception e) {
			System.out.println("Failed reading vertex shading code");
		}
		
		GL20.glShaderSource(vertShader, vertexCode);
		GL20.glCompileShader(vertShader);
		// if there was a problem compiling, reset vertShader to zero
		if (ARBShaderObjects.glGetObjectParameteriARB(vertShader,
				GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.out.println("Error compiling vertex shader "+vertShader);
			vertShader = 0;
		}
		id = vertShader;
		System.out.println("Compiled "+this);
	}
	
	public void attach(int program) {
		GL20.glAttachShader(program,id);
	}
	
	@Override
	public String toString() {
		return "VertexShader ("+id+") '"+filename+"'";
	}

}
