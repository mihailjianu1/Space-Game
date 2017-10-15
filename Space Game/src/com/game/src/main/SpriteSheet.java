package com.game.src.main;

import java.awt.image.BufferedImage;

public class SpriteSheet {

	private BufferedImage image;
	
	public SpriteSheet(BufferedImage image){
		this.image = image;
	}
	
	public BufferedImage grabImage(int col, int row, int width, int height){ //
		
		BufferedImage img = image.getSubimage(row * 32 - 32,  col * 32 - 32,  width,  height);
		return img;
		
	}
}
