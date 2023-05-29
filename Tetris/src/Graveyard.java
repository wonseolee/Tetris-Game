

import java.awt.Color;
import java.awt.Graphics;

public class Graveyard {

	public static final int X_LEFT;
	public static final int Y_TOP;
	public static final int X_RIGHT;
	public static final int Y_BOTTOM;
	private static int cols, rows;
	private static Color[] colors;
	
	static {
		cols = 20;
		rows = 30;
		X_LEFT = 40;
		Y_TOP = 40;
		X_RIGHT = X_LEFT + cols*Block.SIZE;
		Y_BOTTOM = Y_TOP + rows*Block.SIZE;
		colors = new Color[cols*rows]; 
	}
	
	
	public static Color getColor(int x, int y) {
		// map (x, y) --> index i
		int i = (x-X_LEFT)/Block.SIZE + cols*(y-Y_TOP)/Block.SIZE;
		return colors[i];
	}
	
	private static void setColor(int x, int y, Color color) {
		// map (x, y) --> index i
		int i = (x-X_LEFT)/Block.SIZE + cols*(y-Y_TOP)/Block.SIZE;
		colors[i] = color;
	}
	
	
	public static void bury(Block[] blocks) {
		
		// We need to sort blocks in the increasing y value:
		// We will use 'bubble'-sorting algorithm.
		boolean swapOccurred;
		int i;
		Block temp;
		
		while(true) {
			swapOccurred = false;
			for(i=0; i<=blocks.length-2; i++) {
				if(blocks[i].getY()>blocks[i+1].getY()) {
					// swap:
					temp = blocks[i+1];
					blocks[i+1] = blocks[i];
					blocks[i] = temp;
					swapOccurred = true;
				}
			}
			if(!swapOccurred) break;
		}
		
		for(i=0; i<blocks.length; i++) {
			bury(blocks[i]);
		}
	}
	
	private static void bury(Block block) {
		
		if(block.getY()<=Coordinator.GAME_OVER_Y) {
			Coordinator.gameOver = true;
			return;
		}
		
		setColor(block.getX(), block.getY(), block.getColor());
		Coordinator.score++;
		
		if(isFullRow(block.getY())) {
			// shift down all the blocks above by one block size.
			shiftDownByOneRow(block.getY());
		}
	}
	
	
	private static boolean isFullRow(int y) {
		for(int x=X_LEFT; x<X_RIGHT; x+=Block.SIZE) {
			if(getColor(x, y)==null) return false;
		}
		return true;
	}
	
	
	private static void shiftDownByOneRow(int y) {
		int index = cols*(y-Y_TOP)/Block.SIZE;
		for(int i=index-1; i>=0; i--) {
			colors[i+cols] = colors[i];
		}
	}
	
	
	public static void draw(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		// 1. draw vertical lines.
		for(int x=X_LEFT; x<=X_RIGHT; x+=Block.SIZE) {
			g.drawLine(x, Y_TOP, x, Y_BOTTOM);
		}
		// 2. draw horizontal lines. 
		for(int y=Y_TOP; y<=Y_BOTTOM ; y+=Block.SIZE) {
			g.drawLine(X_LEFT, y, X_RIGHT, y);
		} 
		// 3. draw contour line.
		g.setColor(Color.BLACK);
		g.drawRect(X_LEFT, Y_TOP, X_RIGHT-X_LEFT, Y_BOTTOM-Y_TOP);
		
		// 4. show all the colors stored in the array onto the Graveyard:
		// Need to map index i --> (x, y)
		int x, y;
		for(int i=0; i<colors.length; i++) {
			if(colors[i]!=null) {
				x = X_LEFT + i%cols*Block.SIZE;
				y = Y_TOP + i/cols*Block.SIZE;
				g.setColor(colors[i]); 
				g.fillRect(x, y, Block.SIZE, Block.SIZE); 
				g.setColor(Color.BLACK); 
				g.drawRect(x, y, Block.SIZE, Block.SIZE); 
			}
		}
		
	}
	
}

 









