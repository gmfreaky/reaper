package nl.sonware.opengltest.shader;

import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class ShaderProgram {
	public int id;
	VertexShader vertShader;
	FragmentShader fragShader;
	public ShaderProgram(VertexShader vertShader, FragmentShader fragShader) {
		this.vertShader = vertShader;
		this.fragShader = fragShader;
		id = GL20.glCreateProgram();
		vertShader.attach(id);
		fragShader.attach(id);
		
		GL20.glLinkProgram(id);
		if (ARBShaderObjects.glGetObjectParameteriARB(id, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
			System.out.println("Error linking program "+this);
			System.out.println("Error log\n_________");
			System.out.println(GL20.glGetProgramInfoLog(id, 1024));
		}
		GL20.glValidateProgram(id);
		if (ARBShaderObjects.glGetObjectParameteriARB(id, GL20.GL_VALIDATE_STATUS) == GL11.GL_FALSE) {
			System.out.println("Error validating program "+this);
		}
	}
	
	public void bind() {
		GL20.glUseProgram(id);
	}
	
	@Override
	public String toString() {
		return "ShaderProgram ("+id+"): "+vertShader+" ,"+fragShader;
	}
}
