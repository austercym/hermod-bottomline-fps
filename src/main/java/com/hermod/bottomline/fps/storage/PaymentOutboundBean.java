package com.hermod.bottomline.fps.storage;

import com.orwellg.umbrella.avro.types.payment.fps.FPSOutboundPayment;

import java.io.Serializable;
import java.util.Date;

public class PaymentOutboundBean implements Serializable{

    private Date timestamp;

    private String FPID;
    private String paymentID;
    private FPSOutboundPayment outboundPayment;

    private PaymentStatus status;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getPaymentID() { return paymentID; }

    public void setPaymentID(String paymentID) { this.paymentID = paymentID; }

    public FPSOutboundPayment getOutboundPayment() {
        return outboundPayment;
    }

    public void setOutboundPayment(FPSOutboundPayment outboundPayment) {
        this.outboundPayment = outboundPayment;
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
