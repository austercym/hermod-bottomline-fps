package com.example.test.kafka;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(Math.ceil(1.1));
		System.out.println(Math.ceil(1.5));
		System.out.println(Math.ceil(1.8));
		
		String iban = "GB82WEST12345698765432";
		iban = String.format("%s%s", iban.substring(4), iban.substring(0, 4));
		System.out.println(iban);
		
		long sum = 0;
	    for (Character character : iban.toCharArray()) { 
	    		if (Character.isAlphabetic(character)) {
	    			sum += ((character - 'A') + 10);
	    			System.out.print(character - 'A' + 10); 
	    		} else if (Character.isDigit(character)){
	    			sum += character;
	    			System.out.print(character); 
	    		}
	    }
	    System.out.println("");
	    System.out.println("Total: " + sum % 97);
	    
	    String test = "aaa";
	    System.out.println(test.charAt(2));
	    
	}

}
