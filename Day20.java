import java.util.ArrayList;


public class Day20 {

	public static void main(String[] args) {
		
		new Day20();

	}

	boolean part1 = false; 
	String filename = "C:\\Users\\TroZ\\Projects\\AdventOfCode2017\\src\\day20.txt";
	


	
	public Day20() {
		//* Toggle comment - switch start of this line between /* and //* to toggle which section of code is active.
		String[] input = Day2.readFile(filename).split("\n");


		
		/*/

		String[] input = ("p=< 3,0,0>, v=< 2,0,0>, a=<-1,0,0>\n" + 
				"p=< 4,0,0>, v=< 0,0,0>, a=<-2,0,0>").split("\n");
		

		//*/
		long st = System.currentTimeMillis();
		
		ArrayList<Particle> particles = new ArrayList<Particle>();
		
		for(String line:input) {
			particles.add(new Particle(line));
		}
		
		boolean away = false;
		int count = 0;
		//move particles until all are moving away from 0,0,0
		while(!away) {
			away = true;
			count++;
			for(Particle p:particles) {
				away &= p.update();
			}
			
		}
		
		
		//now find closest
		Particle close = particles.get(0);
		int num = 0;
		for(Particle p:particles) {		
			//System.out.println(""+num+": "+p.toString());
			num++;
			if(p.getDistance() < close.getDistance()) {
				close = p;
			}
		}
	
		System.out.println("closest: "+particles.indexOf(close));


		
		
		long et = System.currentTimeMillis();
		
		//reset array state
		particles.clear();
		for(String line:input) {
			particles.add(new Particle(line));
		}
		
		//move particles until no collisions in 1000 moves (should be safe enough)
		int counter = 0, totalMoves = 0;;
		while(counter<1000) {
			
			if(checkCollision(particles)) {
				counter = 0;
			}
			for(Particle p:particles) {
				if(p!=null) {
					away &= p.update();
				}
			}
			counter++;
			totalMoves++;
		}
		
		int remain = 0;
		for(Particle p:particles) {
			if(p!=null) {
				remain++;
			}
		}
		
		long eet = System.currentTimeMillis();
		
		
		System.out.println("Remain: "+remain);
		System.out.println("Total Moves: "+totalMoves);
		System.out.println("Time Part1: "+(et-st));
		System.out.println("Time Part2: "+(eet-et));
	}
	
	private boolean checkCollision(ArrayList<Particle> particles) {
		boolean collide = false;
		//compare this particle's position to all others after it, setting their die to true if at same position - this compares all pairs once
		for(int i=0;i<particles.size();i++) {
			Particle a,b;
			a = particles.get(i);
			if(a!=null) {
				for(int j=i+1;j<particles.size();j++) {
					b = particles.get(j);
					if(b!=null)
						collide |= a.checkCollide(b);
				}
			}
		}
		//remove collided particles from arraylist (remove after all checks, so we don't miss more than 2 colliding at once)
		for(int i=0;i<particles.size();i++) {
			Particle p = particles.get(i);
			if(p!=null && p.die) {
				particles.set(i, null);
			}
		}
		return collide;
	}

	static class Particle{
		
		int xp,yp,zp,xv,yv,zv,xa,ya,za;
		boolean die = false;
		
		public Particle(String data) {
			//p=<2366,784,-597>, v=<-12,-41,50>, a=<-5,1,-2>
			String[] d = data.split(">");
			d[0] = d[0].substring(d[0].indexOf('<')+1);
			d[1] = d[1].substring(d[1].indexOf('<')+1);
			d[2] = d[2].substring(d[2].indexOf('<')+1);
			
			String[] p = d[0].split(",");
			xp = Integer.parseInt(p[0].trim());
			yp = Integer.parseInt(p[1].trim());
			zp = Integer.parseInt(p[2].trim());
			
			String[] v = d[1].split(",");
			xv = Integer.parseInt(v[0].trim());
			yv = Integer.parseInt(v[1].trim());
			zv = Integer.parseInt(v[2].trim());
			
			String[] a = d[2].split(",");
			xa = Integer.parseInt(a[0].trim());
			ya = Integer.parseInt(a[1].trim());
			za = Integer.parseInt(a[2].trim());
		}
		
		public boolean checkCollide(Particle b) {
			//set die to true if particle b is at same position as this (and b's die to true as well)
			if(xp==b.xp && yp==b.yp && zp==b.zp) {
				return die=b.die=true;
			}
			return false;
		}

		public boolean update() {
			//updates state, and then returns true if particle is strictly moving away from 0,0,0
			
			xv += xa;
			yv += ya;
			zv += za;
			
			xp += xv;
			yp += yv;
			zp += zv;
			
			
			if( (xp>=0 && xv>=0 && xa >=0)  || (xp<=0 && xv<=0 && xa<=0) ) {
				if( (yp>=0 && yv>=0 && ya >=0)  || (yp<=0 && yv<=0 && ya<=0) ) {
					if( (zp>=0 && zv>=0 && za >=0)  || (zp<=0 && zv<=0 && za<=0) ) {
						return true;
					}
				}
			}
			
			return false;
		}
		
		public int getDistance() {
			return Math.abs(xp)+Math.abs(yp)+Math.abs(zp);
		}
		
		
		public String toString() {
			return "Particle[p=<"+xp+","+yp+","+zp+">,v=<"+xv+","+yv+","+zv+">,a="+xa+","+ya+","+za+">,dist="+getDistance()+"]";
		}
	}
	
}
