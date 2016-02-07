package me.piekingrace;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import me.piekingrace.AI.PathLoc;

public class Level {

	public static boolean showAiPaths = false;
	public Tile[] tileMap;
	public int[] mapFX;
	public int width, height;
	
	public PathLoc[] aiPath;
	
	public Level(int width, int height){
		
		this.width = width;
		this.height = height;
		
		tileMap = new Tile[width * height];
		mapFX = new int[width * height];
		
		load(JOptionPane.showInputDialog(this, "Please Enter a Level To Play."));
		
	}
	
	public void load(String path){
		Scanner s = null;
		
		try{
			s = new Scanner(Level.class.getResourceAsStream("/tracks/"+path+"/"+path+".txt"));
		}catch(NullPointerException e){
			s = new Scanner(Level.class.getResourceAsStream("/tracks/error.txt"));
		}
		
		int count = 0;
		
		while (s.hasNextLine()) {
			count++;
			s.nextLine();
		}
		
		s.close();
		
		try{
			s = new Scanner(Level.class.getResourceAsStream("/tracks/"+path+"/"+path+".txt"));
		}catch(NullPointerException e){
			s = new Scanner(Level.class.getResourceAsStream("/tracks/error.txt"));
		}
		
		aiPath = new PathLoc[count];
		
		for(int i = 0; i < count; i++){ //544 322 0-90
			String s2 = s.nextLine();
			String x = s2.substring(0, 3);
			String y = s2.substring(4, 7);
			String rot = s2.substring(8, 12);
			
			//System.out.println(s2 +" | "+x+" "+y+" "+rot);
			
			aiPath[i] = new PathLoc(Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(rot));
		}
		
		s.close();
		
		//aiPath[0] = new PathLoc(64 * 8 + 32, 64 * 5 + 32, -90);
		//aiPath[1] = new PathLoc(64 * 6, 64 * 2 + 16, 0);
		//aiPath[2] = new PathLoc(64 * 2, 64 * 7 + 32, -90);
		//aiPath[3] = new PathLoc(64 * 10 + 32, 64 * 1 + 16, 90);
		
		BufferedImage b = null;
		try {
			b = ImageIO.read(Level.class.getResourceAsStream("/tracks/"+path+"/"+path+".png"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		int[] pixels;
		
		this.width = b.getWidth();
		this.height = b.getHeight();
		
		pixels = b.getRGB(0, 0, width, height, null, 0, width);
		
		tileMap = new Tile[b.getWidth() * b.getHeight()];
		
		for(int x = 0; x < b.getWidth(); x++){
			for(int y = 0; y < b.getHeight(); y++){
				tile: for(Tile t : Tile.tiles){
					tileMap[x + y * b.getWidth()] = Tile.GRASS;
					if(pixels[x + y * b.getWidth()] == t.color){
						tileMap[x + y * b.getWidth()] = t;
						
						if(t == Tile.BOOST_LR || t == Tile.BOOST_UD){
							//System.out.println(x + " B "+ y);
							mapFX[x + y * b.getWidth()] = 5;
						}else{
							//System.out.println(x + " "+ y);
							mapFX[x + y * b.getWidth()] = -50;
						}
						break tile;
					}
				}
			}
		}
	}
	
	public void generate(){
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				if(y == 0 || x == 0 || y == height-1 || x == width-1){
					tileMap[x + y * width] = Tile.WALL;
				}else{
					tileMap[x + y * width] = Tile.GRASS;
				}
			}
		}
	}
	
	public void render(Graphics g){
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				tileMap[x + y * width].render(g, x * 64, y * 64);
			}
		}
	}
	
	public Tile tileAt(int x, int y){
		return tileMap[Math.round((float)(x / 64)) + Math.round((float)(y / 64)) * width];
	}
	
	public void tileSetFXAt(int x, int y, int set){
		mapFX[Math.round((float)(x / 64)) + Math.round((float)(y / 64)) * width] = set;
	}
	
	public int tileFXAt(int x, int y){
		return mapFX[Math.round((float)(x / 64)) + Math.round((float)(y / 64)) * width];
	}
	
}
