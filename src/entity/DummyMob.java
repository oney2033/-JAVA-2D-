package entity;

import com.again.AnimatedSprite;
import com.again.Screen;
import com.again.Sprite;
import com.again.SpriteSheet;

public class DummyMob extends Mob{
	
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.dummy_down,32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.dummy_up,32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.dummy_left,32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.dummy_right,32, 32, 3);
	
	private AnimatedSprite animSprite = down;
	
	private int time = 0;
	private int xa = 0;
	private int ya = 0;
	
	public DummyMob(int x, int y)
	{
		this.x = x << 4;
		this.y = y << 4;
		sprite = Sprite.dummy;
		
	}
	
	public void update()
	{
		time++;
		if(time % (random.nextInt(50) + 30) == 0)
		{
			xa = random.nextInt(3) - 1;
			ya = random.nextInt(3) - 1;
			if(random.nextInt(4) == 0)
			{
				xa = 0;
				ya = 0;
			}
		}
		if(walking)animSprite.update();
		else animSprite.setFrame(0);
		if(ya < 0) 
		{
			animSprite = up;
			dir = Direction.UP;
		}
		else if(ya > 0) 
		{
			dir = Direction.DOWN;
			animSprite = down;
		}
		 if(xa < 0) 
		{
			 animSprite = left;
			 dir = Direction.LEFT;
		}
		 else if(xa > 0) 
		{
			 dir = Direction.RIGHT;
			 animSprite = right;
		}
				
		if(xa != 0 || ya != 0)
		{			
			move(xa,ya);
			walking = true;
		}
		else
		{
			walking = false;
		}
	}
	
	public void render(Screen screen)
	{
		sprite = animSprite.getSprite();
		screen.renderMob((int)x, (int)y, sprite, 0);
	}
}
