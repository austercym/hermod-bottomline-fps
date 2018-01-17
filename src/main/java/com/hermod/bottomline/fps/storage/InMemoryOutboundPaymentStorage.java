package com.hermod.bottomline.fps.storage;

import com.orwellg.umbrella.avro.types.payment.fps.FPSOutboundPayment;

import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.commons.collections4.map.PassiveExpiringMap;
import org.springframework.beans.factory.annotation.Value;

public class InMemoryOutboundPaymentStorage {

    private Integer timeToExpire;

    private Map<String, PaymentOutboundBean> storage;

    private static InMemoryOutboundPaymentStorage instance = null;

    //private constructor to avoid client applications to use constructor
    private InMemoryOutboundPaymentStorage((Integer timeToExpire){
        this.timeToExpire = timeToExpire;
        storage = Collections.synchronizedMap(new PassiveExpiringMap<String, PaymentBean>(timeToExpire, TimeUnit.MINUTES));
    }

    // Lazy Initialization (If required then only)
    public static InMemoryOutboundPaymentStorage getInstance(Integer timeToExpire) {
        if (instance == null) {
            // Thread Safe. Might be costly operation in some case
            synchronized (InMemoryOutboundPaymentStorage.class) {
                if (instance == null) {
                    instance = new InMemoryOutboundPaymentStorage(timeToExpire);
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
        storage = Collections.synchronizedMap(new PassiveExpiringMap<>(this.timeToExpire, TimeUnit.MINUTES));
    }

}
