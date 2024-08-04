package com.level.Spwan_Level;

import com.again.Screen;
import com.again.Sprite;
import com.level.Tile;

public class SpawnGrassTile extends Tile{

	public SpawnGrassTile(Sprite sprite) 
	{
		super(sprite);
	}
	
	public void render(int x, int y, Screen screen)
	{
		//ÔÚÕâäÖÈ¾
		screen.renderTile(x << 4, y << 4, this);
	}
}
