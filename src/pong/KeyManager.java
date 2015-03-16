package pong;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener{
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			Player.up = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			Player.down = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE){
			Player.serve = true;
		}
	}
	
	public void keyTyped(KeyEvent e) {
		
	}
	
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			Player.up = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			Player.down = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE){
			Player.serve = false;
		}	
	}
}
