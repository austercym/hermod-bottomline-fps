package com.hermod.bottonline.fps.services.transform.test;


import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Test;

import com.hermod.bottonline.fps.services.transform.Pasc002Transform;
import com.hermod.bottonline.fps.types.FPSMessage;
import com.orwellg.umbrella.avro.types.payment.fps.FPSAvroMessage;
import iso.std.iso._20022.tech.xsd.pacs_002_001.*;

public class Pasc002TransformTest {

	private final Pasc002Transform _transform = new Pasc002Transform();
	
	@Test
	public void testConversionFromXmlToAvroWorks() throws Exception {

		//com.sun.org.apache.xml.internal.security.Init.init();
		FPSMessage message = readMessage();
		FPSAvroMessage result = null;
		
		result = _transform.fps2avro(message);
		
		assert(result != null);
	}
	
	private final static FPSMessage readMessage() throws Exception {
		
		DatatypeFactory dt = DatatypeFactory.newInstance();
		Document d = new Document();
		{
			FIToFIPaymentStatusReportV06 report = new FIToFIPaymentStatusReportV06();
			{
				{
					OriginalGroupHeader1 header1 = new OriginalGroupHeader1();
					XMLGregorianCalendar cal = dt.newXMLGregorianCalendar(new GregorianCalendar(2014, 2, 11));
					header1.setOrgnlCreDtTm(cal);
					header1.setOrgnlMsgId("sdfsdf");
					header1.setOrgnlMsgNmId("sdf232323");
					header1.setOrgnlNbOfTxs("23232323");
					TransactionGroupStatus3Code code = TransactionGroupStatus3Code.PART;
					header1.setGrpSts(code);
					report.getOrgnlGrpInfAndSts().add(header1);
				}
				{
					PaymentTransaction52 transaction = new PaymentTransaction52();
					transaction.setAccptncDtTm(dt.newXMLGregorianCalendar(new GregorianCalendar(2017, 2, 3)));
					transaction.setTxSts(TransactionIndividualStatus3Code.RJCT);

					{
						BranchAndFinancialInstitutionIdentification5 b = new BranchAndFinancialInstitutionIdentification5();
						{
							FinancialInstitutionIdentification8 f = new FinancialInstitutionIdentification8();
							{
								PostalAddress6 a = new PostalAddress6();
								a.getAdrLine().add("line1");
								a.getAdrLine().add("line2");
								f.setPstlAdr(a);
							}
							b.setFinInstnId(f);
						}
						transaction.setInstdAgt(b);
					}

					
					report.getTxInfAndSts().add(transaction);									
				}
			}
			d.setFIToFIPmtStsRpt(report);
										
		}
//		GroupHeader53 header = new GroupHeader53();
	//	header.setMsgId("test1234");
		//GregorianCalendar gc= new GregorianCalendar(2017, 1, 1);
		//header.setCreDtTm(factory.newXMLGregorianCalendar(gc));
		
		//report.setGrpHdr(header);
		return d;
		/*
		JAXBContext jc = JAXBContext.newInstance(Document.class);
		Unmarshaller u = jc.createUnmarshaller();
		File xmlFile = new File("src/test/resources/pacs.002.001.xml");
		Object result = u.unmarshal(xmlFile);
		return (Document) result;
		*/
	}
}
