import java.util.*;


public class Day19 {

public static void main(String[] args) {
		
		new Day19();

	}

	boolean part1 = false; 
	String filename = "C:\\Users\\TroZ\\Projects\\AdventOfCode2017\\src\\day19.txt";
	
	long[] registers = new long[26];

	
	public Day19() {
		//* Toggle comment - switch start of this line between /* and //* to toggle which section of code is active.
		String[] input = Day2.readFile(filename).split("\n");


		
		/*/

		String[] input = ("     |          \n" + 
				"     |  +--+    \n" + 
				"     A  |  C    \n" + 
				" F---|----E|--+ \n" + 
				"     |  |  |  D \n" + 
				"     +B-+  +--+ \n" + 
				"                ").split("\n");
		

		//*/
		
		char[][] map = new char[input.length][input[0].length()];
		
		
		//fill map
		for(int yy=0;yy<input.length;yy++) {
			for(int xx=0;xx<input[yy].length();xx++) {
				map[yy][xx] = input[yy].charAt(xx);
			}
		}
		
		
		long st = System.currentTimeMillis();
		
		int y=0;
		int x=0;
		int dir = 0;  //down right up left
		
		
		//find start
		for(int i=0;i<input[0].length();i++) {
			if(map[y][i]=='|') {
				x = i;
				break;
			}
		}
		
		
		//follow
		int count = 0;
		StringBuffer path = new StringBuffer();
		char loc = map[y][x];
		while(loc!=' ') {
			count++;
			if(loc=='+') {
				//need to turn
				if(dir!=2 && map[y+1][x]!=' ') {
					dir=0;
				}else if(dir!=3 && map[y][x+1]!=' ') {
					dir=1;
				}else if(dir!=0 && map[y-1][x]!=' ') {
					dir=2;
				}else if(dir!=1 && map[y][x-1]!=' ') {
					dir=3;
				}else {
					System.out.println("Debug me!");
				}
			}
			if(loc>='A' && loc<='Z') {
				path.append(loc);
			}
		
			switch(dir) {
				case 0:{
					y++;
					break;
				}
				case 1:{
					x++;
					break;
				}
				case 2:{
					y--;
					break;
				}
				case 3:{
					x--;
					break;
				}
			}
		
			loc = map[y][x];
		}
		
		
		long et = System.currentTimeMillis();
		
		System.out.println("Path: "+path.toString());
		System.out.println("Count: "+count);
		System.out.println("Time: "+(et-st));
	}

}
