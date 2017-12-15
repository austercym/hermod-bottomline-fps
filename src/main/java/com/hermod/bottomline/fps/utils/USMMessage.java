package com.hermod.bottomline.fps.utils;

import com.hermod.bottomline.fps.types.FPSMessage;
import com.orwellg.umbrella.avro.types.usm.type0;
import com.orwellg.umbrella.commons.utils.enums.fps.USMMessageTypes;

import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;

public class USMMessage {

    private BigDecimal messageId;
    private XMLGregorianCalendar dateTime;
    private USMMessageTypes usmType;
    private FPSMessage fpsMessage;

    public FPSMessage getFpsMessage() {
        return fpsMessage;
    }

    public void setFpsMessage(FPSMessage fpsMessage) {
        this.fpsMessage = fpsMessage;
    }


    public USMMessageTypes getUsmType() {
        return usmType;
    }

    public void setUsmType(USMMessageTypes usmType) {
        this.usmType = usmType;
    }

    public BigDecimal getMessageId() {
        return messageId;
    }

    public void setMessageId(BigDecimal messageId) {
        this.messageId = messageId;
    }

    public XMLGregorianCalendar getDateTime() {
        return dateTime;
    }

    public void setDateTime(XMLGregorianCalendar dateTime) {
        this.dateTime = dateTime;
    }


}
