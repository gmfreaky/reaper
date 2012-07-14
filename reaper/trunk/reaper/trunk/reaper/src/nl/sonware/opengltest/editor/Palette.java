package nl.sonware.opengltest.editor;

import nl.sonware.opengltest.Textures;
import nl.sonware.opengltest.util.DrawUtils;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class Palette {
	
	float x,y;
	float width,height;
	float itemWidth,itemHeight;
	int xsize=8,ysize=8;
	
	int selectX,selectY;
	PaletteItem selectedItem;
	int selectedItemX,selectedItemY;
	
	PaletteItem[][] itemList;
	
	public Palette(float x, float y, float width, float height) {
		this.x=x;
		this.y=y;
		setSize(width,height);
		
		itemList = new PaletteItem[xsize][ysize];
	}
	
	public boolean addItem(PaletteItem i) {
		for(int yy=0;yy<ysize;yy++)
		for(int xx=0;xx<xsize;xx++) {
			if (itemList[xx][yy]==null) {
				itemList[xx][yy] = i;
				return true;
			}
		}
		return false;
	}
	
	public void update() {
		float mX = Mouse.getX()-x;
		float mY = (Display.getDisplayMode().getHeight()-Mouse.getY())-y;
		
		selectX = (int) Math.floor(mX/itemWidth);
		selectY = (int) Math.floor(mY/itemHeight);
	}
	
	public boolean inPalette(int x, int y) {
		return (x>=0 && x<xsize && y>=0 && y<ysize);
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
		this.width = width;
		this.height = height;
		itemWidth = width/xsize;
		itemHeight = height/ysize;
	}
	
	public PaletteItem getSelectedItem() {
		return selectedItem;
	}
	
	public void render() {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL11.glColor4f(0,0,0,0.5f);
		DrawUtils.drawRectangle(x,y,width,height);
		
		for(int xx=0;xx<xsize;xx++)
		for(int yy=0;yy<ysize;yy++) {
			GL11.glPushMatrix();
			GL11.glTranslatef(x+(xx*itemWidth)+(itemWidth/2), y+(yy*itemHeight)+(itemHeight/2), 0);
			
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
				itemList[xx][yy].render();
			}
			GL11.glPopMatrix();
		}
		
				
	}
}
