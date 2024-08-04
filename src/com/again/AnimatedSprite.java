package com.again;

public class AnimatedSprite extends Sprite{
	
	private int frame = 0;
	private Sprite sprite;
	private int rate = 10;
	private int time = 0;
	private int length = -1;
	
	public AnimatedSprite(SpriteSheet sheet, int width, int height, int length)
	{
		super(sheet,width,height);
		this.length = length;
		sprite = sheet.getSprite()[0];
		if(length > sheet.getSprite().length) System.err.println("Error! Length of animation is too long!");
	}
	
	public void update()
	{
		time++;
		if(time % rate ==0)
		{	
			if(frame >= length -1)frame = 0;
			else frame++;
			sprite = sheet.getSprite()[frame];
		}
		//System.out.println(sprite + ", Frame: " + frame);
		
	}
	
	public Sprite getSprite()
	{
		return sprite;
	}
	
	public void setFrameRate(int frames)
	{
		rate = frames;
	}

	public void setFrame(int index) 
	{
		if(index > sheet.getSprite().length -1)
		{
			System.out.println("Index out of bounds in" + this);
			return;
		}
		sprite = sheet.getSprite()[index];	
	}
}
