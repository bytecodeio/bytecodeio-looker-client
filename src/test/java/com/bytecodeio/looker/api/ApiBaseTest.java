package com.bytecodeio.looker.api;

import static org.junit.Assert.*;

import org.junit.Test;

public class ApiBaseTest {
	
	@Test
	public void testParseFieldArgs(){
		ApiBase apiBase = new ApiBase();
		String[] fields = {"field1", "field2", "field3"};
		String result;	
		result = apiBase.getFieldCriteria(fields);
		assertTrue(result.equals("field1,field2,field3"));	
	}
	
}
