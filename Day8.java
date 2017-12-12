import java.util.HashMap;


public class Day8 {

	public static void main(String[] args) {
		
		new Day8();

	}

	boolean part1 = false; 
	String filename = "C:\\Users\\TroZ\\Projects\\AdventOfCode2017\\src\\Day8.txt";
	
	HashMap<String, Integer> regs = new HashMap<String, Integer>();
	
	public Day8() {
		String[] input = Day2.readFile(filename).split("\n");
		//String[] input = "0 3 0 1 -3".split(" ");
		
		int maxever = Integer.MIN_VALUE;
		
		for(String line: input) {
			
			String[] parts = line.split(" ");
			if( parts.length != 7) {
				System.out.println("error!");
				return;
			}
			
			int val = getVal(parts[0]);
			int comp = getVal(parts[4]);
			int comp2 = Integer.parseInt(parts[6]);
			boolean ok = false;
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
	
	public int getVal(String name) {
		if(regs.containsKey(name)) {
			return regs.get(name);
		}
		return 0;
	}
	
	public void setVal(String name, int v) {
		regs.put(name, Integer.valueOf(v));		
	}
}
