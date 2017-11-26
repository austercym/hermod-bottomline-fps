package com.hermod.bottomline.fps.services.transform.test;


import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import com.hermod.bottomline.fps.services.transform.Pasc004Transform;
import com.hermod.bottomline.fps.types.FPSMessage;
import org.junit.Test;

import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;

import iso.std.iso._20022.tech.xsd.pacs_004_001.Document;

public class ConvertFullPacs004Test {

	private final Pasc004Transform _transform = new Pasc004Transform();
	
	@Test
	public void conversionToAvroAndBackWorksForPacs004() throws Exception {

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
		final File xmlFile = new File("src/test/resources/full_document_004_001_05.xml");
		final JAXBElement<Document> result = (JAXBElement<Document>)u.unmarshal(xmlFile);
		return (Document) result.getValue();
	}
}
