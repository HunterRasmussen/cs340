package StringProcessor.client;

import java.io.*;
import java.util.*;

public class Client{


	public static void main(String[] args){
		
		System.out.println("Hello World");
		StringProcessorProxy mProxy = StringProcessorProxy.getInstance();
		System.out.println("Enter the number ot the type of operation you want:\n"
			+" 1.)toLower \n" + 
			" 2.)trim \n" + 
			" 3.)parseDouble");
		Scanner s = new Scanner(System.in);
		String typeOfEdit = s.next();



		if(typeOfEdit.equals("1")){
			System.out.println("enter the string you want to switch to lowercase");
			s = new Scanner(System.in);
			String stringToEdit = s.nextLine();
			String editedString = mProxy.toLower(stringToEdit);
			System.out.println(editedString);
		}

		else if(typeOfEdit.equals("2")){
			System.out.println("enter the string you want to trim");
			s = new Scanner(System.in);
			String stringToEdit = s.nextLine();
			String editedString = mProxy.trim(stringToEdit);
		}

		else if(typeOfEdit.equals("3")){
			System.out.println("enter the double you want to parse");
			s = new Scanner(System.in);
			String stringToEdit = s.nextLine();
			Double returnedDouble = mProxy.parseDouble(stringToEdit);
			System.out.println(returnedDouble);
		}

		else{
			System.out.println("Invalid Input");
			return;
		}

		
	};



}


