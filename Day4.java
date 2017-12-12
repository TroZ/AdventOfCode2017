import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;


public class Day4 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		boolean part1 = false; 
		
		String[] input = Day2.getInput().split("\n");
		
		
		
		int count = 0;
		for(String str : input) {
			String[] words = str.split(" ");
		
			if(part1) {
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
				
				boolean valid = true;
				for(int i=0;i<words.length;i++) {
					for(int j=i+1;j<words.length;j++) {
						String a = words[i];
						String b = words[j];
						
						char[] ca = a.toCharArray();
						char[] cb = b.toCharArray();
				        Arrays.sort(ca);
				        Arrays.sort(cb);
				        String sa = new String(ca);
				        String sb = new String(cb);
					
				        if(sa.equals(sb)) {
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
