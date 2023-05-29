

import java.awt.Color;
import java.awt.Graphics;


public class Block {

	public static final int SIZE = 20;
	private int xRel, yRel;
	private int x, y;
	private Color color;
	
	public Block(int xRel, int yRel, Color color){
		this.xRel = xRel;
		this.yRel = yRel;
		this.color = color;
	}
	
	public int getX() { return x; }
	public int getY() { return y; }
	public Color getColor() { return color; }
	
	
	public void rotate() {
		int temp = yRel;
		yRel = xRel;
		xRel = -temp;
	}
	
	public void mirror() {
		xRel = -xRel;
	}
	
	
	public void updatePosition(int tetrisX, int tetrisY) {
		x = tetrisX + xRel*SIZE;
		y = tetrisY + yRel*SIZE;
	}
	
	public void draw(Graphics g){
		g.setColor(color);
		g.fillRect(x, y, SIZE, SIZE);
		g.setColor(Color.black);
		g.drawRect(x, y, SIZE, SIZE);
		
		if(xRel==0 && yRel==0) {
			g.drawOval(x+SIZE/4, y+SIZE/4, SIZE/2, SIZE/2);
		}
	}
	
}





 













