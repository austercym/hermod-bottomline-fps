package com.hermod.bottonline.fps.services.transform.test;


import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;

import com.hermod.bottonline.fps.services.transform.Pasc008Transform;
import com.hermod.bottonline.fps.types.FPSMessage;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;

import iso.std.iso._20022.tech.xsd.pacs_008_001.Document;

public class ConvertFullPacs008Test {

	private final Pasc008Transform _transform = new Pasc008Transform();
	
	@Test
	public void conversionToAvroAndBackWorksForPacs008() throws Exception {

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
	

	private final static FPSMessage readMessage() throws Exception {
		
		final JAXBContext jc = JAXBContext.newInstance(Document.class);
		final Unmarshaller u = jc.createUnmarshaller();
		final File xmlFile = new File("src/test/resources/full_document_008_001_05.xml");
		final JAXBElement<Document> result = (JAXBElement<Document>)u.unmarshal(xmlFile);
		return (Document) result.getValue();
	}
}
