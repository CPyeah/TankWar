import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


public class Blood {
	int x, y, w, h;
	TankWarClient tc;
	int step = 0;
	private boolean live = true;
	
	
	public boolean isLive() {
		return live;
	}


	public void setLive(boolean live) {
		this.live = live;
	}

	private int[][] pos = {
		{0,0}, {5,25}, {10,75}, {75,11}, {75,18},
		{15,20}, {20,30}, {30,35}, {45,40},
		{55,48}, {40,30}
	};
	
	public Blood() {
		x = pos[0][0];
		y = pos[0][1];
		w = h =15;
	}
	
	
	public void draw(Graphics g) {
		if(!live) return;
		
		Color c = g.getColor();
		g.setColor(Color.pink);
		g.fillRect(x, y, w, h);
		g.setColor(c);
		
		this.move();
	}
	
	private void move() {
		step ++;
		if(this.step == pos.length) {
			step = 0;
		}
		x = pos[step][0];
		y = pos[step][1];
		
	}
	
	public  Rectangle getRect() {
		return new Rectangle(x, y, w, h);
		
	}
}

