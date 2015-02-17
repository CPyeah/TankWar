package com.cp.tank;
import java.awt.Color;
import java.awt.Graphics;


public class Explode {
	
	private int x, y;
	private boolean live = true;
	
	int[] diameter = {4, 10, 15, 20, 26, 39, 45, 50, 40, 25, 10, 3};
	int step = 0;
	private TankWarClient tc;
	
	public Explode(int x, int y, TankWarClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
	}
	
	public void draw(Graphics g) {
		if(!live) {
			tc.explodes.remove(this);
			return;
		}
		
		if(step == diameter.length) {
			this.live = false;
			this.step = 0;
			return;
		}
		
		Color c = g.getColor();
		g.setColor(Color.orange);
		g.fillOval(x, y, diameter[step], diameter[step]);
		
		g.setColor(c);
		this.step++;
	}
}
