package com.bytecodeio.looker;

public class YapstoneCalls {
	
	public static void main(String[] args){
		
		try{
			String apiToken = "gtfp9GZxRZ2PzyhHPFrG";
			String apiSecret = "XCRvfrpKgdQCjBfH9xw42Z76";
			String apiHost = "https://bytecode.looker.com:19999/api/3.0";
			
			//Render a 'look'
			//https://bytecode.looker.com:19999/api/3.0/looks/42/run/json
			/*
			 [
				  {
				    "usa_1910_2013.gender": "M",
				    "usa_1910_2013.total_names": 153421155
				  },
				  {
				    "usa_1910_2013.gender": "F",
				    "usa_1910_2013.total_names": 142305910
				  }
				]
			 */
			
			//Render a filter on a 'look'
			//Query: 650
			/*
			 [
				  {
				    "usa_1910_2013.gender": "M",
				    "usa_1910_2013.total_names": 153421155
				  },
				  {
				    "usa_1910_2013.gender": "F",
				    "usa_1910_2013.total_names": 142305910
				  }
				]
			 */
			
			//Add a filter to a look
			
			
			System.out.println("Fin");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
