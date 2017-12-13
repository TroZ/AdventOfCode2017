import java.util.HashSet;


public class Day6 {

	public static void main(String[] args) {


		String[] input = "11	11	13	7	0	15	5	5	4	4	1	1	7	1	15	11".split("\t");
		//String[] input = "0	2	7	0".split("\t");

		//this will hold all the configurations we have seen already
		HashSet<String> configs = new HashSet<String>(); 
		
		//current configuration
		int[] banks = new int[input.length];
		//set up initial state
		for(int i=0;i<input.length;i++) {
			banks[i] = Integer.parseInt(input[i]);
		}
		
		//find a duplicate from the initial state for part 1
		findDupe(configs,banks);
				
		System.out.println("passes: " + configs.size());
		

		//clear save configs, then find a duplicate from here for part 2.
		configs.clear();
		
		findDupe(configs,banks);
		
		System.out.println("loop size: " + configs.size());
	}

	
	//finds the number of steps until we see a duplicate configuration 
	static void findDupe(HashSet<String> configs, int[] banks) {
		while(!configs.contains(banks2String(banks))) {
			configs.add(banks2String(banks));
		
			int amount = 0;
			int pos = 0;
			//find biggest
			for(int i=0;i<banks.length;i++) {
				if(banks[i]>amount) {
					amount = banks[i];
					pos = i;
				}
			}
			
			
			//distribute
			banks[pos] = 0;
			while(amount>0) {
				pos++;
				banks[pos%banks.length]++;
				amount--;
			}
		
		}
	}
	
	static String banks2String(int[] data) {
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<data.length;i++) {
			sb.append(data[i]);
			sb.append(" ");
		}
		return sb.toString();
	}
}
