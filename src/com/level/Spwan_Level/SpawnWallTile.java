package com.level.Spwan_Level;

import com.again.Screen;
import com.again.Sprite;
import com.level.Tile;

public class SpawnWallTile extends Tile {

	public SpawnWallTile(Sprite sprite) 
	{
		super(sprite);
		// TODO 自动生成的构造函数存根
	}
	
	public void render(int x, int y, Screen screen)
	{
		//在这渲染
		screen.renderTile(x << 4, y << 4, this);
	}
	
	public boolean solid()
	{
		return true;
	}
}
