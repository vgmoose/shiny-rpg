import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.*;



public class GameWindow extends JFrame implements ActionListener, MouseMotionListener, MouseListener
{
    Timer clock = new Timer(20,this);

	boolean ismousedown = false;
	int ix, iy, mx, my; // initial and mouse current
	
	int playx, playy; // temp player coor
	
	double joyx, joyy;
	
	int px, py; // max sizes
	
	Character c;
	
	public GameWindow()
	{
		clock.start();
	}
	
	static class DrawBoard extends JPanel
	{
		GameWindow p;
		
		DrawBoard(GameWindow parent)
		{
			super();
			this.p = parent;
			setBackground(Color.white);
			
			p.c = new Character(this);
			
		}
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
						
			p.c.drawSprite(g, p.playx, p.playy, p.getDirectionValue());
			
//			g.setColor(Color.blue);
//			g.fillRect(p.playx, p.playy, 10, 10);
			
			if (p.isMouseDown()) 
			{
				
				
				int distance = 2*(int)Math.sqrt((p.px-p.ix)*(p.px-p.ix) + (p.py-p.iy)*(p.py-p.iy));
				int distance_cur = 2*(int)Math.sqrt((p.mx-p.ix)*(p.mx-p.ix) + (p.my-p.iy)*(p.my-p.iy));
				
				if (distance_cur > distance)
				{
					p.px = p.mx;
					p.py = p.my;
					distance = distance_cur;
				}
				
				int bx = p.mx;
				int by = p.my;
				
				if (distance>100)
					distance = 100;		
				
				if (distance_cur>100)
				{
					double theta = Math.atan2(p.my-p.iy, p.mx-p.ix);
					bx = (int) (p.ix + 50 * Math.cos(theta));
					by = (int) (p.iy + 50 * Math.sin(theta));
					
				}
				
				
				
				((Graphics2D) g).setStroke(new BasicStroke(3));

				g.setColor(Color.LIGHT_GRAY);
				g.fillOval(p.ix-distance/2, p.iy-distance/2-22, distance, distance); // outer
				g.setColor(Color.gray);
				g.fillOval(p.getInitialX()-5, p.getInitialY()-27, 10, 10); // initial
//				g.setColor(Color.black);
				g.fillOval(bx-5, by-27, 10, 10); // head

				((Graphics2D) g).setStroke(new BasicStroke(7));

				g.drawLine(bx, by-22, p.ix, p.iy-22); // line
//				g.setColor(Color.darkGray);
			

				p.joyx = (bx - p.ix) / 50.0;
				p.joyy = (by - p.iy) / 50.0;
				
				p.playx += 6*p.joyx;
				p.playy += 6*p.joyy;
				
				Robot r;
				try {
					// align mouse
					r = new Robot();
					r.mouseMove(bx+p.getX(), by+p.getY());
				} catch (AWTException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				

				
			}
			g.setColor(Color.black);
			g.drawString(p.getCoordinatesString(), 10, 20);
			g.drawString(p.getDirectionStatus(), 10, 40);
			
			


		}
	}


	public static void main (String[] args)
	{		
		GameWindow me = new GameWindow();
		DrawBoard db = new DrawBoard(me);
		
		db.setPreferredSize(new Dimension(500, 500));
		db.repaint();
		
		me.getContentPane().add(db);

		me.addMouseListener(me);	// I am my own mouse listener
		me.addMouseMotionListener(me);

		me.popup();
	}
	
	public int getDirectionValue()
	{
		return (int)((((Math.atan2(my-iy, mx-ix))*(180/Math.PI))+180+22.5)/45);
	}
	
	public String getDirectionStatus() 
	{
		String s;
		int a = getDirectionValue();
				
		switch (a)
		{
		case 1:
			s = "Up-Left";
			break;
		case 2:
			s = "Up";
			break;
		case 3:
			s = "Up-Right";
			break;
		case 4:
			s = "Right";
			break;
		case 5:
			s = "Down-Right";
			break;
		case 6:
			s = "Down";
			break;
		case 7:
			s = "Down-Left";
			break;
		case 0:
		case 8:
			s = "Left";
			break;
		
		
		default:
			s = "";
			
		}
		
		return s;
	}

	public String getCoordinatesString() 
	{
		return ""+joyx+", "+joyy;
	}

	public int getInitialY() 
	{
		return iy;
	}

	public int getInitialX() 
	{
		return ix;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) 
	{
		ismousedown = true;
		ix = arg0.getX();
		iy = arg0.getY();
		
		px = mx = ix;
		py = my = iy;
		
		
//		repaint();
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) 
	{
		ismousedown = false;
		
		px = 0;
		py = 0;
		
//		repaint();
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) 
	{
		mx = arg0.getX();
		my = arg0.getY();
		
//		repaint();
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		if (arg0.getSource() == clock)
			repaint();
	}
	
	public boolean isMouseDown()
	{
		return ismousedown;
	}
	

	public void popup(){
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Get the size of the screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		// Determine the new location of the window
		int w = getSize().width;
		int h = getSize().height;
		int x = (dim.width-w)/2;
		int y = (dim.height-h)/2;

		// Move the window
		setLocation(x, y);
		setResizable(false);
		setVisible(true);
	}


}
