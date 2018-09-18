package StringProcessor.client;

import java.io.*;
import java.util.*;


public class Client{


	public static void main(String[] args)throws Exception{

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
			System.out.println();
			try{
				String editedString = mProxy.toLower(stringToEdit);
				System.out.println(editedString);
			}
			catch(Exception e){
				throw e;
			}

		}

		else if(typeOfEdit.equals("2")){
			System.out.println("enter the string you want to trim");
			s = new Scanner(System.in);
			String stringToEdit = s.nextLine();
			System.out.println("Your untrimmed string is '" + stringToEdit + "'");
						System.out.println();

			try{
				String editedString = mProxy.trim(stringToEdit);
				System.out.println("The trimmed string is '" + editedString + "'");
			}
			catch(Exception e){
				throw e;
			}

		}

		else if(typeOfEdit.equals("3")){
			System.out.println("enter the double you want to parse");
			s = new Scanner(System.in);
			String stringToEdit = s.nextLine();
						System.out.println();
			try{
				Double returnedDouble = mProxy.parseDouble(stringToEdit);

				System.out.println(returnedDouble);
			}
			catch(Exception e){
				throw e;
			}
		}

		else{
			System.out.println("Invalid Input");
			return;
		}


	};



}
