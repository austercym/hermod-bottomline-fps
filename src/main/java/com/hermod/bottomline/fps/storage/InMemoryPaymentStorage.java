package com.hermod.bottomline.fps.storage;

import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;

public class InMemoryPaymentStorage {

    private HashMap<String, PaymentBean> storage = new HashMap<>();

    private static InMemoryPaymentStorage instance = null;

    //private constructor to avoid client applications to use constructor
    private InMemoryPaymentStorage(){}

    // Lazy Initialization (If required then only)
    public static InMemoryPaymentStorage getInstance() {
        if (instance == null) {
            // Thread Safe. Might be costly operation in some case
            synchronized (InMemoryPaymentStorage.class) {
                if (instance == null) {
                    instance = new InMemoryPaymentStorage();
                }
            }
        }
        return instance;
    }

    public PaymentBean findPayment(String FPID, String originalMessage){

        String key = generateHash(FPID, originalMessage);

        return  storage.get(key);

    }

    public PaymentBean storePayment(String FPID, String originalMessage, String paymentId, String paymentType) {
        String key = generateHash(FPID, originalMessage);
        PaymentBean message = new PaymentBean();
        message.setFPID(FPID);
        message.setTimestamp(new Date());
        message.setStatus(PaymentStatus.PENDING);
        message.setPaymentID(paymentId);
        message.setPaymentType(paymentType);
        storage.put(key,message);
        return message;
    }

    public PaymentBean completePaymentResponse(String FPID, String originalMessage, String responseMessage){
        PaymentBean messageToUpdate = findPayment(FPID, originalMessage);
        messageToUpdate.setResponseMessage(responseMessage);
        messageToUpdate.setStatus(PaymentStatus.PROCESSED);
        messageToUpdate.setTimestamp(new Date());
        return messageToUpdate;
    }

    public void cleanStorage() {
        storage = new HashMap<>();
    }

    private String generateHash(String FPID, String originalMessage){
        StringBuilder keyBuilder = (new StringBuilder(FPID)).append(originalMessage);
        byte[] key = keyBuilder.toString().getBytes();
        String md5Hex = DigestUtils.md5DigestAsHex(key);
        return md5Hex;
    }
}
