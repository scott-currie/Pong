package pong;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Ball {
	private Rectangle boundBox;
	private Player player;
	private final double START_MOVE_SPEED = .07;
	private final double SPEED_INCREASE = .005;
	private double moveSpeedY = START_MOVE_SPEED;
	private double moveSpeedX = START_MOVE_SPEED;
//	private double accel = .0001;
	private double x, y;
	private boolean alive;
	
	public Ball(Player player) {
		this.player = player;
		init();
	}
	
	public void init() {	
		x = player.getMaxX();
		y = player.getCenterY();
		boundBox = new Rectangle((int)x, (int)y, 20, 20);
		moveSpeedX = START_MOVE_SPEED;
		moveSpeedY = START_MOVE_SPEED;
		alive = true;
	}
	
	public void update() {
		move();
	}
	
	public void render(Graphics g) {
		g.fillRect((int)x, (int)y, 20, 20);
	}
	
	public void move() {
		x += moveSpeedX;
		y += moveSpeedY;
		boundBox.setLocation((int)x, (int)y);
		
		if (boundBox.getMaxX() >= GamePanel.WIDTH - 1 || boundBox.intersects(player.getRect())) {
			moveSpeedX = -moveSpeedX;
		}
		
		if (boundBox.getMinY() <= 0 || boundBox.getMaxY() >= GamePanel.HEIGHT - 1) {
			moveSpeedY = -moveSpeedY;
		}
				
		if (boundBox.intersects(player.getRect())) {
			player.incrementScore();
			if (moveSpeedX > 0) {
				moveSpeedX += SPEED_INCREASE;
			}
			else {moveSpeedX -= SPEED_INCREASE;}
			
			if (moveSpeedY > 0) {
				moveSpeedY += SPEED_INCREASE;
			}
			else {moveSpeedY -= SPEED_INCREASE;}
			
			//Ball collides with upper or lower edges of paddle
			if (boundBox.getMaxY() >= player.getRect().getMinY()) {
				moveSpeedY = -moveSpeedY;
			}
			
			if (boundBox.getMinY() <= player.getRect().getMaxY()) {
				moveSpeedY = -moveSpeedY;
			}
		}
		
		
		if (boundBox.getMinX() <= 0) {
			alive = false;
		}
		
		
	}
	
	public boolean alive() {
		return alive;
	}
}
