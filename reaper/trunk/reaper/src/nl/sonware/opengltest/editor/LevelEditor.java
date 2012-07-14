package nl.sonware.opengltest.editor;

import java.util.ArrayList;

import nl.sonware.opengltest.Color;
import nl.sonware.opengltest.FlyCamera;
import nl.sonware.opengltest.KeyboardInterface;
import nl.sonware.opengltest.Main;
import nl.sonware.opengltest.MouseInterface;
import nl.sonware.opengltest.Textures;
import nl.sonware.opengltest.Vector3;
import nl.sonware.opengltest.blockmap.Block;
import nl.sonware.opengltest.blockmap.Block.Face;
import nl.sonware.opengltest.blockmap.GridIterator;
import nl.sonware.opengltest.collision.CollisionData;
import nl.sonware.opengltest.util.DrawUtils;
import nl.sonware.opengltest.util.DrawUtils.Cube;
import nl.sonware.opengltest.world.World;
import nl.sonware.opengltest.world.entity.EEntity;
import nl.sonware.opengltest.world.entity.Entity;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class LevelEditor extends World implements KeyboardInterface, MouseInterface{
	
	FlyCamera camera;
	PaletteItem brush = new PaletteItemBlock(Block.DIRT);
	CollisionData selectedBlock;
	Cube selectionCube = new Cube(true);
	
	int deleteTimer;
	int placeTimer;
	
	int reach = 100;
	
	Palette palette;
	boolean showPalette;
	
	public LevelEditor(int xSize, int ySize, int zSize) {
		super(xSize, ySize, zSize);
		
		Mouse.setGrabbed(true);
		
		float mX = getGrid().getxSize()/2f;
		float mY = getGrid().getySize()/2f;
		float mZ = getGrid().getzSize()/2f;
		
		camera = new FlyCamera();
		
		camera.setPos(new Vector3(0,0,mZ));
		camera.setRotation(new Vector3(mX,mY,0));
		
		for(int x=0;x<getGrid().getxSize();x++)
		for(int y=0;y<getGrid().getySize();y++)
		for(int z=0;z<getGrid().getzSize()/2;z++) {
			getGrid().setBlock(Block.WATER, x, y, z);
		}
		Main.renderDist = 100;
		
		float pWidth = Display.getWidth()/2;
		float pHeight = Display.getHeight()/2;
		
		palette = new Palette((Display.getWidth()/2)-(pWidth/2f),(Display.getHeight()/2)-(pHeight/2f),pWidth,pHeight);
		for(Block b:Block.values()) {
			palette.addItem(new PaletteItemBlock(b));
		}
		palette.addItem(new PaletteItemEntity(EEntity.ENTITY_CONVEYORBELT));
		
		Main.fogColor = new Color(0.6f,0.8f,0.9f);
	}
	
	public void update(float delta) {
		super.update(delta);
		camera.update(delta);
		
		deleteTimer-=delta;
		placeTimer-=delta;
		
		if (!showPalette) {
			GridIterator iterator = new GridIterator(getGrid(), new Vector3(camera.getX(), camera.getY(), camera.getZ()), new Vector3(camera.getXTo(), camera.getYTo(), camera.getZTo()), reach);
			ArrayList<CollisionData> collisionList = iterator.getList();
			if (collisionList.size()>0) {
				selectedBlock = collisionList.get(0);
			} else {
				selectedBlock = null;
			}
			
			ArrayList<Entity> entityCollisionList = getEntitiesRay(new Vector3(camera.getX(), camera.getY(), camera.getZ()), new Vector3(camera.getXTo(), camera.getYTo(), camera.getZTo()), reach, null);
			if (!entityCollisionList.isEmpty()) {
				Entity hit = entityCollisionList.get(0);
				double currentDist=0, newDist=0;
				if (selectedBlock!=null) {
					Vector3 currentSelection = new Vector3(selectedBlock.x+0.5, selectedBlock.y+0.5, selectedBlock.z+0.5);
					Vector3 newSelection = hit.getPosition();
					currentDist = currentSelection.sub(camera.getPosition()).getLengthSq();
					newDist = newSelection.sub(camera.getPosition()).getLengthSq();
				}
				if (selectedBlock==null || newDist<currentDist) // if object is closer than block
				selectedBlock = new CollisionData(hit,Face.TOP,hit.getPosition().getXI(),hit.getPosition().getYI(),hit.getPosition().getZI());
			}
			
			if (selectedBlock!=null) {
				if (placeTimer<=0 && Mouse.isButtonDown(1)) { // placing blocks
					if (selectedBlock.o!=null) {
						placeTimer = 160;
						Face f = selectedBlock.face;
						int xo = f.x;
						int yo = f.y;
						int zo = f.z;
						brush.paint(this, new Vector3(selectedBlock.x+xo,selectedBlock.y+yo,selectedBlock.z+zo),new Vector3(0,0,0));
					}
				}
				if (deleteTimer<=0 && Mouse.isButtonDown(0)) {
					if (selectedBlock.o!=null) {
						if (selectedBlock.o instanceof Block) {
							getGrid().setBlock(null, selectedBlock.x,selectedBlock.y,selectedBlock.z);
						}
						if (selectedBlock.o instanceof Entity) {
							((Entity) selectedBlock.o).remove();
						}
						deleteTimer=160;
					}
				}
			}
			if (!Mouse.isButtonDown(0)){
				deleteTimer=0;
			}
			if (!Mouse.isButtonDown(1)){
				placeTimer=0;
			}
		}
		if (showPalette) {
			palette.update();
		}
	}

	@Override
	public void onKeyboard() {
		if (Keyboard.getEventKeyState() && Keyboard.getEventKey()==Keyboard.KEY_E && !Keyboard.isRepeatEvent()) {
			if (!showPalette) {
				Mouse.setGrabbed(false);
				showPalette = true;
			} else {
				Mouse.setGrabbed(true);
				showPalette = false;
			}
		}
	}
	
	@Override
	public void onMouse() {
		if (Mouse.getEventButtonState()) {
			if (Mouse.getEventButton()==0) {
				if (showPalette) {
					palette.selectBlock();
					if (palette.getSelectedItem()!=null) {
						brush = palette.getSelectedItem();
					}
				}
			}
		}
	}
	
	public void render() {
		renderCamera = camera;
		GLU.gluLookAt((float)camera.getX(),(float)camera.getY(),(float)camera.getZ(),	(float)camera.getXTo(),(float)camera.getYTo(),(float)camera.getZTo(),	0, 0, 1); // Setup the cam
		super.render();
		
		if (selectedBlock!=null) {
			Textures.bindTexture(0);
			GL11.glColor4f(1,1,1,0.5f);
			
			GL11.glPushMatrix();
			
				GL11.glTranslatef(selectedBlock.x+0.5f, selectedBlock.y+0.5f, selectedBlock.z+0.5f);
				GL11.glScalef(1.01f, 1.01f, 1.01f);
				selectionCube.render();
			
			GL11.glPopMatrix();
			
			GL11.glColor3f(1, 1, 1);
		}
	}

	public void renderOverlay() {
		GL11.glDisable(GL11.GL_FOG);
		GL11.glColor3f(1, 1, 1);
		
		DrawUtils.drawSpriteCentered(Textures.crosshair, Main.width/2, Main.height/2, 16, 16);
		
		// editor gui
		if (showPalette) {
			palette.render();
		}
	}
}
