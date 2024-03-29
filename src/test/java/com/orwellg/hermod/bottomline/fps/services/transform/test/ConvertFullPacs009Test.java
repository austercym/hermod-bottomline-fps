package com.orwellg.hermod.bottomline.fps.services.transform.test;


import com.orwellg.hermod.bottomline.fps.services.transform.Pasc009Transform;
import com.orwellg.hermod.bottomline.fps.types.FPSMessage;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;
import iso.std.iso._20022.tech.xsd.pacs_009_001.Document;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class ConvertFullPacs009Test {

	private final Pasc009Transform _transform = new Pasc009Transform();
	
	@Test
	public void conversionToAvroAndBackWorksForPacs009() throws Exception {

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
		final File xmlFile = new File("src/test/resources/full_document_009_001_05.xml");
		final JAXBElement<Document> result = (JAXBElement<Document>)u.unmarshal(xmlFile);
		return (Document) result.getValue();
	}
}
