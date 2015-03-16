package pong;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final int TARGET_FPS = 60;
	public static final int NO_DELAYS_PER_YIELD = 16;
	public static final int MAX_FRAME_SKIPS = 5;
	public int updateTimer;
	private boolean running;
	private Thread gameThread;
	private Graphics bg;				//Painting graphics object
	private Image buffImage;			//Graphics buffer image
	private Player player;
	private Ball ball;
	
	public GamePanel() {
		init();
	}
	
	public void init() {
		player = new Player();
		ball = new Ball(player);
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.addKeyListener(new KeyManager());
		this.setFocusable(true);
		this.requestFocus();
		gameThread = new Thread(this);
		gameThread.start();

	}

	public void run() {		
		long beforeTime, afterTime, timeDiff, sleepTime;
		long overSleepTime = 0L;
		int noDelays = 0;
		long excess = 0L;
		long period = (1 / TARGET_FPS) * 1000000000; 
		beforeTime = System.nanoTime();
		running = true;
		while(running) {
//			System.out.println("Looping.");
			gameUpdate();
			gameRender();
			paintScreen();
			afterTime = System.nanoTime();
			timeDiff = afterTime - beforeTime;
			sleepTime = (period - timeDiff) - overSleepTime;
			if (sleepTime > 0) { // some time left in this cycle
				try {
					Thread.sleep(sleepTime/1000000L); // nano -> ms
				}
				catch(InterruptedException ex){}
				overSleepTime = (System.nanoTime() - afterTime) - sleepTime;
			}
			else { // sleepTime <= 0; frame took longer than the period
				excess -= sleepTime; // store excess time value
				overSleepTime = 0L;
				if (++noDelays >= NO_DELAYS_PER_YIELD) {
					Thread.yield( ); // give another thread a chance to run
					noDelays = 0;
				}
			}
			beforeTime = System.nanoTime();
			/* If frame animation is taking too long, update the game state
			without rendering it, to get the updates/sec nearer to
			the required FPS. */
			int skips = 0;
			while((excess > period) && (skips < MAX_FRAME_SKIPS)) {
				excess -= period;
				gameUpdate( ); // update state but don't render
				skips++;
			}
		}
		gameThread = null;
		System.exit(0);	
	}
	
	public void gameUpdate() {
		player.update();
		if (ball == null) {
			System.out.println("Spawning ball.");
			ball = new Ball(player);
		}
		ball.update();
		if (!ball.alive()) {
			System.out.println("Killing ball.");
			ball = null;
		}
	}
	
	public void gameRender() {
		if (buffImage == null) {
			buffImage = createImage(WIDTH, HEIGHT);
		}
		else {
			Graphics bg = buffImage.getGraphics();
			if (bg == null) {
			}
			bg.setColor(Color.BLACK);
			bg.fillRect(0, 0, WIDTH, HEIGHT);
			player.render(bg);
			if (ball != null) {
				ball.render(bg);
			}
			bg.drawString(Integer.toString(player.getScore()), WIDTH / 2, 16);		
		}
	}
	
	public void paintScreen() {
		Graphics g;
		try {
			g = this.getGraphics();
			if (g != null && buffImage != null) {
				g.drawImage(buffImage, 0, 0, null);
				Toolkit.getDefaultToolkit().sync();
				g.dispose();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
