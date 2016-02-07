package me.piekingrace;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

import me.piekingrace.input.InputHandler;

public class Main extends JFrame implements ActionListener {
	
	private static boolean dev=true;
	
	public static int time=0;
    
    public static Level level = new Level(12, 9);
    
	public static Racecar player1= new Racecar(550, 80, "Player 1", "15", (byte) 25);
	public static Racecar player2= new Racecar(650, 250, "Player 2", "8", (byte) 25);
	
	public static Racecar ai1= new Racecar(550, 100, "AI 1", "88", (byte) 25);
	public static Racecar ai2= new Racecar(550, 300, "AI 2", "4", (byte) 25);
	
	public static List<Racecar> cars= new ArrayList<Racecar>();
	
	public static InputHandler input;
	
	private static String title="Racing";
	private static String version="0.0.5";
	
	public static final int WIDTH=800-26;
	public static final int HEIGHT=600+4;
	public static int WIDTHC;
	public static int HEIGHTC;
	private static final long serialVersionUID = 1L;
	public static Render canvas = new Render();
	public static Main display;
	
	public static BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
    public static int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	
    public static Graphics gr;
    
    public Timer timer;
    
    public static SpriteSheet sheet = new SpriteSheet("/textures/spritesheet.png");
    
	public static void main(String[] args){
		display = new Main();
	}
	
	public Main(){
		
		boolean running=true;
		setLayout(new BorderLayout());
		Dimension d = new Dimension(WIDTH, HEIGHT);
		setMinimumSize(d);
		setMaximumSize(d);
		setSize(d);
		setResizable(false);
		setTitle(title+" v"+version);
		add("Center", canvas);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		canvas.setBackground(new Color(63, 191, 63));
		repaint();
		requestFocus();
		//pack();
		WIDTHC=getContentPane().getWidth();
		HEIGHTC=getContentPane().getHeight();
		//setIgnoreRepaint(true);
		
		cars.add(player1);
		cars.add(player2);
		cars.add(ai1);
		cars.add(ai2);
		
		input = new InputHandler(canvas);
		
		canvas.init();
		
		gr= canvas.getGraphics();
		
		timer = new Timer(10, this);
		timer.setRepeats(true);
		timer.start();
		
		
	}
	
	private void tick(){
		//System.out.println(player1.speed);
		if(time%3==0){
			//canvas.paint(canvas.bs.getDrawGraphics());
			gr.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
			canvas.paint(gr);
		}
			for(Racecar c:cars){
				c.tick();
			}
			if(time%5==0){
			if(input.w.isPressed()){
				player1.calcmove(1);
			}
			if(input.s.isPressed()){
				player1.calcmove(-1);
			}
			if(input.up.isPressed()){
				player2.calcmove(1);
			}
			if(input.down.isPressed()){
				player2.calcmove(-1);
			}
			}
			
			//still tick %1000, but needed seperator 
			
			int rotSpeed = 3;
			
			if(input.s.isPressed()){
				if(input.a.isPressed()){
					player1.setRot(player1.getRot()+rotSpeed);
				}
				if(input.d.isPressed()){
					player1.setRot(player1.getRot()-rotSpeed);
				}
			}else{
				if(input.a.isPressed()){
					player1.setRot(player1.getRot()-rotSpeed);
				}
				if(input.d.isPressed()){
					player1.setRot(player1.getRot()+rotSpeed);
				}	
			}
			if(input.down.isPressed()){
				if(input.left.isPressed()){
					player2.setRot(player2.getRot()+rotSpeed);
				}
				if(input.right.isPressed()){
					player2.setRot(player2.getRot()-rotSpeed);
				}
			}else{
				if(input.left.isPressed()){
					player2.setRot(player2.getRot()-rotSpeed);
				}
				if(input.right.isPressed()){
					player2.setRot(player2.getRot()+rotSpeed);
				}	
			}
		time++;
	}
	
	public static void delay(int millis){
		long timeOld = System.currentTimeMillis();
		long timeNew = timeOld+millis;
		while(System.currentTimeMillis()<timeNew){
			
		}
	}
	
	public static enum DebugLevel {
        INFO, WARNING, SEVERE;
    }
    
    public static enum DebugPriority {
        DEV, NORMAL;
    }
	
	public static void print(DebugLevel l, DebugPriority p, String msg){
		if(p==DebugPriority.DEV){
			switch (l) {
			case INFO:
				System.out.println("["+title+"] [Info] "+msg);
				break;
			case WARNING:
				System.out.println("["+title+"] [WARNING] "+msg);
				break;
			case SEVERE:
				System.out.println("["+title+"] [SEVERE] "+msg);
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		tick();
	}
	
}
