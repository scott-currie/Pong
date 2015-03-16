package pong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

public class Player {
	private Rectangle boundBox;
	private Point origin;
	private double x = 30, y = 0;
	public static boolean up, down, serve;
	private static final double MOVE_SPEED = .25;
	private double move;
	private int score = 0;
//	private Ball ball;
	
	public Player() {
		init();
	}
	
	public void init() {
		boundBox = new Rectangle(30, 0, 20, 100);
		origin = new Point((int)x, (int)y);
	}
	
	public void update() {
		double vShift;
		if (up && boundBox.getMinY() > 0) {
			vShift = -MOVE_SPEED;
		}
		else if (down && boundBox.getMaxY() < GamePanel.HEIGHT - 1) {
			vShift = MOVE_SPEED;
		}
		else {
			vShift = 0;
		}
		
		y += vShift;
		move();
//		System.out.println("y = " + y);
	}
	
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(origin.x, origin.y, 20, 100);
	}
	
	public void move() {
		boundBox.setLocation((int)x, (int)y);
		origin.setLocation((int)x, (int)y);
	}
	
	public double getMaxX() {
		return boundBox.getMaxX();
	}
	
	public double getCenterY() {
		return boundBox.getCenterY();
	}
	
	public Rectangle getRect() {
		return boundBox;
	}
	
	public void incrementScore() {
		score++;
	}
	
	public int getScore() {
		return score;
	}
}
