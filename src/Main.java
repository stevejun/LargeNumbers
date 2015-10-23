// Main.java
// Copyright (c) 2014	 Steve Jun		All rights reserved.
// All work is the original work. DO NOT COPY and/or distribute.
import java.io.*;

public class Main {
	
	public static void main(String[] args) throws Exception{
		LargeNumbers N1, N2;
		FileReader theFile;
		BufferedReader inFile;
		String line;
		
		try{
			
			theFile = new FileReader(args[0]);
			inFile = new BufferedReader(theFile);
			line = inFile.readLine();
			
			while(line!=null){
				//Reallocate(since inside while loop) memory 
				//for the next pair of large numbers
				N1 = new LargeNumbers();
				N2 = new LargeNumbers();
				
				//Prepare the current line for our firstNumber
				//Reads, stores(into N1), and echoes firstNumber
				String firstNumber[] = line.split(",");
				System.out.print("The 1st number is: ");
				readAndEcho(firstNumber, N1);
			
				//Prepare the next line for our secondNumber
				//Reads, stores(into N2), and echoes secondNumber
				line = inFile.readLine();
				String secondNumber[] = line.split(",");
				System.out.print("The 2nd number is: ");
				readAndEcho(secondNumber, N2);
				
				printProduct(N1,N2);
				printSum(N1,N2);
				
				System.out.println();
				
				//Project description states each pair of largeNumbers 
				//are separated by a space. Therefore, another line is
				//skipped to proceed with the next pair of largeNumbers.
				line = inFile.readLine();					
				line = inFile.readLine();
			}
		}catch(Exception e){System.out.println("Invalid input");}		
			
	}
	
	static public void readAndEcho(String[] firstNumber, LargeNumbers N){
		for(int i=0; i<firstNumber.length; ++i)
			N.store(Integer.parseInt(firstNumber[i]));
		System.out.println(N.toString());		
	}
	
	static public void printSum(LargeNumbers N1, LargeNumbers N2){
		LargeNumbers sum;		
		sum = N1.add(N2);
		System.out.println("**Their sum equals: "+sum.toString());
	}
	
	static public void printProduct(LargeNumbers N1, LargeNumbers N2){
		LargeNumbers product;
		product = N1.multiply(N2);
		System.out.println("**Their product equals: "+product.toString());
	}
}