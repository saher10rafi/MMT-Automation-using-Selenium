package com.trip.automation;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
			Scanner sc = new Scanner(System.in);
			Implementation implementation = new Implementation();
			System.out.println("Enter 1 for Chrome \nEnter 2 for Firefox"); //for selecting the browser
			int ch = sc.nextInt();  //input
			sc.close();
			
			implementation.createDriver(ch);
			implementation.Imp();
	}

}
