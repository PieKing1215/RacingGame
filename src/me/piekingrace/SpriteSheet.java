package me.piekingrace;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class SpriteSheet {

	public String path;
	public int width;
	public int height;
	
	public int[] pixels;
	
	public SpriteSheet(String path){
		BufferedImage image = null;
		try {
			image = ImageIO.read(SpriteSheet.class.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(image == null) { 
			return;
		}
		
		this.path = path;
		this.width = image.getWidth();
		this.height = image.getHeight();
		
		pixels = image.getRGB(0, 0, width, height, null, 0, width);
		
		for (int i = 0; i < pixels.length; i++){
			pixels[i] = (pixels[i] & 0xff)/64;
		}
	}
	
	public void render(Graphics g, int x, int y, int tileX, int tileY, int scale){
		ImageIcon i = new ImageIcon(Render.class.getResource(Main.sheet.path));
		
		Image b = i.getImage();
		
		Shape oldClip = g.getClip();
		
		g.setClip(x, y, 16 * scale, 16 * scale);
		g.drawImage(b, x - (tileX * 16 * scale), y - (tileY * 16 * scale), b.getWidth(null) * scale, b.getHeight(null) * scale, null);
		
		if((tileX == 7 || tileX == 8) && tileY == 2){
			double fx = Main.level.mapFX[(x / 64) + (y / 64) * Main.level.width];
			if(fx != 0){
				//System.out.println(fx);
				Color c = g.getColor();
				g.setColor(new Color(1f, 1f, 1f, divide(fx, 15)));
				g.fillRect(x - (tileX * 16 * scale),  y - (tileY * 16 * scale), b.getWidth(null) * scale, b.getHeight(null) * scale);
				g.setColor(c);
				Main.level.tileSetFXAt(x, y, (int)fx - 1);
			}
		}
		
		g.setClip(oldClip);
	}
	
	public float divide(double a, double b){
		return (float)a / (float)b;
	}
	
}
