import java.util.*;



public class Day12 {

	public static void main(String[] args) {
		
		new Day12();

	}

	boolean part1 = false; 
	String filename = "C:\\Users\\TroZ\\Projects\\AdventOfCode2017\\src\\day12.txt";
	
	ArrayList<List<Integer>> comm = new ArrayList<List<Integer>>();
	
	
	
	public Day12() {
		//*
		String[] input = Day2.readFile(filename).split("\n");
		/*/
		String[] input =("0 <-> 2\n" + 
				"1 <-> 1\n" + 
				"2 <-> 0, 3, 4\n" + 
				"3 <-> 2, 4\n" + 
				"4 <-> 2, 3, 6\n" + 
				"5 <-> 6\n" + 
				"6 <-> 4, 5").split("\n");
		//*/
		
		
		
		for(String line:input) {
			
			String[] parts = line.split("<->");
			String whos = parts[0].trim();
			Integer who = Integer.parseInt(whos);
			String[] withs = parts[1].split(",");
			List<Integer> l = null;
			if(comm.size()<=who || comm.get(who) == null) {
				l = new ArrayList<Integer>();
			}else {
				l = comm.get(who);
			}
			for(String with:withs) {
				l.add(Integer.parseInt(with.trim()));
			}
			comm.add(who,l);
			
		}
		
		
		HashSet<Integer> com0 =  makeGroup(0);
				
		
		System.out.println("\ntalk to 0: "+com0.size());
		
		
		
		ArrayList<HashSet<Integer>> groups = new ArrayList<HashSet<Integer>>();
		groups.add(com0);
		
		for(int i = 0 ; i<comm.size(); i++) {
			//see if i is in a current group
			boolean found = false;
			for(int g=0;g<groups.size();g++) {
				if(groups.get(g).contains(i)){
					found = true;
				}
			}
			
			
			if(!found) {
				//make group with i
				HashSet<Integer> com = makeGroup(i);
				groups.add(com);
			}
			
		}
		
		
		System.out.println("groups: "+groups.size());
		
	}

		
	public HashSet<Integer> makeGroup(int num) {
		HashSet<Integer> com0 = new HashSet<Integer>();
		com0.add(num);
		int size=0,newSize=com0.size();
		
		//loop until size stops changing (no new added to group) 
		while(size!=newSize) {
			size=newSize;
			
			//loop through existing adding who the can talk to to the set (looping through a copy to prevent concurrent modification exception )
			List<Integer> l = new ArrayList<Integer>(com0);
			for(Integer p:l) {
				List<Integer> canTalk = comm.get(p);
				for(Integer c:canTalk) {
					com0.add(c);
				}
			}
			newSize = com0.size();
		}
		
		return com0;
	}
}
