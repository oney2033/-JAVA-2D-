package com.level;

import com.again.Screen;
import com.again.Sprite;

public class RockTile extends Tile {

	public RockTile(Sprite sprite) {
		super(sprite);

	}
	
	public void render(int x, int y, Screen screen)
	{
		//ÔÚÕâäÖÈ¾
		screen.renderTile(x << 4, y << 4, this);
		//screen.renderTile(x , y , this);
	}
	
	public boolean solid()
	{
		return true;
	}
}
