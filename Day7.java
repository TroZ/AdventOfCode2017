import java.util.ArrayList;
import java.util.HashMap;


public class Day7 {

	public static void main(String[] args) {
		boolean part1 = false; 

		//balence a tree of child weights
		
		String[] input = Day2.getInput().split("\n");
		//String[] input = "0 3 0 1 -3".split(" ");
		
		int correctTotalWeight = Integer.MAX_VALUE;
		int correctWeight = Integer.MAX_VALUE;
		Prgm badGuy = null;
		
		HashMap<String,Prgm> all = new HashMap<String,Prgm>();
		
		//get data
		for(String line : input) {
			Prgm guy = new Prgm();
			
			String[] vals = line.split("->");
			guy.name = vals[0].trim();
			int pos = guy.name.indexOf(' ');
			if(pos > 0) {
				
				guy.weight = Integer.parseInt(guy.name.substring(pos+2,guy.name.lastIndexOf(')')));
				guy.name = guy.name.substring(0,pos).trim();
			}
			
			if(vals.length>1) {
				String[] children = vals[1].split(",");
				for(String child : children) {
					guy.childnames.add(child.trim());
				}
			}
			all.put(guy.name,guy);
		}
		
		//make tree
		Prgm t = null;;
		for(Prgm guy:all.values()) {
			
			for(String child:guy.childnames) {
				Prgm c = all.get(child);
				guy.children.add(c);
				c.parent = guy;
			}
			t = guy;
		}
		
		while(t.parent!=null) {
			t = t.parent;
		}
		
		//part 1, find the bottom guy
		System.out.println("Bottom: "+t.name);
		
		
		
		//calc total weight at each node
		calcWeight(t);
		
		//find node to adjust weight of (will only be one)
		for(Prgm p:all.values()) {
			
			int w = -1;
			boolean good = true;
			
			//debugging code for when calculating the correct answer
			//if(p.name.equals("ltleg")) {
			//	System.out.println("here");
			//}
			
			int cnt = 0;
			
			//the following were not originally part of my solution - I added them after I submitted the correct answer, so that the program could compute the correct answer
			Prgm f=null,s=null,o=null,g=null;  //f = first child, s = second child, o = off weight, g = good weight;
			
			for(Prgm c:p.children) {
				if(w<0) {
					w=c.totalWeight; //save weight if first child.
					f=c;
				}else {
					if(cnt==1) {
						s=c;
					}
					if(c.totalWeight != w) {
						
						if(cnt==1) {
							s=c; //if second child is different than first, we aren't sure if first or second is wrong
						}else{
							if(good==false) {
								o=f;	//if we are are 3 or later child and weight is still 'wrong', that first child was wrong
								g=c;
							}else {
								o=c;	//otherwise, this is the child that is wrong
								g=f;
							}
						}
						good = false;
					}
				}
				cnt++;
			}
		
			
			if(!good) {
				
				
				//this is what this program originally output, and I eyeballed it to find the the one guy of this that were output who was off, and how much to correct his weight by
				System.out.println("Children of  "+p.name+"  have an issue!");
				for(Prgm c:p.children) {
					System.out.println("  "+c.name+"  "+c.weight +"   "+c.totalWeight);
				}
				

				//after getting the correct answer with some calculation of my own, I added the below code to have the program figure it out
				if(o==null) {
					o=s; 	//if o is null here, then the second child was the wrong weight, as o would be set in all other cases.
					g=f;
				}
				int correct = o.weight - (o.totalWeight - g.totalWeight);
				
				if(g.totalWeight < correctTotalWeight) {//the guy to adjust is the one that is off with the lowest weight.  He should also have at least 2 siblings.
					correctTotalWeight = g.totalWeight;
					correctWeight = correct;
					badGuy = o;
				}
				
			}
			
		}
		
		System.out.println("\n\n"+badGuy.name+" should be "+correctWeight);
		
		
	}
	
	static int calcWeight(Prgm p) {
		
		int sum = p.weight;
		for(Prgm c:p.children) {
			
			sum += calcWeight(c);
		}
		
		p.totalWeight = sum;
		return sum;
		
		
	}

	
	static class Prgm{
		String name;
		int weight;
		int totalWeight;
		ArrayList<String> childnames = new ArrayList<String>();
		ArrayList<Prgm> children = new ArrayList<Prgm>();
		Prgm parent = null;
	}
}
