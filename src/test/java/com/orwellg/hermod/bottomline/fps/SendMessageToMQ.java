package com.orwellg.hermod.bottomline.fps;

import com.ibm.jms.JMSTextMessage;
import com.ibm.mq.jms.*;
import com.ibm.msg.client.wmq.WMQConstants;
import org.apache.commons.io.FileUtils;

import javax.jms.JMSException;
import javax.jms.Session;
import java.io.File;


public class SendMessageToMQ {

    public static void main(String[] args){

        try {
            MQQueueConnectionFactory cf = new MQQueueConnectionFactory();

            // Config
            cf.setHostName("localhost");
            cf.setPort(1414);
            cf.setTransportType(WMQConstants.WMQ_CM_CLIENT);
            cf.setQueueManager("QM1");
            cf.setChannel("DEV.APP.SVRCONN");

            MQQueueConnection connection = (MQQueueConnection) cf.createQueueConnection();
            MQQueueSession session = (MQQueueSession) connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            MQQueue queue = (MQQueue) session.createQueue("DEV.QUEUE.1");
            MQQueueSender sender =  (MQQueueSender) session.createSender(queue);

            File myFile = new File(args[0]);

            String messageFile = FileUtils.readFileToString(myFile);


            JMSTextMessage message = (JMSTextMessage) session.createTextMessage(messageFile);

            // Start the connection
            connection.start();

            sender.send(message);
            System.out.println("Sent message:\\n" + message);


            sender.close();
            session.close();
            connection.close();

            System.out.println("\\nSUCCESS\\n");

        } catch (Exception ex) {
            System.out.println(ex);
            System.out.println("\\nFAILURE\\n");
        }
    }


}
