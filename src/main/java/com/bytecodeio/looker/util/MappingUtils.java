package com.bytecodeio.looker.util;

import java.util.ArrayList;
import java.util.List;

import com.bytecodeio.looker.model.Dashboard;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class MappingUtils {

	public static final String serializeToJson(Object object)throws Exception{
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
	
	public static final String serializeToJson(List objects)throws Exception{
		ObjectMapper objectMapper = new ObjectMapper();
		StringBuffer results = new StringBuffer();
		for(Object o : objects){
			results.append(objectMapper.writeValueAsString(o));
		}
		return results.toString();
	}
	
	public static final void populateFromJson(String jsonSource, Object target)throws Exception{
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.readerForUpdating(target).readValue(jsonSource);
	}
	
	public static final <T>List<T>getCollectionFromJson(String jsonSource, Class<T> targetClass)throws Exception{
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		TypeFactory t = TypeFactory.defaultInstance();
	    ArrayList<T> results = mapper.readValue(jsonSource, t.constructCollectionType(ArrayList.class,targetClass));
		return results;
	}
	
}
