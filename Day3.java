
public class Day3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		boolean part1 = false;
		
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
		
			int count=1;
			int dir = 1;
			while(count<input) {
				matrix[x][y] = count;
				count++;
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
			
			int dist = Math.abs(x-center)+Math.abs(y-center);
			System.out.println("Distance is "+dist);
			
		}else { 
			
			matrix[x][y]=1;
			
			int sum = 0;
			int dir = 1;
			while(sum<input) {
				
				sum = 0;
				for(int i=-1;i<2;i++) {
					for(int j=-1;j<2;j++){
						sum+=matrix[x+i][y+j];
					}
				}
				
				
				matrix[x][y] = sum;
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
