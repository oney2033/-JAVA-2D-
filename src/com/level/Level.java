package com.level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.again.Screen;
import com.again.Sprite;
import com.spawner.ParticleSpawner;
import com.spawner.Spawner;

import entity.Entity;
import entity.Particle;
import entity.Player;
import entity.Projectile;
import util.Vector2i;

public class Level {
	protected int width, height;
	protected int[] tilesInt;
	protected int[] tiles;
	//protected Level level;
	//protected Tile[] tiles;
	
	private List<Entity>entities = new ArrayList<Entity>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();
	private List<Particle> particles = new ArrayList<Particle>();
	public List<Entity> toplayer = new ArrayList<Entity>();
	
	private List<Player>players = new ArrayList<Player>();
	

	
	public static Level spawn = new SpawnLevel("resa/textures/levels/spawn.png");
	
	
	public Level(int width, int height)
	{
		this.width = width;
		this.height = height;
		tilesInt = new int[width * height];
		generateLevel();
	}
	
	public Level(String path)
	{
		loadLevel(path);
		generateLevel();
		
	}
	
	
	
	protected void generateLevel() {
		
	}
	
	protected void loadLevel(String path)
	{
		
	}
	
	public void update()
	{
		for(int i = 0; i < entities.size(); i++)
		{
			entities.get(i).update();
		}
		
		for(int i = 0; i < projectiles.size(); i++)
		{
			projectiles.get(i).update();
		}
		
		for(int i = 0; i < particles.size(); i++)
		{
			particles.get(i).update();
		}
		for(int i = 0; i < players.size(); i++)
		{
			players.get(i).update();
		}
		remove();
	}
	
	private void remove()
	{
		for(int i = 0; i < entities.size(); i++)
		{
			if(entities.get(i).isRemove())entities.remove(i);
		}
		
		for(int i = 0; i < projectiles.size(); i++)
		{
			if(projectiles.get(i).isRemove())projectiles.remove(i);
		}
		
		for(int i = 0; i < particles.size(); i++)
		{
			if(particles.get(i).isRemove())particles.remove(i);
		}
		
		for(int i = 0; i < players.size(); i++)
		{
			if(players.get(i).isRemove())players.remove(i);
		}
	}
	
	public void time()
	{
		
	}
	
	public boolean tilecollision(int x,int y, int size,int xOffset, int yOffset)
	{
		boolean solid = false;
		for(int c = 0; c < 4; c++)
		{
			int xt = (x - c % 2	* size + xOffset) >> 4;
			int yt = (y - c / 2 * size + yOffset) >> 4;
			//System.out.println("(" + xt	+ "," + yt + ")");
			if(getTile((int)xt,(int)yt).solid())solid = true;
		}
		return solid;
	}
	
	
	
	/*
	public boolean tilecollision(double x, double y, double xa, double ya, int size)
	{
		boolean solid = false;
		for(int c = 0; c < 4; c++)
		{
			int xt = (((int)x + (int)xa) + c % 2 * size / 6-9) / 16;
			int yt = (((int)y + (int)ya) + c / 2 * size / 8) / 16;
			System.out.println("(" + xt	+ "," + yt + ")");
			if(getTile((int)xt,(int)yt).solid())solid = true;
		}
		return solid;
	}
	*/
	public List<Projectile>getProjectiles()
	{
		return projectiles;
	}
	
	public void render(int xScroll, int yScroll,Screen screen)
	{
		screen.setOffset(xScroll, yScroll);
		int x0 = xScroll>>4;
		int x1 = (xScroll + screen.width + 16)>>4;
		int y0 = yScroll>>4;
		int y1 = (yScroll + screen.height + 16)>>4;
		
		for(int y = y0; y < y1; y++)
		{
			for(int x = x0; x < x1; x++)
			{
				getTile(x,y).render(x, y, screen);
			}
		}
		for(int i = 0; i < entities.size(); i++)
		{
			entities.get(i).render(screen);
		}
		
		for(int i = 0; i < projectiles.size(); i++)
		{
			projectiles.get(i).render(screen);
		}
		
		for(int i = 0; i < particles.size(); i++)
		{
			particles.get(i).render(screen);;
		}
		
		for(int i = 0; i < players.size(); i++)
		{
			players.get(i).render(screen);;
		}
		
	}
	
	public void add(Entity e)
	{
		e.init(this);
		if(e instanceof Particle)
		{
			particles.add((Particle)e);
		}
		else if(e instanceof Projectile)
		{			
			projectiles.add((Projectile)e);
		}
		else if(e instanceof Player)
		{
			players.add((Player)e);
		}
		else
		{
			entities.add(e);
		}
	}
	
	public List<Player> getPlayer()
	{
		return players;
	}
	
	public Player getPlayerAt(int index)
	{
		return players.get(index);
	}
	
