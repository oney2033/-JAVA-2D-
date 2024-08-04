package com.level;

import com.again.Screen;
import com.again.Sprite;

public class FlowerTile extends Tile {

	public FlowerTile(Sprite sprite) {
		super(sprite);
	}
	
	public void render(int x, int y, Screen screen)
	{
		//ÔÚÕâäÖÈ¾
		screen.renderTile(x << 4, y << 4, this);
		//screen.renderTile(x , y , this);
	}
}
