package com.orwellg.hermod.bottomline.fps.storage;

import java.io.Serializable;
import java.util.Date;

public class PaymentBean implements Serializable{

    private Date timestamp;

    private String FPID;
    private String paymentID;
    private String responseMessage;
    private String paymentType;
    private String environmentMQ;


    private PaymentStatus status;

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getEnvironmentMQ() {
        return environmentMQ;
    }

    public void setEnvironmentMQ(String environmentMQ) {
        this.environmentMQ = environmentMQ;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getPaymentID() { return paymentID; }

    public void setPaymentID(String paymentID) { this.paymentID = paymentID; }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public String getFPID() { return FPID; }

    public void setFPID(String FPID) { this.FPID = FPID; }
}
