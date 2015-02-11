
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;




public class TankWarClient extends Frame{
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGTH = 600;
	
	Wall w1 = new Wall(100, 150, 20, 200, this);
	Wall w2 = new Wall(300, 400, 150, 10, this);
	Tank myTank = new Tank(350, 500, true, Tank.Direction.STOP, this);
	List<Missile> missiles = new ArrayList<Missile>();
	List<Explode> explodes = new ArrayList<Explode>();
	List<Tank> tanks = new ArrayList<Tank>();
	
	
	Image offScreenImage = null;
	
	public void paint(Graphics g) {
		g.drawString("Missiles count: " + missiles.size(), 10, 50);
		g.drawString("Explodes count: "+ explodes.size(), 10, 70);
		g.drawString(" Tanks count: " + tanks.size(), 10, 90);
		
		myTank.draw(g);
		w1.draw(g);
		w2.draw(g);
		
		
		for(int i=0; i<missiles.size(); i++) {
			Missile m = missiles.get(i);
			//if( !m.isLive()) missiles.remove(m);
			//else m.draw(g);
			m.hitTanks(tanks);
			m.hitTank(myTank);
			m.draw(g);
			m.hitWall(w1);
			m.hitWall(w2);
			
		}
		
		for(int i=0; i<explodes.size(); i++) {
			Explode e = explodes.get(i);
			e.draw(g);
		}
		
		for(int i = 0; i<tanks.size(); i++) {
			Tank t = tanks.get(i);
			t.draw(g);
			t.hitsWall(w1);
			t.hitsWall(w2);
		}
		
	}
	

	public void update(Graphics g) {
		if(offScreenImage == null) {
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGTH);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.green);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGTH);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}
	
	private class PaintThread implements Runnable  {

		public void run() {
			while (true){
				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
				
					e.printStackTrace();
				}
			}
		}
		
	}//重画线程

	private class KeyMonitor extends KeyAdapter {

		public void keyReleased(KeyEvent e) {
			
			myTank.keyReleased(e);
		}

		public void keyPressed(KeyEvent e){
			myTank.keyPressed(e);
		}
		
		
	}
	
	public void launchFrame() {
		
		for(int i=0; i<10; i++) {
			tanks.add(new Tank(50+40*(i+1), 50, false, Tank.Direction.D,  this));
		}
		
		this.setLocation(200,100);
		this.setSize(GAME_WIDTH, GAME_HEIGTH);
		this.setTitle("Tank War");//改变标题 
		this.setBackground(Color.GREEN);//背景颜色
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});//关闭窗口
		this.setResizable(false);//让窗口不能改变大小
		setVisible(true);
		new Thread(new PaintThread()).start();//线程开始
		this.addKeyListener(new KeyMonitor());
	}
	
	public static void main(String[] args) {
		TankWarClient tc =  new TankWarClient();
		tc.launchFrame();
	}

}
