import java.util.*;


public class Day13 {

	public static void main(String[] args) {
		
		new Day13();

	}

	boolean part1 = false; 
	String filename = "C:\\Users\\TroZ\\Projects\\AdventOfCode2017\\src\\day13.txt";
	
	
	
	
	
	public Day13() {
		//* Toggle comment - switch start if this line between /* and //* to toggle which section of code is active.
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
		System.out.println("Severity: "+ getSeverity(test));
		
		
		long start = System.currentTimeMillis();
		int delay = 0; 
		do {
			
			//copy state to test
			for(int i=0;i<state.length;i++) {
				for(int j=0;j<state[i].length;j++) {
					test[i][j] = state[i][j];
				}
			}
			
			
			//test out test
			if(!getHit(test)) {
				break;
			}
			
			moveScanners(state);
			delay++;
			if(delay%100 == 0) {
				System.out.println(""+delay);
			}
		}while(true);
		long end = System.currentTimeMillis();
		
		System.out.println("need a delay of "+delay);
		System.out.println(" time: "+(end-start));
		//*/
		
	}
	
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
	
	
	
	private void initialTry(String[] input) {
		//this is too slow for part 2
		

		int maxDepth = 0;
		
		for(String line:input) {
			String[] parts = line.split(":");
			maxDepth = Math.max(maxDepth, Integer.parseInt(parts[0].trim()));
		}
		
		int[][] firewall = null;
		firewall = new int[maxDepth+1][];
		
		for(String line:input) {
			String[] parts = line.split(":");
			int depth = Integer.parseInt(parts[0].trim());
			int range = Integer.parseInt(parts[1].trim());
			firewall[depth] = new int[range];
		}
		
		boolean[] up = new boolean[maxDepth+1]; //false = down
		
		
		reset(firewall);
		
		
		int sev = getSeverity(firewall, up,false);
		
		
		
		
		
		System.out.println("Severity: "+sev);
		
		
		
		int delay = -1;
		do {
			
			delay++;
			
			reset(firewall);
			up = new boolean[maxDepth+1];
			
			for(int i=0;i<delay;i++) {
				moveScanners(firewall,up);
			}
			
			sev = getSeverity(firewall, up, true);
			
			System.out.println("delay: "+delay+"   gives sev:"+sev);
			
			
		}while(sev >0);
		
		
		System.out.println(delay);
	}

	/**
	 * @param firewall
	 * @param up
	 * @return
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
