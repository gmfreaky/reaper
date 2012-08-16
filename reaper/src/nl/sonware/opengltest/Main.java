package nl.sonware.opengltest;

import static org.lwjgl.opengl.EXTFramebufferObject.GL_DEPTH_ATTACHMENT_EXT;
import static org.lwjgl.opengl.EXTFramebufferObject.GL_FRAMEBUFFER_EXT;
import static org.lwjgl.opengl.EXTFramebufferObject.GL_RENDERBUFFER_EXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glBindFramebufferEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glBindRenderbufferEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glFramebufferRenderbufferEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glGenRenderbuffersEXT;
import static org.lwjgl.opengl.EXTFramebufferObject.glRenderbufferStorageEXT;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import nl.sonware.opengltest.shader.Shaders;
import nl.sonware.opengltest.util.BufferUtils;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.glu.GLU;


public class Main {

	static boolean finished = false;
	public static float width = 1024;
	public static float height = 768;
	static boolean fullscreen = false;

	public static float renderDist = 100; // 100 meters renderdistance
	public static Color fogColor = new Color(0.4f, 0.65f, 1f, 1);

	static long lastTime = System.currentTimeMillis();
	static long lastFpsTime = System.currentTimeMillis();
	static short frameCount;
	static short fps;
	static StateManager manager;
	
	static int frameBuffer;
	static int depthRenderBuffer;
	static int renderTexture;
	static int renderVertexVBO;
	
	static int v_coordID;
	static int texID;
	static int timerID;
	
	static float time = 0;

	public static void main(String[] args) throws LWJGLException {
		try {
			if (fullscreen) {
				Display.setDisplayMode(Display.getDesktopDisplayMode());
				width = Display.getDesktopDisplayMode().getWidth();
				height = Display.getDesktopDisplayMode().getHeight();
				Display.setFullscreen(true);
			} else {
				Display.setDisplayMode(new DisplayMode((int) width,
						(int) height));
			}
			int samples = 0; // anti-aliasing
			if (samples > 1)
				Display.create(new PixelFormat(32, 0, 24, 8, samples));
			else
				Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		init();

		while (!finished) {
			float delta = System.currentTimeMillis() - lastTime;
			if (System.currentTimeMillis() - lastFpsTime >= 1000) {
				fps = frameCount;
				Display.setTitle("Fps: " + fps);
				frameCount = 0;
				lastFpsTime = System.currentTimeMillis();
			}
			lastTime = System.currentTimeMillis();
			render(delta);
			frameCount++;
		}

		Display.destroy();
		Mouse.setGrabbed(false); // release mouse
		System.exit(0);
	}

	public static void init() {
		// Enable OpenGL functions
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_FOG);

		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		// postprocessing
		initPostProcessing();
		
		manager = new StateManager();
	}

