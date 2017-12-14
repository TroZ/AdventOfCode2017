import java.util.ArrayList;
import java.util.HashSet;


public class Day14 {

	public static void main(String[] args) {
		
		new Day14();

	}

	boolean part1 = false; 
	String filename = "C:\\Users\\TroZ\\Projects\\AdventOfCode2017\\src\\day14.txt";
	
	
	final int LEN = 256;
	final int SIZE = 128;
	
	
	public Day14() {
		//* Toggle comment - switch start of this line between /* and //* to toggle which section of code is active.
		//String[] input = Day2.readFile(filename).split("\n");
		String input =("amgozmfv");
		/*/
		String input =("flqrgnkx");
		//*/
		
		
		
		boolean[][] blocks = new boolean[SIZE][SIZE];
		
		//make block matrix
		for(int i=0;i<SIZE;i++) {
			
			//make hash for line of blocks
			String hash = input+"-"+i;
			
			String hashResult = makeKnotHash(hash);
			
			//fill in allocated blocks
			for(int j=0;j<32;j++) {
				
				char c = hashResult.charAt(j);
				int cc = Integer.parseInt(""+c, 16);
				blocks[i][j*4+0] = (cc&8)>0;
				blocks[i][j*4+1] = (cc&4)>0;
				blocks[i][j*4+2] = (cc&2)>0;
				blocks[i][j*4+3] = (cc&1)>0;
			}
			
		}
		
		//count total allocated
		int count = 0;
		for(int i=0;i<SIZE;i++) {
			for(int j=0;j<SIZE;j++) {
				if(blocks[i][j])
					count++;
			}
		}
		
		
		System.out.println("allocated blocks: "+count);
		
		
		
		int[][] regions = new int[SIZE][SIZE];
		
		//find regions of allocated blocks
		int id = 1;
		for(int i=0;i<SIZE;i++) {
			for(int j=0;j<SIZE;j++) {
				
				
				if(blocks[i][j]) {
					
					ArrayList<Integer> ids = new ArrayList<Integer>();//overkill  - we only need to check above and to the left, and right and down not filled in yet
					//check surrounding and use id if found
					if(i>0 && regions[i-1][j] > 0) {
						ids.add(regions[i-1][j]);
					}
					if(j>0 && regions[i][j-1] > 0) {
						ids.add(regions[i][j-1]);
					}
					
					if(ids.size() >1) {
						//merging two areas - get min id
						int min = ids.get(0);
						for(int k=1;k<ids.size();k++) {
							min = Math.min(min, ids.get(k));
						}
						//replace min id
						for(int k=0;k<ids.size();k++) { //overkill, part 2
							int replace = ids.get(k);
							if(min != replace) {
								replace(regions, replace, min); //merge existing regions under the lower id (higher id gets 'lost' - not reused)
							}
						}
						//set this to min as well 
						regions[i][j] = min;
					}else if( ids.size() == 1) {
						regions[i][j] = ids.get(0);
					}else {
						regions[i][j] = id;
						id++;
					}
					
				}
				
			}
		}
		
		
		
		//count regions
		HashSet<Integer> regionIds = new HashSet<Integer>();
		for(int i=0;i<SIZE;i++) {
			for(int j=0;j<SIZE;j++) {
				
				System.out.print(regions[i][j]+"\t");
				
				if(regions[i][j] != 0){ //unallocated will be region '0', do not count it
					regionIds.add(regions[i][j]);
				}
			}
			System.out.println();
		}
		
		System.out.println("total regions: "+regionIds.size());

		
	}
	
	//replaces all instances of 'find' number with 'replace' number 
	public void replace(int[][] regions, int find, int replace) {
		for(int i=0;i<SIZE;i++) {
			for(int j=0;j<SIZE;j++) {
				
				if(regions[i][j] == find) {
					regions[i][j] = replace;
				}
			}
		}
	}
	
	
	
	//pulled out from Day 10, just to modify to return a string and not print (didn't want to modify Day 10 answer)
	public String makeKnotHash(String input) {
		
		int[] list = new int[LEN];
		int pos = 0;
		
		StringBuffer sb = new StringBuffer(input.trim());
		//sb.append("17,31,73,47,23");
		sb.append((char)17);
		sb.append((char)31);
		sb.append((char)73);
		sb.append((char)47);
		sb.append((char)23);
		//append fixed swaps to end so even an empty string won't just be the numbers in order 
		
		//reset array
		for(int i=0;i<LEN;i++){
			list[i] = i;
		}
		
		int skip = 0;
		pos = 0;
		
		//do 64 loops
		for(int count = 0;count<64;count++){
						
			for(int p=0;p<sb.length();p++){
				
				int change = (int)sb.charAt(p);
			
				pos = reverse(list, pos, change,skip);
				skip++;

			}
		}
		
		//calc dense array by xoring every 16 together
		int[] dense = new int[16];
		for(int i=0;i<16;i++){
			
			for(int j=0;j<16;j++){
				
				dense[i] ^= list[i*16+j];
				
			}
		}
	
		//print out final hash
		//System.out.println("Dense:");
		StringBuilder sbout = new StringBuilder();
		for(int i=0;i<16;i++){
			String out = Integer.toHexString(dense[i]);
			if(out.length()==1){
				sbout.append("0");
				sbout.append(out);
				//System.out.print("0"+out);  //this was the bug that took me a while to find.  toHexString only print out the minimum characters needed, so we need a leading 0 if < 16
			}else{
				//System.out.print(out);
				sbout.append(out);
			}
		}
		return sbout.toString();
	}
	
	public int reverse(int[] list,int pos, int len, int skip){
		
		int[] temp = new int[len];
		
		//copy to temp
		for(int i=0;i<len;i++){
			temp[i] = list[(i+pos)%LEN];//modulus to the rescue again for cycling around to beginning of the array
		}
		
		//reverse
		for(int i=0;i<len/2;i++){
			int t = temp[i];
			temp[i] = temp[len-i-1];
			temp[len-i-1] = t;
		}
		
		//write back
		for(int i=0;i<len;i++){
			list[(i+pos)%LEN] = temp[i];
		}
		
		pos += len + skip;
		pos = pos % LEN;
		return pos;
	}

}
