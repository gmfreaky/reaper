package nl.sonware.opengltest.world;

import java.util.ArrayList;
import java.util.Random;

import nl.sonware.opengltest.Vector3;
import nl.sonware.opengltest.blockmap.Block;
import nl.sonware.opengltest.blockmap.BlockChange;

public class TickTask implements Runnable{

	World w;
	boolean finished;
	TickTask(World w) {
		this.w=w;
	}
	
	@Override
	public void run() {
		while(!finished) {
			
			long startTime = System.currentTimeMillis();
			tick();
			System.out.println("Tick took "+(System.currentTimeMillis()-startTime)+" milliseconds");
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void tick() {
		
		ArrayList<BlockChange> changeList = new ArrayList<BlockChange>();
		
		Random r = new Random();
		for(int xx=0;xx<w.getGrid().getxSize();xx++)
		for(int yy=0;yy<w.getGrid().getySize();yy++)
		for(int zz=0;zz<w.getGrid().getzSize();zz++) {
			if (w.getGrid().get(xx, yy, zz)==Block.LEAVES) {
				int surroundingLeaves = w.getGrid().getBlockCountRadius(xx,yy,zz,2,Block.LEAVES);
				int surroundingLogs = w.getGrid().getBlockCountRadius(xx,yy,zz,2,Block.LOG);
				
				if (surroundingLogs==0) {
					changeList.add(new BlockChange(new Vector3(xx,yy,zz),w.getGrid(),null));
				}
				
				int spreadChance = (surroundingLeaves*10)/(surroundingLogs+1);
				if (r.nextInt(spreadChance+1)==0 && surroundingLogs>surroundingLeaves/10) {
					int xLoc=0,yLoc=0,zLoc=0;
					int direction = r.nextInt(6);
					switch(direction) {
						case 0:xLoc=-1; break;
						case 1:yLoc=-1; break;
						case 2:zLoc=-1; break;
						case 3:xLoc=1; break;
						case 4:yLoc=1; break;
						case 5:zLoc=1; break;
					}
					
					if (w.isPlaceFree(new Vector3(xx+xLoc,yy+yLoc,zz+zLoc))) {
						changeList.add(
								new BlockChange(
										new Vector3(xx+xLoc, yy+yLoc, zz+zLoc),
										w.getGrid(),
										Block.LEAVES));
					}
				}
			}
		}
		
		for(BlockChange b:changeList) {
			b.execute();
		}
	}
	
	public void stop() {
		finished = true;
	}
}