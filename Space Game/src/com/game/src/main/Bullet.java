package com.game.src.main;

import java.awt.Graphics;

import com.game.src.libs.Animation;
import com.game.src.main.classes.EntityA;

public class Bullet extends GameObject implements EntityA{
	
	public Bullet(double x, double y, int dimX, int dimY, double speed, Textures tex, Game game){
		super(x, y, dimX, dimY, speed);
		
		anim = new Animation(5, tex.bullet[0], tex.bullet[1], tex.bullet[2]);
	}
	
	public void tick(){
		y -= speed;
		
		anim.runAnimation();
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public void render(Graphics g){
		anim.drawAnimation(g, x, y, 0);
	}

}