	public Player getClientPlayer()
	{
		return players.get(0);
	}
	
	// A * �����㷨
	public List<Node> findPath(Vector2i start, Vector2i goal)
	{
		List<Node> openList = new ArrayList<Node>();
		List<Node> closedList = new ArrayList<Node>();
		Node current = new Node(start, null, 0, getDistance(start,goal));
		openList.add(current);
		while(openList.size() > 0)
		{
			Collections.sort(openList,nodeSorter);
			current = openList.get(0);
			if(current.tile.equals(goal))
			{
				//return
				List<Node>path = new ArrayList<Node>();
				while(current.parent != null)
				{
					path.add(current);
					current = current.parent;
				}
				openList.clear();
				closedList.clear();
				return path;
			}
			openList.remove(current);
			closedList.add(current);
			for(int i = 0; i < 9; i++)
			{
				if(i == 4)continue;
				int x = current.tile.getX();
				int y = current.tile.getY();
				int xi = (i % 3) -1;
				int yi = (i / 3) -1;
				Tile at = getTile(x + xi, y + yi);
				if(at == null)continue;
				if(at.solid())continue;
				Vector2i a = new Vector2i(x + xi, y + yi);
				double gCost = current.gCost + (getDistance(current.tile,a)==1?1:0.95);
				double hCost = getDistance(a,goal);
				Node node = new Node(a, current, gCost, hCost);
				if(vecInList(closedList, a) && gCost >= node.gCost)continue;
				if(!vecInList(openList, a) || gCost < node.gCost)openList.add(node);
			}
		}
		closedList.clear();
		return null;
	}
	
	private Comparator<Node> nodeSorter = new Comparator<Node>()
	{
		public int compare(Node n0, Node n1)
		{	
			if(n1.fCost < n0.fCost) return +1;
			if(n1.fCost > n0.fCost) return -1;
			return 0;
		}
	};
	
	private boolean vecInList(List<Node> list, Vector2i vector)
	{
		for(Node n : list)
		{
			if(n.tile.equals(vector))return true;
		}
		return false;
	}
	
	public double getDistance(Vector2i tile, Vector2i goal)
	{
		double dx = tile.getX() - goal.getX();
		double dy = tile.getY() - goal.getY();
		return Math.sqrt(dx * dx + dy * dy);
	}

	public void addProjectile(Projectile p)
	{
		p.init(this);
		projectiles.add(p);
	}
	
	public List<Entity> getEntities(Entity e, int radius)
	{
		List<Entity>result = new ArrayList<Entity>();
		int ex = (int)e.getX();
		int ey = (int)e.getY();
		
		for(int i = 0; i < entities.size(); i++)
		{
			Entity entity = entities.get(i);
			if(entity.equals(e))continue;
			if(entity instanceof ParticleSpawner)continue;
			int x = (int)entity.getX();
			int y = (int)entity.getY();
			
			int dx = Math.abs(x - ex);
			int dy = Math.abs(y - ey);
			double distance = Math.sqrt((dx * dx) + (dy * dy));
			if(distance <= radius)result.add(entity);
		}
		return result;
	}
	
	public List<Player>getPlayers(Entity e, int radius)
	{
		List<Player>result = new ArrayList<Player>();
		int ex = (int)e.getX();
		int ey = (int)e.getY();
		for(int i = 0; i < players.size(); i++)
		{
			Player player = players.get(i);
			int x = (int)player.getX();
			int y = (int)player.getY();
			
			int dx = Math.abs(x - ex);
			int dy = Math.abs(y - ey);
			double distance = Math.sqrt((dx * dx) + (dy * dy));
			if(distance <= radius)result.add(player);
		}
		return result;
	}
	
	
	
	//0xff00ff6f
	//0xffe0ff19
	//0xff6d7c0c
	
	public Tile getTile(int x, int y)
	{
		if( x < 0 || y < 0 || x >= width || y >= height) return Tile.voidTile;
		
		if(tiles[x + y * width] ==Tile.col_spawn_floor)return Tile.spawn_floor;
		if(tiles[x + y * width] ==Tile.col_spawn_grass)return Tile.spawn_grass;
		if(tiles[x + y * width] ==Tile.col_spawn_hedge)return Tile.spawn_hedge;
		if(tiles[x + y * width] ==Tile.col_spawn_wall1)return Tile.spawn_wall1;
		if(tiles[x + y * width] ==Tile.col_spawn_wall2)return Tile.spawn_wall2;
		if(tiles[x + y * width] ==Tile.col_spawn_water)return Tile.spawn_water;
		return Tile.voidTile;
		
		//if(tilesInt[x + y * width] ==0)return Tile.grass;
		//if(tilesInt[x + y * width] ==1)return Tile.flower;
		//if(tilesInt[x + y * width] ==2)return Tile.rock;
		//return Tile.voidTile;
	}
	
}
