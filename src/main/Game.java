package main;

import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

public class Game {
	public static GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
	public static GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
	
	public static void main(String[] args) {
		JFrame window = new JFrame("Independent Study Game");
		window.setContentPane(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setExtendedState(JFrame.MAXIMIZED_BOTH);
		window.setUndecorated(true);
		window.setResizable(false);
		window.setBackground(Color.BLACK);
		window.pack();
		graphicsDevice.setFullScreenWindow(window);
		window.setVisible(true);
	}
}
