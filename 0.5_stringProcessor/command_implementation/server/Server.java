package StringProcessor.server;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.*;
import java.io.*;
import java.net.*;




public class Server {

	private Encoder encoder = new Encoder();
	private HttpServer mServer;
	private static final int MAX_WAIT_CONNECTIONS = 12;
	private static StringProcessor stringProcessor;


	public static void main(String[] args){

		System.out.println("Hello from the server");
		stringProcessor = StringProcessor.getInstance();
		new Server().runServer();
	}

	public void runServer(){
		System.out.println("initializing Server");
		try{
			mServer = HttpServer.create(new InetSocketAddress(8080), MAX_WAIT_CONNECTIONS);
			System.out.println("Server Established on port 8000");
		}
		catch(IOException e){
			System.out.println("Couldn't create server");
		}

		mServer.setExecutor(null);

		System.out.println("Creating Contexts");
		mServer.createContext("/", defaultHandler);


		System.out.println("Starting Server");
		mServer.start();
		System.out.println("Server Started");


	}

	private HttpHandler defaultHandler = new HttpHandler(){
		@Override
		public void handle(HttpExchange exchange){
			System.out.println("Default Handler!");
			try{
				InputStream reqBody = exchange.getRequestBody();
				DataTransferObject decodedData = encoder.readDTO(reqBody, new DataTransferObject());
				String editedString;
				String toSend = null;
				String command = decodedData.getCommand();
				String stringToEdit = decodedData.getDataToEdit();
				System.out.println("Command: " + decodedData.getCommand());
				if(command.equals("trim")){
				  editedString = stringProcessor.trim(stringToEdit);
					toSend = encoder.encodeData("String",editedString);
				}
				else if(command.equals("lowercase")){
					editedString = stringProcessor.toLower(stringToEdit);
					System.out.println("After going to the stringProcessor, our string is\n" + editedString);
					toSend = encoder.encodeData("String",editedString);
					System.out.println("After encoding to send to client, we have\n" + toSend);
				}
				else if(command.equals("parse")){
					Double foundDouble = stringProcessor.parseDouble(stringToEdit);
					toSend = encoder.encodeData("Double",foundDouble.toString());

				}
				if(toSend != null){
					System.out.println("About to send the following back from the server\n" + toSend);
					sendData(HttpURLConnection.HTTP_OK, toSend, exchange);
				}
				System.out.println("Finished on the Server.  Client should receive response");
				//sendData(toSend);
			}
			catch (IOException e){
                String toSend = encoder.encodeData("Error","IOException");
                System.out.println(e.getMessage());
                sendData(HttpURLConnection.HTTP_NOT_AUTHORITATIVE, toSend, exchange);
			}
			catch (NumberFormatException e){
                String toSend = encoder.encodeData("Error", "NumberFormatException");
                System.out.println(e.getMessage());
                sendData(HttpURLConnection.HTTP_NOT_AUTHORITATIVE, toSend, exchange);
			}
		}
	};


	private void sendData(int responseCode, String responseBody, HttpExchange httpExchange){
		try{
			httpExchange.sendResponseHeaders(responseCode,0);
			OutputStreamWriter os = new OutputStreamWriter(httpExchange.getResponseBody());
			os.write(responseBody);
			os.close();
		}
		catch(IOException e){
            //e.printStackTrace();
            System.out.println("\n Error sending data from sendData method");
        }
	}

}
