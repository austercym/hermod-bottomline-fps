package com.orwellg.hermod.bottomline.fps.utils.generators;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;

public class SchemeValidatorBean {

    private static SchemeValidatorBean instance = null;
    private Validator validatorPacs008;
    private Validator validatorPacs007;
    private Validator validatorPacs002;
    private Validator validatorUSM;

    public Validator getValidatorPacs008() {
        return validatorPacs008;
    }

    public Validator getValidatorPacs007() {
        return validatorPacs007;
    }

    public Validator getValidatorPacs002() {
        return validatorPacs002;
    }

    public Validator getValidatorUSM() {
        return validatorUSM;
    }

    //private constructor to avoid client applications to use constructor
    private SchemeValidatorBean() throws IOException, SAXException {
        validatorPacs008 = getValidator("./xsd/pacs.008.001.05.xsd");
        validatorPacs007 = getValidator("./xsd/pacs.007.001.05.xsd");
        validatorPacs002 = getValidator("./xsd/pacs.002.001.06.xsd");
        validatorUSM = getUSMValidator();
    }

    private Validator getValidator(String s) throws SAXException, IOException {
        Resource xsdResource = new ClassPathResource(s);
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(new StreamSource(xsdResource.getInputStream()));
        return schema.newValidator();
    }

    private Validator getUSMValidator() throws SAXException, IOException {
        Resource fpsUSMxsdResource = new ClassPathResource("./xsd/usm/FPSUSMs.xsd");
        Resource iso20022TypesxsdResource = new ClassPathResource("./xsd/usm/ISO20022Types.xsd");
        Resource fpsUSMTypesxsdResource = new ClassPathResource("./xsd/usm/FPSUSMTypes.xsd");
        Resource fpsUSMElementsResource = new ClassPathResource("./xsd/usm/FPSUSMElements.xsd");
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        Source[]  xsdSources = new Source[]{new StreamSource(iso20022TypesxsdResource.getInputStream()),
                    new StreamSource(fpsUSMTypesxsdResource.getInputStream()),
                    new StreamSource(fpsUSMElementsResource.getInputStream()),
                    new StreamSource(fpsUSMxsdResource.getInputStream())};
        Schema schema = schemaFactory.newSchema(xsdSources);
        return schema.newValidator();

    }

    // Lazy Initialization (If required then only)
    public static SchemeValidatorBean getInstance() throws IOException, SAXException {
        if (instance == null) {
            // Thread Safe. Might be costly operation in some case
            synchronized (SchemeValidatorBean.class) {
                if (instance == null) {
                    instance = new SchemeValidatorBean();
                }
            }
        }
        return instance;
    }

}
