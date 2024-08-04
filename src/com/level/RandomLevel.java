package com.level;

import java.util.Random;

public class RandomLevel extends Level{

	public RandomLevel(int width, int height) {
		super(width, height);
	}
	
	private static final Random random = new Random();
	
	protected void generateLevel()
	{
		for(int y = 0; y < height; y++)
		{
			for(int x = 0; x < width; x++)
			{
				tilesInt[x + y*width] = random.nextInt(4);
			}
		}
	}
}
