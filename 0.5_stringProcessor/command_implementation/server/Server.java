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
		mServer.createContext("/trim", trimHandler);
		mServer.createContext("/lowercase", toLowercaseHandler);
		mServer.createContext("/parse", parseDoubleHandler);
		mServer.createContext("/", defaultHandler);


		System.out.println("Starting Server");
		mServer.start();
		System.out.println("Server Started");


	}

	private HttpHandler defaultHandler = new HttpHandler(){
		@Override
		public void handle(HttpExchange exchange){
			System.out.println("Default Handler!");
		}
	};

	private HttpHandler trimHandler = new HttpHandler(){
		@Override
		public void handle(HttpExchange exchange){
			try{
				System.out.println("TrimHandler here on the server!");
				InputStream reqBody = exchange.getRequestBody();
				String stringToEdit = encoder.readString(reqBody);
				String editedString = stringProcessor.trim(stringToEdit);
				String toSend = encoder.encodeData("String",editedString);
				sendData(HttpURLConnection.HTTP_OK, toSend, exchange);
				System.out.println("Finished the trimHandler.  Client should receive response");
				//sendData(toSend);
			}
			catch (IOException e){
                String toSend = encoder.encodeData("Error",e.getMessage());
                System.out.println(e.getMessage());
                sendData(HttpURLConnection.HTTP_NOT_AUTHORITATIVE, toSend, exchange);
			}
		}

	};
	private HttpHandler toLowercaseHandler = new HttpHandler(){
		@Override
		public void handle(HttpExchange exchange){
			try{
				System.out.println("Lowercase Handler here on the server!");
				InputStream reqBody = exchange.getRequestBody();
				String stringToEdit = encoder.readString(reqBody);
				String editedString = stringProcessor.toLower(stringToEdit);
				String toSend = encoder.encodeData("String",editedString);
				sendData(HttpURLConnection.HTTP_OK, toSend, exchange);
				System.out.println("Finished the lowercase handler.  Client should receive response");
			}
			catch (IOException e){
                String toSend = encoder.encodeData("Error",e.getMessage());
                System.out.println(e.getMessage());
                sendData(HttpURLConnection.HTTP_NOT_AUTHORITATIVE, toSend, exchange);
			}

		}

	};
	private HttpHandler parseDoubleHandler = new HttpHandler(){
		@Override
		public void handle(HttpExchange exchange){
			System.out.println("Parse Double Handler here on the server!");
			try{
				InputStream reqBody = exchange.getRequestBody();
				String stringToEdit = encoder.readString(reqBody);
				Double foundDouble = stringProcessor.parseDouble(stringToEdit);
				String toSend = encoder.encodeData("Double",foundDouble.toString());
				sendData(HttpURLConnection.HTTP_OK, toSend, exchange);
				System.out.println("Finished the parseDouble handler.  Client should receive response");
			}
			catch (IOException e){
				System.out.println("Caught an IOException");
                String toSend = encoder.encodeData("Error",e.getMessage());
                System.out.println(e.getMessage());
                sendData(HttpURLConnection.HTTP_NOT_AUTHORITATIVE, toSend, exchange);
			}
			catch(NumberFormatException e){
				System.out.println("Caught a NumberFormatException");
				String toSend = encoder.encodeData("Error", "NumberFormatException");
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
