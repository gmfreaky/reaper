package nl.sonware.opengltest;

public class Color {
	public float red,green,blue,alpha;
	public Color() {
		this(1,1,1,1);
	}
	public Color(Color c) {
		this(c.getRed(),c.getGreen(),c.getBlue(),c.getAlpha());
	}
	public Color(float red, float green, float blue) {
		this(red,green,blue,1);
	}
	public Color(float red, float green, float blue, float alpha) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}
	public float getRed() {
		return red;
	}
	public float getGreen() {
		return green;
	}
	public float getBlue() {
		return blue;
	}
	public float getAlpha() {
		return alpha;
	}
	
	public int getRedInt() {
		return Math.round(alpha*256);
	}
	public int getGreenInt() {
		return Math.round(alpha*256);
	}
	public int getBlueInt() {
		return Math.round(alpha*256);
	}
	public int getAlphaInt() {
		return Math.round(alpha*256);
	}
	
	public void setRed(float red) {
		this.red = red;
	}
	public void setGreen(float green) {
		this.green = green;
	}
	public void setBlue(float blue) {
		this.blue = blue;
	}
	
	public void setRedInt(int red) {
		setRed((red)/256f);
	}
	public void setGreenInt(int green) {
		setGreen((green)/256f);
	}
	public void setBlueInt(int blue) {
		setBlue((blue)/256f);
	}
	public void setAlphaInt(int alpha) {
		setAlpha((alpha)/256f);
	}
	
	public void setRGBA(float red, float green, float blue, float alpha) {
		setRed(red);
		setGreen(green);
		setBlue(blue);
		setAlpha(alpha);
	}
	public void setRGB(float red, float green, float blue) {
		setRGBA(red,green,blue,getAlpha());
	}
	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}
	public float[] getArray() {
		return new float[]{red,green,blue,alpha};
	}
}
