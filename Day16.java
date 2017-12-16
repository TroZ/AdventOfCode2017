import java.util.HashMap;


public class Day16 {

	public static void main(String[] args) {
		
		new Day16();

	}

	boolean part1 = false; 
	String filename = "C:\\Users\\TroZ\\Projects\\AdventOfCode2017\\src\\day16.txt";
	
	
	public Day16() {
		//* Toggle comment - switch start of this line between /* and //* to toggle which section of code is active.
		String[] input = Day2.readFile(filename).split(",");

		String dancers = "abcdefghijklmnop";
		
		/*/
		String dancers = "abcde";
		String[] input = "s1,x3/4,pe/b".split(",");
		//*/
		
		
		//part1
		
		HashMap<String,String> positions = new HashMap<String,String>();
		char[] dancer = dancers.toCharArray();
		
		
				
		int times =1000000000;
		for(int xx = 0; xx < times;xx++) {
			
			
			
			if(xx%1000000==0) {
				System.out.print(".");
			}
			
			String start = new String(dancer);
		
			if(positions.containsKey(start)) {
				String end = positions.get(start);
				dancer = end.toCharArray();
				
			} else {
				
				
				//this block here was my original part 1
				for(String move:input) {
					
					char m = move.charAt(0);
					String param = move.substring(1);
					int a=0,b=0;
					String as = "", bs = "";
					if(param.contains("/")) {
						String[] params = param.trim().split("/");
						if(m!='p') {
							a = Integer.parseInt(params[0]);
							b = Integer.parseInt(params[1]);
						}else {
							as = params[0];
							bs = params[1];
						}
					}else {
						a = Integer.parseInt(param.trim());
					}
					
					switch(m) {
						case 's':{
							spin(dancer,a);
							break;
						}
						case 'x':{
							exchange(dancer,a,b);
							break;
						}
						case 'p':
							partner(dancer,as,bs);
					}
				}
				
				
				//add positions to HashMap
				String end = new String(dancer); 
				positions.put(start, end);
				
			}
		}
		
		System.out.println("\nOrder: "+(new String(dancer)));
		
		
		//do mapping
		//this didn't work - likely due to partners move (if it was all position based, this should have worked)
		/* 
		char[] start = dancers.toCharArray();
		char[] end = dancer.clone();
		times = 1; //999999999;
		
		for(int xx = 0; xx < times;xx++) {
			
			if(xx%1000000==0) {
				System.out.print(".");
			}
			
			
			char[] dancerNext = new char[start.length];
			for(int i=0;i<dancerNext.length;i++) {
				int pos = end[i] - 'a';
				dancerNext[i] = dancer[pos];
			}
			
			dancer = dancerNext;
			
		}
		
		System.out.println("\nFinal Order: "+(new String(dancer)));
		*/
		
		
	}

	private void partner(char[] dancer, String as, String bs) {
		int pos1 = 0, pos2 = 0;
		for(int i=0;i<dancer.length;i++) {
			if(dancer[i] == as.charAt(0)) {
				pos1 = i;
			}
			if(dancer[i] == bs.charAt(0)) {
				pos2 = i;
			}
		}
		exchange(dancer,pos1,pos2);
		
	}

	private void exchange(char[] dancer, int a, int b) {
		char t = dancer[a];
		dancer[a] = dancer[b];
		dancer[b] = t;
	}

	private void spin(char[] dancer, int a) {
		for(int i=0;i<a;i++) {
			char t = dancer[dancer.length-1];
			for(int j=dancer.length-1;j>0;j--) {
				dancer[j] = dancer[j-1];
			}
			dancer[0] = t;
		}
	}
	
	
	
	
}
