package entity;

import com.again.Screen;
import com.again.Sprite;
import com.spawner.ParticleSpawner;
import com.spawner.Spawner;

public class WizarProjectile extends Projectile{

	public static final int FIRE_RATE = 8;
	public static final int STAR_FIRE_RATE = 16;
	
	public WizarProjectile(double x, double y, double dir)
	{
		super(x, y, dir);
		range = 100;
		damage = 4;
		speed = 1;
		sprite = Sprite.rotate(Sprite.projectile_arrow,angle);
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
		//System.out.println("nx" + nx+" ny "+ ny);
	}
	
	public void update()
	{
		if(level.tilecollision((int)(x + nx), (int)(y + ny),7,0,8))
		{
			level.add(new ParticleSpawner((int)x, (int)y, 44, 50, level));
			remove();
		}
		move();
		//System.out.println("move");
	}
	
	public void move()
	{
			x += nx;
			y += ny;
		if(distance() > range)remove();
		//System.out.println("Distance: " + distance());
	}
	
	private double distance() 
	{
		double dist = 0;
		double xy2 = 0;
		xy2 = Math.abs((xOrigin - x) * (xOrigin - x)) + Math.abs((yOrigin - y) * (yOrigin - y));
		dist = Math.sqrt(xy2);
		return dist;
	}

	public void render(Screen screen)
	{
		screen.renderProjectile((int)x - 12, (int)y - 2,this);
		//System.out.println("x" + x+","+" y "+ y);
	}
}
