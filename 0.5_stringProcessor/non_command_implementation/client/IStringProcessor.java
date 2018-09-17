package StringProcessor.client;




public interface IStringProcessor{


	/*pass in a string, returns the string with no whitespace*/
	public String trim(String toTrim);

	// pass in a string, returns the same string in all lowercase
	public String toLower(String stringToChange);

	//pass in a String that contains a double, returns the double found in it
	public Double parseDouble(String toParse);

}