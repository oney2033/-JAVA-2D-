package com.level;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Chaser;
import entity.DummyMob;
import entity.Shooter;
import entity.Star;

public class SpawnLevel extends Level {
	
	//private Tile[] tiles;
	//private int[] tiles;
	
	public SpawnLevel(String path) {
		super(path);
	}
	
	protected void loadLevel(String path)
	{
		try {
			BufferedImage image = ImageIO.read(new File(path));
			int w = width= image.getWidth();
			int h = height =image.getHeight();
			//tiles = new Tile[w * h];
			tiles = new int[w * h];
			image.getRGB(0, 0, w, h, tiles, 0, w);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			System.out.println("Exception! Could not load level file!");
		}
		for(int i = 0; i < 5; i++) 
		{	
			//add(new DummyMob(20,50));
		}
		//add(new Shooter(22,23));
		//add(new Shooter(22,28));
		//add(new Shooter(20,24));
		add(new Star(20,20));
		add(new Chaser(22,20));
	}
	
	//0xff00ff6f
	//0xffe0ff19
	//0xff6d7c0c
	protected void generateLevel() 
	{
		//for(int i = 0; i < levelPixels.length; i++)
		//{
			//if(levelPixels[i] == 0xff00ff6f) tiles[i] = Tile.grass;
			//if(levelPixels[i] == 0xffe0ff19) tiles[i] = Tile.flower;
			//if(levelPixels[i] == 0xff6d7c0c) tiles[i] = Tile.rock;
		//}
	}
}
