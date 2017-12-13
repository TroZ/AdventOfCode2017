import java.util.HashMap;


public class Day11 {

	public static void main(String[] args) {
		
		new Day11();

	}
	
	//hex array distance
	//I never really worked with hex arrays.  Looking quickly has the resource the linked and from some games I've played (Thanks Opus Magnum!)
	//I somewhat know about treating it as an offset grid, with every other column offset by 1/2. In order to not deal with fractions, I doubled everything.
	//so up or down was y +/- 2, and sideways (diagnal) movements were x +/- 1 and y +/- 1 (depending on direction).  Once the 'Manhattan' distance was calcualted
	//I would divide by 2.  Luckily my assumption that a simple xoffset+yoffset = number of steps worked out to be true.

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
		//move coords based on path
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
			
			int dist = Math.abs(x+y)/2;  // div 2 since we are adding 2 for each step
			if(dist>maxdist) {
				maxdist = dist;
			}
		}
		
		int dist = Math.abs(x+y)/2;
		
		System.out.println("\ndist: "+dist);
		System.out.println("maxdist: "+maxdist);
	}
	
}
