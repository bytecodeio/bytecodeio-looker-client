package com.bytecodeio.looker.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import com.bytecodeio.looker.model.AuthToken;

public class RestClient {

	public static String performPOSTOperation(String authToken, String apiEndpoint){
		return performPOSTOperation(authToken, apiEndpoint, null);
	}
	
	public static String performPOSTOperation(String authToken, String apiEndpoint, String jsonPostBody){
		URL url;
		HttpURLConnection conn=null;
		StringBuffer response;
		BufferedReader br;
		String output;
		try{
			url = new URL(apiEndpoint);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			if(authToken!=null){
				conn.setDoInput(true);
				conn.setRequestProperty("Authorization", "token "+ authToken);
			}
			else{
				conn.setDoInput(true);
			}
			
			if(jsonPostBody!=null){
				
				conn.setRequestProperty("Content-Type", "application/json");
				
				conn.setDoOutput(true);
				OutputStream outputStream = conn.getOutputStream();
				outputStream.write(jsonPostBody.getBytes("UTF-8"));
				outputStream.flush();
			}
			
		    conn.connect();
			response = new StringBuffer();
			
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
			
			br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			while ((output = br.readLine()) != null) {
				response.append(output);
			}
			
			conn.disconnect();
			
			return response.toString();
		}
		catch(MalformedURLException m){
			throw new ApiException("Unable to connect to malformed url...");
		}
		catch( ProtocolException p){
			throw new ApiException("Unable to connect to specified url (protocol exception)...");
		}
		catch(IOException io){
			throw new ApiException("Unable to connect to specified url (io exception)...");
		}
	}
	
	public static String performGETOperation(String authToken, String apiEndpoint){
		URL url;
		HttpURLConnection conn=null;
		OutputStream os;
		StringBuffer response;
		BufferedReader br;
		String output;
		try{
			url = new URL(apiEndpoint);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			if(authToken!=null){
				conn.setRequestProperty("Authorization", "token "+ authToken);
			}
			
		    conn.connect();
			response = new StringBuffer();
			
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
			
			br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			while ((output = br.readLine()) != null) {
				response.append(output);
			}
			System.out.println(response.toString());
			
			conn.disconnect();
			
			return response.toString();
		}
		catch(MalformedURLException m){
			throw new ApiException("Unable to connect to malformed url...");
		}
		catch( ProtocolException p){
			throw new ApiException("Unable to connect to specified url (protocol exception)...");
		}
		catch(IOException io){
			throw new ApiException("Unable to connect to specified url (io exception)...");
		}
	}
	
	public static byte[] performBinaryGETOperation(String authToken, String apiEndpoint){
		URL url;
		HttpURLConnection conn=null;
		OutputStream os;
		StringBuffer response;
		BufferedInputStream inputStream;
		String output;
		try{
			url = new URL(apiEndpoint);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			conn.setRequestProperty("Authorization", "token "+ authToken);
			
			
		    conn.connect();
			response = new StringBuffer();
			
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
			
			InputStream is = conn.getInputStream();
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();

			int nRead;
			byte[] data = new byte[16384];

			while ((nRead = is.read(data, 0, data.length)) != -1) {
			  buffer.write(data, 0, nRead);
			}

			conn.disconnect();
			
			return buffer.toByteArray();
			
		}
		catch(MalformedURLException m){
			throw new ApiException("Unable to connect to malformed url...");
		}
		catch( ProtocolException p){
			throw new ApiException("Unable to connect to specified url (protocol exception)...");
		}
		catch(IOException io){
			throw new ApiException("Unable to connect to specified url (io exception)...");
		}
	}
	
	public static void performDELETEOperation(String authToken, String apiEndpoint){
		URL url;
		HttpURLConnection conn=null;
		OutputStream os;
		StringBuffer response;
		BufferedReader br;
		String output;
		try{
			url = new URL(apiEndpoint);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("DELETE");
			conn.setDoInput(true);
			conn.setRequestProperty("Authorization", "token "+ authToken);
			
			
		    conn.connect();
			response = new StringBuffer();
			
			if (conn.getResponseCode()!= 200&&conn.getResponseCode()!=204) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode() +" for url '"+ apiEndpoint +"'");
			}
			
			br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			while ((output = br.readLine()) != null) {
				response.append(output);
			}
			System.out.println(response.toString());
			
			conn.disconnect();
			
		}
		catch(MalformedURLException m){
			throw new ApiException("Unable to connect to malformed url...");
		}
		catch( ProtocolException p){
			throw new ApiException("Unable to connect to specified url (protocol exception)...");
		}
		catch(IOException io){
			throw new ApiException("Unable to connect to specified url (io exception)...");
		}
	}
	
}
