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
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;


public class RestClient {

	static final int[] SUCCESS_RESPONSE_CODE_CRITERIA = {200};

	public static String performPOSTOperation(String authToken, String apiEndpoint, HashMap<String, String>params){
		return performPOSTOperation(authToken, apiEndpoint, null, params);
	}

	public static String performPOSTOperation(String authToken, String apiEndpoint, String jsonPostBody, HashMap<String, String>params){
		URL url;
		HttpURLConnection conn=null;
		StringBuffer response;
		BufferedReader br;
		String output;
		String requestUrl = null;
		try{

			System.out.println("Api endpoint: "+ apiEndpoint);

			requestUrl = getUrl(apiEndpoint, params);

			System.out.println("Requesting URL: "+ requestUrl);

			url = new URL(requestUrl);
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
			System.out.println(conn.getResponseCode());

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
			throw new ApiException("Unable to connect to specified url (io exception)..."+ requestUrl);
		}
		catch(Exception ex){
			throw new ApiException("Unable to connect to specified url (exception)..."+ requestUrl + jsonPostBody + ex.toString());
		}
	}

	public static String performGETOperation(String authToken, String apiEndpoint){
		return performGETOperation(authToken, apiEndpoint, new HashMap());
	}

	public static String performSimpleGETOperation(String endpoint, HashMap<String, String>params, int[] expectedResponseCodes){

		URL url;
		HttpURLConnection conn=null;
		OutputStream os;
		StringBuffer response;
		BufferedReader br;
		String output;

		try{
			String requestUrl = getUrl(endpoint, params);

			url = new URL(requestUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setDoInput(true);

		    conn.connect();
			response = new StringBuffer();

			System.out.println("Performing GET operation for '"+ endpoint +"'");
			System.out.println("Response code: "+ conn.getResponseCode());

			boolean expectedResponseCode = false;
			for(int x=0;x<expectedResponseCodes.length;x++){
				if(conn.getResponseCode()==expectedResponseCodes[x]){
					expectedResponseCode = true;
					break;
				}
			}

			if(!expectedResponseCode) {
				String expCodes = "";
				for(int x=0;x<expectedResponseCodes.length;x++){
					expCodes += ","+ expectedResponseCodes;
				}
				throw new RuntimeException("Failed : HTTP response code: "+ conn.getResponseCode() +" did not match expected ("+ expCodes +")");
			}

			if(conn.getResponseCode()==401){
				return null;
			}

			br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			while ((output = br.readLine()) != null) {
				response.append(output);
			}
			System.out.println(response.toString());

			conn.disconnect();

			return response.toString();

		}
		catch(Exception ex){
			throw new ApiException("Unable to connect to specified url (exception)...");
		}

	}

	static String getUrl(String apiEndpoint, HashMap<String, String>params) throws Exception{
		if(params==null){
			return apiEndpoint;
		}

		URIBuilder ub = new URIBuilder(apiEndpoint);
		if(params!=null){
			Iterator<String> i = params.keySet().iterator();
			String key;
			while(i.hasNext()){
				key = i.next();
				ub.addParameter(key, params.get(key));
			}
		}

		return ub.toString();
	}

	/*
	public static String performGETOperation(String authToken, String apiEndpoint, HashMap<String, String>params){
		//return performGETOperation(authToken, apiEndpoint, params, true);


	}
	*/

	public static String performGETOperation(String authToken, String apiEndpoint, HashMap<String, String>params){
		int[] validResponseCodes = {200,404};
		return performGETOperation(authToken, apiEndpoint, params, validResponseCodes);
	}

	public static String performGETOperation(String authToken, String apiEndpoint, HashMap<String, String>params, int[] expectedResponseCodes){
		URL url;
		HttpURLConnection conn=null;
		OutputStream os;
		StringBuffer response;
		BufferedReader br;
		String output;
		try{

			String requestUrl = getUrl(apiEndpoint, params);

			System.out.println("Performing GET operation for '"+ requestUrl +"'");

			url = new URL(requestUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			if(authToken!=null){
				conn.setRequestProperty("Authorization", "token "+ authToken);
			}

		    conn.connect();
			response = new StringBuffer();

			System.out.println("Response code is '"+ conn.getResponseCode() +"'");

			boolean expectedResponseCode = false;
			for(int x=0;x<expectedResponseCodes.length;x++){
				if(conn.getResponseCode()==expectedResponseCodes[x]){
					expectedResponseCode = true;
					break;
				}
			}

			if(!expectedResponseCode) {
				String expCodes = "";
				for(int x=0;x<expectedResponseCodes.length;x++){
					expCodes += ","+ expectedResponseCodes;
				}
				throw new RuntimeException("Failed : HTTP response code: "+ conn.getResponseCode() +" did not match expected ("+ expCodes +")");
			}

			if(conn.getResponseCode()!=200){
				return null;
			}

			br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			while ((output = br.readLine()) != null) {
				response.append(output);
			}
			System.out.println(response.toString());

			conn.disconnect();

			return response.toString();
		}
		catch(URISyntaxException u){
			throw new ApiException("Unable to connect to malformed url...");
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
		catch(Exception ex){
			throw new ApiException("Unable to connect to specified url (exception)...");
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

	public static String performPUTOperation(String authToken, String apiEndpoint, String jsonPostBody, HashMap<String, String>params){
		URL url;
		HttpURLConnection conn=null;
		StringBuffer response;
		BufferedReader br;
		String output;
		String requestUrl = null;
		try{

			System.out.println("Api endpoint: "+ apiEndpoint);

			requestUrl = getUrl(apiEndpoint, params);

			System.out.println("Requesting URL: "+ requestUrl +" (HTTP PUT)");

			url = new URL(requestUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("PUT");
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
			System.out.println("Response code for HTTP PUT method "+ conn.getResponseCode());

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
			throw new ApiException("Unable to connect to specified url (io exception)..."+ requestUrl);
		}
		catch(Exception ex){
			throw new ApiException("Unable to connect to specified url (exception)..."+ requestUrl + jsonPostBody + ex.toString());
		}
	}
	
	public static String performPATCHOperation(String authToken, String apiEndpoint, String jsonBody, HashMap<String, String>params){
		/*
		URL url;
		HttpURLConnection conn=null;
		StringBuffer response;
		BufferedReader br;
		String output;
		String requestUrl = null;
		*/
		try{
			/*
			System.out.println("Api endpoint: '"+ apiEndpoint +"'");

			requestUrl = getUrl(apiEndpoint, params);

			System.out.println("Requesting URL: '"+ requestUrl +"' (HTTP PATCH)");

			url = new URL(requestUrl);
			conn = (HttpURLConnection) url.openConnection();
			//conn.setRequestProperty("X-HTTP-Method-Override", "PATCH");
			conn.setRequestMethod("PATCH");
			if(authToken!=null){
				conn.setDoInput(true);
				conn.setRequestProperty("Authorization", "token "+ authToken);
			}
			else{
				conn.setDoInput(true);
			}

			if(jsonPostBody!=null){

				conn.setRequestProperty("Content-Type", "application/json");
				conn.setRequestProperty("Accept", "application/json");
				
				conn.setDoOutput(true);
				OutputStream outputStream = conn.getOutputStream();
				outputStream.write(jsonPostBody.getBytes("UTF-8"));
				outputStream.flush();
			}

		    conn.connect();
			response = new StringBuffer();
			System.out.println("Response code for HTTP PATCH method "+ conn.getResponseCode());

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
			*/
			
			
			HttpClient client;
			client = HttpClientBuilder.create().build();
	    	HttpPatch request = new HttpPatch(apiEndpoint);
	    	
	    	request.setEntity((HttpEntity)new StringEntity(jsonBody));
	    	request.addHeader("Accept", "application/json");
	    	request.addHeader("Content-Type", "application/json");
	    	request.addHeader("Authorization", "token "+ authToken);
	    	
	    	HttpResponse response = client.execute(request);
	    	
	    	BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

	    	StringBuffer result = new StringBuffer();
    		String line = "";
    		while ((line = rd.readLine()) != null) {
    			result.append(line);
    		}
    		return result.toString();
			
		}
		catch(MalformedURLException m){
			throw new ApiException("Unable to connect to malformed url...");
		}
		catch( ProtocolException p){
			throw new ApiException("Unable to connect to specified url (protocol exception)...");
		}
		catch(IOException io){
			throw new ApiException("Unable to connect to specified url (io exception)..."+ apiEndpoint);
		}
		catch(Exception ex){
			throw new ApiException("Unable to connect to specified url (exception)..."+ apiEndpoint);
		}
	}
}
