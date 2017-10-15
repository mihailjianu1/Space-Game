package com.game.src.main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.game.src.main.Game.STATE;

public class MouseInput implements MouseListener{

	private Game game;
	
	MouseInput(Game game){
		this.game = game;
	}

	public void mouseClicked(MouseEvent arg0) {
		
		
	}

	public void mouseEntered(MouseEvent arg0) {
		
		
	}
	
	public void mouseExited(MouseEvent arg0) {
		
		
	}

	
	public void mousePressed(MouseEvent e) {
		if (game.getState() == STATE.MENU)
			game.getMenu().mousePressed(e);
		else if (game.getState() == STATE.HELP)
			game.getHelp().mousePressed(e);
		else if (game.getState() == STATE.OPTIONS)
			game.getOptions().mousePressed(e);
		else if (game.getState() == STATE.SCORE)
			game.getScore().mousePressed(e);
	}

	public void mouseReleased(MouseEvent arg0) {
		
		
	}
}
