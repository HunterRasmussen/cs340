package StringProcessor.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.lang.Object;
import java.io.IOException;

public class Encoder{

	public String encodeData(DataTransferObject dto){
		JsonObject jsonData = new JsonObject();
		jsonData.addProperty("command", dto.getCommand());
		jsonData.addProperty("String", dto.getDataToEdit());
		Gson gsonObject = new GsonBuilder().disableHtmlEscaping().create();
		//System.out.println("The encoded DTO is as follows\n" + gsonObject.toJson(jsonData));
		String toReturn = gsonObject.toJson(jsonData);
		//System.out.println("From the Encoder encodeData method, the following is being returned\n" +toReturn);
		return toReturn;
	}

	public String decodeError(String errorJson)throws Exception{
		 Gson gson = new Gson();
		JsonObject json = gson.fromJson(errorJson, JsonObject.class);
		String errorType = null;
		if(json.has("Error")){
			errorType = json.get("Error").getAsString();
			if (errorType.equals("NumberFormatException")){
				throw new NumberFormatException();
			}
			else if(errorType.equals("IOException")){
				throw new IOException();
			}
		}
		return errorType;
	}

	public String decodeResponse(String responseJson){
		Gson gson = new Gson();
		JsonObject json = gson.fromJson(responseJson, JsonObject.class);
		String toReturn = null;
		if(json.has("String")){
			toReturn = json.get("String").getAsString();
			//System.out.println("At the decodeResponse level, the String is '" + toReturn + "'");
		}
		else if(json.has("Double")){
			toReturn = json.get("Double").getAsString();
		}
		return toReturn;
	}

}
