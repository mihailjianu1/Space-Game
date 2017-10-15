package com.game.src.main;

import java.awt.Graphics;

import com.game.src.libs.Animation;
import com.game.src.main.classes.EntityA;
import com.game.src.main.classes.EntityB;

public class Player extends GameObject implements EntityA {
	
	Game game;
	Controller c;
	
	private double velX = 0;
	private double velY = 0;
	
	public Player(double x, double y, int dimX, int dimY, double speed, Textures tex, Game game, Controller c){
		super(x, y, dimX, dimY, speed);
		this.game = game;
		this.c = c;
		anim = new Animation(5, tex.player[0], tex.player[1], tex.player[2]);
	}
	
	public double getSpeed(){
		return speed;
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public void setX(double x){
		this.x = x;
	}
	
	public void setY(double y){
		this.y = y;
	}
	
	public void tick(){
		x += velX;
		y += velY;
		
		if(x <= 0){
			x = 0;
		}
		if(x >= Game.WIDTH * Game.SCALE - dimX){
			x = Game.WIDTH * Game.SCALE - dimX;
		}
		if(y <= 0){
			y = 0;
		}
		if (y >= Game.HEIGHT * Game.SCALE - dimY){
			y = Game.HEIGHT * Game.SCALE - dimY;
		}
		
		for(int i = 0; i < game.eb.size(); i++){
			EntityB tempEnt = game.eb.get(i);
			if(Physics.Collision(this,  tempEnt)){
				c.removeEntity(tempEnt);
				game.setEnemy_killed(game.getEnemy_killed() + 1);
				game.health -= 10;
			}
		}
		anim.runAnimation();
	}
	
	public void render(Graphics g){
		anim.drawAnimation(g, x, y, 0);
	}
	
	public void setVelX(double velX){
		this.velX = velX;
	}
	
	public void setVelY(double velY){
		this.velY = velY;
	}
	
	public double getVelX(){
		return velX;
	}
	
	public double getVelY(){
		return velY;
	}
}
