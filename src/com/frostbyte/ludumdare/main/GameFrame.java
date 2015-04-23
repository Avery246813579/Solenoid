package com.frostbyte.ludumdare.main;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class GameFrame extends JFrame implements ComponentListener {
	private static final long serialVersionUID = 1L;
	public static int WIDTH = 800, HEIGHT = 600;

	public GameFrame() {
		setTitle("Solenoid v1.0.0");
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		pack();
		setVisible(true);

		try {
			setIconImage(ImageIO.read(getClass().getResourceAsStream("/GUI/ICON.png")));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		addComponentListener(this);
	}

	@Override
	public void componentResized(ComponentEvent event) {
		WIDTH = event.getComponent().getWidth();
		HEIGHT = event.getComponent().getHeight();
	}

	@Override
	public void componentHidden(ComponentEvent event) {

	}

	@Override
	public void componentMoved(ComponentEvent event) {

	}

	@Override
	public void componentShown(ComponentEvent event) {
		
	}
}
