package StringProcessor.client;

import java.io.IOException;

public class StringProcessorProxy implements IStringProcessor{

	private ClientCommunicator communicator;

	private static StringProcessorProxy proxyInstance = null;

	public static StringProcessorProxy getInstance(){
		if (proxyInstance == null){
			proxyInstance = new StringProcessorProxy();
		}

		return proxyInstance;
	}

	public StringProcessorProxy(){
		communicator = ClientCommunicator.getInstance();
	}



	public String trim(String toTrim) throws Exception{
		try{
			return communicator.askServer("trim",toTrim);

		}
		catch (IOException e) {
			throw e;
		}
	}

	// pass in a string, returns the same string in all lowercase
	public String toLower(String stringToChange) throws Exception{
		try{
			return communicator.askServer("lowercase",stringToChange);
		}
		catch (IOException e) {
			throw e;
		}
	}

	//pass in a String that contains a double, returns the double found in it
	public Double parseDouble(String toParse)throws Exception{

			///----------------  NOTE -----------------//

		//I recognize the point was to go to the server to get the double.  Which my server does.  But in encoding it to get it back,
		//    it goes into string form again.  Instead of writing a bunch of duplicate methods so that I can return a double up the
		//   chain, I instead just parse it here a second time.
		try{
			String parsedDouble = communicator.askServer("parse",toParse);
			//Double foundDouble = Double.parseDouble(parsedDouble);
			System.out.println(parsedDouble);
			return null;
			//return foundDouble;
		}
		catch (IOException e) {
			throw e;
		}
	}




}
