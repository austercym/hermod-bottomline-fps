package com.hermod.bottomline.fps.services.transform.test;

import org.junit.Test;

public class PerformanceTest {

	@Test
	public void runPerformanceTest() throws Exception {
		ConvertFullPacs002Test t1 = new ConvertFullPacs002Test();
		ConvertFullPacs004Test t2 = new ConvertFullPacs004Test();
		ConvertFullPacs007Test t3 = new ConvertFullPacs007Test();
		ConvertFullPacs008Test t4 = new ConvertFullPacs008Test();
		ConvertFullPacs009Test t5 = new ConvertFullPacs009Test();
		
		t1.conversionToAvroAndBackWorksForPacs002();
		t2.conversionToAvroAndBackWorksForPacs004();
		t3.conversionToAvroAndBackWorksForPacs007();
		t4.conversionToAvroAndBackWorksForPacs008();
		t5.conversionToAvroAndBackWorksForPacs009();
		

		System.out.println("starting... ");
		long startTime = System.currentTimeMillis();
		int loops = 10;
		for (int i = 0; i < loops; ++i) {
			t1.conversionToAvroAndBackWorksForPacs002();
			t2.conversionToAvroAndBackWorksForPacs004();
			t3.conversionToAvroAndBackWorksForPacs007();
			t4.conversionToAvroAndBackWorksForPacs008();
			t5.conversionToAvroAndBackWorksForPacs009();
		}
		long endTime = System.currentTimeMillis();

		// 5 - test, 2 - conversions in test 
		double timeUsed = (endTime - startTime) / loops / 5.0 / 2.0;
		
		System.out.println("Time used -  loops:"+ loops +" took " + timeUsed + " ms per conversion (including file marshaling)");		
	}
}
