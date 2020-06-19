package com.revature.mariokartfighter_v2.service;

import java.util.Scanner;

public class ValidationService {
	Scanner input;
	
	public ValidationService() {
		input = new Scanner(System.in);
	}
	
	public String getValidString() {
		String userInput;
		boolean invalid = true;
		do {
			userInput = input.nextLine();
			if (!userInput.isEmpty()) {
				invalid = false;
				break;
			} else {
				System.out.println("Invalid input...enter a non-empty string");
			}
		} while (invalid);
		return userInput;
	}
	
	public int getValidInt() {
		int userInput = 0;
		boolean invalid = true;
		do {
			try {
				userInput = Integer.parseInt(input.nextLine());
				invalid = false;
				break;
			} catch (NumberFormatException ex) {
				System.out.println("Invalid input...enter an integer value");
			}
		} while (invalid);
		return userInput;
	}
	
	public double getValidDouble() {
		double userInput = 0.0;
		boolean invalid = true;
		do {
			try {
				userInput = Double.parseDouble(input.nextLine());
				invalid = false;
				break;
			} catch (NumberFormatException ex) {
				System.out.println("Invalid input...enter a decimal value");
			}
		} while (invalid);
		return userInput;
	}
	
}
