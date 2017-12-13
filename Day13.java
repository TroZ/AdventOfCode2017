import java.util.*;


public class Day13 {

	public static void main(String[] args) {
		
		new Day13();

	}

	boolean part1 = false; 
	String filename = "C:\\Users\\TroZ\\Projects\\AdventOfCode2017\\src\\day13.txt";
	
	
	
	
	
	public Day13() {
		//* Toggle comment - switch start of this line between /* and //* to toggle which section of code is active.
		String[] input = Day2.readFile(filename).split("\n");
		/*/
		String[] input =("0: 3\n" + 
				"1: 2\n" + 
				"4: 4\n" + 
				"6: 4").split("\n");
		//*/
		
		
		/*
		initialTry(input)
		
		/*/
		
		int maxDepth = 0;
		
		for(String line:input) {
			String[] parts = line.split(":");
			maxDepth = Math.max(maxDepth, Integer.parseInt(parts[0].trim()));
		}
		
		int[][] state = new int[maxDepth+1][3];
		int[][] test = new int[maxDepth+1][3];
		//y = 0 is range for that depth (-1 = no scanner)
		//y = 1 is dir (-1,1);
		//y = 2 is current position
		
		
		//setup
		for(int i = 0; i<state[0].length;i++) {
			state[i][0] = -1;
			state[i][1] = 1;
			state[i][2] = -1;
		}
		
		//read input
		for(String line:input) {
			String[] parts = line.split(":");
			int depth = Integer.parseInt(parts[0].trim());
			int range = Integer.parseInt(parts[1].trim());
			state[depth][0] = range;
			state[depth][2] = 0;
		}
		
		//copy state to test
		for(int i=0;i<state.length;i++) {
			for(int j=0;j<state[i].length;j++) {
				test[i][j] = state[i][j];
			}
		}
		//part 1
		System.out.println("Severity: "+ getSeverity(test));
		
		
		//part 2, find the delay needed to get past all scanners
		
		long start = System.currentTimeMillis();
		int delay = 0; 
		do {
			
			//copy state to test
			for(int i=0;i<state.length;i++) {
				for(int j=0;j<state[i].length;j++) {
					test[i][j] = state[i][j];
				}
			}
			
			
			//test out test array
			if(!getHit(test)) {
				break; //we got through!
			}
			
			moveScanners(state); //for next delay amount
			delay++;
			if(delay%100 == 0) {
				System.out.println(""+delay); //progress report
			}
		}while(true);
		long end = System.currentTimeMillis();
		
		System.out.println("need a delay of "+delay);
		System.out.println(" time: "+(end-start));
		//*/
		
	}
	
	
	//methods for more efficient version
	
	//get the severity of detection for the scanner state
	public int getSeverity(int[][] scanners) {
		int sev = 0;
		for(int i=0;i<scanners.length;i++) {
			
			//move packet
			int[] scanner = scanners[i];
			if(scanner[0]>1) {
				if(scanner[2] == 0) {
					sev+= i*scanner[0];
				}
			}
			
			//move scanners
			moveScanners(scanners);
			
		}
		return sev;
	}
	
	//returns true if a packet sent in this state would be detected
	public boolean getHit(int[][] scanners) {
		for(int i=0;i<scanners.length;i++) {
			
			//move packet
			int[] scanner = scanners[i];
			if(scanner[0]>1) {
				if(scanner[2] == 0) {
					return true;
				}
			}
			
			//move scanners
			moveScanners(scanners);
			
		}
		return false;
	}
	
	//updates the state of the scanners
	public void moveScanners(int[][] state) {
		
		for(int i=0;i<state.length;i++) {
			if(state[i][0]>1) {
				if(state[i][1]==1) {
					if(state[i][2]<(state[i][0]-1)) {
						state[i][2]++;
					}else {
						state[i][2]--;
						state[i][1]=-1;
					}
				}else {
					if(state[i][2]>0) {
						state[i][2]--;
					}else {
						state[i][2]++;
						state[i][1]=1;
					}
				}
			}
		}
		
	}
	
	
	
