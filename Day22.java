
import java.util.*;

public class Day22 {

	public static void main(String[] args) {
		
		new Day22();

	}

	boolean part1 = false; 
	String filename = "C:\\Users\\TroZ\\Projects\\AdventOfCode2017\\src\\day22.txt";
	
	final int SIZE = 1000;
	
	public Day22() {
		//* Toggle comment - switch start of this line between /* and //* to toggle which section of code is active.
		String[] input = Day2.readFile(filename).split("\n");
		int loops = 10000;
		/*/

		String[] input = ("..#\r\n" + 
				"#..\r\n" + 
				"...").split("\n");
		int loops = 70;
		//*/
		long st = System.currentTimeMillis();
		
		{	
			boolean[][] grid = new boolean[SIZE][SIZE];
	
			
			int h = input.length;
			int w = input[0].trim().length();
			
			int sx = (SIZE-w)/2+1;
			int sy = (SIZE-h)/2+1;
			
			
			for(int y=0;y<h;y++) {
				for(int x=0;x<w;x++) {
					int yy = sy+y;
					int xx = sx+x;
					if(input[y].charAt(x)=='#') {
						grid[xx][yy] = true;
					}
				}
			}
			
			
			int x=500;
			int y=500;
			int dir=2;
			int infectCount = 0;
			
			
			for(int i=0;i<loops;i++) {
				
				boolean infected = grid[x][y];
				
				//turn
				if(infected) {
					dir--;
					dir=(dir<0)?3:dir;
				}else {
					dir++;
					dir%=4;
				}
				
				//infect / clean
				if(!infected) {
					infectCount++;
				}
				grid[x][y] = !infected;
				
				//move
				switch(dir) {
					case 0:
						y++;
						break;
					case 1:
						x++;
						break;
					case 2:
						y--;
						break;
					case 3:
						x--;
						break;
				}
				
			}
			
			
			
			
			long et = System.currentTimeMillis();
			System.out.println("Infect count: "+infectCount);
			System.out.println(" Time Part1: "+(et-st));
		
		}
		
		
		//part 2
		
		loops = 10000000;
		
		st = System.currentTimeMillis();
		
		
		short[][] grid = new short[SIZE][SIZE];
		
		//0 = clean
		//1 = weakened
		//2 = infected
		//3 = flagged

		
		int h = input.length;
		int w = input[0].trim().length();
		
		int sx = (SIZE-w)/2+1;
		int sy = (SIZE-h)/2+1;
		
		
		for(int y=0;y<h;y++) {
			for(int x=0;x<w;x++) {
				int yy = sy+y;
				int xx = sx+x;
				if(input[y].charAt(x)=='#') {
					grid[xx][yy] = 2;
				}
			}
		}
		
		
		int x=500;
		int y=500;
		int dir=2;
		int infectCount = 0;
		
		
		for(int i=0;i<loops;i++) {
			
			short state = grid[x][y];
			
			//turn
			if(state == 0) {
				dir++;
				dir%=4;
			}else if(state == 2) {
				dir--;
				dir=(dir<0)?3:dir;
			}else if(state == 3) {
				dir+=2;
				dir%=4;
			}
			
			//infect / clean
			if(state==1) {
				infectCount++;
			}
			grid[x][y] = (short) ((state+1)%4);
			
			//move
			switch(dir) {
				case 0:
					y++;
					break;
				case 1:
					x++;
					break;
				case 2:
					y--;
					break;
				case 3:
					x--;
					break;
			}
			
		}
		
		
		
		
		long et = System.currentTimeMillis();
		System.out.println("Infect count: "+infectCount);
		System.out.println(" Time Part2: "+(et-st));
		
	}
		

}
