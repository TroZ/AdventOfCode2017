import java.util.HashMap;


public class Day11 {

	public static void main(String[] args) {
		
		new Day11();

	}

	boolean part1 = false; 
	String filename = "C:\\Users\\TroZ\\Projects\\AdventOfCode2017\\src\\day11.txt";
	
	HashMap<String, Integer> regs = new HashMap<String, Integer>();
	
	int gchars = 0;
	
	public Day11() {
		String[] input = Day2.readFile(filename).split(",");
		
		//String[] input = "se,sw,se,sw,sw".split(",");
		
		int maxdist = 0;

		int x=0;
		int y=0;
		for(String dir : input) {
			switch(dir.trim()) {
			case "n":
				y+=2;
				break;
			case "ne":
				y+=1;
				x+=1;
				break;
			case "nw":
				y+=1;
				x-=1;
				break;
			case "s":
				y-=2;
				break;
			case "se":
				y-=1;
				x+=1;
				break;
			case "sw":
				y-=1;
				x-=1;
				break;
			default:
				System.out.println("BAD! "+dir);
			}	
			
			int dist = Math.abs(x+y)/2;
			if(dist>maxdist) {
				maxdist = dist;
			}
		}
		
		int dist = Math.abs(x+y)/2;
		
		System.out.println("\ndist: "+dist);
		System.out.println("maxdist: "+maxdist);
	}
	
}
