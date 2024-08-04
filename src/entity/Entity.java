package entity;

import java.util.Random;

import com.again.Screen;
import com.again.Sprite;
import com.level.Level;

public  class Entity {
	protected  double x , y ;
	private boolean removed = false;
	protected Level level;
	protected final Random random = new Random();
	protected Sprite sprite;
	
	public Entity()
	{
		
	}
	
	public Entity(int x, int y, Sprite sprite)
	{
		this.x = x;
		this.y =x;
		this.sprite = sprite;
	}
	
	public Sprite getSprite()
	{
		return sprite;
	}
	
	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
	
	public  void update()
	{
		
	}
	
	public void render(Screen screen)
	{
		//if(sprite == null)screen.renderSprite((int)x, (int)y, sprite, true);
	}
	
	
	public void remove()
	{
		removed = true;
	}
	
	public void init(Level level)
	{
		this.level = level;
	}
	
	public boolean isRemove()
	{
		return removed;
	}
}
