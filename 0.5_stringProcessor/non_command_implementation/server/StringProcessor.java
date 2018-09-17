package StringProcessor.server;


public class StringProcessor implements IStringProcessor {



	private static StringProcessor processorInstance = null;

	public static StringProcessor getInstance(){
		if (processorInstance == null){
			processorInstance = new StringProcessor();
		}

		return processorInstance;
	}



	public String trim(String toTrim){
		return toTrim.trim();
	}

	// pass in a string, returns the same string in all lowercase
	public String toLower(String stringToChange){
		return stringToChange.toLowerCase();
	}

	//pass in a String that contains a double, returns the double found in it
	public Double parseDouble (String toParse) throws NumberFormatException{
		Double toReturn = Double.parseDouble(toParse);
		return toReturn;
	}




}