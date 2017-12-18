import java.util.*;

public class Day18 {

	public static void main(String[] args) {
		
		new Day18();

	}

	boolean part1 = false; 
	String filename = "C:\\Users\\TroZ\\Projects\\AdventOfCode2017\\src\\day18.txt";
	
	long[] registers = new long[26];

	
	public Day18() {
		//* Toggle comment - switch start of this line between /* and //* to toggle which section of code is active.
		String[] input = Day2.readFile(filename).split("\n");


		
		/*/

		String[] input = ("set a 1\n" + 
				"add a 2\n" + 
				"mul a a\n" + 
				"mod a 5\n" + 
				"snd a\n" + 
				"set a 0\n" + 
				"rcv a\n" + 
				"jgz a -1\n" + 
				"set a 1\n" + 
				"jgz a -2").split("\n");
		
		input = ("snd 1\n" + 
				"snd 2\n" + 
				"snd p\n" + 
				"rcv a\n" + 
				"rcv b\n" + 
				"rcv c\n" + 
				"rcv d").split("\n");
		//*/

		
		
		
		
		
		int iptr = 0;
		long curSound = 0;
		
		
		while(iptr < input.length) {
			
			String[] instr = input[iptr].split(" ");
			
			boolean jump =false;
			switch(instr[0]) {
				case "snd":
					//play sound
					curSound = getVal(instr[1]);
					break;
				case "set":
					registers[instr[1].charAt(0)-'a'] = getVal(instr[2]);
					break;
				case "add":
					registers[instr[1].charAt(0)-'a'] += getVal(instr[2]);
					break;
				case "mul":
					registers[instr[1].charAt(0)-'a'] *= getVal(instr[2]);
					break;
				case "mod":
					registers[instr[1].charAt(0)-'a'] %= getVal(instr[2]);
					break;
				case "rcv":
					//recover
					if(getVal(instr[1])!=0) {
						iptr = input.length; // break out of loop
					}
					break;
				case "jgz":
					
					if( getVal(instr[1]) > 0) {
						jump = true;
						iptr += getVal(instr[2]);
					}
					break;
					
			
			}
			
			if(!jump)
				iptr++;
			
		}
		
		
		
		System.out.println("\nSound: "+ curSound);
		
		
		Prg prg0 = new Prg(input,0);
		Prg prg1 = new Prg(input,1);
		prg0.setOther(prg1);
		prg1.setOther(prg0);
		
		
		
		long st = System.currentTimeMillis();
		/*
		//don't know why, but this is giving me random answers :(
		//figured it out.  I wasn't marking the waiting thread as not waiting until it woke up, but running thread could start waiting, but see other thread as waiting, and think there is a deadlock.
		//so mark other thread as not waiting on send.
		
		Thread t0 = new Thread(prg0);
		Thread t1 = new Thread(prg1);
		
		t0.start();
		t1.start();
		try {
			t1.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*/
		
		
		while(!prg0.die && !prg1.die) {
			
			
			if(!prg0.waiting)
				prg0.runOne();
			if(!prg1.waiting)
				prg1.runOne();
			
			if(prg0.waiting && prg1.waiting)
				break;
		}
		
		
		//*/
		long et = System.currentTimeMillis();
		
		System.out.println("Send Count: "+prg1.sendCount);
		System.out.println("Time: "+(et-st));
	}
	
	
	static class Prg implements Runnable {
		int pid =0;
		long[] registers = new long[26];
		int iptr = 0;
		long sendCount = 0;
		String[] input = null;
		boolean waiting = false;
		boolean die = false;
		
		Queue<Long> queue = new ArrayDeque<Long>();
		
		Prg other = null;
		
		
		public Prg(String[] i,int id) {
			input = i;
			pid = id;
			registers['p'-'a'] = pid;
		}
		
		public void setOther(Prg p) {
			
			other = p;
		}
		
		public long getVal(String s) {
			if( (s.charAt(0)<'0' || s.charAt(0)>'9') && s.charAt(0)!='-') {
				//not a number
				return registers[s.charAt(0)-'a'];
			}
			return Integer.parseInt(s);
		}
		
