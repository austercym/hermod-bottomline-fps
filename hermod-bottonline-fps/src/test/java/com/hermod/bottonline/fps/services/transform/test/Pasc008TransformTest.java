package com.hermod.bottonline.fps.services.transform.test;


import java.io.File;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;

import com.hermod.bottonline.fps.services.transform.Pasc008Transform;
import com.hermod.bottonline.fps.types.FPSMessage;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;

import iso.std.iso._20022.tech.xsd.pacs_008_001.Document;

public class Pasc008TransformTest {

	private final Pasc008Transform _transform = new Pasc008Transform();
	
	@Test
	public void testConversionFromXmlToAvroWorks() throws Exception {

		//com.sun.org.apache.xml.internal.security.Init.init();
		FPSMessage message = readMessage();
		FPSAvroMessage result = null;
		try {
			for (int i = 0; i < 10; ++i) {
				long start = System.currentTimeMillis();
				result = _transform.fps2avro(message);
				long stop = System.currentTimeMillis();
				System.out.println("Execution time: " + (stop - start));
			}
			assert(result != null);			
		}
		catch (Exception err) {
			err.printStackTrace();
			assert(false);
		}
		
	}
	
	private final static FPSMessage readMessage() throws Exception {
		
		JAXBContext jc = JAXBContext.newInstance(Document.class);
		Unmarshaller u = jc.createUnmarshaller();
		File xmlFile = new File("src/test/resources/pacs.008.001.xml");
		JAXBElement<Document> result = (JAXBElement<Document>)u.unmarshal(xmlFile);
		return (Document) result.getValue();
	}
}
