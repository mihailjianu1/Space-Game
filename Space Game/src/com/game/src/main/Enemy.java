package com.game.src.main;

import java.awt.Graphics;
import java.util.Random;

import com.game.src.libs.Animation;
import com.game.src.main.classes.EntityA;
import com.game.src.main.classes.EntityB;

public class Enemy extends GameObject implements EntityB {
	
	Random r = new Random();
	private Game game;
	private Controller c;
	
	public Enemy(double x, double y, int dimX, int dimY, Textures tex, Controller c, Game g){
		super(x, y, dimX, dimY, Game.enemy_min_speed);
		this.speed = r.nextInt((int)Game.enemy_max_speed) + Game.enemy_min_speed;
		this.c = c;
		this.game = g;
		
		anim = new Animation(5, tex.enemy[0], tex.enemy[1], tex.enemy[2]);
	}
	
	public void tick(){
		y += speed;
		
		if (y >= Game.HEIGHT * Game.SCALE){
			x = r.nextInt(Game.WIDTH * Game.SCALE - dimX);
			y = -dimY;
			speed = r.nextInt(3) + 1;
		}
		
		for(int i = 0; i < game.ea.size(); i++){
			EntityA tempEnt = game.ea.get(i);
			if(Physics.Collision(this, tempEnt)){
				c.removeEntity(tempEnt);
				c.removeEntity(this);
				game.setEnemy_killed(game.getEnemy_killed() + 1);	
				game.setTotal_killed(game.getTotal_killed() + 1);
			}
		}
		
		anim.runAnimation();
	}
	
	public void render(Graphics g){
		anim.drawAnimation(g, x, y, 0);
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
}
