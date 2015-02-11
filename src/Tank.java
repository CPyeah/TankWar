import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Random;

import org.omg.CORBA.PUBLIC_MEMBER;


public class Tank {
	private int x, y;
	private int oldX, oldY;
	public static final int XSPEED = 5;
	public static final int YSPEED = 5;
	public static final int WIDTH = 30;
	public static final int HEIGH = 30;
	private static Random  r = new Random();
	private boolean live = true;
	private boolean bL = false, bU = false, bR = false, bD = false;
	enum Direction {L, LU, U, RU, R, RD, D, LD, STOP};
	private Direction dir = Direction.STOP;
	private boolean good;
	

	TankWarClient tc;
	private Direction ptDir = Direction.D;
	private int step = 0;
	public Tank(int x, int y, boolean good) {
		
		this.x = x;
		this.y = y;
		this.oldX = x;
		this.oldY = y;
		this.good = good;
	}
	
	public Tank(int x, int y, boolean good, Direction dir, TankWarClient tc) {
		this(x,y,good);
		this.tc = tc;
		this.dir = dir;
	}
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}
	
	public boolean  isGood() {
		return good;
	}

	public void draw(Graphics g) {
		if(!live) {
			if(!good){
				tc.tanks.remove(this);
			}
			return;
		}
		Color c = g.getColor();
		if(good) g.setColor(Color.red);
		else g.setColor(Color.white);
		g.fillOval(x, y, WIDTH, HEIGH);//»­Ô²µÄ·½·¨
		g.setColor(c);
		switch(ptDir) {
		case L: 
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGH/2, x, y + Tank.HEIGH/2);
			break;
		case LU:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGH/2, x, y);
			break;
		case U:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGH/2, x + Tank.WIDTH/2, y );
			break;
		case RU:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGH/2, x + Tank.WIDTH, y);
			break;
		case R:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGH/2, x + Tank.WIDTH, y + Tank.HEIGH/2);
			break;
		case RD:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGH/2, x + Tank.WIDTH, y + Tank.HEIGH);
			break;
		case D:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGH/2, x + Tank.WIDTH/2, y + Tank.HEIGH);
			break;
		case LD:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGH/2, x, y + Tank.HEIGH);
			break;
		}
		move();
	}
	
	private void stay() {
		x = this.oldX;
		y = this.oldY;
	}
	
	void move() {
		
		this.oldX = x;
		this.oldY = y;
		
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
		case STOP:
			break;
		}
		
		if(this.dir != Direction.STOP) {
			this.ptDir = this.dir;
		}
		
		if(!good) {
			Direction[] dirs = Direction.values();
			if(step==0) {
				step = r.nextInt(12) + 3;
				int rn = r.nextInt(dirs.length);
				dir = dirs[rn];
			}
			step--;
			
			if(r.nextInt(30)>28) 
				this.fire();
			
		}
		
		if(x<0) x = 0;
		if(y<30) y = 30;
		if(x>TankWarClient.GAME_WIDTH-Tank.WIDTH)  x =  TankWarClient.GAME_WIDTH-Tank.WIDTH;
		if(y>TankWarClient.GAME_HEIGTH-Tank.HEIGH) y = TankWarClient.GAME_HEIGTH-Tank.HEIGH;
	}
	
	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_LEFT:
			bL = true;
			break;
		case KeyEvent.VK_UP:
			bU = true;
			break;
		case KeyEvent.VK_RIGHT:
			bR = true;
			break;
		case KeyEvent.VK_DOWN:
			bD = true;
			break;
		}
		locateDirction();  
	}

	void locateDirction() {
		if(bL && !bU && !bR && !bD)
			dir = Direction.L;
		else if(bL && bU && !bR && !bD)
			dir = Direction.LU;
		else if(!bL && bU && !bR && !bD)
			dir = Direction.U;
		else if(!bL && bU && bR && !bD)
			dir = Direction.RU;
		else if(!bL && !bU && bR && !bD)
			dir = Direction.R;
		else if(!bL && !bU && bR && bD)
			dir = Direction.RD;
		else if(!bL && !bU && !bR && bD)
			dir = Direction.D;
		else if(bL && !bU && !bR && bD)
			dir = Direction.LD;
		else if(!bL && !bU && !bR && !bD)
			dir = Direction.STOP ;
	}



	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		switch(key) {
		case KeyEvent.VK_CONTROL:
			fire();
			break;
		case KeyEvent.VK_LEFT:
			bL = false;
			break;
		case KeyEvent.VK_UP:
			bU = false;
			break;
		case KeyEvent.VK_RIGHT:
			bR = false;
			break;
		case KeyEvent.VK_DOWN:
			bD = false;
			break;
		}
		locateDirction(); 
	}
	
	public Missile fire() {
		if(!live) 
			return null;
		int x = this.x + Tank.WIDTH/2 - Missile.WIDTH/2;
		int y = this.y + Tank.HEIGH/2 - Missile.HEIGH/2;
		Missile m = new Missile(x, y, good, ptDir, this.tc );
		tc.missiles.add(m);
		return m;
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH, HEIGH);
	}
	
	public boolean hitsWall(Wall w) {
		if(this.live && this.getRect().intersects(w.getRect())) {
			this.stay();
			return true;
		}
		return false;
	}
}













