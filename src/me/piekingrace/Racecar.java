package me.piekingrace;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.io.IOException;

import javax.imageio.ImageIO;

import me.piekingrace.AI.PathLoc;
import me.piekingrace.sound.Sound;

public class Racecar {

	private TriangleE tri;
	private int x,y,spawnX,spawnY;
	private double rot;
	public int speed = 0;
	private boolean mover=false;
	private String name="ERROR";
	private byte size;
	private int boostTime = 0;
	private int boostSpeed = 0;
	
	public boolean ai = true;
	
	public int desLocIndex = 0;
	
	public PathLoc desLoc = Main.level.aiPath[desLocIndex];
	
	private Image carImg;
	
	public Racecar(int x, int y, String name, String number, byte size){
		this.name=name;
		this.rot=0;
		this.size=size;
		
		if(name.contains("Player")){
			ai = false;
		}
		
		tri = new TriangleE(x, y, size, this);
		this.x=getTri().getCenter().x;
		this.y=getTri().getCenter().y;
		
		this.spawnX=this.x;
		this.spawnY=this.y;
		try {
			carImg = ImageIO.read(Racecar.class.getResourceAsStream("/textures/cars/Car"+number+".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void tick(){
		
		if(ai){
			if(speed >= 3 + boostSpeed){
				speed = 3 + boostSpeed;
			}
			int range = 10;
			if(getX() - desLoc.x > range){
				setRot(-90);
				calcmove(1);
			}else if(Math.abs(getX() - desLoc.x) > range){ 
				setRot(90);
				calcmove(1);
			}else if(getY() - desLoc.y > range){
				setRot(0);
				calcmove(1);
			}else if(Math.abs(getY() - desLoc.y) > range){
				setRot(180);
				calcmove(1);
			}else if(getRot() > desLoc.rot && false){
				speed = 0;
				setRot(getRot()-1);
			}else if(getRot() < desLoc.rot && false){
				speed = 0;
				setRot(getRot()+1);
			}else{
				
				speed = 0;
				
				desLocIndex++;
				if(desLocIndex >= Main.level.aiPath.length){
					desLocIndex = 0;
				}
				
				desLoc = Main.level.aiPath[desLocIndex];
				//speed = 0;
				
			}
			//System.out.println("Reached "+desLocIndex+" at "+desLoc.x+" "+desLoc.y+" "+desLoc.rot+" | "+getX()+" "+getY()+" "+getRot());
		}
		
		if(boostTime > 0) {
			calcmove(1);
			boostTime--;
		}else if(boostSpeed > 0){
			boostSpeed = 0;
		}
		
		if(!mover&&Main.time%10==0){
			if(speed>0){
				speed--;
			}else if(speed<0){
				speed++;
			}
		}
		
		if(speed!=0){
			move(speed);
		}
		mover=false;
	}
	
	public void render(Graphics2D g2d){
		//getTri().render(g2d);
		
		double angle = Render.degToRad(getRot());
		g2d.rotate(angle, getTri().getCenter().x, getTri().getCenter().y);
		g2d.drawImage(carImg, getTri().getCenter().x - 20, getTri().getCenter().y - 20, 40, 40, null);
		g2d.rotate(-angle, getTri().getCenter().x, getTri().getCenter().y);
		
		Font f = g2d.getFont();
		Font n = new Font("Verdana", 1, 16);
		g2d.setFont(n);
		g2d.setColor(Color.YELLOW);
		//System.out.println((fnt.stringWidth(name)*2));
		g2d.drawString(name, x - (g2d.getFontMetrics().stringWidth(name)/2), y-size);
		g2d.setFont(f);
		g2d.drawLine(x, y, getTri().calcDist(-1).x, getTri().calcDist(-1).y);
	}
	
	public void calcmove(int dir){
		
		if(dir > 0){
			if(speed < Math.round((float)(size / 6))+boostSpeed){
				speed++;
				mover = true;
			}
		}else if(dir < 0){
			if(speed > Math.round((float)(size / -12.5))){
				speed--;
				mover = true;
			}
		}else if(dir == 0){
			if(speed > 0){
				speed--;
			}else if(speed < 0){
				speed++;
			}
		}
		
		//System.out.println(speed);
	}
	
	public void move(int dist){
		//System.out.println("move");
		Point p = getTri().calcDist(speed);
		
		for(int x2=-4; x2<4; x2++){
			for(int y2=-4; y2<4; y2++){
				//System.out.println(Main.level.tileAt(p.x + x2, p.y + y2));
				if(Main.level.tileAt(p.x + x2, p.y + y2).solid){
					if(!Main.level.tileAt(p.x + x2 + 2, p.y + y2).solid || !Main.level.tileAt(p.x + x2 - 1, p.y + y2).solid){
						//System.out.println("1");
						p.setLocation(x, p.y);
					}else{
						//System.out.println("2");
						p.setLocation(p.x, y);
					}
					//speed = 0;
					//return;
				}else if(Main.level.tileAt(getX() + x2, getY() + y2) == Tile.GRASS){
					if(speed > 3 + boostSpeed){
						speed--;
					}
				}else if(Main.level.tileAt(getX() + x2, getY() + y2) == Tile.BOOST_LR || Main.level.tileAt(getX() + x2, getY() + y2) == Tile.BOOST_UD){
					
					if(Main.level.tileFXAt(getX() + x2, getY() + y2) < 8){
						Sound.playSound("boost.wav", 0.5f);
					}
					
					Main.level.tileSetFXAt(getX() + x2, getY() + y2, 10);
					boostTime = 10;
					boostSpeed = 3;
					
				}
				for(Racecar car : Main.cars){
					if(car != this){
						for(int x3=-8; x3<8; x3++){
							for(int y3=-8; y3<8; y3++){
								if(car.getX()+x3 == p.x && car.getY()+y3 == p.y){
									p.setLocation(x, y);
									speed = 0;
								}
							}
						}
					}
				}
			}
		}
		
		setX(p.x);
		setY(p.y);
	}
	
	public void reset(){
		setX(spawnX);
		setY(spawnY);
		speed=0;
		setRot(0);
	}
	
	public String getName(){
		return name;
	}
	
	public byte getSize(){
		return size;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public double getRot(){
		return rot;
	}
	
	public void setX(int toX){
		getTri().setX(toX);
		this.x=getTri().getCenter().x;
	}
	
	public void setY(int toY){
		getTri().setY(toY);
		this.y=getTri().getCenter().y;
	}
	
	public void setRot(double toRot){
		rot=toRot;
		getTri().setRot(rot);
	}

	public TriangleE getTri() {
		return tri;
	}
	
}
