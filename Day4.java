import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;


public class Day4 {

	public static void main(String[] args) {

		boolean part1 = false; 
		
		String[] input = Day2.getInput().split("\n");
		
		//passphrases
		
		int count = 0;
		for(String str : input) {
			String[] words = str.split(" ");
		
			if(part1) {
				//no duplicate words
				
				HashSet<String> m = new HashSet<String>();
				
				boolean valid = true;
				for(String word:words) {
					if(m.contains(word)) {
						valid = false;
						break;
					}
					m.add(word);
				}
				if(valid)
					count++;
				
				
			}else {
				
				//no duplicate anagrams
				//for each of checking, we just alphabetize each word, and check if that exists
				
				boolean valid = true;
				//this is a bit dumb - we are sorting more times than necessary. I should have sorted each word, threw it in a set, and saw if there was a collision.  Set.contains() then Set.add() if no collision.
				for(int i=0;i<words.length;i++) {
					for(int j=i+1;j<words.length;j++) {
						String a = words[i];
						String b = words[j];
						
						char[] ca = a.toCharArray();
						char[] cb = b.toCharArray();
				        Arrays.sort(ca); //alphabetizes the character array
				        Arrays.sort(cb);
				        String sa = new String(ca);
				        String sb = new String(cb);
					
				        if(sa.equals(sb)) { //probably could have done Arrays.equals, probably would have been faster. 
				        	valid=false;
				        	break;
				        }
					}
					if(!valid)
						break;
				}
				
				if(valid)
					count++;
				
			}
			
		}
		
		System.out.println(" " + count+" valid passphrases");
	}

}
