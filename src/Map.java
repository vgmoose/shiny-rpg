import java.awt.Color;
import java.awt.Graphics;
import java.io.File;


public class Map 
{	
	int xoffset, yoffset;
	int chunk = 16;
	
	String[] map;
	
	public Map(File tilemap)
	{
		createSetFromFile(tilemap);
	}
	
	private void createSetFromFile(File tilemap) {
		// TODO Auto-generated method stub
		
	}

	public Map()
	{
		createDemoTileset();
	}
	
	private void createDemoTileset()
	{
		String[] map = {"00000011100001110001001010100001000","00000011100001110001001010100001000","00000011100001110001001010100001000","00000011100001110001001010100001000","00000000000000000001001010100001000","00000000000000000001001010100001000","00000000000000000001001010100001000","00000000000000000001001010100001000","00000000000000000001001010100001000","00000000000000000001001010100001000","11111000000000000000000000000000000","11111000000000000000000000000000000","11111000000000000000000000000000000","11111000000000000000000000000000000","11111000000000000000000000000000000","11111000000000000000000000000000000","11111000000000000000000000000000000","11111000000000000000000000000000000","11111000000000000000000000000000000"};
		this.map = map;
	}
	
	void drawMap(Graphics g)
	{
		for (int x=0; x<map.length; x++)
		{
			String s = map[x];
			for (int y=0; y<s.length(); y++)
			{
				char c = s.charAt(y);

				
				if (c=='0')
				{
					g.setColor(Color.GRAY);
					g.drawRect(y*chunk+yoffset, x*chunk+xoffset, chunk, chunk);
				}
				else
				{
					g.setColor(Color.BLACK);
					g.fillRect(y*chunk+yoffset, x*chunk+xoffset, chunk, chunk);
				}
			}
		}
	}

	public boolean[] validMove(int x, int y, int size) 
	{
		boolean[] b = {true, true};
		
		y = y/chunk;
		x = x/chunk;
		int ys = (y+size)/chunk;
		int xs = (x+size)/chunk;
		
		try
		{
		// validate x
		if (map[y].charAt(x) == '1' || map[y].charAt(xs) == '1')
			b[0] = false;
		} catch (Exception e)
		{
			b[0] = false;
		}
		
		// validate y
		try
		{
		if (map[ys].charAt(x) == '1' || map[ys].charAt(xs) == '1')
			b[1] = false;
		} catch (Exception e)
		{
			b[1] = false;
		}

		
		return b;
	}
	
	
}
