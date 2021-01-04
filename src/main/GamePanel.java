package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import assets.Assets;
import gameState.GameStateManager;
import gfx.RenderEffect;
import input.Keys;

public class GamePanel extends JPanel implements Runnable, KeyListener {
	private static final long serialVersionUID = 1L;

	// Dimensions
	public static final int WIDTH = 320;
	public static final int HEIGHT = 192;
	public static final double SCALE = 4.5;
	
	// Screen Dimensions
	public static final int SCREEN_WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static final int SCREEN_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();

	// Game Thread
	private Thread thread;
	private boolean running;
	private int FPS = 60;
	private long targetTime = 1000 / FPS;

	// Image
	private BufferedImage image;
	private Graphics2D g;
	
	// Game State Manager
	private GameStateManager gsm;
	
	// Debug
	public static boolean DEBUG_RENDERBOXES = false;
	
	// Easter Eggs
	public static boolean DOGE_MODE = false;

	public GamePanel() {
		super();
		setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		setFocusable(true);
		this.setBackground(Color.BLACK);
		requestFocus();
	}

	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}

	private void init() {
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D)image.getGraphics();

		running = true;
		
		gsm = new GameStateManager();
		
		Assets.init(gsm);
		
		gsm.populateLevels();
	}

	@Override
	public void run() {
		init();

		long start;
		long elapsed;
		long wait;


		// Game Loop
		while(running) {

			start = System.nanoTime();

			update();
			draw();
			
			drawToScreen();

			elapsed = System.nanoTime() - start;

			wait = targetTime - elapsed / 1000000;
			
			if(wait < 0) wait = 1;
			try {
				Thread.sleep(wait);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void update() {
		gsm.update();
		Keys.update();
	}

	private void draw() {
		// Clear Screen
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		gsm.draw(g);
	}

	private void drawToScreen() {
		Graphics g2 = getGraphics();
		int xOffset = (int) ((SCREEN_WIDTH - (WIDTH * SCALE)) / 2);
		int yOffset = (int) ((SCREEN_HEIGHT - (HEIGHT * SCALE)) / 2);
		
		g2.drawImage(image, xOffset, yOffset, (int)(WIDTH * SCALE), (int)(HEIGHT * SCALE), null);
		g2.dispose();
	}

	@Override
	public void keyPressed(KeyEvent key) {
		Keys.keySet(key.getKeyCode(), true);
	}

	@Override
	public void keyReleased(KeyEvent key) {
		Keys.keySet(key.getKeyCode(), false);
	}

	@Override
	public void keyTyped(KeyEvent key) {}
	
	public GameStateManager getGameStateManager() {
		return gsm;
	}
}
