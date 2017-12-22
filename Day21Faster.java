
import java.util.*;
import java.util.Map.Entry;

public class Day21Faster {

	public static void main(String[] args) {
		
		new Day21Faster();

	}

	boolean part1 = false; 
	String filename = "C:\\Users\\TroZ\\Projects\\AdventOfCode2017\\src\\day21.txt";
	


	
	public Day21Faster() {
		//* Toggle comment - switch start of this line between /* and //* to toggle which section of code is active.
		String[] input = Day2.readFile(filename).split("\n");
	
		int iters = 5;
		if(part1==false) {
			iters = 18;
		}
		
		/*/

		String[] input = ("../.# => ##./#../...\n" + 
				".#./..#/### => #..#/..../..../#..#").split("\n");
		int iters = 2;

		//*/
		long st = System.currentTimeMillis();
		
		//setup initial array
		String[] start = (".#.\n" + 
				"..#\n" + 
				"###").split("\n");
		
		
		boolean[][] img = new boolean[3][3];
		for(int j=0;j<start.length;j++) {
			String line = start[j];
			for(int i=0;i<line.length();i++) {
				if(line.charAt(i)=='#') {
					img[i][j] = true;
				}
			}
		}
		
		
		printImg(img);
		
		
		//read rules
		HashMap<String,String> rules = new HashMap<String, String>();
		for(String line:input) {
			String[] data = line.split("=>");
			rules.put(data[0].trim(), data[1].trim());
		}
		
		//make rotated / flipped versions of rules
		makeRules(rules);
		
		
		//iterate
		for(int iter = 0;iter<iters;iter++) {
		
			String[][] pieces = breakUp(img);
		
			for(int x=0;x<pieces.length;x++) {
				for(int y=0;y<pieces[0].length;y++) {
					pieces[x][y] = enhance(rules,pieces[x][y]);
				}
			}
		
			img = buildImage(pieces);
			
			printImg(img);
			
		}
		
		
		int on = countOn(img);
		
		long et = System.currentTimeMillis();
		System.out.println("On bits: "+on);
		System.out.println(" Moves Part 2: ");
		System.out.println(" Time Part1: "+(et-st));
		
	}
	
	
	//make rotated / flipped versions of rules
	private void makeRules(HashMap<String, String> rules) {
		String rulesCopy[][] = new String[rules.size()][2];
		
		
		//copy rules
		int count = 0;
		for(Entry<String,String> entry:rules.entrySet()) {
			rulesCopy[count][0] = entry.getKey();
			rulesCopy[count][1] = entry.getValue();
			count++;
		}
		
		//rotate the initial rule and add it to the map
		for(int i=0;i<rulesCopy.length;i++) {
			String str = rulesCopy[i][0];
			
			for(count=0;count<8;count++) {
				if(count==3) {
					//flip
					String[] parts = str.split("/");
					if(parts.length == 2) {
						str = parts[1]+'/'+parts[0];
					}else {
						str = parts[2]+'/'+parts[1]+'/'+parts[0];
					}
				}else {
					//rotate
					if(str.length()==5) {
						str = ""+str.charAt(3)+str.charAt(0)+'/'+
								str.charAt(4)+str.charAt(1);
					}else {
						str = ""+str.charAt(8)+str.charAt(4)+str.charAt(0)+'/'+
								str.charAt(9)+str.charAt(5)+str.charAt(1)+'/'+
								str.charAt(10)+str.charAt(6)+str.charAt(2);
					}
				}
				rules.put(str, rulesCopy[i][1]);
			}
		}
	}

	private void printImg(boolean[][] img) {
		
		/*
		int size = img.length;
		
		System.out.println();
		for(int y=0;y<size;y++) {
			for(int x=0;x<size;x++) {
				System.out.print(img[x][y]?"#":".");
			}
			System.out.println();
		}
		*/
		
	}

	//counts on bits of image
	private int countOn(boolean[][] img) {
		int count = 0;
		
		for(int x=0;x<img.length;x++) {
			for(int y=0;y<img.length;y++) {
				if(img[x][y]) {
					count++;
				}
			}
			
		}
		
		
		return count;
	}

	//put pieces together to make an image
	private boolean[][] buildImage(String[][] pieces) {
		int sizep = 3;
		if(pieces[0][0].length()>16) {
			sizep = 4;
		}
		
		int size = sizep * pieces.length;
		
		boolean[][] img = new boolean[size][size];
		
		//x,y is top left corner for this 3x3 or 4x4 square (sizep is the size of the piece)
		for(int y=0;y<pieces.length;y++) {
			for(int x=0;x<pieces[y].length;x++) {
			
				String piece = pieces[x][y];
				
				String[] rows = piece.split("/");
				//i,j are offset into this small square
				for(int j=0;j<rows.length;j++) {
					for(int i=0;i<rows[j].length();i++) {
						if(rows[j].charAt(i)=='#') {
							img[x*sizep+i][y*sizep+j] = true;
						}
					}
				}
				
			}
		}
		
		return img;
	}


	//runs the rules on individual strings - does rotations / flips as necessary 
	private String enhance(HashMap<String, String> rules, String str) {

		String res = null;

			res = rules.get(str);
			if(res==null) {
				System.out.println("ERROR!");
			}

		
		return res;
	}



	//Split the image into Strings for each area to run rules on
	private String[][] breakUp(boolean[][] img) {
		int size = img.length;
		int f = 2;
		if(size%2==0) {
			f = 2;
		}else {
			f = 3;
		}
		size /= f;
		
		String[][] res = new String[size][size];
		
		StringBuilder sb = new StringBuilder();
		//x,y is offset for this small square (2x2 or 3x3 as per f)
		for(int y=0;y<size;y++) {
			for(int x=0;x<size;x++) {
			
				sb.setLength(0);//clear string buffer (ding this instead of making a new object each time
				//i,j are offset into small square
				for(int j=0;j<f;j++) {
					for(int i=0;i<f;i++) {
						sb.append(img[x*f+i][y*f+j]?'#':'.');//char for this pixel
					}
					sb.append('/');//Separator as in the rules
				}
				//we added 1 to many slashes - remove
				sb.setLength(sb.length()-1);
				res[x][y] = sb.toString();
			}
		}

		return res;
	}

}
