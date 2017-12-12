import java.util.HashMap;

public class Day10 {

	public static void main(String[] args) {
		new Day10();

	}

	boolean part1 = false; 
	String filename = "D:\\AdventOfCode2017\\src\\day10.txt";
	
	final int LEN = 256;
	int[] list = new int[LEN];
	int pos = 0;
	
	
	public Day10() {
		//String input = Day2.readFile(filename);//.split("\n");
		//String input = "{{<a!>},{<a!>},{<a!>},{<ab>}}";
		//String input = "<<<<>";
		//String[] input = "94,84,0,79,2,27,81,1,123,93,218,23,103,255,254,243".split(",");
		
		String input = "94,84,0,79,2,27,81,1,123,93,218,23,103,255,254,243";
		
		//String input = "1,2,4";
		
		/*
		for(int i=0;i<LEN;i++){
			list[i] = i;
		}
		
		int skip = 0;
		
		for(String s:input.split(",")){
			
			int change = Integer.parseInt(s);
			reverse(change,skip);
			skip++;
			
			pos = pos %LEN;
			
			for(int i=0;i<LEN;i++){
				
				if(i==pos){
					System.out.print("("+list[i]+"),");
				}else{
					System.out.print(""+list[i]+",");
				}
				
				
			}
			System.out.println();
			
			
		}
		
		
		
		
		
		System.out.println("\n Number: "+list[0]*list[1]);
		System.out.println("\n\n\n");
		*/
		
		
		//17, 31, 73, 47, 23
		
		StringBuffer sb = new StringBuffer(input.trim());
		sb.append((char)17);
		sb.append((char)31);
		sb.append((char)73);
		sb.append((char)47);
		sb.append((char)23);
		
		//sb.append("17,31,73,47,23");
		
		//StringBuffer b = new StringBuffer();
		
		String real = sb.toString();
		
		for(int i=0;i<LEN;i++){
			list[i] = i;
		}
		
		int skip = 0;
		pos = 0;
		
		for(int count = 0;count<64;count++){
			
			//for(String s:input.split(",")){
				
				//int change = Integer.parseInt(s);
			
			for(int p=0;p<sb.length();p++){
				
				int change = (int)sb.charAt(p);
			
				reverse(change,skip);
				skip++;
				
				pos = pos %LEN;
				
			}
		}
		
		
		System.out.println("Sparse:");
		for(int i=0;i<LEN;i++){
		
			if(i==pos){
				System.out.print("("+list[i]+"),");
			}else{
				System.out.print(""+list[i]+",");
			}
		}
		System.out.println();
		
		

		int[] dense = new int[16];
		for(int i=0;i<16;i++){
			
			for(int j=0;j<16;j++){
				
				dense[i] ^= list[i*16+j];
				
			}
		}
	
		System.out.println("Dense:");
		for(int i=0;i<16;i++){
			String out = Integer.toHexString(dense[i]);
			if(out.length()==1){
				System.out.print("0"+out);
			}else{
				System.out.print(out);
			}
		}
		
		
		
		
		
		//System.out.println("garbage chars: "+gchars);
	}

	public void reverse(int len, int skip){
		
		int[] temp = new int[len];
		
		//copy to temp
		for(int i=0;i<len;i++){
			temp[i] = list[(i+pos)%LEN];
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

	}
}
