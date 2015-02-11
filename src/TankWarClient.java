
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;

import org.omg.PortableServer.THREAD_POLICY_ID;


public class TankWarClient extends Frame{
	public static final int GAME_WIDTH = 800;
	public static final int GAME_HEIGTH = 600;
	

	Tank myTank = new Tank(50, 50);
	
	Image offScreenImage = null;
	public void paint(Graphics g) {
		myTank.draw(g);
		
		
	}//����һ��Բ���ػ�ʱy+5
	

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
		
	}//�ػ��߳�

	private class KeyMonitor extends KeyAdapter {

		public void keyPressed(KeyEvent e){
			myTank.keyPressed(e);
		}
		
		
	}
	public void launchFrame() {
		this.setLocation(200,100);
		this.setSize(GAME_WIDTH, GAME_HEIGTH);
		this.setTitle("Tank War");//�ı���� 
		this.setBackground(Color.GREEN);//������ɫ
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});//�رմ���
		this.setResizable(false);//�ô��ڲ��ܸı��С
		setVisible(true);
		new Thread(new PaintThread()).start();
		this.addKeyListener(new KeyMonitor());
	}
	
	public static void main(String[] args) {
		TankWarClient tc =  new TankWarClient();
		tc.launchFrame();
	}

}
