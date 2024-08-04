package entity;

import java.util.List;

import com.again.AnimatedSprite;
import com.again.Screen;
import com.again.Sprite;
import com.again.SpriteSheet;

import entity.Mob.Direction;
import util.Debug;
import util.Vector2i;

public class Shooter extends Mob{
	
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.dummy_down,32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.dummy_up,32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.dummy_left,32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.dummy_right,32, 32, 3);
	
	private AnimatedSprite animSprite = down;
	
	private int time = 0;
	private int xa = 0;
	private int ya = 0;
	
	private int fireRate = 0;
	
	private Entity rand = null;
	
	public Shooter(int x, int y)
	{
		this.x = x << 4;
		this.y = y << 4;
		sprite = Sprite.dummy;
		fireRate = WizarProjectile.STAR_FIRE_RATE;
	}
	
	public void update()
	{
		time++;
		if(fireRate > 0)fireRate--;
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
			//move(xa,ya);
			walking = true;
		}
		else
		{
			walking = false;
		}
		
		shootRandom();
	}
	
	private void shootRandom()
	{
		if(time % (60 + random.nextInt(61)) == 0) 
		{
			
			List<Entity>entities = level.getEntities(this, 100);
			entities.add(level.getClientPlayer());
			int index = random.nextInt(entities.size());
			rand = entities.get(index);
		}
		
		if(rand != null)
		{
			double dx = rand.getX() - x;
			double dy = rand.getY() - y;
			double dir = Math.atan2(dy,dx);
			if(fireRate <= 0) 
			{
				shoot(x,y,dir);
				fireRate = WizarProjectile.STAR_FIRE_RATE;
			}
		}
	}
	
	private void shootClosest()
	{
		List<Entity>entities = level.getEntities(this, 100);
		entities.add(level.getClientPlayer());
		double min = 0;
		Entity closest = null;
		for(int i = 0; i < entities.size(); i++)
		{
			Entity e = entities.get(i);
			double distance = Vector2i.getDistance(new Vector2i((int)x,(int)y), new Vector2i((int)e.getX(),(int)e.getY()));
			if(i == 0 || distance < min) 
			{
				min = distance;
				closest = e;
			}
		}
		if(closest != null) 
		{
			double dx = closest.getX() - x;
			double dy = closest.getY() - y;
			double dir = Math.atan2(dy,dx);
			shoot(x+4, y, dir);
		}
	}
	
	public void render(Screen screen)
	{
		//Debug.drawRect(screen, 50, 50, 32, 32,false);
		sprite = animSprite.getSprite();
		screen.renderMob((int)x-16, (int)y-16, this);
	}
}
