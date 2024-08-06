package com.again;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Random;

import javax.swing.JFrame;

import com.level.Level;
import com.level.RandomLevel;
import com.level.SpawnLevel;
import com.level.TileCoordinate;

import entity.Player;
import ui.UIComponent;
import ui.UIManager;
import ui.UIPanel;


public class Game extends Canvas implements Runnable 
{
	private static final long serialVersionUID = 6436659312396801788L;
	
	private static int width = 300 - 80 ;
	private static int height = 168;
	//private static int height = width / 16 * 9;
	private static int scale = 3;
	public static String title = "Rain";
	
	private Thread thread;
	private Screen screen;
	private JFrame frame;
	private keyboard key;
	private Level level;
	private Player player;
	private Fonts font;
	//private Mouese mouse;
	private boolean running = false;
	private static UIManager uiManager;
	
	private BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);//创建图像，然后访问图像
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();//将图像对象转换为整数数组
	
	public Game()
	{
		Dimension size = new Dimension(width * scale + 80 * 3, height *scale );
		//Dimension size = new Dimension(width , height );
		setPreferredSize(size);
		uiManager = new UIManager();
		screen = new Screen(width,height);
		frame = new JFrame();
		key = new keyboard();
		//level = new RandomLevel(64,64);
		//level = new SpawnLevel("resa/textures/levels/level.png");
		level = level.spawn;
		TileCoordinate playerSpawn = new TileCoordinate(19,8);
		player = new Player("Hello World",playerSpawn.x(),playerSpawn.y(),key);
		//player.init(level);
		level.add(player);
		font = new Fonts();
		

		addKeyListener(key);
		Mouese mouse = new Mouese();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}
	
	public static int getWindowWidth()
	{
		return width * scale;
	}
	
	public static int getWindowHeight()
	{
		return height * scale;
	}
	
	public static UIManager getUIManager()
	{
		return uiManager;
	}
	
	public synchronized void start()
	{
		running = true;
		thread = new Thread(this,"Display");
		thread.start();
	}
	
	public synchronized void stop()
	{
		running = false;
		try
		{
			thread.join();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	public void run()
	{
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0/60.0;
		double delta = 0;
		int frames =0;
		int updates =0;
		requestFocus();
		while(running)
		{
			long now = System.nanoTime();
			delta +=(now - lastTime)/ns;
			lastTime = now;
			while(delta >= 1)
			{
				update();
				updates++;
				delta--;
			}
			render();
			frames++;
			if(System.currentTimeMillis() - timer > 1000)
			{
				timer +=1000;
				System.out.println(updates + " ups " + frames + " fps ");
				frame.setTitle(title + " | " + updates + " ups " + frames + " fps ");
				updates = 0;
				frames = 0;
			}
		}
	}
	
	//int x =0,y=0;
	public void update()
	{
		key.update();
		//player.update();
		level.update();
		uiManager.update();
	}
	
	public void render()
	{
		BufferStrategy bs =  getBufferStrategy();
		if(bs == null)
		{
			createBufferStrategy(3);
			return;
		}
		
		screen.Clear();
		double xScroll = player.getX() - screen.width/2;
		double yScroll = player.getY() - screen.height/2;
		level.render((int)xScroll, (int)yScroll, screen);
		//uiManager.render(screen);
		//font.render(50, 50, -3, "Hey what's up \nguys, My name is\nThe Comedy!", screen);
		//player.render(screen);
		//screen.renderSheet(40, 40, SpriteSheet.player_down, false);
		for(int i = 0; i < pixels.length; i++)
		{
			pixels[i] = screen.Pixels[i];
		}
		Graphics g = bs.getDrawGraphics();
		//g.setColor(new Color(0xff00ff));
		//g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(image,0,0,width * scale,height * scale,null);
		uiManager.render(g);;
		//g.drawImage(image,0,0,getWidth(),getHeight(),null);
		g.setColor(Color.white);
		g.setFont(new Font("Verdand",0,20));
		g.drawString("X: " + player.getX() + ", Y: " + player.getY(), 150, 100);
		g.dispose();
		bs.show();
		
	}
	
	public static void main(String[] args)
	{
		Game game = new Game();  		// 创建游戏对象
		game.frame.setResizable(false); //设置窗口不可调整大小
		game.frame.setTitle(title);		//设置窗口标题
		game.frame.add(game);			//将游戏添加到窗口
		game.frame.pack();				// 调整窗口大小以适应其子组件
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 设置窗口关闭操作
		game.frame.setLocationRelativeTo(null);// 设置窗口位置
		game.frame.setVisible(true);	// 设置窗口可见
		
		game.start(); // 启动游戏
		
	}
}
