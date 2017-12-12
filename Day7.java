import java.util.ArrayList;
import java.util.HashMap;


public class Day7 {

	public static void main(String[] args) {
		boolean part1 = false; 

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
		
		
		System.out.println("Bottom: "+t.name);
		
		
		
		//calc total weight at each node
		calcWeight(t);
		
		//find node to adjust weight of (will only be one)
		for(Prgm p:all.values()) {
			
			int w = -1;
			boolean good = true;
			
			if(p.name.equals("ltleg")) {
				System.out.println("here");
			}
			
			int cnt = 0;
			Prgm f=null,s=null,o=null,g=null;
			for(Prgm c:p.children) {
				if(w<0) {
					w=c.totalWeight;
					f=c;
				}else {
					if(cnt==1) {
						s=c;
					}
					if(c.totalWeight != w) {
						
						if(cnt==1) {
							s=c;
						}else{
							if(good==false) {
								o=f;
								g=c;
							}else {
								o=c;
								g=f;
							}
						}
						good = false;
					}
				}
				cnt++;
			}
		
			
			if(!good) {
				
				
				
				System.out.println("Children of  "+p.name+"  have an issue!");
				for(Prgm c:p.children) {
					System.out.println("  "+c.name+"  "+c.weight +"   "+c.totalWeight);
				}
				

				if(o==null) {
					o=s;
					g=f;
				}
				int correct = o.weight - (o.totalWeight - g.totalWeight);
				
				if(g.totalWeight < correctTotalWeight) {
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
