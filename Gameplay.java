package brickBracker;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Gameplay extends JPanel implements KeyListener,ActionListener{
	private boolean play = false;
	private int score = 0;
	private int totalbricks = 36;
	
	private int totalBricks = 21;
	
	private Timer timer;
	private int delay = 3;
	private int ballposX = 120;
	private int ballposY = 350;
	private int ballXdir = -1;
	private int ballYdir = -2;
	private int playerX = 310;
	private MapGenerator map;
	
//	Constructor
	public Gameplay() {
		map = new MapGenerator(4,9);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(true);
		
		timer = new Timer (delay,this);
		timer.start();
	}
	
	public void paint(Graphics g) {
		//Black canvas background 
		g.setColor(Color.yellow); //change color
		g.fillRect(1, 1,692,592);
		
		//Border
		g.setColor(Color.red);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(691, 0, 3, 592);
		
		//Ball
		g.setColor(Color.red);
		g.fillOval(ballposX,ballposY, 20, 20);
	
//		the paddle
		g.setColor(Color.black);
		g.fillRect(playerX, 550, 100,8);
		
		// the scores 		
				g.setColor(Color.red);
				g.setFont(new Font("serif",Font.BOLD, 25));
				g.drawString(""+score, 590,30);

				// when you won the game
				if(totalbricks <= 0)
				{
					play = false;
				    ballXdir = 0;
				    ballYdir = 0;
				    g.setColor(Color.RED);
				    g.setFont(new Font("serif",Font.BOLD, 30));
				    g.drawString("You Won", 260,300);
				             
				    g.setColor(Color.RED);
				    g.setFont(new Font("serif",Font.BOLD, 20));           
				    g.drawString("Press (Enter) to Restart", 230,350);  
				}
						
				// when you lose the game
				if(ballposY > 570)
				{
					play = false;
				    ballXdir = 0;
				    ballYdir = 0;
				    g.setColor(Color.RED);
				    g.setFont(new Font("serif",Font.BOLD, 30));
				    g.drawString("Game Over, Scores: "+score, 190,300);
				             
				    g.setColor(Color.RED);
				    g.setFont(new Font("serif",Font.BOLD, 20));           
				    g.drawString("Press (Enter) to Restart", 230,350);        
				}
			//drawing map
				map.draw((Graphics2D)g);
	}
	
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			if(playerX<=2) {
				playerX = 2;
			}else {
				moveLeft();
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(playerX >=600) {
				playerX = 600;
			}else {
				moveRight();
			}
		}
	}		

		public void moveLeft() {
			play = true;
			playerX-=20;
		}
		
	public void moveRight() {
		play = true;
		playerX+=20;
	}

	@Override

public void actionPerformed(ActionEvent e) {
		
		timer.start();
		if(play) {
						
			//to move the ball when game is started
			ballposX += ballXdir;
			ballposY += ballYdir;
			
			if(ballposX < 0)
			{
				ballXdir = -ballXdir;
			}
			if(ballposY < 0)
			{
				ballYdir = -ballYdir;
			}
			if(ballposX > 670)
			{
				ballXdir = -ballXdir;
			}
			
			//collision between ball and paddle
			if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8)))
			{
				ballYdir = -ballYdir;
			}
			
			// check map collision with the ball	
			//here in map.map.length first map is object we have created and second map is the 2d array we have created

			A: for(int i = 0; i<map.map.length; i++)
			{
				for(int j =0; j<map.map[0].length; j++)
				{				
					if(map.map[i][j] > 0)
					{
						//for intersection we need to first detect the position of ball and brick with respect to height and width of the brick
						int brickX = j * map.brickWidth + 80;
						int brickY = i * map.brickHeight + 50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);					
						Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
						Rectangle brickRect = rect;
						
						if(ballRect.intersects(brickRect))
						{					
							map.setBrickValue(0, i, j);
							score+=5;	
							totalbricks--;
							
							// when ball hit right or left of brick
							if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width)	
							{
								ballXdir = -ballXdir;
							}
							// when ball hits top or bottom of brick
							else
							{
								ballYdir = -ballYdir;				
							}
							
							break A;
						}
					}
				}
		}
			repaint();
	}
}
}
