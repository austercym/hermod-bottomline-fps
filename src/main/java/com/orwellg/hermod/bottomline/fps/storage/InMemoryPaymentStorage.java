package com.orwellg.hermod.bottomline.fps.storage;

import org.springframework.util.DigestUtils;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.commons.collections4.map.PassiveExpiringMap;

public class InMemoryPaymentStorage {

    private Integer timeToExpire;

    private Map<String, PaymentBean> storage;

    private static InMemoryPaymentStorage instance = null;

    //private constructor to avoid client applications to use constructor
    private InMemoryPaymentStorage(Integer timeToExpire){
        this.timeToExpire = timeToExpire;
        storage = Collections.synchronizedMap(new PassiveExpiringMap<String, PaymentBean>(timeToExpire, TimeUnit.MINUTES));
    }

    // Lazy Initialization (If required then only)
    public static InMemoryPaymentStorage getInstance(Integer timeToExpire) {
        if (instance == null) {
            // Thread Safe. Might be costly operation in some case
            synchronized (InMemoryPaymentStorage.class) {
                if (instance == null) {
                    instance = new InMemoryPaymentStorage(timeToExpire);
                }
            }
        }
        return instance;
    }

    public PaymentBean findPayment(String FPID, String originalMessage){

        String key = generateHash(FPID, originalMessage);

        return  storage.get(key);

    }

    public PaymentBean storePayment(String FPID, String originalMessage, String paymentId, String paymentType, String environmentMQ) {
        String key = generateHash(FPID, originalMessage);
        PaymentBean message = new PaymentBean();
        message.setFPID(FPID);
        message.setTimestamp(new Date());
        message.setStatus(PaymentStatus.PENDING);
        message.setPaymentID(paymentId);
        message.setPaymentType(paymentType);
        message.setEnvironmentMQ(environmentMQ);
        storage.put(key,message);
        return message;
    }

    public PaymentBean completePaymentResponse(String FPID, String originalMessage, String responseMessage){
        PaymentBean messageToUpdate = findPayment(FPID, originalMessage);
        if(messageToUpdate != null) {
            messageToUpdate.setResponseMessage(responseMessage);
            messageToUpdate.setStatus(PaymentStatus.PROCESSED);
            messageToUpdate.setTimestamp(new Date());
        }
        return messageToUpdate;
    }

    public void cleanStorage() {
        storage.clear();
    }

    private String generateHash(String FPID, String originalMessage){
        StringBuilder keyBuilder = (new StringBuilder(FPID)).append(originalMessage);
        byte[] key = keyBuilder.toString().getBytes();
        String md5Hex = DigestUtils.md5DigestAsHex(key);
        return md5Hex;
    }
}
