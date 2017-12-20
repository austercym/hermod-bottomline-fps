package com.hermod.bottomline.fps.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

public class CurrencyCodes {

    public final static Logger LOG = LogManager.getLogger(CurrencyCodes.class);

    private static CurrencyCodes instance = null;

    private static final HashMap<String, String> currencyCodes = createMap();

    // Lazy Initialization (If required then only)
    public static CurrencyCodes getInstance() {
        if (instance == null) {
            // Thread Safe. Might be costly operation in some case
            synchronized (CurrencyCodes.class) {
                if (instance == null) {
                    instance = new CurrencyCodes();
                }
            }
        }
        return instance;
    }

    private static HashMap<String, String> createMap()
    {
        HashMap<String,String> currencyCodesMap = new HashMap<String,String>();
        currencyCodesMap.put("AED","784");
        currencyCodesMap.put("AFN","971");
        currencyCodesMap.put("ALL","8");
        currencyCodesMap.put("AMD","51");
        currencyCodesMap.put("ANG","532");
        currencyCodesMap.put("AOA","973");
        currencyCodesMap.put("ARS","32");
        currencyCodesMap.put("AUD","36");
        currencyCodesMap.put("AWG","533");
        currencyCodesMap.put("AZN","944");
        currencyCodesMap.put("BAM","977");
        currencyCodesMap.put("BBD","52");
        currencyCodesMap.put("BDT","50");
        currencyCodesMap.put("BGN","975");
        currencyCodesMap.put("BHD","48");
        currencyCodesMap.put("BIF","108");
        currencyCodesMap.put("BMD","60");
        currencyCodesMap.put("BND","96");
        currencyCodesMap.put("BOB","68");
        currencyCodesMap.put("BOV","984");
        currencyCodesMap.put("BRL","986");
        currencyCodesMap.put("BSD","44");
        currencyCodesMap.put("BTN","64");
        currencyCodesMap.put("BWP","72");
        currencyCodesMap.put("BYR","974");
        currencyCodesMap.put("BZD","84");
        currencyCodesMap.put("CAD","124");
        currencyCodesMap.put("CDF","976");
        currencyCodesMap.put("CHE","947");
        currencyCodesMap.put("CHF","756");
        currencyCodesMap.put("CHW","948");
        currencyCodesMap.put("CLF","990");
        currencyCodesMap.put("CLP","152");
        currencyCodesMap.put("CNY","156");
        currencyCodesMap.put("COP","170");
        currencyCodesMap.put("COU","970");
        currencyCodesMap.put("CRC","188");
        currencyCodesMap.put("CUC","931");
        currencyCodesMap.put("CUP","192");
        currencyCodesMap.put("CVE","132");
        currencyCodesMap.put("CZK","203");
        currencyCodesMap.put("DJF","262");
        currencyCodesMap.put("DKK","208");
        currencyCodesMap.put("DOP","214");
        currencyCodesMap.put("DZD","12");
        currencyCodesMap.put("EGP","818");
        currencyCodesMap.put("ERN","232");
        currencyCodesMap.put("ETB","230");
        currencyCodesMap.put("EUR","978");
        currencyCodesMap.put("FJD","242");
        currencyCodesMap.put("FKP","238");
        currencyCodesMap.put("GBP","826");
        currencyCodesMap.put("GEL","981");
        currencyCodesMap.put("GHS","936");
        currencyCodesMap.put("GIP","292");
        currencyCodesMap.put("GMD","270");
        currencyCodesMap.put("GNF","324");
        currencyCodesMap.put("GTQ","320");
        currencyCodesMap.put("GYD","328");
        currencyCodesMap.put("HKD","344");
        currencyCodesMap.put("HNL","340");
        currencyCodesMap.put("HRK","191");
        currencyCodesMap.put("HTG","332");
        currencyCodesMap.put("HUF","348");
        currencyCodesMap.put("IDR","360");
        currencyCodesMap.put("ILS","376");
        currencyCodesMap.put("INR","356");
        currencyCodesMap.put("IQD","368");
        currencyCodesMap.put("IRR","364");
        currencyCodesMap.put("ISK","352");
        currencyCodesMap.put("JMD","388");
        currencyCodesMap.put("JOD","400");
        currencyCodesMap.put("JPY","392");
        currencyCodesMap.put("KES","404");
        currencyCodesMap.put("KGS","417");
        currencyCodesMap.put("KHR","116");
        currencyCodesMap.put("KMF","174");
        currencyCodesMap.put("KPW","408");
        currencyCodesMap.put("KRW","410");
        currencyCodesMap.put("KWD","414");
        currencyCodesMap.put("KYD","136");
        currencyCodesMap.put("KZT","398");
        currencyCodesMap.put("LAK","418");
        currencyCodesMap.put("LBP","422");
        currencyCodesMap.put("LKR","144");
        currencyCodesMap.put("LRD","430");
        currencyCodesMap.put("LSL","426");
        currencyCodesMap.put("LYD","434");
        currencyCodesMap.put("MAD","504");
        currencyCodesMap.put("MDL","498");
        currencyCodesMap.put("MGA","969");
        currencyCodesMap.put("MKD","807");
        currencyCodesMap.put("MMK","104");
        currencyCodesMap.put("MNT","496");
        currencyCodesMap.put("MOP","446");
        currencyCodesMap.put("MRO","478");
        currencyCodesMap.put("MUR","480");
        currencyCodesMap.put("MVR","462");
        currencyCodesMap.put("MWK","454");
        currencyCodesMap.put("MXN","484");
        currencyCodesMap.put("MXV","979");
        currencyCodesMap.put("MYR","458");
        currencyCodesMap.put("MZN","943");
        currencyCodesMap.put("NAD","516");
        currencyCodesMap.put("NGN","566");
        currencyCodesMap.put("NIO","558");
        currencyCodesMap.put("NOK","578");
        currencyCodesMap.put("NPR","524");
        currencyCodesMap.put("NZD","554");
        currencyCodesMap.put("OMR","512");
        currencyCodesMap.put("PAB","590");
        currencyCodesMap.put("PEN","604");
        currencyCodesMap.put("PGK","598");
        currencyCodesMap.put("PHP","608");
        currencyCodesMap.put("PKR","586");
        currencyCodesMap.put("PLN","985");
        currencyCodesMap.put("PYG","600");
        currencyCodesMap.put("QAR","634");
        currencyCodesMap.put("RON","946");
        currencyCodesMap.put("RSD","941");
        currencyCodesMap.put("RUB","643");
        currencyCodesMap.put("RWF","646");
        currencyCodesMap.put("SAR","682");
        currencyCodesMap.put("SBD","90");
        currencyCodesMap.put("SCR","690");
        currencyCodesMap.put("SDG","938");
        currencyCodesMap.put("SEK","752");
        currencyCodesMap.put("SGD","702");
        currencyCodesMap.put("SHP","654");
        currencyCodesMap.put("SLL","694");
        currencyCodesMap.put("SOS","706");
        currencyCodesMap.put("SRD","968");
        currencyCodesMap.put("SSP","728");
        currencyCodesMap.put("STD","678");
        currencyCodesMap.put("SVC","222");
        currencyCodesMap.put("SYP","760");
        currencyCodesMap.put("SZL","748");
        currencyCodesMap.put("THB","764");
        currencyCodesMap.put("TJS","972");
        currencyCodesMap.put("TMT","934");
        currencyCodesMap.put("TND","788");
        currencyCodesMap.put("TOP","776");
        currencyCodesMap.put("TRY","949");
        currencyCodesMap.put("TTD","780");
        currencyCodesMap.put("TWD","901");
        currencyCodesMap.put("TZS","834");
        currencyCodesMap.put("UAH","980");
        currencyCodesMap.put("UGX","800");
        currencyCodesMap.put("USD","840");
        currencyCodesMap.put("USN","997");
        currencyCodesMap.put("UYI","940");
        currencyCodesMap.put("UYU","858");
        currencyCodesMap.put("UZS","860");
        currencyCodesMap.put("VEF","937");
        currencyCodesMap.put("VND","704");
        currencyCodesMap.put("VUV","548");
        currencyCodesMap.put("WST","882");
        currencyCodesMap.put("XAF","950");
        currencyCodesMap.put("XAG","961");
        currencyCodesMap.put("XAU","959");
        currencyCodesMap.put("XBA","955");
        currencyCodesMap.put("XBB","956");
        currencyCodesMap.put("XBC","957");
        currencyCodesMap.put("XBD","958");
        currencyCodesMap.put("XCD","951");
        currencyCodesMap.put("XDR","960");
        currencyCodesMap.put("XOF","952");
        currencyCodesMap.put("XPD","964");
        currencyCodesMap.put("XPF","953");
        currencyCodesMap.put("XPT","962");
        currencyCodesMap.put("XSU","994");
        currencyCodesMap.put("XTS","963");
        currencyCodesMap.put("XUA","965");
        currencyCodesMap.put("XXX","999");
        currencyCodesMap.put("YER","886");
        currencyCodesMap.put("ZAR","710");
        currencyCodesMap.put("ZMW","967");
        currencyCodesMap.put("ZWL","932");
        return currencyCodesMap;
    }

    public String getCurrencyCode(String currency) {
        String currencyCode = "";

        currencyCode = currencyCodes.get(currency);

        return currencyCode;
    }

}
