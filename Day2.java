import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFileChooser;


public class Day2 {

	public static void main(String[] args) {

		boolean part1 = false;
		
		String input = getInput();

		String[] lines = input.split("\n");
		
		int checksum = 0;
		int divsum = 0;
		
		for(String line :lines) {
			int max = Integer.MIN_VALUE;
			int min = Integer.MAX_VALUE;
			
			String[] values = line.split("\t");
			
			int[] nums = new int[values.length];
			
			for(int i=0;i<values.length;i++) {
				int v = Integer.parseInt(values[i]);
				
				nums[i] = v;
				
				
				max = Math.max(max, v);
				min = Math.min(min, v);
			}

			checksum += max-min;
			
			boolean found = false;
			for(int i=0;i<nums.length;i++) {
				for(int j=i+1;j<nums.length;j++) {
					
					/*
					double div = (double)nums[i]/(double)nums[j];
					double idiv = nums[i]/nums[j];
					
					if(div==idiv) {
						divsum += idiv;
						found = true;
						break;
					}
					*/
					max = Math.max(nums[i], nums[j]);
					min = Math.min(nums[i], nums[j]);
					
					if(max%min==0) {
						divsum += max/min;
						found = true;
					}
				
				}
				if(found)
					break;
			}
			if(!found) {
				System.out.println("error with data: "+line);
			}
		}
		
		System.out.println("Checksum: "+checksum);
		
		System.out.println("Div sum: "+divsum);
		
	}
	
	
	public static String getInput() {
		JFileChooser jfc = new JFileChooser();
		int result = jfc.showOpenDialog(null);
		if(result != JFileChooser.APPROVE_OPTION) {
			System.exit(0);
		}
		File srcFile = jfc.getSelectedFile();
		System.out.println("File: "+srcFile.getAbsolutePath());
		return readFile(srcFile.getAbsolutePath());
	}
	
	public static String readFile(String filename) {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF8"));
			
			String line = br.readLine();
			while(line!=null) {
				sb.append(line);
				sb.append('\n');
				line = br.readLine();
			}
			
		} catch ( IOException e) {
			System.out.println("Error opening "+filename);
			e.printStackTrace();
		} finally {
			if(br!=null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return sb.toString();
	}

}
