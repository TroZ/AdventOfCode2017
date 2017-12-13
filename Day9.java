import java.io.IOException;
import java.io.StringBufferInputStream;
import java.io.StringReader;
import java.util.HashMap;


public class Day9 {

	public static void main(String[] args) {
		
		new Day9();

	}

	boolean part1 = false; 
	String filename = "C:\\Users\\TroZ\\Projects\\AdventOfCode2017\\src\\day9.txt";
	
	HashMap<String, Integer> regs = new HashMap<String, Integer>();
	
	int gchars = 0;
	
	public Day9() {
		String input = Day2.readFile(filename);//.split("\n");
		//String input = "{{<a!>},{<a!>},{<a!>},{<ab>}}";
		//String input = "<<<<>";
		
		//convert string to a stream
		StringReader sr = new StringReader(input);
		int score = getScore(sr,1);
		
		
		
		System.out.println("\nScore: "+score);
		System.out.println("garbage chars: "+gchars);
	}
	
	//returns the score for this group and it's sub-groups
	public int getScore(StringReader sr,int s) {
		//state
		int score = 0;
		boolean garbage = false;
		boolean cancel = false;

		
		try {
			while(true) {
				int val = sr.read();
				if(val==-1) {
					return score;
				}
				char c = (char)val; 
				
				
				if(cancel) {
					cancel=false;
					continue;
				}
				
				if(c=='!') {
					cancel = true;
				}else if(!garbage) {
					if(c=='{') {
						score+=s;
						score+= getScore(sr,s+1); //found a sub-group - recursively calc it's score
					} else if( c=='}') {
						break; //return score
					} else if( c=='<') {
						garbage = true;
					}
				}else if(c=='>'){
					garbage = false;
				}else if(garbage) {
					gchars++;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		return score;
	}

}
