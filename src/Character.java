import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;


public class Character 
{
	Graphics g;
	GameWindow.DrawBoard db;
	BufferedImage img1;
	int prevd = -1, frame = 0;
	
	Character(GameWindow.DrawBoard db)
	{
		this.db = db;
		try {
			img1 = ImageIO.read(new File("solana.png").toURI().toURL());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	BufferedImage cropDat(int d)
	{
		if (prevd != d)
			frame = 0;
		else
			frame++;
		
		if (frame == 40) frame = 0;
		
		prevd = d;
		
		int x, y;
		
		switch(d)
		{
		case 8:
		case 0: // left
			x = 0; y = 33*2;
			break;
		case 6: //down
			x = 0; y = 0;
			break;
		case 2: // up
			x = 33*4; y = 0;
			break;
		case 4: // right
			x = 33*4; y = 33*2;
			break;
		case 7: // downleft
			x = 33*4; y = 33;
			break;
		case 3: // upright
			x = 33*4; y = 33*3;
			break;
		case 1: //upleft
			x = 0; y = 33*3;
			break;
		case 5: //downright
			x = 0; y = 33;
			break;
		default: x = 0; y = 0;
			
		}
		return img1.getSubimage(x+33*(frame/10)+1, y+1, 32, 32);
	}
	
	void drawSprite(Graphics g, int x, int y, int direction) 
	{
		BufferedImage img2 = cropDat(direction);
	    ((Graphics2D) g).drawImage(img2, x, y, db);
	}
}
