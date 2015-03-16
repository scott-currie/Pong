package pong;

import javax.swing.JFrame;

public class Game {
	
	
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GamePanel panel = new GamePanel();
		frame.add(panel);
		frame.pack();
//		frame.setFocusable(true);
//		frame.requestFocus();
//		panel.setFocusable(true);
//		panel.requestFocus();
		frame.setVisible(true);
	}
}
