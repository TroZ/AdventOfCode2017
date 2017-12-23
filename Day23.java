

public class Day23 {

	public static void main(String[] args) {
		
		//new Day23();  //this is part 1
		part2();

	}

	private static void part2() {
		
		long st = System.currentTimeMillis();
		int a=1;
		int b = 109900;
		int c = 126900;
		int d = 0;
        int e = 0;
        int f = 0;
        int g = 0;
        int h = 0;

		
		//*
		for(;b<=c;b+=17) {
			

			
			
			f = 1;
			d = 2;
			e = 2;
			while(true) {
				
				// loop through e from 2 to b then reset and increment d and go again (loop through e's again)
				// until d = b or you find a d*e that equals b (and if found, clear flag)
				
				g = ( d*e)-b;
				if(g==0) {
					f = 0;
					break;
				}
				e++;
				if(e<b) {
					continue;
				}
				
				d++;
				if(d<b) {
					e=2;
					continue;
				}
				break;
			}

			
			if(f!=0) {
				h++;
			}
			
		}
		
		/*/
	
        do {
			
			if(!isPrime(b)) {
				h++;
			}
			
			b+=17;
        }while(b<=c);
		//*/
		System.out.println("H: "+h);
		
		long et = System.currentTimeMillis();
		System.out.println(" Time Part1: "+(et-st));
		
	}
	
	
	private static boolean isPrime(int num) {
        if (num < 2) return false;
        if (num == 2) return true;
        if (num % 2 == 0) return false;
        for (int i = 3; i * i <= num; i += 2)
            if (num % i == 0) return false;
        return true;
}

	boolean part1 = false; 
	String filename = "C:\\Users\\TroZ\\Projects\\AdventOfCode2017\\src\\day23.txt";
	
	
	

	
	public Day23() {
		//* Toggle comment - switch start of this line between /* and //* to toggle which section of code is active.
		String[] input = Day2.readFile(filename).split("\n");

		/*/

		String[] input = ("").split("\n");

		//*/
		long st = System.currentTimeMillis();
		
		int[] registers = new int['h'-'a'+1];
		
		int iptr = 0;
		int mulCount=0;
		//registers[0]=1; //this was for part 2.... 
		
		String[][] instrs = new String[input.length][];
		for(int i=0;i<input.length;i++) {
			instrs[i] = input[i].split(" ");
		}
		
		
		while(iptr < input.length) {
			
			String[] instr = instrs[iptr];
			
			if(iptr == 31) {
				System.out.print(".");
			}
			
			boolean jump =false;
			switch(instr[0]) {
				case "set":
					registers[instr[1].charAt(0)-'a'] = getVal(registers,instr[2]);
					break;
				case "sub":
					registers[instr[1].charAt(0)-'a'] -= getVal(registers,instr[2]);
					break;
				case "mul":
					registers[instr[1].charAt(0)-'a'] *= getVal(registers,instr[2]);
					mulCount++;
					break;
				case "jnz":
					
					if( getVal(registers,instr[1]) != 0) {
						jump = true;
						iptr += getVal(registers,instr[2]);
					}
					break;
					
			
			}
			
			if(!jump)
				iptr++;
			
		}		
		
		
		
		long et = System.currentTimeMillis();
		System.out.println("\nMul Count: "+mulCount);
		System.out.println("Register H: "+registers['h'-'a']);
		System.out.println(" Time Part1: "+(et-st));
	}
	
	public int getVal(int[] registers,String s) {
		if( (s.charAt(0)<'0' || s.charAt(0)>'9') && s.charAt(0)!='-') {
			//not a number
			return registers[s.charAt(0)-'a'];
		}
		return Integer.parseInt(s);
	}

}
