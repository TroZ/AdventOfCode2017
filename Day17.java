import java.util.*;


public class Day17 {

	public static void main(String[] args) {
		
		new Day17();

	}

	boolean part1 = false; 
	String filename = "C:\\Users\\TroZ\\Projects\\AdventOfCode2017\\src\\day16.txt";
	
	
	public Day17() {
		//* Toggle comment - switch start of this line between /* and //* to toggle which section of code is active.
		//String[] input = Day2.readFile(filename).split(",");


		
		int input = 369;
		
		/*/

		
		int input = 3;
		//*/


		
		ArrayList<Integer> buffer = new ArrayList<Integer>();

		int pos = 0;
		
		int end = 50000000; // 2018
		int answer = 0;
		
		
		for(int i=0;i<end;i++) {
			
			if(i%100000==0) {
				System.out.print(".");
			}
			
			
			/*  Toggle - This section for part 1 along with setting end to 2018
			//Brute force -  works for part 1, but too slow for part 2  
			 
			
			//increment position, then insert new i
			if(buffer.size()>0) {
				pos = (pos + input) % buffer.size();
				buffer.add(pos+1,i);
				pos++;
			} else {
				buffer.add(i);
			}
			
			
		}
		
		
		System.out.println("\nnext num: "+buffer.get(pos+1));
		
		if(buffer.get(0)==0) {
			System.out.println("\nAfter 0: "+buffer.get(1));
		} else {
			System.out.println("\nDEBUG ME ");
			
		}
		
		/*/
			
			//increment position, then insert new i
			//actually, this is a 'fake'.  0 is always going to be the first position.
			//so the answer is the number in the second position.  so we just need to remember what number gets inserted
			//when pos lands on 0.  The buffer length will always be the same as the number we are inserting, so no need to really build the buffer.
			
			int bufferSize = i;
			
			if(bufferSize>0) {
				pos = (pos + input) % bufferSize;
				if(pos==0) {
					answer = i;
				}
				pos++;
			} 
			
			
		}
		
		System.out.println("\nAfter 0: "+answer);
		
		
		//*/
		
		
		
		
		
		
	}

}
