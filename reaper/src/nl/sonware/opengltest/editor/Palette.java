package nl.sonware.opengltest.editor;

import nl.sonware.opengltest.Textures;
import nl.sonware.opengltest.gui.Gui;
import nl.sonware.opengltest.gui.GuiElement;
import nl.sonware.opengltest.util.DrawUtils;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class Palette extends GuiElement{
	
	float itemWidth,itemHeight;
	float itemScale;
	int xItems=8,yItems=8;
	
	int selectX,selectY;
	PaletteItem selectedItem;
	int selectedItemX=0,selectedItemY=yItems;
	
	PaletteItem[][] itemList;
	
	public Palette(Gui gui, float x, float y, float width, float height) {
		super(gui, x, y);
		setSize(width,height);
		itemScale = ((width+height)/2)/500;
		
		itemList = new PaletteItem[xItems][yItems];
	}
	
	public boolean addItem(PaletteItem i) {
		for(int yy=yItems-1;yy>=0;yy--)
		for(int xx=0;xx<xItems;xx++) {
			if (itemList[xx][yy]==null) {
				itemList[xx][yy] = i;
				return true;
			}
		}
		return false;
	}
	
	public void update(float delta) {
		super.update(delta);
		float mX = Mouse.getX()-x;
		float mY = Mouse.getY()-y;
		
		selectX = (int) Math.floor(mX/itemWidth);
		selectY = (int) Math.floor(mY/itemHeight);
	}
	
	public boolean inPalette(int x, int y) {
		return (x>=0 && x<xItems && y>=0 && y<yItems);
	}
	
	public void selectBlock() {
		if (inPalette(selectX,selectY)) {
			if (itemList[selectX][selectY]!=null) {
				selectedItem = itemList[selectX][selectY];
				selectedItemX = selectX;
				selectedItemY = selectY;
			}
		}
	}
	
	public void setSize(float width,float height) {
		this.xSize = width;
		this.ySize = height;
		itemWidth = width/xItems;
		itemHeight = height/yItems;
	}
	
	public PaletteItem getSelectedItem() {
		return selectedItem;
	}
	
	public void render() {
		super.render();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL11.glColor4f(0,0,0,0.5f);
		DrawUtils.drawRectangle(x,y,xSize,ySize);
		
		for(int xx=0;xx<xItems;xx++)
		for(int yy=0;yy<yItems;yy++) {
			GL11.glPushMatrix();
			GL11.glTranslatef(x+(xx*itemWidth)+(itemWidth/2), (y+(yy*itemHeight)+(itemHeight/2)), 0);
			
			Textures.bindTexture(0);
			GL11.glColor4f(0,0,0,0.5f);
			if (selectX==xx && selectY==yy)
			{
				GL11.glColor4f(0.5f,0.5f,0.5f,0.5f);
			}
			if (selectedItemX==xx && selectedItemY==yy)
			{
				GL11.glColor4f(0.8f,0.9f,1f,0.6f);
			}
			DrawUtils.drawRectangle(-itemWidth/2f,-itemHeight/2f, itemWidth, itemHeight);
			
			if (itemList[xx][yy]!=null) {
				GL11.glScalef(itemScale, itemScale, itemScale);
				itemList[xx][yy].render();
			}
			GL11.glPopMatrix();
		}
		
				
	}
}
