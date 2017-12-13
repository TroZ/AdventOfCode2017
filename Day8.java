import java.util.HashMap;


public class Day8 {

	public static void main(String[] args) {
		// now actually do calculation in a 'normal' class, and not just in main / static methods
		new Day8();

	}

	boolean part1 = false; 
	String filename = "C:\\Users\\TroZ\\Projects\\AdventOfCode2017\\src\\Day8.txt";
	
	//map of register name to value, missing names are assumed to be 0
	HashMap<String, Integer> regs = new HashMap<String, Integer>();
	
	public Day8() {
		String[] input = Day2.readFile(filename).split("\n");
		//String[] input = "0 3 0 1 -3".split(" ");
		
		int maxever = Integer.MIN_VALUE;
		
		for(String line: input) {
			
			//process input
			String[] parts = line.split(" ");
			if( parts.length != 7) {
				System.out.println("error!");
				return;
			}
			
			
			int val = getVal(parts[0]);
			int comp = getVal(parts[4]);
			int comp2 = Integer.parseInt(parts[6]);
			boolean ok = false;
			//do comparison
			switch(parts[5]) {
				case "<":
					ok = comp < comp2;
					break;
				case ">":
					ok = comp > comp2;
					break;
				case "==":
					ok = comp == comp2;
					break;
				case "!=":
					ok = comp != comp2;
					break;
				case ">=":
					ok = comp >= comp2;
					break;
				case "<=":
					ok = comp <= comp2;
					break;
			}
			
			//set new val of comparison ok
			if(ok) {
				int diff = Integer.parseInt(parts[2]);
				if(parts[1].equals("inc")) {
					int v = val+diff;
					setVal(parts[0],v);
					maxever = Math.max(maxever, v);
				}else {
					int v = val-diff;
					setVal(parts[0],v);
					maxever = Math.max(maxever, v);
				}
				
			}
			
			
		}
		
		//find biggest
		int max = Integer.MIN_VALUE;
		for(Integer i:regs.values()) {
			max = Math.max(max, i.intValue());
		}
		
		
		
		
		System.out.println("\nbiggest: "+max);
		System.out.println("biggest ever: "+maxever);
	}
	
	//returns the value of the named register, assuming 0 if not in the map yet;
	public int getVal(String name) {
		if(regs.containsKey(name)) {
			return regs.get(name);
		}
		return 0;
	}
	
	//ease of use method - probably wasn't needed (could have let it autobox the int to Integer)
	public void setVal(String name, int v) {
		regs.put(name, Integer.valueOf(v));		
	}
}
