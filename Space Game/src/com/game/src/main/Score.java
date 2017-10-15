package com.game.src.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import com.game.src.main.Game.STATE;

public class Score {
	private Game game;
	public static int DIM = 60;
	private static int delayText = 180;
	private static int SPACE = 100;
	
	private Button backButton = new Button(100, 200, "Back");
	
	public Score(Game game){
		this.game = game;
	}
	
	public void render(Graphics g){
		backButton.render(g);
		
		Font fnt0 = new Font("arial", Font.BOLD, DIM);
		g.setFont(fnt0);
		g.setColor(Color.white);
		g.drawString("Score: " +  Integer.toString (game.getTotal_killed()), Game.WIDTH * Game.SCALE / 2 - delayText, SPACE);
	}
	
	public void mousePressed(MouseEvent e){
		int mx = e.getX();
		int my = e.getY();
		
		if (backButton.contains(mx, my)){
			game.setState(STATE.MENU);
			System.out.println("lola");
		}
	}
}
