
public class Day5 {

	public static void main(String[] args) {
		
		
		boolean part1 = false; 

		String[] input = Day2.getInput().split("\n");
		//String[] input = "0 3 0 1 -3".split(" ");
		
		int[] instr = new int[input.length];
		for(int i=0;i<input.length;i++) {
			instr[i] = Integer.parseInt(input[i]);
		}
		
		int pos = 0;
		int count =0;
		while(pos < instr.length) {
			int next = instr[pos];
			if( (!part1) && next >= 3) {
				instr[pos]--;
			}else {
				instr[pos]++;
			}
			pos += next;
			count++;
			
		}
		
		System.out.println("steps: "+count);

	}

}
