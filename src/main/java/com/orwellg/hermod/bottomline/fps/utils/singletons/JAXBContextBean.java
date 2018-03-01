package com.orwellg.hermod.bottomline.fps.utils.singletons;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

public class JAXBContextBean {

    public static final String PACS_008 = "pacs008";
    public static final String PACS_002 = "pacs002";
    private static JAXBContextBean instance = null;
    private  JAXBContext jaxbContextPacs008;
    private  JAXBContext jaxbContextPacs002;

    public JAXBContext getJaxbContextPacs008() {
        return jaxbContextPacs008;
    }

    public void setJaxbContextPacs008(JAXBContext jaxbContextPacs008) {
        this.jaxbContextPacs008 = jaxbContextPacs008;
    }

    public JAXBContext getJaxbContextPacs002() {
        return jaxbContextPacs002;
    }

    public void setJaxbContextPacs002(JAXBContext jaxbContextPacs002) {
        this.jaxbContextPacs002 = jaxbContextPacs002;
    }


    //private constructor to avoid client applications to use constructor
    private JAXBContextBean() throws JAXBException {
        jaxbContextPacs008 = getJAXBContext(PACS_008);
        jaxbContextPacs002 = getJAXBContext(PACS_002);
    }

    private JAXBContext getJAXBContext(String pacs) throws JAXBException {
        JAXBContext jc;
        if(PACS_008.equalsIgnoreCase(pacs)) {
            jc = JAXBContext.newInstance(iso.std.iso._20022.tech.xsd.pacs_008_001.Document.class);
        }else{
            jc = JAXBContext.newInstance(iso.std.iso._20022.tech.xsd.pacs_002_001.Document.class);
        }
        return jc;
    }

    // Lazy Initialization (If required then only)
    public static JAXBContextBean getInstance() throws JAXBException {
        if (instance == null) {
            // Thread Safe. Might be costly operation in some case
            synchronized (JAXBContextBean.class) {
                if (instance == null) {
                    instance = new JAXBContextBean();
                }
            }
        }
        return instance;
    }

}
