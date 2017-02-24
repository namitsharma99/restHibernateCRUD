package com.ignore;

public class Sample {
	
	public static void main(String[] args){
		
		boolean flag = false;
		// scenario 1
		if (flag)
			System.out.println("line 1");
			if (!flag)
			System.out.println("line 2");
			else 
			System.out.println("line 3");
	//	else
				System.out.println("line 4");
				
		
		// scenario 2
		if (flag)
			if(!flag)
				System.out.println("line 5");
			else 
				System.out.println("line 6");
	}

}
