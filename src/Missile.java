import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.print.attribute.standard.Finishings;


public class Missile {
	private int x, y;
	public static final int XSPEED = 10;
	public static final int YSPEED = 10;
	public static final int WIDTH = 10;
	public static final int HEIGH = 10;
	
	Tank.Direction dir;
	private boolean live = true;
	private TankWarClient tc;
	
	public boolean isLive() {
		return live;
	}

	public Missile(int x, int y, Tank.Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	public Missile(int x, int y, Tank.Direction dir, TankWarClient tc) {
		this(x,y,dir);
		this.tc =tc;
	}
	
	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.BLACK);
		g.fillOval(x, y, WIDTH, HEIGH);
		g.setColor(c);
		move();
		
	}
	
	private void move() {
		switch(dir) {
		case L: 
			x -= XSPEED;
			break;
		case LU:
			x -= XSPEED;
			y -= YSPEED;
			break;
		case U:
			y -= YSPEED;
			break;
		case RU:
			x += XSPEED;
			y -= YSPEED;
			break;
		case R:
			x += XSPEED;
			break;
		case RD:
			x += XSPEED;
			y += YSPEED;
			break;
		case D:
			y += YSPEED;
			break;
		case LD:
			x -= XSPEED;
			y += YSPEED;
			break;
		}
		if(x <0 || y < 0 || x > TankWarClient.GAME_WIDTH || y > TankWarClient.GAME_HEIGTH){
			live = false;
			tc.missiles.remove(this);
		}
		
		
	}
	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH, HEIGH);
	}
	
	boolean hitTank(Tank t) {
		if(this.getRect().intersects(t.getRect()) && t.isLive()) {
			t.setLive(false);
			this.live = false;
			Explode e = new Explode(x, y, tc);
			tc.explodes.add(e);
			return true;
		}
		return false;
	}

	
}
