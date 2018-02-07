package com.orwellg.hermod.bottomline.fps.storage;

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
        messageStorage = InMemoryPaymentStorage.getInstance(20);
        messageStorage.storePayment("fpid", "originalmessage", "paymentId", "paymentType", "bottomline1");
    }

    @After
    public void tearDown() throws Exception {
        messageStorage.clearStorage();
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
        messageStorage.storePayment("fpid", "otheroriginalmessage", "paymentId2", "paymentType", "bottomline1");
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

    @Test
    public void getOriginalMessageWithOutCreDtTm() throws Exception {

        String otheroriginalMessage = "{\"GrpHdr\":{\"MsgId\":\"008-FPS1812392313901--4020180206826\",\"CreDtTm\":1517917547000,\"NbOfTxs\":\"1\",\"SttlmInf\"";
        String updateMessage = messageStorage.getMessageWithOutHeader(otheroriginalMessage);
        assertThat(updateMessage, is("{\"GrpHdr\":{\"MsgId\":\"008-FPS1812392313901--4020180206826\",\"NbOfTxs\":\"1\",\"SttlmInf\""));


    }

}