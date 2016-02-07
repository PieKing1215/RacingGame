package me.piekingrace;

import java.awt.Graphics;

public class Tile {

	public int spriteX, spriteY, color;
	public boolean solid = false;
	
	public Tile(int id, int spriteX, int spriteY, int color, boolean solid){
		this.spriteX = spriteX;
		this.spriteY = spriteY;
		this.color = color;
		tiles[id]=this;
		this.solid = solid;
	}
	
	public static final int tileMax = 33;
	public static final Tile[] tiles = new Tile[tileMax]; 
	
	public static final Tile GRASS = new Tile(0, 1, 0, 0xFF00FF00, false);
	
	public static final Tile WALL  = new Tile(1, 0, 0, 0xFF666666, true);
	
	public static final Tile START_LR  = new Tile(2, 2, 0, 0xFFc0c0c0, false);
	public static final Tile START_UD  = new Tile(3, 3, 0, 0xFFc1c1c1, false);
	
	public static final Tile ROAD_TL  = new Tile(4, 4, 0, 0xFF202020, false);
	public static final Tile ROAD_TM  = new Tile(5, 5, 0, 0xFF212121, false);
	public static final Tile ROAD_TR  = new Tile(6, 6, 0, 0xFF222222, false);
	public static final Tile ROAD_ML  = new Tile(7, 4, 1, 0xFF232323, false);
	public static final Tile ROAD_MM  = new Tile(8, 5, 1, 0xFF242424, false);
	public static final Tile ROAD_MR  = new Tile(9, 6, 1, 0xFF252525, false);
	public static final Tile ROAD_BL  = new Tile(10, 4, 2, 0xFF262626, false);
	public static final Tile ROAD_BM  = new Tile(11, 5, 2, 0xFF272727, false);
	public static final Tile ROAD_BR  = new Tile(12, 6, 2, 0xFF282828, false);
	public static final Tile ROAD_O_BR  = new Tile(13, 7, 0, 0xFF292929, false);
	public static final Tile ROAD_O_BL  = new Tile(14, 8, 0, 0xFF2A2A2A, false);
	public static final Tile ROAD_O_TR  = new Tile(15, 7, 1, 0xFF2B2B2B, false);
	public static final Tile ROAD_O_TL  = new Tile(16, 8, 1, 0xFF2C2C2C, false);
	public static final Tile ROAD_UD  = new Tile(19, 3, 1, 0xFF2D2D2D, false);
	public static final Tile ROAD_LR  = new Tile(20, 3, 2, 0xFF2E2E2E, false);
	public static final Tile ROAD_IN_TR  = new Tile(21, 3, 3, 0xFF2F2F2F, false);
	public static final Tile ROAD_IN_TL  = new Tile(22, 4, 3, 0xFF303030, false);
	public static final Tile ROAD_IN_BR  = new Tile(23, 5, 3, 0xFF313131, false);
	public static final Tile ROAD_IN_BL  = new Tile(24, 6, 3, 0xFF323232, false);
	public static final Tile ROAD_IN_TR_L  = new Tile(25, 3, 4, 0xFF333333, false);
	public static final Tile ROAD_IN_BR_L  = new Tile(26, 4, 4, 0xFF343434, false);
	public static final Tile ROAD_IN_TL_B  = new Tile(27, 5, 4, 0xFF353535, false);
	public static final Tile ROAD_IN_TR_B  = new Tile(28, 6, 4, 0xFF363636, false);
	public static final Tile ROAD_IN_BL_R  = new Tile(29, 3, 5, 0xFF383838, false);
	public static final Tile ROAD_IN_TL_R  = new Tile(30, 4, 5, 0xFF393939, false);
	public static final Tile ROAD_IN_BR_T  = new Tile(31, 5, 5, 0xFF3A3A3A, false);
	public static final Tile ROAD_IN_BL_T  = new Tile(32, 6, 5, 0xFF3B3B3B, false);
	
	public static final Tile BOOST_LR  = new Tile(17, 7, 2, 0xFFFF5500, false);
	public static final Tile BOOST_UD  = new Tile(18, 8, 2, 0xFFFF6600, false);
	
	
	public void render(Graphics g, int x, int y){
		Main.sheet.render(g, x, y, spriteX, spriteY, 4);
	}
	
}
