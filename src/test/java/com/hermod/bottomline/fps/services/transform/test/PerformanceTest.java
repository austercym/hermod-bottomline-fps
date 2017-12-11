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
		ConvertUSMNetSenderThresholdStatusChangeMessageTest t6 = new ConvertUSMNetSenderThresholdStatusChangeMessageTest();
		ConvertUSMFPSInst3rdPartyStatusMessageTest t7 = new ConvertUSMFPSInst3rdPartyStatusMessageTest();
		ConvertUSMASPMAlertMessageTest t8 = new ConvertUSMASPMAlertMessageTest();
		ConvertUSMNetSenderCapChangeMessageTest t9 = new ConvertUSMNetSenderCapChangeMessageTest();
		ConvertUSMNetSenderCapStatusMessageTest t10 = new ConvertUSMNetSenderCapStatusMessageTest();
		ConvertUSMNetSenderThresholdMessageTest t11 = new ConvertUSMNetSenderThresholdMessageTest();
		ConvertUSMSchemeReturnPaymentFailureTest t12 = new ConvertUSMSchemeReturnPaymentFailureTest();
		ConvertUSMServiceStatusMessageTest t13 = new ConvertUSMServiceStatusMessageTest();
		ConvertUSMSettlementStatusMessageTest t14 = new ConvertUSMSettlementStatusMessageTest();
		ConvertUSMSITLChangeMessageTest t15 = new ConvertUSMSITLChangeMessageTest();
		
		t1.conversionToAvroAndBackWorksForPacs002();
		t2.conversionToAvroAndBackWorksForPacs004();
		t3.conversionToAvroAndBackWorksForPacs007();
		t4.conversionToAvroAndBackWorksForPacs008();
		t5.conversionToAvroAndBackWorksForPacs009();
		t6.conversionToAvroAndBackWorksForNetSenderThresholdStatusChangeMessage();
		t7.conversionToAvroAndBackWorksForFPSInst3rdPartyStatusMessage();
		t7.conversionToAvroAndBackWorksForFPSInst3rdPartyStatusMessage2();
		t8.conversionToAvroAndBackWorksForASPMAlertMessage();
		t9.conversionToAvroAndBackWorksForNetSenderCapChangeMessage();
		t10.conversionToAvroAndBackWorksForNetSenderCapStatusMessage();
		t11.conversionToAvroAndBackWorksForNetSenderThresholdMessage();
		t12.conversionToAvroAndBackWorksForSchemeReturnPaymentFailure();
		t13.conversionToAvroAndBackWorksForServiceStatusMesage();
		t14.conversionToAvroAndBackWorksForSettlementStatusMessage();
		t15.conversionToAvroAndBackWorksForSITLChangeMessage();
		

		System.out.println("starting... ");
		long startTime = System.currentTimeMillis();
		int loops = 10;
		for (int i = 0; i < loops; ++i) {
			t1.conversionToAvroAndBackWorksForPacs002();
			t2.conversionToAvroAndBackWorksForPacs004();
			t3.conversionToAvroAndBackWorksForPacs007();
			t4.conversionToAvroAndBackWorksForPacs008();
			t5.conversionToAvroAndBackWorksForPacs009();
			t6.conversionToAvroAndBackWorksForNetSenderThresholdStatusChangeMessage();
			t7.conversionToAvroAndBackWorksForFPSInst3rdPartyStatusMessage();
			t7.conversionToAvroAndBackWorksForFPSInst3rdPartyStatusMessage2();
			t8.conversionToAvroAndBackWorksForASPMAlertMessage();
			t9.conversionToAvroAndBackWorksForNetSenderCapChangeMessage();
			t10.conversionToAvroAndBackWorksForNetSenderCapStatusMessage();
			t11.conversionToAvroAndBackWorksForNetSenderThresholdMessage();
			t12.conversionToAvroAndBackWorksForSchemeReturnPaymentFailure();
			t13.conversionToAvroAndBackWorksForServiceStatusMesage();
			t14.conversionToAvroAndBackWorksForSettlementStatusMessage();
			t15.conversionToAvroAndBackWorksForSITLChangeMessage();
		}
		long endTime = System.currentTimeMillis();

		// 16 - test, 2 - conversions in test
		double timeUsed = (endTime - startTime) / loops / 16.0 / 2.0;
		
		System.out.println("Time used -  loops:"+ loops +" took " + timeUsed + " ms per conversion (including file marshaling)");		
	}
}