	public static void initPostProcessing() {
		boolean FBOEnabled = GLContext.getCapabilities().GL_EXT_framebuffer_object;
		if (!FBOEnabled) {
			System.out.println("FBO not available!");
			System.exit(-1);
		}
		
		// init fbo
		renderTexture = GL11.glGenTextures();
		frameBuffer = EXTFramebufferObject.glGenFramebuffersEXT();
		depthRenderBuffer = glGenRenderbuffersEXT();
		
		EXTFramebufferObject.glBindFramebufferEXT( EXTFramebufferObject.GL_FRAMEBUFFER_EXT, frameBuffer);
		
		// initalize color texture
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, renderTexture);									// Bind the colorbuffer texture
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR); // make it linear filterd
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, (int)width, (int)height, 0, GL11.GL_RGBA, GL11.GL_INT, (ByteBuffer) null);	// Create the texture data
		EXTFramebufferObject.glFramebufferTexture2DEXT( EXTFramebufferObject.GL_FRAMEBUFFER_EXT, EXTFramebufferObject.GL_COLOR_ATTACHMENT0_EXT,
				GL11.GL_TEXTURE_2D, renderTexture, 0);
		
		// initialize depth renderbuffer
		glBindRenderbufferEXT(GL_RENDERBUFFER_EXT, depthRenderBuffer);				// bind the depth renderbuffer
		glRenderbufferStorageEXT(GL_RENDERBUFFER_EXT, GL14.GL_DEPTH_COMPONENT24, (int)width, (int)height);	// get the data space for it
		glFramebufferRenderbufferEXT(GL_FRAMEBUFFER_EXT,GL_DEPTH_ATTACHMENT_EXT,GL_RENDERBUFFER_EXT, depthRenderBuffer); // bind it to the renderbuffer

		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);
		
		FloatBuffer vertexbuffer = BufferUtils.wrapDirectFloat(
				new float[] {
				-1,-1,
				1,-1,
				1,1,
				-1,1,
				});
		
		renderVertexVBO = ARBVertexBufferObject.glGenBuffersARB();
		ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB,renderVertexVBO);
		ARBVertexBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB,vertexbuffer,ARBVertexBufferObject.GL_STATIC_DRAW_ARB);
		ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB,0);
		
		// Initalize shaders
		
		v_coordID = GL20.glGetAttribLocation(Shaders.post_pass.id, "v_coord");
		texID = 	GL20.glGetUniformLocation(Shaders.post_pass.id, "fbo_texture");
		timerID = 	GL20.glGetUniformLocation(Shaders.post_pass.id, "time");
		
		System.out.println("v_coordID location = "+v_coordID);
		System.out.println("texID location = "+texID);
		System.out.println("timerID location = "+timerID);
		
		//ARBShaderObjects.glUniform1iARB(texID, renderTexture);
		
	}

	public static void render(float delta) {
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			finished = true;
		}
		
		GL11.glViewport( 0, 0, (int)width, (int)height); 	// render to a texture the size of the window
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);					// unlink textures because if we dont it all is gonna fail
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, frameBuffer);		// switch to rendering on our FBO
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_DEPTH_TEST);

		GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_LINEAR);
		GL11.glFogf(GL11.GL_FOG_START, 0);
		GL11.glFogf(GL11.GL_FOG_END, renderDist);
		GL11.glFog(GL11.GL_FOG_COLOR,
				BufferUtils.wrapDirectFloat(fogColor.getArray()));
		
		// Clear the screen with the right color (fog)
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(fogColor.getRed(), fogColor.getGreen(),
				fogColor.getBlue(), fogColor.getAlpha());

		// Draw perspective
		GL11.glLoadIdentity();
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluPerspective(60, width / height, 0.1f, renderDist);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		// Render the scene
		manager.render();
		
		glBindFramebufferEXT(GL_FRAMEBUFFER_EXT, 0);					// switch to rendering on the framebuffer
		
		manager.update(Math.min(delta, 200));
		time+=delta;

		// Draw orthographic
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		GL11.glLoadIdentity();
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, 0, height, 100, -100);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_DEPTH_TEST);

		GL11.glColor3f(1,1,1);
		renderSceneFromFBO();
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		
		// Render overlay
		manager.renderOverlay();

		Display.update();
	}
	
	public static void renderSceneFromFBO() {
		
		//Shaders.post_bloom.bind();
		Shaders.post_pass.bind();
		ARBShaderObjects.glUniform1fARB(timerID, time);
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, renderTexture);
		GL20.glUniform1i(texID, 0);
		
		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		
			ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB,renderVertexVBO);
			GL11.glVertexPointer(2,GL11.GL_FLOAT,0,0);
			
			GL20.glEnableVertexAttribArray(v_coordID);
			GL20.glVertexAttribPointer(v_coordID, // attribute
					2, // number of elements per vertex, here (x,y)
					GL11.GL_FLOAT, // the type of each element
					false, // take our values as-is
					0, // no extra data between each position
					0 // offset of first element
			);
			
			GL11.glDrawArrays(GL11.GL_QUADS, 0, 8);
			GL20.glDisableVertexAttribArray(v_coordID);
			
		GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
		
		// unbind ARB buffer
		ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB,0);

		
		Shaders.unbind();
	}

}
