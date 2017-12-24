
import java.util.*;

public class Day24 {

	public static void main(String[] args) {
		
		new Day24();

	}

	boolean part1 = false; 
	String filename = "C:\\Users\\TroZ\\Projects\\AdventOfCode2017\\src\\day24.txt";
	
	final int SIZE = 1000;
	
	public Day24() {
		//* Toggle comment - switch start of this line between /* and //* to toggle which section of code is active.
		String[] input = Day2.readFile(filename).split("\n");

		/*/

		String[] input = ("0/2\r\n" + 
				"2/2\r\n" + 
				"2/3\r\n" + 
				"3/4\r\n" + 
				"3/5\r\n" + 
				"0/1\r\n" + 
				"10/1\r\n" + 
				"9/10").split("\n");

		//*/
		long st = System.currentTimeMillis();
		
		
		ArrayList<Port> ports = new ArrayList<Port>();
		for(int i=0;i<input.length;i++) {
			ports.add(new Port(input[i]));
		}
		
		
		
		int curside = 0;
		
		ArrayList<Port> best = new ArrayList<Port>();
		
		
		getBestBridge(ports, curside, best);
		
		
		int strength = getStrength(best);
		
		
		
		long et = System.currentTimeMillis();
		System.out.println("Strength: "+strength);
		System.out.println("Length: "+best.size());
		for(int i=0;i<best.size();i++) {
			System.out.print(best.get(i).name);
			System.out.print(" - ");
		}
		System.out.println();
		
		
		System.out.println(" Time Part1: "+(et-st));
	
	}

	
	private int getStrength(ArrayList<Port> best) {
		int strength = 0;
		for(int i=0;i<best.size();i++) {
			strength += best.get(i).str;
		}
		return strength;
	}


	private void getBestBridge(ArrayList<Port> ports, int curside, ArrayList<Port> best) {
		//find largest total strength bridge from current 'best' ports that connectes to a side of 'curside' 
		
		ArrayList<Port> bports = null; //best ports to attach next
		int bstr = 0;
		
		for(int i=0;i<ports.size();i++) {
			Port p = ports.get(i);
			
			//can fit?
			if(!(p.sides[0]==curside || p.sides[1]==curside)) {
				continue;
			}
			//is used already?
			if(best.contains(p)) {
				continue;
			}
			
			
			//try p out
			ArrayList<Port> b = (ArrayList<Port>) best.clone();
			b.add(p);
			int nextside = -1;
			if(curside == p.sides[0]) {
				nextside = p.sides[1];
			}else {
				nextside = p.sides[0];
			}
			getBestBridge(ports,nextside,b);
			int str = getStrength(b);
			
			int blen = 0;
			if(bports!=null) {
				blen = bports.size();
			}
			int len = b.size();
			
			if(part1) {
				if(bports==null || str > bstr) {
					bports = b;
					bstr = str;
				}
			}else {
				//part2
				if(len > blen) {
					bports = b;
					bstr = str;
				}else if(len == blen) {
					if(bports==null || str > bstr) {
						bports = b;
						bstr = str;
					}
				}
				
			}
			
		}
		
		//ok we went through all.  Add the best found to the end of best
		if(bports!=null) {
			for(int i=best.size();i<bports.size();i++) {
				best.add(bports.get(i));
			}
		}
	}


	class Port{
		String name;
		int[] sides = new int[2];
		int str = 0;
		
		public Port(String p) {
			name = p.trim();
			String[] s = name.split("/");
			sides[0] = Integer.parseInt(s[0].trim());
			sides[1] = Integer.parseInt(s[1].trim());
			str = sides[0]+sides[1];
		}
		
		@Override
		public boolean equals(Object o) {
			if(o instanceof Port) {
				Port p = (Port)o;
				if(this.name.equals(p.name)) {
					return true;
				}
			}
			return false;
		}
		
		@Override
		public String toString() {
			return name;
		}
	}
}
