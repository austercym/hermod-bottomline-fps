package com.hermod.bottomline.fps.storage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class InMemoryPaymentStorageTest {


    InMemoryPaymentStorage messageStorage;

    @Before
    public void setUp() throws Exception {
        messageStorage = InMemoryPaymentStorage.getInstance();
        messageStorage.storePayment("fpid", "originalmessage", "paymentId", "paymentType");
    }

    @After
    public void tearDown() throws Exception {
        messageStorage.cleanStorage();
    }

    @Test
    public void findMessageByFPID() throws Exception {

        PaymentBean message = messageStorage.findPayment("fpid", "originalmessage");

        assertThat(message.getFPID(), is("fpid"));
        assertThat(message.getPaymentID(), is("paymentId"));

    }

    @Test
    public void returnNullIfNoPaymentFound() throws Exception {

        PaymentBean message = messageStorage.findPayment("fpid2", "originalmessage");

        assertThat(message, nullValue());
    }

    @Test
    public void storeDifferentPaymentWithSameFPID() throws Exception {
        messageStorage.storePayment("fpid", "otheroriginalmessage", "paymentId2", "paymentType");
        PaymentBean message = messageStorage.findPayment("fpid", "originalmessage");
        assertThat(message.getPaymentID(), is("paymentId"));

        message = messageStorage.findPayment("fpid", "otheroriginalmessage");
        assertThat(message.getPaymentID(), is("paymentId2"));
    }

    @Test
    public void changeStatusFromResponseMessage() throws Exception {

        PaymentBean message = messageStorage.completePaymentResponse("fpid", "originalmessage","responseMessage" );

        assertThat(message.getStatus(), is(PaymentStatus.PROCESSED));
        assertThat(message.getResponseMessage(), is("responseMessage"));

    }

}