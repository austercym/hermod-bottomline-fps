package com.orwellg.hermod.bottomline.fps.services.transform.test;


import com.orwellg.hermod.bottomline.fps.services.transform.Pasc008Transform;
import com.orwellg.hermod.bottomline.fps.types.FPSMessage;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;
import iso.std.iso._20022.tech.xsd.pacs_008_001.Document;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.JsonDecoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

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

	@Test
	@Ignore
	public void conversionToFPSAndBackWorksForPacs008() throws Exception {

		FPSMessage message = null;
		FPSAvroMessage result = readFPSMessage();
		try {
			message = _transform.avro2fps(result);
			assert(message != null);

			FPSAvroMessage revert = _transform.fps2avro(message);
			assert(revert != null);
		}
		catch (Exception err) {
			err.printStackTrace();
			throw err;
		}
	}

	private final static FPSAvroMessage readFPSMessage() throws Exception {

		String fileString = new String(Files.readAllBytes(Paths.get("src/test/resources/fps20022_paymentrequest_in_01_008.json")), StandardCharsets.UTF_8);
		FPSAvroMessage record = parseJson(fileString, com.orwellg.umbrella.avro.types.payment.iso20022.pacs.pacs008_001_05.Document.getClassSchema().toString());
		return record;
	}

	private final static FPSMessage readMessage() throws Exception {

		final JAXBContext jc = JAXBContext.newInstance(Document.class);
		final Unmarshaller u = jc.createUnmarshaller();

		//final File xmlFile = new File("src/test/resources/fps20022_paymentrequest_in_01_008.xml");
		final File xmlFile = new File("src/test/resources/check_no_RemittanceInfo.xml");

		final JAXBElement<Document> result = (JAXBElement<Document>)u.unmarshal(xmlFile);
		return result.getValue();
	}


	private static FPSAvroMessage parseJson(String json, String schema) throws IOException {
	  //Schema parsedSchema = Schema.parse(schema);
		Schema.Parser parser = new Schema.Parser();
		Schema parsedSchema  = parser.parse(schema);
		Decoder decoder = DecoderFactory.get().jsonDecoder(parsedSchema, json);

	  DatumReader<FPSAvroMessage> reader =
		  new GenericDatumReader<>(parsedSchema);
	  return reader.read(null, decoder);
	}

}
