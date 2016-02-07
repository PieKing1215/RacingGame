package me.piekingrace;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Stroke;

public class TriangleE {

	private static final boolean DRAWCENTER = false;
	
	private int x,y,side;
	private Point[] points = new Point[3];
	private Point center;
	private double rot=0; //In Degrees
	private double h;
	private Racecar car;
	
	public TriangleE(int x, int y, int side, Racecar car){
		this.x=x;
		this.y=y;
		this.side=side;
		this.car=car;
		
		h=0.5*Math.sqrt(3)*side;
		
		Point p2=new Point(x,y-Math.round((float)(0.667*h)));
		
		points[0]=new Point(p2.x,p2.y);
		points[1]=new Point(p2.x-(side/2),Math.round((float) (p2.y+h)));
		points[2]=new Point(p2.x+(side/2),Math.round((float) (p2.y+h)));
		
		center= new Point(((side*points[0].x)+(side*points[1].x)+(side*points[2].x))/(3*side), ((side*points[0].y)+(side*points[1].y)+(side*points[2].y))/(3*side));
	}
	
	public void render(Graphics2D g2d){
		Color c=g2d.getColor();
		double angle = Render.degToRad(getRot());
		g2d.rotate(angle, getCenter().x, getCenter().y);
		int[] xs = new int[]{getPoint(0).x, getPoint(1).x, getPoint(2).x};
		int[] ys = new int[]{getPoint(0).y, getPoint(1).y, getPoint(2).y};
		g2d.setColor(Color.DARK_GRAY);
		g2d.rotate(Render.degToRad(-10), x+Math.round((float)(car.getSize()/3.125)), y-Math.round((float)(car.getSize()/12.5))); // 25/3.125 =8;
		g2d.fillOval(x+Math.round((float)(car.getSize()/4.1667)), y-Math.round((float)(car.getSize()/12.5)), Math.round((float)(car.getSize()/3.125)), Math.round((float)(car.getSize()/2.083)));
		g2d.rotate(Render.degToRad(10), x+Math.round((float)(car.getSize()/3.125)), y-Math.round((float)(car.getSize()/12.5)));
		
		g2d.rotate(Render.degToRad(10), x-Math.round((float)(car.getSize()/1.786)), y-Math.round((float)(car.getSize()/8.33)));
		g2d.fillOval(x-Math.round((float)(car.getSize()/1.786)), y-Math.round((float)(car.getSize()/8.33)), Math.round((float)(car.getSize()/3.125)), Math.round((float)(car.getSize()/2.083)));
		g2d.rotate(Render.degToRad(-10), x-Math.round((float)(car.getSize()/1.786)), y-Math.round((float)(car.getSize()/8.33)));
		//g2d.setColor(col);
		g2d.fillPolygon(new Polygon(xs, ys, 3));
		g2d.setColor(Color.BLACK);
		Stroke s=g2d.getStroke();
		g2d.setStroke(new BasicStroke(Math.round((float)(side/8))));
		g2d.drawPolygon(new Polygon(xs, ys, 3));
		g2d.setStroke(s);
		int l=100;
		
		if(DRAWCENTER){
			g2d.setColor(Color.BLUE);
			g2d.drawOval(x-5, y-5, 10, 10);
			g2d.drawOval(getCenter().x-l, getCenter().y-l, 2*l, 2*l);
		}
		
		g2d.rotate(-angle, getCenter().x, getCenter().y);
		g2d.setColor(c);
	}
	
	public Point getPoint(int index){
		Point p=null;
		try{
			p = points[index];
		}catch(ArrayIndexOutOfBoundsException e){
			Main.print(Main.DebugLevel.SEVERE,Main.DebugPriority.NORMAL,"ERROR: INVALID POINT INDEX AT 'TriangleE.getPoint()':");
			e.printStackTrace();
		}
		return p;
	}
	
	public Point getCenter(){
		center= new Point(((side*points[0].x)+(side*points[1].x)+(side*points[2].x))/(3*side), ((side*points[0].y)+(side*points[1].y)+(side*points[2].y))/(3*side));
		return center;
	}
	
	public double getRot(){
		return rot;
	}
	
	public void setRot(double toRot){ //toRot should be in degrees
		
		rot=toRot;
		while(rot>=360){
			rot-=360;
		}
	}
	
	public void setX(int toX){
		x=toX;
		
		double h=0.5*Math.sqrt(3)*side;
		
		Point p2=new Point(x,y-Math.round((float)(0.667*h)));
		
		points[0]=new Point(p2.x,p2.y);
		points[1]=new Point(p2.x-(side/2),Math.round((float) (p2.y+h)));
		points[2]=new Point(p2.x+(side/2),Math.round((float) (p2.y+h)));
		
		center= new Point(((side*points[0].x)+(side*points[1].x)+(side*points[2].x))/(3*side), ((side*points[0].y)+(side*points[1].y)+(side*points[2].y))/(3*side));
	}
	
	public void setY(int toY){
		y=toY;
		
		double h=0.5*Math.sqrt(3)*side;
		
		Point p2=new Point(x,y-Math.round((float)(0.667*h)));
		
		points[0]=new Point(p2.x,p2.y);
		points[1]=new Point(p2.x-(side/2),Math.round((float) (p2.y+h)));
		points[2]=new Point(p2.x+(side/2),Math.round((float) (p2.y+h)));
		
		center= new Point(((side*points[0].x)+(side*points[1].x)+(side*points[2].x))/(3*side), ((side*points[0].y)+(side*points[1].y)+(side*points[2].y))/(3*side));
	}
	
	public Point calcDist(int dist){
		
		double xa=(getCenter().x + dist * Math.cos((getRot()+270)/57.5));
		double ya=(getCenter().y + dist * Math.sin((getRot()+270)/57.5));
		
		if(Math.abs(dist)<=2){
			xa=((getCenter().x) + dist * Math.cos((getRot()+270)/57.5));
			ya=((getCenter().y+1) + dist * Math.sin((getRot()+270)/57.5));
		}
		
		
		
		return new Point(Math.round((float) xa), Math.round((float) ya));
	}
	
}
