package StringProcessor.client;


import java.io.*;
import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;


//deals with communicating with the server
public class ClientCommunicator{

	private Encoder encoder;


	private static ClientCommunicator communicatorInstance = null;

	public static ClientCommunicator getInstance(){
		if(communicatorInstance == null){
			communicatorInstance = new ClientCommunicator();
		}

		return communicatorInstance;
	}

	private ClientCommunicator(){
		encoder = new Encoder();
	}

	public String askServer(DataTransferObject toSend)throws Exception{
		String jsonToSend = encoder.encodeData(toSend);
		HttpURLConnection connection = null;
		connection = sendToServer(jsonToSend, connection);
		try{
			String response = (getResponse(connection));

			return response;
		}
		catch (IOException e) {
			throw e;
		}
		catch (NumberFormatException e) {
			throw e;
		}
	}

	private HttpURLConnection sendToServer(String stringToSend, HttpURLConnection connection)throws IOException{
		URL url = createURL();
		byte[] bytesToSend = stringToSend.getBytes(StandardCharsets.UTF_8);
		int requestLength = bytesToSend.length;
		try{
			connection = (HttpURLConnection) url.openConnection();
			if(connection == null){
				System.out.println("Sending to server, but connection is null");
			}
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("POST");
			//System.out.println(connection.getRequestMethod());
			connection.setRequestProperty("Content-Length", Integer.toString(requestLength));
			connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			OutputStream os = connection.getOutputStream();
			os.write(bytesToSend);
			return connection;
		}
		catch (IOException e){
            throw new IOException();

        }
	}

	private String getResponse(HttpURLConnection connection) throws Exception{
		try{
			//System.out.println("GetResponse method in ClientCommunicator");
			int responseCode = connection.getResponseCode();
			//System.out.println(responseCode);
			String serverResponse = readInputStream(connection);
			if (responseCode == HttpURLConnection.HTTP_NOT_AUTHORITATIVE){
				return encoder.decodeError(serverResponse);
			}
			else if(responseCode == HttpURLConnection.HTTP_OK){
				return (encoder.decodeResponse(serverResponse));
			}
			return null;
		}
		catch (IOException e) {
			//System.out.println("found error when getting responseCode");
			throw e;
		}
		catch(NumberFormatException e){
			throw e;
		}
	}


	private String readInputStream(HttpURLConnection connection)throws IOException{
		try{
			BufferedReader responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuilder response = new StringBuilder();
			Scanner scanner = new Scanner(responseReader);
			while(scanner.hasNextLine()){
                response.append(scanner.nextLine());
            }
        	//System.out.println(response.toString());
        	responseReader.close();
        	return response.toString();
        }
        catch (IOException e) {
					throw e;
		}
	}


	private URL createURL(){
		try{
		 	URL url = new URL("http://"+ "127.0.0.1" + ":" + "8080" + "/");
		 return url;
		}
		catch(MalformedURLException e){
            System.out.println("Error making the url.  Encoder createURl method");
            return null;
		}
	}

}
