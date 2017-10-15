package com.game.src.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import com.game.src.main.Game.STATE;

public class Menu {
	
	public static int DIM = 60;
	private static int delayText = 180;
	private static int SPACE = 100;
	
	private Game game;
	
	public Button playButton = new Button(Game.WIDTH * Game.SCALE / 2, 150, "Play");
	public Button optionsButton = new Button(Game.WIDTH * Game.SCALE / 2, 220, "Options");
	public Button helpButton = new Button(Game.WIDTH * Game.SCALE / 2, 290, "Help");
	public Button quitButton = new Button(Game.WIDTH * Game.SCALE / 2, 360, "Quit");
	
	public Menu(Game game){
		this.game = game;
	}
	
	public void render(Graphics g){
		playButton.render(g);
		optionsButton.render(g);
		helpButton.render(g);
		quitButton.render(g);
		
		Font fnt0 = new Font("arial", Font.BOLD, DIM);
		g.setFont(fnt0);
		g.setColor(Color.white);
		g.drawString("Space Game", Game.WIDTH * Game.SCALE / 2 - delayText, SPACE);
	}
	
	public void mousePressed(MouseEvent e){
		int mx = e.getX();
		int my = e.getY();
		
		if (playButton.contains(mx, my)){
			game.initGame();
			game.setState(STATE.GAME);
		}
		if (optionsButton.contains(mx, my))
			game.setState(STATE.OPTIONS);
		
		if (helpButton.contains(mx, my))
			game.setState(STATE.HELP);
		
		if (quitButton.contains(mx, my))
			System.exit(1);
		
	}
}

