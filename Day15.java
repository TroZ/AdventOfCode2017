
public class Day15 {

	public static void main(String[] args) {
		
		new Day15();

	}

	boolean part1 = false; 
	String filename = "C:\\Users\\TroZ\\Projects\\AdventOfCode2017\\src\\day15.txt";
	
	
	static final int aFactor = 16807;
	static final int bFactor = 48271;
	static final int divisor = 2147483647;
	long a = 0;
	long b = 0;
	
	public Day15() {
		//* Toggle comment - switch start of this line between /* and //* to toggle which section of code is active.
		//String[] input = Day2.readFile(filename).split("\n");

		
		int aStart = 722;
		int bStart = 354;
		/*/

		int aStart = 65;
		int bStart = 8921;
		//*/
		
		
		//part1
		a = aStart;
		b = bStart;
		
		int count = 0;
		
		for(int i=0;i<40000000;i++) {
			
			getNext();
			
			if((a&0xffff)==(b&0xffff)) {
				count++;
			}
		}
		
		System.out.println("total matches: "+count);
		
		
		//part2
		a = aStart;
		b = bStart;

		count=0;
		for(int i=0;i<5000000;i++) {
			
			getNext2();
			
			if((a&0xffff)==(b&0xffff)) {
				count++;
			}
			
		}

		System.out.println("total matches 2: "+count);
		
	}
	
	public void getNext() {
		
		a = a * aFactor;
		a %= divisor;
		
		b = b * bFactor;
		b %= divisor;	
	}
	
	public void getNext2() {
		
		do {
			a = a * aFactor;
			a %= divisor;
		}while(a%4!=0);
		
		do {
			b = b * bFactor;
			b %= divisor;
		}while(b%8!=0);

	}

	
}
