package me.piekingrace;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import me.piekingrace.AI.PathLoc;

public class Render extends Canvas {
	
	private static final long serialVersionUID = 1L;
	public static BufferStrategy bs;
	
	public void init(){
		bs=this.getBufferStrategy();
		if(bs==null){
			this.createBufferStrategy(2);
			bs=this.getBufferStrategy();
		}
		//System.out.println(bs);
	}
	
	@Override
	public void paint(Graphics g){
		//bs.show();
		g= Main.image.getGraphics();
		
		Graphics2D g2d = (Graphics2D) g;
		//g.drawImage(Main.image, 0, 0, null);
		//g.setColor(new Color(63, 191, 63));
		//g.fillRect(0, 0, Main.image.getWidth(), Main.image.getHeight());
		//g.setColor(new Color(31,191,31));
		
		//g.fillRect(0,0,Main.WIDTHC,Main.HEIGHTC);
		/*g.setColor(new Color(63, 191, 63));
		g.fillRect(0,0,Main.WIDTHC,Main.HEIGHTC);
		g.setColor(Color.DARK_GRAY);
		Stroke s=g2d.getStroke();
		g2d.setStroke(new BasicStroke(10));
		g.drawRect(0,0,Main.WIDTHC,Main.HEIGHTC);
		g2d.setStroke(s);*/
		
		g.setColor(Color.BLACK);
		
		//for(Racecar c: Main.cars){
		//	g.clearRect(c.getTri().getCenter().x-Math.round((float)(c.getSize()*1.5)), c.getTri().getCenter().y-(c.getSize()*3), c.getSize()*3, c.getSize()*4);
		//}
		Main.level.render(g);
		
		for(Racecar c: Main.cars){
			c.render(g2d);
		}
		
		if(Level.showAiPaths){
			for(int i = 0; i < Main.level.aiPath.length; i++){
				PathLoc p = Main.level.aiPath[i];
				
				int b = i - 1;
				
				if(b < 0){
					b = Main.level.aiPath.length - 1;
				}
				
				PathLoc p2 = Main.level.aiPath[b];
				
				Graphics2D g2d2 = (Graphics2D) g;
				g2d2.setStroke(new BasicStroke(3));
				g2d2.setColor(Color.CYAN);
				
				g2d2.drawLine(p2.x, p2.y, p.x, p2.y);
				g2d2.drawLine(p.x, p2.y, p.x, p.y);
				
				g2d2.fillRect(p.x-4, p.y-4, 9, 9);
			}
		}

	}
	
	public static double degToRad(double deg){
		return (deg*Math.PI)/180;
	}
	
	public static double radToDeg(double rad){
		return (rad*180)/Math.PI;
	}
	
}
