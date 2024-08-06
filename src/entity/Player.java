package entity;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.again.AnimatedSprite;
import com.again.Game;
import com.again.Mouese;
import com.again.Screen;
import com.again.Sprite;
import com.again.SpriteSheet;
import com.again.keyboard;

import ui.UIActionListener;
import ui.UIButton;
import ui.UIButtonListener;
import ui.UILabel;
import ui.UIManager;
import ui.UIPanel;
import ui.UIProgressBar;
import util.ImageUtils;
import util.Vector2i;


public class Player extends Mob{
	private String name;
	private keyboard input;
	private Sprite sprite;
	private int anim = 0;
	private boolean walking = false;
	private int fireRate = 0;
	
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.player_down,32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.player_up,32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.player_left,32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_right,32, 32, 3);
	
	private AnimatedSprite animSprite = down;
	
	private UIManager ui;
	private UIProgressBar uiHealthBar;
	private UIButton button;
	private BufferedImage image;
	
	public Player(String name,keyboard input)
	{
		this.input = input;
		this.name = name;
		sprite = sprite.player_forward;
		//animSprite = down;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Player(String name, int x, int y,keyboard input)
	{
		this.name = name;
		this.x = x;
		this.y = y;
		this.input = input;
		fireRate = WizarProjectile.FIRE_RATE;
		ui = Game.getUIManager();
		UIPanel panel = (UIPanel)new UIPanel(new Vector2i((300-80)*3,0),new Vector2i(80 * 3, 168 * 3)).setColor(0x4f4f4f);
		ui.addPanel(panel);
		UILabel nameLabel = new UILabel(new Vector2i(10,200),name);
		nameLabel.setColor(0xbbbbff);
		nameLabel.setFont(new Font("Verdarn", Font.PLAIN,24));
		nameLabel.dropShadow = true;
		panel.addComponent(nameLabel);
		
		uiHealthBar = new UIProgressBar(new Vector2i(10,215),new Vector2i(220,20));
		uiHealthBar.setColor(0x6a6a6a);
		uiHealthBar.setForegroundColor(0xee3030);
		panel.addComponent(uiHealthBar);
		UILabel hpLabel = new UILabel(new Vector2i(uiHealthBar.position).add(new Vector2i(2,16)),"HP");
		hpLabel.setColor(0xffffff);
		hpLabel.setFont(new Font("Verdana", Font.PLAIN,18));
		panel.addComponent(hpLabel);
		health = 100;
		
		button = new UIButton(new Vector2i(10,260), new Vector2i(100,30),new UIActionListener() 
		{
			public void perform()
			{
				System.out.println("Action Performed!");
			}
		});
		
		button.setText("Hello");
		panel.addComponent(button);
		
		try
		{
			image = ImageIO.read(new File("resa/textures/sheets/home.png"));
			//System.out.println(image.getType());
		} catch (IOException e) 
		{
			e.printStackTrace();
		}

		UIButton imageButton = new UIButton(new Vector2i(10, 360), image, new UIActionListener() 
		{
			public void perform()
			{
				System.exit(0);
			}
		});
		imageButton.setButtonListener(new UIButtonListener()
		{
			public void entered(UIButton button)
			{
				button.setImage(ImageUtils.changeBrightness(image, -50));
			}

			public void exited(UIButton button) 
			{
				button.setImage(image);
			}

			public void pressed(UIButton button) 
			{
				button.setImage(ImageUtils.changeBrightness(image, 50));
			}

			public void released(UIButton button)
			{
				button.setImage(image);
			}
		});
		panel.addComponent(imageButton);
	}
	
	public void update()
	{

		if(walking)animSprite.update();
		else animSprite.setFrame(0);
		if(fireRate > 0)fireRate--;
		double xa = 0, ya = 0;
		double speed =2.5;
		if(anim < 7500) anim++;
		else anim = 0;
		if(input.up) 
		{
			ya-= speed;
			animSprite = up;
		}
		else if(input.down) 
		{
			ya+= speed;
			animSprite = down;
		}
		 if(input.left) 
		{
			xa-= speed;
			animSprite = left;
		}
		 else if(input.right) 
		{
			xa+= speed;
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
		clear();
		updateShooting();
		health = 90;
		uiHealthBar.setProgress(health / 100.0);
	}
	
	private void clear() 
	{
		for(int  i = 0; i < level.getProjectiles().size(); i++)
		{
			Projectile p = level.getProjectiles().get(i);
			if(p.isRemove())level.getProjectiles().remove(i);
		}
	}

	private void updateShooting()
	{
		if(Mouese.getX() > 660)return;
		if(Mouese.getButton()==1 && fireRate <= 0)
		{
			double dx = Mouese.getX() - Game.getWindowWidth() / 2;
			double dy = Mouese.getY() - Game.getWindowHeight() / 2;
			double dir = Math.atan2(dy, dx);
			//double trueangle = dir * 180/Math.PI;
			//System.out.println("truedir" + trueangle);
			shoot(x,y,dir);
			fireRate = WizarProjectile.FIRE_RATE;
		}
		
	}
	
	protected boolean collision(double xa, double ya)
	{
		boolean solid = false;
		for(int c = 0; c < 4; c++)
		{
			double xt = ((x + xa) - c % 2 * 2 - 6) / 16;
			double yt = ((y + ya) - c / 2 * 4 + 3) / 16;
			int ix = (int)Math.ceil(xt);
			int iy = (int)Math.ceil(yt);
			if(c % 2 == 0) ix = (int) Math.floor(xt);
			if(c / 2 == 0) iy = (int) Math.floor(yt);
			if(level.getTile(ix,iy).solid())solid = true;
			//if(level.getTile((int)xt,(int)yt).solid())solid = true;
		}
		return solid;
	}
	
	public void render(Screen screen)
	{
		int flip = 0;
	/*
		if(dir == 0)
		{
			sprite = sprite.player_forward;
			if(walking)
			{
				if(anim % 20 > 10)
				{
					sprite = sprite.player_forward_1;
				}
				else
				{
					sprite = sprite.player_forward_2;
				}
			}
		}
		if(dir == 1)
		{
			sprite = sprite.player_side;
			if(walking)
			{
				if(anim % 20 > 10)
				{
					sprite = sprite.player_side_1;
				}
				else
				{
					sprite = sprite.player_side_2;
				}
			}
		}
		
		if(dir == 2)
			{
				sprite = sprite.player_back;
				if(walking)
				{
					if(anim % 20 > 10)
					{
						sprite = sprite.player_back_1;
					}
					else
					{
						sprite = sprite.player_back_2;
					}
				}
			}

		if(dir == 3)
			{
				sprite = sprite.player_side;
				flip = 1;
				if(walking)
				{
					if(anim % 20 > 10)
					{
						sprite = sprite.player_side_1;
					}
					else
					{
						sprite = sprite.player_side_2;
					}
				}
			}
			*/
		sprite = animSprite.getSprite();
		screen.renderMob((int)(x-16), (int)(y-16), sprite,flip);
	}
	

}