		public void run() {
			
			while(iptr < input.length) {
				
				String[] instr = input[iptr].split(" ");
				
				boolean jump =false;
				switch(instr[0]) {
					//case "snd":
						//play sound
					//	curSound = getVal(instr[1]);
					//	break;
					case "set":
						registers[instr[1].charAt(0)-'a'] = getVal(instr[2]);
						break;
					case "add":
						registers[instr[1].charAt(0)-'a'] += getVal(instr[2]);
						break;
					case "mul":
						registers[instr[1].charAt(0)-'a'] *= getVal(instr[2]);
						break;
					case "mod":
						registers[instr[1].charAt(0)-'a'] %= getVal(instr[2]);
						break;
					//case "rcv":
						//recover
					//	if(getVal(instr[1])!=0) {
					//		iptr = input.length; // break out of loop
					//	}
					//	break;
					case "jgz":
						
						if( getVal(instr[1]) > 0) {
							jump = true;
							iptr += getVal(instr[2]);
						}
						break;
					case "snd":
						if(other!=null) {
							synchronized(other.queue) { //get other thread's queue lock
								other.queue.add(getVal(instr[1]));
								other.waiting = false;  //I had missed this line at first - causing the seemingly random results.  Other thread isn't waiting if it has items in queue but hasn't woken up yet!
								other.queue.notify(); //wake other thread (if sleeping)
							}
							sendCount++;
						}
						break;
					case "rcv":
						if(queue.isEmpty()) {
							synchronized(queue){ //get our queue lock
								waiting = true; //set us to waiting
								if(other.waiting) { 
									
									//deadlock - kill threads
									die = true;
									other.die = true;
									synchronized(other.queue){ //get other thread queue lock 
										other.queue.notify(); //wake other thread (he will see die signal and exit)
									}
									return;
								}
								
								try {
									queue.wait();  //sleep until woken - this also releases the lock while we are sleeping
									waiting = false; //set up as not waiting (but this should already be set as other thread sets it to false
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								if(die) {
									//other thread hit deadlock - die
									return;
								}
							}
						}
						registers[instr[1].charAt(0)-'a'] = queue.remove();
						break;
				
				}
				
				if(!jump)
					iptr++;
				
			}
			
			
		}
		
		public void runOne() {
			
			if(iptr>input.length) {
				die = true;
				return;
			}
			
			String[] instr = input[iptr].split(" ");
			
			boolean jump =false;
			switch(instr[0]) {
				//case "snd":
					//play sound
				//	curSound = getVal(instr[1]);
				//	break;
				case "set":
					registers[instr[1].charAt(0)-'a'] = getVal(instr[2]);
					break;
				case "add":
					registers[instr[1].charAt(0)-'a'] += getVal(instr[2]);
					break;
				case "mul":
					registers[instr[1].charAt(0)-'a'] *= getVal(instr[2]);
					break;
				case "mod":
					registers[instr[1].charAt(0)-'a'] %= getVal(instr[2]);
					break;
				//case "rcv":
					//recover
				//	if(getVal(instr[1])!=0) {
				//		iptr = input.length; // break out of loop
				//	}
				//	break;
				case "jgz":
					
					if( getVal(instr[1]) > 0) {
						jump = true;
						iptr += getVal(instr[2]);
					}
					break;
				case "snd":
		
					other.queue.add(getVal(instr[1]));
					sendCount++;
					other.waiting = false;
					break;
				case "rcv":
					if(queue.isEmpty()) {
						waiting = true;
					} else {
						registers[instr[1].charAt(0)-'a'] = queue.remove();
					}
					break;
			
			}
			
			if(!jump && !waiting)
				iptr++;
			
		}
		
	}
	
	public long getVal(String s) {
		if( (s.charAt(0)<'0' || s.charAt(0)>'9') && s.charAt(0)!='-') {
			//not a number
			return registers[s.charAt(0)-'a'];
		}
		return Integer.parseInt(s);
	}
	
	
	
	
}
