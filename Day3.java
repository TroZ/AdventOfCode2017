
public class Day3 {

	public static void main(String[] args) {

		boolean part1 = false; //set this to true for part 1 answer
		
		//spiral memory thing
		
		int input = 368078;
		double size = Math.sqrt(input);
		int s = (int)Math.ceil(size);
		if(s%2==0) {
			s++;
		}
		s+=2;//border for safety
		
		int[][] matrix = new int[s][s];
		int center = (s/2)+1;
		
		int x,y;
		x=y=center;
		
		if(part1) {
			
			//part 1 solution
			//make a spiral shape incrementing numbers until we hit input number, then calculate distance to center
		
			int count=1;
			int dir = 1;
			while(count<input) {
				//set val
				matrix[x][y] = count;
				count++;
				
				//move to next position
				switch(dir) {
					case 1:{
						x++;
						if(matrix[x][y-1]==0) {
							dir++;
						}
					}
					break;
					case 2:{
						y--;
						if(matrix[x-1][y]==0) {
							dir++;
						}
					}
					break;
					case 3:{
						x--;
						if(matrix[x][y+1]==0) {
							dir++;
						}
					}
					break;
					case 4:{
						y++;
						if(matrix[x+1][y]==0) {
							dir=1;
						}
					}
					break;
				}
			}
			
			matrix[x][y] = count;
			
			//calc distance
			int dist = Math.abs(x-center)+Math.abs(y-center);
			System.out.println("Distance is "+dist);
			
		}else { 
			
			//part 2, instead of each number being one more, now each position if sum of 8 surrounding values, starting with the center as 1
			
			//set center to 1
			matrix[x][y]=1;
						
			int sum = 0;
			int dir = 1;
			while(sum<input) {
				
				//calc surrounding 8 values 
				sum = 0;
				for(int i=-1;i<2;i++) {
					for(int j=-1;j<2;j++){
						sum+=matrix[x+i][y+j];
					}
				}
				
				//set it
				matrix[x][y] = sum;
				
				//move to next location (same code as part 1)
				switch(dir) {
					case 1:{
						x++;
						if(matrix[x][y-1]==0) {
							dir++;
						}
					}
					break;
					case 2:{
						y--;
						if(matrix[x-1][y]==0) {
							dir++;
						}
					}
					break;
					case 3:{
						x--;
						if(matrix[x][y+1]==0) {
							dir++;
						}
					}
					break;
					case 4:{
						y++;
						if(matrix[x+1][y]==0) {
							dir=1;
						}
					}
					break;
				}
			}
			
			System.out.println("First Larger Number is "+sum);
			
			
		}
		
	}

}
