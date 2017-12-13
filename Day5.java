
public class Day5 {

	public static void main(String[] args) {
		
		//jump maze
		//part 1 each jump increments a position each time it's used
		//part 2, each jump increments when used, unless jump is 3 or more, then it decrements 
		
		
		boolean part1 = false; 

		String[] input = Day2.getInput().split("\n");
		//String[] input = "0 3 0 1 -3".split(" ");
		
		//get jump offsets
		int[] instr = new int[input.length];
		for(int i=0;i<input.length;i++) {
			instr[i] = Integer.parseInt(input[i]);
		}
		
		//follow them
		int pos = 0;
		int count =0;
		while(pos < instr.length) {
			int next = instr[pos];
			if( (!part1) && next >= 3) {
				instr[pos]--;  //part 2, and need to decrement
			}else {
				instr[pos]++; //increment
			}
			pos += next;
			count++;
			
		}
		
		System.out.println("steps: "+count);

	}

}
