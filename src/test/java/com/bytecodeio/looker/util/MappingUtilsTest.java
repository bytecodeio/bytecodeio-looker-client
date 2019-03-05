package com.bytecodeio.looker.util;

import java.util.List;

import org.junit.Test;

import com.bytecodeio.looker.model.Dashboard;
import com.bytecodeio.looker.model.Look;

public class MappingUtilsTest {
	
	MappingUtils mappingUtils = new MappingUtils();
	
	@Test
	public void populateFromJson()throws Exception{
		
		String jsonSource = "[{\"title\":\"a\"},{\"title\":\"b\"}]";
		List<Look> results = mappingUtils.getCollectionFromJson(jsonSource, Look.class);		
		for(Look look:results){
			look.getTitle();
		}
		
		List<Dashboard> dashboards = mappingUtils.getCollectionFromJson(jsonSource, Dashboard.class);		
		for(Dashboard dasboard:dashboards){
			dasboard.getTitle();
		}
		
	}

}
