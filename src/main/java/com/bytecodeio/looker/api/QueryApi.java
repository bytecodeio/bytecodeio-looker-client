package com.bytecodeio.looker.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.bytecodeio.looker.util.RestClient;
import com.bytecodeio.looker.util.UniqueTitleFormatter;
import com.bytecodeio.looker.model.Look;
import com.bytecodeio.looker.model.Query;
import com.bytecodeio.looker.model.Space;
import com.bytecodeio.looker.util.ApiException;
import com.bytecodeio.looker.util.Config;
import com.bytecodeio.looker.util.MappingUtils;

public class QueryApi extends ApiBase{

	String apiSuffix_3_0 = Config.CONFIG_API_BASE_3_0 +"/queries";

	public static final String FORMAT_CSV = "csv";
	public static final String FORMAT_HTML = "html";
	public static final String FORMAT_XLSX = "xlsx";
	public static final String FORMAT_PNG = "png";
	public static final String FORMAT_JPG = "jpg";
	public static final String FORMAT_PDF = "pdf";

	/**
	 * 'Render' a look to a given text format (csv, html, or xlsv).
	 *
	 *
	 * @param queryId
	 * @param format
	 * @return
	 * @throws ApiException
	 */
	public String executeQueryRenderTaskForTextContent(Long queryId, String format)throws ApiException{
		String requestUrl =  apiSuffix_3_0 +"/"+ queryId +"/run/"+ format;
		String responseHtml = RestClient.performGETOperation(getAuthToken(), requestUrl);
		return responseHtml;
	}

	public byte[] executeQueryRenderTaskForBinaryContent(Long queryId, String format)throws ApiException{
		String requestUrl =  apiSuffix_3_0 +"/"+ queryId +"/run/"+ format;
		byte[] responseByte = RestClient.performBinaryGETOperation(getAuthToken(), requestUrl);
		return responseByte;
	}
	
	public String executeQuery(String query)throws ApiException{
		String queryResponseJson = RestClient.performPOSTOperation(getAuthToken(), apiSuffix_3_0 +"/run/json", query, new HashMap());
		return queryResponseJson;
	}
	
	public Query createQuery(Query query){
		String queryJson;
		
		try{
			queryJson = MappingUtils.serializeToJson(query);
		}catch(Exception e){
			throw new ApiException("Unable to map object to json format");
		}

		String jsonResponse = RestClient.performPOSTOperation(getAuthToken(), apiSuffix_3_0, queryJson, null);
		
		try{
			MappingUtils.populateFromJson(jsonResponse, query);
		}catch(Exception e){
			throw new ApiException("Unable to parse response from call");
		}

		return query;
	}
}
