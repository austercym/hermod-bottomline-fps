package com.hermod.bottomline.fps.storage;

import com.orwellg.umbrella.avro.types.payment.fps.FPSOutboundPayment;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;

public class InMemoryOutboundPaymentStorage {

    private HashMap<String, PaymentOutboundBean> storage = new HashMap<>();

    private static InMemoryOutboundPaymentStorage instance = null;

    //private constructor to avoid client applications to use constructor
    private InMemoryOutboundPaymentStorage(){}

    // Lazy Initialization (If required then only)
    public static InMemoryOutboundPaymentStorage getInstance() {
        if (instance == null) {
            // Thread Safe. Might be costly operation in some case
            synchronized (InMemoryOutboundPaymentStorage.class) {
                if (instance == null) {
                    instance = new InMemoryOutboundPaymentStorage();
                }
            }
        }
        return instance;
    }

    public PaymentOutboundBean findPayment(String paymentId){
        return  storage.get(paymentId);

    }

    public PaymentOutboundBean storePayment(FPSOutboundPayment outboundPayment, String paymentId) {
        PaymentOutboundBean message = new PaymentOutboundBean();
        message.setOutboundPayment(outboundPayment);
        message.setTimestamp(new Date());
        message.setStatus(PaymentStatus.PENDING);
        message.setPaymentID(paymentId);
        storage.put(paymentId, message);
        return message;
    }


    public void cleanStorage() {
        storage = new HashMap<>();
    }

}
