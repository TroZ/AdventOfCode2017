
import java.util.*;

public class Day25 {

	public static void main(String[] args) {
		
		new Day25();

	}

	boolean part1 = false; 
	String filename = "C:\\Users\\TroZ\\Projects\\AdventOfCode2017\\src\\day25.txt";
	
	
	public Day25() {
		
		int right = 1;
		int left = -1;
		
		HashMap<String,State> states = new HashMap<String,State>();
		
		//* Toggle comment - switch start of this line between /* and //* to toggle which section of code is active.
		//String[] input = Day2.readFile(filename).split("\n");
		
		
		//from input
		State temp = new State("a",1,right,"b",0,left,"b");
		states.put(temp.name, temp);
		temp = new State("b",0,right,"c",1,left,"b");
		states.put(temp.name, temp);
		temp = new State("c",1,right,"d",0,left,"a");
		states.put(temp.name, temp);
		temp = new State("d",1,left,"e",1,left,"f");
		states.put(temp.name, temp);
		temp = new State("e",1,left,"a",0,left,"d");
		states.put(temp.name, temp);
		temp = new State("f",1,right,"a",1,left,"e");
		states.put(temp.name, temp);
		
		int end = 12629077;

		/*/

		//String[] input = ("").split("\n");
		
		State temp = new State("a",1,right,"b",0,left,"b");
		states.put(temp.name, temp);
		temp = new State("b",1,left,"a",1,right,"a");
		states.put(temp.name, temp);

		int end = 6;
		//*/
		long st = System.currentTimeMillis();
		
		
		
		LinkedList<Integer> tape = new LinkedList<Integer>();
		
		tape.add(0);
		
		int pos = 0;
		String curState = "a";
		
		
		for(int step=0;step < end ;step++) {
			
			if(step%100000 == 0) {
				System.out.print(""+(step / 100000)+"... ");
				if((step / 100000)%10==9)
					System.out.println();
			}
			
			//get pos and state
			int val = tape.get(pos);
			State s = states.get(curState);
			
			//apply state transition
			tape.set(pos, s.write[val]);
			curState = s.newState[val];
			pos += s.dir[val];
			
			//adjust tape if needed
			if(pos<0) {
				tape.addFirst(0);
				pos=0;
			}else if(pos==tape.size()) {
				tape.addLast(0);
			}
		}
		
		
		//now count 1s
		int count = 0;
		for(Integer val:tape) {
			count += val.intValue();
		}
		
		
		
		long et = System.currentTimeMillis();
		System.out.println("\n\nChecksum: "+count);
		System.out.println(" Time Part1: "+(et-st));
	
	}

	
	class State{
		String name;
		int[] write;
		int[] dir;
		String[] newState;
		
		public State(String name,int write0,int dir0,String new0,int write1,int dir1,String new1) {
			this.name = name;
			write = new int[2];
			write[0]=write0;
			write[1]=write1;
			dir = new int[2];
			dir[0]=dir0;
			dir[1]=dir1;
			newState = new String[2];
			newState[0] = new0;
			newState[1] = new1;
		}
	}

}
