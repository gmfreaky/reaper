package nl.sonware.opengltest;

import nl.sonware.opengltest.util.BufferUtils;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class Main {
	
	static boolean finished = false;
	public static float width = 800;
	public static float height = 600;
	static boolean fullscreen = false;
	
	public static float renderDist = 100;
	public static Color fogColor = new Color(0.4f,0.65f,1f,1);
	
	static long lastTime = System.currentTimeMillis();
	static long lastFpsTime = System.currentTimeMillis();
	static short frameCount;
	static short fps;
	static StateManager manager;
	
	public static void main(String[] args) throws LWJGLException {
		try {
			if (fullscreen){
				Display.setDisplayMode(Display.getDesktopDisplayMode());
				width = Display.getDesktopDisplayMode().getWidth();
				height = Display.getDesktopDisplayMode().getHeight();
				Display.setFullscreen(true);
			} else {
				Display.setDisplayMode(new DisplayMode((int)width, (int)height));
			}
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		init();
		
		while(!finished) {
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
		
		manager = new StateManager();
	}
	
	public static void render(float delta) {
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			finished = true;
		}
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		
		GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_LINEAR);
		GL11.glFogf(GL11.GL_FOG_START, renderDist/2);
		GL11.glFogf(GL11.GL_FOG_END, renderDist);
		GL11.glFog(GL11.GL_FOG_COLOR, BufferUtils.wrapDirectFloat(fogColor.getArray()));
		
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(fogColor.getRed(), fogColor.getGreen(), fogColor.getBlue(), fogColor.getAlpha());
		
		// Draw perspective
		
		GL11.glLoadIdentity();
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluPerspective(60, width/height, 0.1f, renderDist);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		// Render the scene
		manager.render();
		manager.update(Math.min(delta,200));
		
		// Draw orthographic (2d)
		
		GL11.glLoadIdentity();
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, height, 0, 100,-100);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		
		// Render overlay
		manager.renderOverlay();
		
		Display.update();
	}
	
}
