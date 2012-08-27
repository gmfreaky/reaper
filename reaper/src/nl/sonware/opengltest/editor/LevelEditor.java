package nl.sonware.opengltest.editor;

import java.util.ArrayList;

import nl.sonware.opengltest.FlyCamera;
import nl.sonware.opengltest.KeyboardInterface;
import nl.sonware.opengltest.Main;
import nl.sonware.opengltest.MouseInterface;
import nl.sonware.opengltest.Textures;
import nl.sonware.opengltest.Vector3;
import nl.sonware.opengltest.blockmap.GridIterator;
import nl.sonware.opengltest.blockmap.blocks.Block;
import nl.sonware.opengltest.blockmap.blocks.Block.Face;
import nl.sonware.opengltest.blockmap.blocks.BlockList;
import nl.sonware.opengltest.collision.CollisionData;
import nl.sonware.opengltest.gui.Gui;
import nl.sonware.opengltest.gui.GuiButton;
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
	PaletteItem brush = new PaletteItemBlock(BlockList.DIRT);
	CollisionData selectedBlock;
	Cube selectionCube = new Cube(true);
	
	int deleteTimer;
	int placeTimer;
	
	int reach = 100;
	
	Gui gui;
	Palette palette;
	boolean showGui;
	
	public LevelEditor(int xSize, int ySize, int zSize) {
		super(xSize, ySize, zSize);
		
		Mouse.setGrabbed(true);
		
		float mX = getGrid().getxSize()/2f;
		float mY = getGrid().getySize()/2f;
		float mZ = getGrid().getzSize()/2f;
		
		camera = new FlyCamera();
		
		camera.setPosition(new Vector3(0,0,mZ+4));
		camera.setRotation(new Vector3(mX,mY,mZ));
		
		for(int x=0;x<getGrid().getxSize();x++)
		for(int y=0;y<getGrid().getySize();y++)	{
			getGrid().set(BlockList.STONE, x, y, 0);
		}
		
		float pWidth = Display.getWidth()/2;
		float pHeight = Display.getHeight()/2;

		enableSimulatePhysics(false);
		
		gui = new Gui();
		gui.addElement("button_new", new GuiButton(gui, 0,Display.getHeight()-64-(24*0),128, "New"));
		gui.addElement("button_load", new GuiButton(gui, 0,Display.getHeight()-64-(24*1),128, "Load"));
		gui.addElement("button_save", new GuiButton(gui, 0,Display.getHeight()-64-(24*2),128, "Save"));
		gui.addElement("button_saveas", new GuiButton(gui, 0,Display.getHeight()-64-(24*3),128, "Save as"));
		gui.addElement("button_properties", new GuiButton(gui, 0,(24*0),128, "Properties"));
		gui.addElement("button_publish", new GuiButton(gui, 0,(24*1),128, "Publish..."));
		
		gui.addElement("button_menu", new GuiButton(gui, Display.getWidth()-128,(24*0),128, "Main menu"));
		
		palette = new Palette(gui, (Display.getWidth()/2)-(pWidth/2f),(Display.getHeight()/2)-(pHeight/2f),pWidth,pHeight);
		gui.addElement("palette", palette);
		
		for(BlockList b:BlockList.values()) {
			if (b.getType()!=null)
			palette.addItem(new PaletteItemBlock(b));
		}
		for (EEntity e:EEntity.values()) {
			palette.addItem(new PaletteItemEntity(e));
		}
	}
	
	public void update(float delta) {
		super.update(delta);
		camera.update(delta);
		
		if (showGui) {
			gui.update(delta);
		}
		
		deleteTimer-=delta;
		placeTimer-=delta;
		
		if (!showGui) {
			
			if (selectedBlock!=null) {
				if (placeTimer<=0 && Mouse.isButtonDown(1)) { // placing blocks
					if (selectedBlock.o!=null) {
						placeTimer = 150;
						Face f = selectedBlock.face;
						int xo = f.x;
						int yo = f.y;
						int zo = f.z;
						brush.paint(this, new Vector3(selectedBlock.x+xo,selectedBlock.y+yo,selectedBlock.z+zo),camera.getRotation());
					}
				}
				if (deleteTimer<=0 && Mouse.isButtonDown(0)) {
					if (selectedBlock.o!=null) {
						if (selectedBlock.o instanceof Block) {
							getGrid().set(BlockList.AIR, selectedBlock.x,selectedBlock.y,selectedBlock.z);
						}
						if (selectedBlock.o instanceof Entity) {
							((Entity) selectedBlock.o).remove();
						}
						deleteTimer=150;
					}
				}
			}
			if (!Mouse.isButtonDown(0)){
				deleteTimer=0;
			}
			if (!Mouse.isButtonDown(1)){
				placeTimer=0;
			}
			
			GridIterator iterator = new GridIterator(getGrid(), camera.getPosition(), camera.getLookat(), reach);
			ArrayList<CollisionData> collisionList = iterator.getList();
			if (collisionList.size()>0) {
				selectedBlock = collisionList.get(0);
			} else {
				selectedBlock = null;
			}
			
			ArrayList<Entity> entityCollisionList = getEntitiesRay(camera.getPosition(),camera.getLookat(), reach, null);
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
		}
	}

	@Override
	public void onKeyboard() {
		super.onKeyboard();
		if (Keyboard.getEventKeyState() && !Keyboard.isRepeatEvent()) {
			if (Keyboard.getEventKey()==Keyboard.KEY_E) {
				if (!showGui) {
					Mouse.setGrabbed(false);
					showGui = true;
				} else {
					Mouse.setGrabbed(true);
					showGui = false;
				}
			}
		}
		
		
	}
	
	@Override
	public void onMouse() {
		if (Mouse.getEventButtonState()) {
			if (Mouse.getEventButton()==0) {
				if (showGui) {
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
		GLU.gluLookAt(
				camera.getPosition().getXF(),camera.getPosition().getYF(),camera.getPosition().getZF(),
				camera.getLookat().getXF(), camera.getLookat().getYF(), camera.getLookat().getZF(),
				0, 0, 1); // Setup the cam
		super.render();
		
		if (selectedBlock!=null) {
			if (selectedBlock.o instanceof Block) {
				Textures.bindTexture(0);
				GL11.glColor4f(1,1,1,0.2f);
				
				GL11.glPushMatrix();
				
					GL11.glTranslatef(selectedBlock.x+0.5f, selectedBlock.y+0.5f, selectedBlock.z+0.5f);
					GL11.glScalef(1.01f, 1.01f, 1.01f);
					selectionCube.render();
				
				GL11.glPopMatrix();
				
				GL11.glColor3f(1, 1, 1);
			} else if (selectedBlock.o instanceof Entity) {
				GL11.glColor4f(0.9f,1,0.9f,0.2f);
				Entity e = (Entity) selectedBlock.o;
				if (e.getBoundingBox()!=null) {e.getBoundingBox().render();}
			}
		}
	}

	public void renderOverlay() {
		GL11.glDisable(GL11.GL_FOG);
		GL11.glColor3f(1, 1, 1);
		
		DrawUtils.drawSpriteCentered(Textures.crosshair, Main.width/2, Main.height/2, 16, 16);
		
		// editor gui
		if (showGui) {
			gui.render();
		}
	}
}
