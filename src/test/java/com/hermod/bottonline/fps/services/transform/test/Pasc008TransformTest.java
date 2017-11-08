package com.hermod.bottonline.fps.services.transform.test;


import java.io.File;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.io.DatumReader;
import org.apache.avro.specific.SpecificDatumReader;
import org.junit.Test;

import com.hermod.bottonline.fps.services.transform.Pasc008Transform;
import com.hermod.bottonline.fps.types.FPSMessage;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;

import iso.std.iso._20022.tech.xsd.pacs_008_001.Document;

public class Pasc008TransformTest {

	private final Pasc008Transform _transform = new Pasc008Transform();
	
	@Test
	public void testConversionFromXmlToAvroWorks() throws Exception {

		FPSMessage message = readMessage();
		FPSAvroMessage result = null;
		try {
			result = _transform.fps2avro(message);
			assert(result != null);
			
			FPSMessage revert = _transform.avro2fps(result);
			revert.hashCode();
		}
		catch (Exception err) {
			err.printStackTrace();
			throw err;
		}		
	}
	
	/*
	@Test
	public void testConversionFromAvroToXmlWorks() throws Exception {
		try {
			FPSAvroMessage message = readAvroMessage();
		}
		catch (Exception err) {
			err.printStackTrace();
			throw err;
		}
	}
	*/
	private final static FPSMessage readMessage() throws Exception {
		
		final JAXBContext jc = JAXBContext.newInstance(Document.class);
		final Unmarshaller u = jc.createUnmarshaller();
		final File xmlFile = new File("src/test/resources/pacs.008.001.xml");
		final JAXBElement<Document> result = (JAXBElement<Document>)u.unmarshal(xmlFile);
		return (Document) result.getValue();
	}
	
	private final static FPSAvroMessage readAvroMessage() throws Exception {
		final Schema schema = com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Document.SCHEMA$;
		final DatumReader<com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Document> reader = 
				new SpecificDatumReader<com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Document>(com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Document.class);

		final DataFileReader<com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Document> fileReader = 
				new DataFileReader<com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Document>(new File("src/test/resources/pacs.008.001.json"), reader); 
				
		final Object document = fileReader.next();
		
		final FPSAvroMessage message = new FPSAvroMessage(document);
		
		return message;
	}
}
