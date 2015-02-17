package com.cp.tank;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

import javax.print.attribute.standard.Finishings;


public class Missile {
	private int x, y;
	public static final int XSPEED = 10;
	public static final int YSPEED = 10;
	public static final int WIDTH = 10;
	public static final int HEIGH = 10;
	public static int d = 0;//杀死坦克的数量
	
	
	Tank.Direction dir;
	private boolean good;
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
	
	public Missile(int x, int y, boolean good, Tank.Direction dir, TankWarClient tc) {
		this(x,y,dir);
		this.tc =tc;
		this.good = good;
	}
	
	public void draw(Graphics g) {
		if(!live) {
			tc.missiles.remove(this);
			return;
		}
		if(this.good){
			Color c = g.getColor();
			g.setColor(Color.magenta);
			g.fillOval(x, y, WIDTH, HEIGH);
			g.setColor(c);
		}
		else {
			Color c = g.getColor();
			g.setColor(Color.pink);
			g.fillOval(x, y, WIDTH, HEIGH);
			g.setColor(c);
		}
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
			//tc.missiles.remove(this);
		}
		
		
	}
	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH, HEIGH);
	}
	
	boolean hitTank(Tank t) {
		if(this.live &&this.getRect().intersects(t.getRect()) && t.isLive() && this.good != t.isGood()) {
			if(t.isGood()) {
				t.setLife(t.getLife() - 20);
				if(t.getLife()<=0) t.setLive(false);
			} 
			else 
			t.setLive(false);
			this.live = false;
			Explode e = new Explode(x, y, tc);
			tc.explodes.add(e);
			
			return true;
		}
		return false;
	}

	public boolean hitTanks(List<Tank> tanks) {
		for(int i=0; i<tanks.size(); i++) {
			if(hitTank(tanks.get(i))) {
				d++;
				return true;
			}
			
		}
		return false;
	}
	
	public boolean hitWall(Wall w) {
		if(this.live && this.getRect().intersects(w.getRect())) {
			this.live = false;
			return true;
		}
		return false;
	}
}








