package com.level;

import com.again.Screen;
import com.again.Sprite;
import com.level.Spwan_Level.SpawnFloorTile;
import com.level.Spwan_Level.SpawnGrassTile;
import com.level.Spwan_Level.SpawnHedgeTile;
import com.level.Spwan_Level.SpawnWallTile;
import com.level.Spwan_Level.SpawnWaterTile;

public class Tile {
	public int x, y;
	public Sprite sprite;
	
	public static Tile grass = new GrassTile(Sprite.grass);
	public static Tile voidTile = new VoidTile(Sprite.voidTile);
	public static Tile flower = new FlowerTile(Sprite.flower);
	public static Tile rock = new RockTile(Sprite.rock);
	
	public static Tile spawn_grass = new SpawnGrassTile(Sprite.spawn_grass);
	public static Tile spawn_hedge = new SpawnHedgeTile(Sprite.spawn_hedge);
	public static Tile spawn_water = new SpawnWaterTile(Sprite.spawn_water);
	public static Tile spawn_wall1 = new SpawnWallTile(Sprite.spawn_wall1);
	public static Tile spawn_wall2 = new SpawnWallTile(Sprite.spawn_wall2);
	public static Tile spawn_floor = new SpawnFloorTile(Sprite.spawn_floor);
	
	public static final int col_spawn_grass = 0xff5EFFA9;
	public static final int col_spawn_hedge = 0;
	public static final int col_spawn_water = 0;
	public static final int col_spawn_wall1 = 0xff808080;
	public static final int col_spawn_wall2 = 0xff303030;
	public static final int col_spawn_floor = 0xffFCFF99;
	
	public Tile(Sprite sprite)
	{
		this.sprite = sprite;
	}
	
	public void render(int x, int y, Screen screen)
	{
		
	}	
	
	public boolean solid()
	{
		return false;
	}
}
