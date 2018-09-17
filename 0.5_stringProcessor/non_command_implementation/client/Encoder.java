package StringProcessor.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.lang.Object;

public class Encoder{

	public String encodeData(String stringToEdit){
		JsonObject jsonData = new JsonObject();
		jsonData.addProperty("String",stringToEdit);
		Gson gsonObject = new GsonBuilder().disableHtmlEscaping().create();
		String toReturn = gsonObject.toJson(jsonData);
		//System.out.println("From the Encoder encodeData method, the following is being returned\n" +toReturn);
		return toReturn;
	}

	public String decodeError(String errorJson){
		 Gson gson = new Gson();
		JsonObject json = gson.fromJson(errorJson, JsonObject.class);
		String toReturn = null;
		if(json.has("Error")){
			toReturn = json.get("Error").getAsString();
		}
		return toReturn;
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