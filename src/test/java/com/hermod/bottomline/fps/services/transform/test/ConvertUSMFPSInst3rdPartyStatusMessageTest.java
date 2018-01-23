package com.hermod.bottomline.fps.services.transform.test;

import com.bottomline.directfps.fpsusmelements.FPSInst3RdPartyStatusMessageType;
import com.hermod.bottomline.fps.services.transform.FPSInst3rdPartyStatusMessageTransform;
import com.hermod.bottomline.fps.types.FPSMessage;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;
import org.junit.Test;

import javax.xml.bind.JAXB;
import java.io.File;

public class ConvertUSMFPSInst3rdPartyStatusMessageTest {

	private final FPSInst3rdPartyStatusMessageTransform _transform = new FPSInst3rdPartyStatusMessageTransform();


	@Test
	public void conversionToAvroAndBackWorksForFPSInst3rdPartyStatusMessage() throws Exception {

		FPSMessage message = readMessage();
		FPSAvroMessage result = null;
		try {
			result = _transform.fps2avro(message);
			assert(result != null);

			FPSMessage revert = _transform.avro2fps(result);
			assert(revert != null);
		}
		catch (Exception err) {
			err.printStackTrace();
			throw err;
		}		
	}

	@Test
	public void conversionToAvroAndBackWorksForFPSInst3rdPartyStatusMessage2() throws Exception {

		FPSMessage message = readMessage2();
		FPSAvroMessage result = null;
		try {
			result = _transform.fps2avro(message);
			assert(result != null);

			FPSMessage revert = _transform.avro2fps(result);
			assert(revert != null);
		}
		catch (Exception err) {
			err.printStackTrace();
			throw err;
		}
	}
	

	private final static FPSMessage readMessage() throws Exception {

		final File xmlFile = new File("src/test/resources/usm_PartyStatusMessage.xml");

		FPSInst3RdPartyStatusMessageType serviceStatusMessage = JAXB.unmarshal(xmlFile, FPSInst3RdPartyStatusMessageType.class);
		return serviceStatusMessage;

	}

	private final static FPSMessage readMessage2() throws Exception {

		final File xmlFile = new File("src/test/resources/usm_PartyStatusMessage2.xml");

		FPSInst3RdPartyStatusMessageType serviceStatusMessage = JAXB.unmarshal(xmlFile, FPSInst3RdPartyStatusMessageType.class);
		return serviceStatusMessage;

	}
}
