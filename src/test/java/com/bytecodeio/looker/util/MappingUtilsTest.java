package com.bytecodeio.looker.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.bytecodeio.looker.model.Look;

public class MappingUtilsTest {
	
	MappingUtils mappingUtils = new MappingUtils();
	
	@Test
	public void populateFromJson()throws Exception{
		
		String jsonSource = "[{\"title\":\"a\"},{\"title\":\"b\"}]";
		
		List<Look> results = new ArrayList();
		mappingUtils.populateFromJson(jsonSource, results);
		/*
		for(Look look:results){
			look.getTitle();
		}
		*/
		
	}

}
