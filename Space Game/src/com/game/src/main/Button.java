package com.game.src.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Button {
	
	private int x, y;
	private Rectangle r;
	private String text;
	private static int delayX = 20, delayY = 35;
	private int dimX = 150, dimY = 50;
	
	public Button(int x, int y, String text){
		this.x = x;
		this.y = y;
		r = new Rectangle(x - dimX / 2, y, dimX, dimY);
		this.text = text;
	}
	
	public void render(Graphics g){
		Graphics2D g2d = (Graphics2D)g;
		
		Font fnt1 = new Font("arial", Font.BOLD, Menu.DIM / 2);
		g.setColor(Color.white);
		g.setFont(fnt1);
		g.drawString(text, r.x + delayX, r.y + delayY);
		g2d.draw(r);
	}
	
	public boolean contains(int mx, int my){
		return x - dimX / 2 <= mx && mx <= x + dimX / 2
			&& y <= my && my <= y + dimY;
	}
}