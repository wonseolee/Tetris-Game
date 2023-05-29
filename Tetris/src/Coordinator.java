
import resources.*;
import java.awt.*;

import javax.swing.ImageIcon;

public class Coordinator {

	public static final String PATH = "jrJava/tetris_sounds/";
	private static Color[] menu = { Color.RED, Color.GREEN, Color.BLUE, Color.CYAN,
			                        Color.YELLOW, Color.ORANGE, Color.PINK, Color.MAGENTA };
	public static int score;
	public static boolean gameOver;
	public static final int GAME_OVER_Y = 100;
			
	
	public static void main(String[] args) {

		DrawingBoard board = new DrawingBoard(500, 700);
		Graphics g = board.getCanvas();
		Timer timer = new Timer();

		TetrisObject tObj = new BlockShape(240, 100, Color.GREEN);
		board.getJFrame().addKeyListener(tObj);
		
		SoundPlayer bgSound = new SoundPlayer(PATH + "imperialMarch.wav");
		bgSound.playLoop();
		
		int count = 0;
		while(!gameOver) {
			count++;
			if(count%30==0) tObj.moveDown();
			
			board.clear();
			Graveyard.draw(g);
			tObj.draw(g);
			
			g.setColor(Color.BLACK); 
			g.drawString("Score:" + score, 40, 30);
			
			if(tObj.toBeBuried()) {
				tObj.bury();
				board.getJFrame().removeKeyListener(tObj); 
				
				//tObj = new BlockShape(240, 100, Color.RED);
				
				Color color = menu[(int)(Math.random()*menu.length)];
				double rand = Math.random();
				if(rand<1/7.0) tObj = new Cross(240, 100, color);
				else if(rand<2/7.0) tObj = new Bar(240, 100, color);
				else if(rand<3/7.0) tObj = new LongBar(240, 100, color);
				else if(rand<4/7.0) tObj = new LShape(240, 100, color);
				else if(rand<5/7.0) tObj = new BlockShape(240, 100, color);
				else if(rand<6/7.0) tObj = new UShape(240, 100, color);
				else tObj = new OddShape(240, 100, color);
				
				
				board.getJFrame().addKeyListener(tObj);
			}
			
			board.repaint();
			timer.pause(20);
		}
 
		bgSound.stop();
		
		
		g.setColor(Color.BLACK);
		Font font = new Font("Times", Font.BOLD, 60);
		g.setFont(font);
		g.drawString("GAME OVER", 50, 300);
		
		
		/*Image image = new ImageIcon(PATH + "gameOver.png").getImage();
		g.drawImage(image, 0, 200, null);*/
		
		board.repaint();
		
	}
 

}







