package input;

import java.awt.event.KeyEvent;

public class Keys {
	public static final int NUM_KEYS = 7;
	public static boolean keyState[] = new boolean[NUM_KEYS];
	public static boolean prevKeyState[] = new boolean[NUM_KEYS];
	
	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	public static final int ACTION1 = 4;
	public static final int ENTER = 5;
	public static final int ESCAPE = 6;
	
	public static void keySet(int i, boolean b) {
		if(i == KeyEvent.VK_W) keyState[UP] = b;
		else if(i == KeyEvent.VK_S) keyState[DOWN] = b;
		else if(i == KeyEvent.VK_A) keyState[LEFT] = b;
		else if(i == KeyEvent.VK_D) keyState[RIGHT] = b;
		else if(i == KeyEvent.VK_SPACE) keyState[ACTION1] = b;
		else if(i == KeyEvent.VK_ENTER) keyState[ENTER] = b;
		else if(i == KeyEvent.VK_ESCAPE) keyState[ESCAPE] = b;
	}
	
	public static void update() {
		for(int i = 0; i < keyState.length; i++) {
			prevKeyState[i] = keyState[i];
		}
	}
	
	public static boolean isPressed(int i) {	
		return keyState[i] && !prevKeyState[i];
	}
	
	public static boolean isHeld(int i) {
		
		return keyState[i] && prevKeyState[i];
	}
}
