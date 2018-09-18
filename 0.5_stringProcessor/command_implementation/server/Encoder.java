package StringProcessor.server;

import com.google.gson.stream.JsonReader;
import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;

import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class Encoder{


	private Gson gson = new Gson();




	public DataTransferObject readDTO(InputStream is, DataTransferObject DTO) throws IOException {
    	StringBuilder sb = new StringBuilder();
    	InputStreamReader sr = new InputStreamReader(is);
    	BufferedReader reader = new BufferedReader(sr);
    	String line = reader.readLine();
    	while(line != null){
    		sb.append(line);
    		line = reader.readLine();
    	}
    	reader.close();
    	JsonObject json = gson.fromJson(sb.toString(), JsonObject.class);
    	String toReturn = null;
    	if(json.has("String")){
    		DTO.setDataToEdit(json.get("String").getAsString());
    	}
			if(json.has("command")){
				DTO.setCommand(json.get("command").getAsString());
			}
			if(json.has("Double")){
				DTO.setDataToEdit(json.get("Double").getAsString());
			}


    	return DTO;
	}

	public String getData(InputStream is, String data) throws IOException {
		StringBuilder sb = new StringBuilder();
		InputStreamReader sr = new InputStreamReader(is);
		BufferedReader reader = new BufferedReader(sr);
		String line = reader.readLine();
		while(line != null){
			sb.append(line);
			line = reader.readLine();
		}
		//reader.close();
		JsonObject json = gson.fromJson(sb.toString(), JsonObject.class);
		String toReturn = null;
		if(json.has(data)){
			toReturn = json.get(data).getAsString();
		}
	//	if(data.equals("String")){
	//		reader.close();
	//	}
		return toReturn;
	}



	JsonObject createJsonStringtoSend (String messageType, String stringToSend){
        JsonObject toReturn = new JsonObject();
        toReturn.addProperty(messageType, stringToSend);
        return toReturn;
	}



	 private void temp(Object obj, HttpExchange httpExchange){
        try{
            if(obj == null){
               httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, -1);
            }
            else{
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStreamWriter os = new OutputStreamWriter(httpExchange.getResponseBody());
                Gson gsonToSend = new GsonBuilder().disableHtmlEscaping().create();
                String jsonToSend = gsonToSend.toJson(obj);
                os.write(jsonToSend);
                os.close();
            }
        }
        catch(IOException e){
            //e.printStackTrace();
            System.out.println("\n Error sending data from sendData method");
        }
	}

	public String encodeData(String messageType, String messageTosend){
		JsonObject toSend = createJsonStringtoSend(messageType, messageTosend);
		Gson gsonObj = new GsonBuilder().disableHtmlEscaping().create();
		String toReturn = gsonObj.toJson(toSend);
		return toReturn;
	}



}
