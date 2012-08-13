package nl.sonware.opengltest.shader;

import java.io.BufferedReader;
import java.io.FileReader;

import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class FragmentShader{

	public int id;
	public String filename;
	public FragmentShader(String filename) {
		this.filename = filename;
		int fragShader = GL20.glCreateShader(ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
		String fragCode = "";
		String line;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			while ((line = reader.readLine()) != null) {
				fragCode += line + "\n";
			}
			reader.close();
		} catch (Exception e) {
			System.out.println("Failed reading fragment shading code");
		}
		GL20.glShaderSource(fragShader, fragCode);
		GL20.glCompileShader(fragShader);
		if (ARBShaderObjects.glGetObjectParameteriARB(fragShader,
				GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.out.println("Error compiling fragment shader "+fragShader);
			fragShader = 0;
		}
		id = fragShader;
		System.out.println("Compiled "+this);
	}
	
	public void attach(int program) {
		GL20.glAttachShader(program,id);
	}
	
	@Override
	public String toString() {
		return "FragmentShader ("+id+") '"+filename+"'";
	}
}
