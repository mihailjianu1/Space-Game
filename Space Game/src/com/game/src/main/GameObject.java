package com.game.src.main;

import java.awt.Rectangle;

import com.game.src.libs.Animation;

public class GameObject {

	public double x, y;
	public int dimX, dimY;
	public double speed;
	Animation anim;
	
	public GameObject(double x, double y, int dimX, int dimY, double speed){
		this.x = x;
		this.y = y;
		this.dimX = dimX;
		this.dimY = dimY;
		this.speed = speed;
	}
	
	public Rectangle getBounds(){
		return new Rectangle((int)x, (int)y, dimX, dimY);
	}
}
