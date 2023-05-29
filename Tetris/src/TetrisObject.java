

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import resources.*;


public abstract class TetrisObject implements KeyListener {
	
	protected Block[] blocks;
	protected int x;
	protected int y;
	protected Color color;
	private SoundPlayer arrow, shift, bubble;
	protected boolean toBeBuried;
	
	
	public TetrisObject(int x, int y, Color color){
		this.x = x;
		this.y = y;
		this.color = color;
		
		arrow = new SoundPlayer(Coordinator.PATH + "arrow.wav");
		shift = new SoundPlayer(Coordinator.PATH + "shift.wav");
		bubble = new SoundPlayer(Coordinator.PATH + "bubbling.wav");
		
		formShape();
		updateBlocks();
	}
	
	protected abstract void formShape();
	
	 
	public boolean toBeBuried() {
		return toBeBuried;
	}
	
	
	public void bury() {
		Graveyard.bury(blocks); 
	}
	
	public void moveDown() {
		moveDownByOneBlock();
		bubble.play();
	}
	
	
	private void moveDownByOneBlock(){ 
		y += Block.SIZE;
		updateBlocks();
		
		if(isIllegalMove()) {
			y -= Block.SIZE;
			updateBlocks();
			toBeBuried = true;
			return;
		}
	}
	
	
	private void hardDrop() {
		while(!toBeBuried) moveDownByOneBlock();
		arrow.play();
	}
	
	
	private void shiftLeft(){ 
		x -= Block.SIZE;
		updateBlocks();
		
		if(isIllegalMove()) {
			x += Block.SIZE;
			updateBlocks();
			return;
		}
		
		shift.play();
	}
	
	private void shiftRight(){ 
		x += Block.SIZE;
		updateBlocks();
		
		if(isIllegalMove()) {
			x -= Block.SIZE;
			updateBlocks();
			return;
		}
		
		shift.play();
	}
	
	
	private void rotate(){
		for(int i=0; i<blocks.length; i++) {
			blocks[i].rotate();
		}
		updateBlocks();
		
		if(isIllegalMove()) {
			int i, k;
			for(k=0; k<3; k++) {
				for(i=0; i<blocks.length; i++) {
					blocks[i].rotate();
				}
			}
			updateBlocks();
			return;
		}
		
		arrow.play();
	}
	
	
	
	private void mirror(){ 
		for(int i=0; i<blocks.length; i++) {
			blocks[i].mirror();
		}
		updateBlocks();
		
		if(isIllegalMove()) {
			for(int i=0; i<blocks.length; i++) {
				blocks[i].mirror();
			}
			updateBlocks();
			return;
		}
		
		arrow.play();
	}
	
	
	public void keyPressed(KeyEvent e) { 
		
		if(e.getKeyCode()==KeyEvent.VK_LEFT) shiftLeft();
		else if(e.getKeyCode()==KeyEvent.VK_RIGHT) shiftRight();
		else if(e.getKeyCode()==KeyEvent.VK_DOWN) hardDrop();
		else if(e.getKeyCode()==KeyEvent.VK_R) rotate();
		else if(e.getKeyCode()==KeyEvent.VK_M) mirror();
	}
	
	
	public void keyReleased(KeyEvent e) { }
	public void keyTyped(KeyEvent e) { }
	
	
	// Checks if any of the tetrisObject blocks has gone
	// outside the boundary of the Graveyard.
	// Checks if any of the tetrisObject blocks overlaps
	// with occupied blocks of the Graveyard.
	private boolean isIllegalMove() {
		
		for(int i=0; i<blocks.length; i++) {
			if(blocks[i].getX()<Graveyard.X_LEFT ||
			   blocks[i].getX()>=Graveyard.X_RIGHT ||
			   blocks[i].getY()>=Graveyard.Y_BOTTOM ||
			   Graveyard.getColor(blocks[i].getX(), blocks[i].getY())!=null) return true;
		}
		return false;
	}
	
	

	protected void updateBlocks() {
		for(int i=0; i<blocks.length; i++) {
			blocks[i].updatePosition(x, y);
		}
	}
	
	
	public void draw(Graphics g){
		for(int i=0; i<blocks.length; i++){
			blocks[i].draw(g);
		}
	}
 
}
