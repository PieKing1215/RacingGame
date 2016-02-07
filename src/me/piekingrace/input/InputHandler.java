package me.piekingrace.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import me.piekingrace.Main;
import me.piekingrace.Racecar;
import me.piekingrace.Render;

public class InputHandler implements KeyListener , MouseListener{

    public InputHandler(Render panel) {
        panel.addKeyListener(this);
        panel.addMouseListener(this);
    }

    public class Key {
        private int numTimesPressed = 0;
        private boolean pressed = false;

        public int getNumTimesPressed() {
            return numTimesPressed;
        }

        public boolean isPressed() {
            return pressed;
        }

        public void toggle(boolean isPressed) {
            pressed = isPressed;
            if (isPressed) numTimesPressed++;
        }
    }

    public Key w = new Key();
    public Key s = new Key();
    public Key a = new Key();
    public Key d = new Key();
    public Key up = new Key();
    public Key down = new Key();
    public Key left = new Key();
    public Key right = new Key();
    public Key space = new Key();
    public Key esc = new Key();
    
    public void keyPressed(KeyEvent e) {
        toggleKey(e.getKeyCode(), true);
    }

    public void keyReleased(KeyEvent e) {
        toggleKey(e.getKeyCode(), false);
    }

    public void keyTyped(KeyEvent e) {
    }

    public void toggleKey(int keyCode, boolean isPressed) {
        if (keyCode == KeyEvent.VK_ESCAPE) {
            esc.toggle(isPressed);
        }
        if (keyCode == KeyEvent.VK_W) {
            w.toggle(isPressed);
        }
        
        if (keyCode == KeyEvent.VK_S) {
            s.toggle(isPressed);
        }
        if (keyCode == KeyEvent.VK_A) {
            a.toggle(isPressed);
        }
        if (keyCode == KeyEvent.VK_D) {
            d.toggle(isPressed);
        }
        //============================================================
        if (keyCode == KeyEvent.VK_UP) {
            up.toggle(isPressed);
        }
        if (keyCode == KeyEvent.VK_DOWN) {
            down.toggle(isPressed);
        }
        if (keyCode == KeyEvent.VK_LEFT) {
            left.toggle(isPressed);
        }
        if (keyCode == KeyEvent.VK_RIGHT) {
            right.toggle(isPressed);
        }
        if (keyCode == KeyEvent.VK_SPACE) {
            space.toggle(isPressed);
        }
        
        if (keyCode == KeyEvent.VK_R) {
            for(Racecar c:Main.cars){
            	c.reset();
            }
            //Main.gr.clearRect(0, 0, Main.display.getContentPane().getWidth(), Main.display.getContentPane().getHeight());
        }
    }

	public void mouseClicked(MouseEvent arg0) {
		
	}

	public void mouseEntered(MouseEvent arg0) {
		
	}

	public void mouseExited(MouseEvent arg0) {
		
	}

	public void mousePressed(MouseEvent arg0) {
		
	}

	public void mouseReleased(MouseEvent arg0) {
		
	}
}