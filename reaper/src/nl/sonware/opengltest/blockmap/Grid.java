package nl.sonware.opengltest.blockmap;

public interface Grid {
	public Object get(int x, int y, int z);
	public void set(Object o, int x, int y, int z);
}