	//the below methods were from my initial attempt
	//this modeled the system exactly as described in the example
	//I have a 2 dimensional array, where the first dimension is depth
	//and the second dimension is the positions the scanner can be in at the depth (unallocated if no scanner),
	//with a 1 if the scanner is in that position and a 0 if not.
	//a second array held a boolean for each depth, to indicate if that scanner was going up or down. 
	//Additionally, for part 2, for each delay, I reset the state of everything, and the called move scanners delay times (O n2)
	//Needless to say, I didn't expect an answer in the millions.
	//
	//I initially got an answer of 4192, but this was not accepted and I realized that using severity to check if there was a
	//detection would work if the first scanner was the only one to detect the packet, show that is the cause of the part2 boolean.
	
	
	private void initialTry(String[] input) {
		//this is too slow for part 2
		

		int maxDepth = 0;
		
		//read input, find the max scanner depth
		for(String line:input) {
			String[] parts = line.split(":");
			maxDepth = Math.max(maxDepth, Integer.parseInt(parts[0].trim()));
		}
		
		int[][] firewall = null;
		firewall = new int[maxDepth+1][];
		
		//read input again, now creating the second dimension array for each scanner.
		for(String line:input) {
			String[] parts = line.split(":");
			int depth = Integer.parseInt(parts[0].trim());
			int range = Integer.parseInt(parts[1].trim());
			firewall[depth] = new int[range];
		}
		
		boolean[] up = new boolean[maxDepth+1]; //false = down
		
		//setup initial firewall state (scanner in first position)
		reset(firewall);
		
		
		int sev = getSeverity(firewall, up,false);
		
		//part 2
		System.out.println("Severity: "+sev);
		
		
		//part 2 (never finished - took a few minutes just to get to 40,000)
		int delay = -1;
		do {
			
			delay++;
			
			//set initial state
			reset(firewall);
			up = new boolean[maxDepth+1];
			
			//delay
			for(int i=0;i<delay;i++) {
				moveScanners(firewall,up);
			}
			
			//check if there is a hit
			sev = getSeverity(firewall, up, true);
			
			//print status
			System.out.println("delay: "+delay+"   gives sev:"+sev);
			
			
		}while(sev >0);
		
		
		System.out.println(delay);
	}

	/**
	 * @param firewall sparse 2d array
	 * @param up current directions
	 * @param part2 if any detection is severity 1
	 * @return severity of detection if packet sent at this state
	 */
	private int getSeverity(int[][] firewall, boolean[] up, boolean part2) {
		//check detection
		int sev = 0;
		for(int i=0;i<firewall.length;i++) {
			
			//move packet
			int[] scanner = firewall[i];
			if(scanner != null) {
				if(scanner[0] == 1) {
					
					//hit
					sev+= i*scanner.length;
					if(part2) {
						return 1;
					}
					
				}
			}
			
			//move scanners
			moveScanners(firewall,up);
			
			
			//printFirewall(firewall,i);
		}
		return sev;
	}

	/**
	 * @param firewall
	 * resets state of scanners 
	 */
	private void reset(int[][] firewall) {
		//set up scanners
		for(int i=0;i<firewall.length;i++) {
			int[] scanner = firewall[i];
			if(scanner != null) {
				for(int j=0;j<scanner.length;j++) {
					scanner[j] = (j==0)?1:0;
				}
			}
		}
	}

	//debug printing (as I initially had a bug in the move scanner code)
	private void printFirewall(int[][] fw,int step) {
		System.out.println("\nSTEP "+step+":");
		for(int i=0;i<fw.length;i++) {
			System.out.print(""+i+"\t");
			int[] scanner = fw[i];
			if(scanner != null) {
				for(int j=0;j<scanner.length;j++) {
					System.out.print("[");
					if(scanner[j]==1) {
						System.out.print("S");
					}else {
						System.out.print(" ");
					}
					System.out.print("]");
				}
			}
			System.out.println();
		}
		
	}

	//moves the scanners to the next position based on their current position and the direction
	private void moveScanners(int[][] fw, boolean[] up) {
		for(int i=0;i<fw.length;i++) {
			int[] scanner = fw[i];
			if(scanner != null) {
				
				
				for(int j=0;j<scanner.length;j++) {
					
					if(scanner[j]==1) {
						scanner[j]=0;
						
						if(scanner.length==1) {
							scanner[0]=1;
						}else if(up[i] && j>0) {
							scanner[j-1] = 1;
						}else if(up[i]) {
							scanner[j+1] = 1;
							up[i] = false;
						}else if(!up[i] && j<(scanner.length-1)) {
							scanner[j+1] = 1;
						}else if(!up[i]) {
							scanner[j-1] = 1;
							up[i] = true;
						}
						
						break;
								
					}
				}
				
				
			}
			
		}
		
	}

}
