import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import com.ibm.mq.jms.*;
import com.ibm.mq.jms.MQQueueReceiver;
import com.ibm.msg.client.wmq.WMQConstants;

public class receive {

    private static int status = 1;

    public static void main(String[] args) {

        // Variables
        MQQueueConnection connection = null;
        MQQueueSession session = null;
        MQQueue queue = null;
        MQQueueReceiver receiver = null;

        try {
            // Create a connection factory
            MQQueueConnectionFactory cf = new MQQueueConnectionFactory();

            // Set the properties
            cf.setHostName("80.169.11.104");
            cf.setPort(49178);
            cf.setTransportType(WMQConstants.WMQ_CM_CLIENT);
            cf.setQueueManager("BT.ORUN.UA.FPS.02");
            cf.setChannel("IPAGOO.CONNECT");

            // Create JMS objects
            connection = (MQQueueConnection) cf.createQueueConnection();//("","");
            session = (MQQueueSession) connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            queue = (MQQueue) session.createQueue("ORUN.HOST.ASYN.SEND.RESP.QR");
            receiver = (MQQueueReceiver) session.createReceiver(queue);

            connection.start();

            // Now, receive the message
            Message receivedMessage = receiver.receive(15000); // in ms or 15 seconds
            System.out.println("\nReceived message:\n" + receivedMessage);

            recordSuccess();
        }
        catch (JMSException jmsex) {
            recordFailure(jmsex);
        }
        finally {
            if (receiver != null) {
                try {
                    receiver.close();
                }
                catch (JMSException jmsex) {
                    System.out.println("Receiver could not be closed.");
                    recordFailure(jmsex);
                }
            }

            if (session != null) {
                try {
                    session.close();
                }
                catch (JMSException jmsex) {
                    System.out.println("Session could not be closed.");
                    recordFailure(jmsex);
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                }
                catch (JMSException jmsex) {
                    System.out.println("Connection could not be closed.");
                    recordFailure(jmsex);
                }
            }
        }
        System.exit(status);
        return;
    } // end main()
    /**
     * Process a JMSException and any associated inner exceptions.
     *
     * @param jmsex
     */
    private static void processJMSException(JMSException jmsex) {
        System.out.println(jmsex);
        Throwable innerException = jmsex.getLinkedException();
        if (innerException != null) {
            System.out.println("Inner exception(s):");
        }
        while (innerException != null) {
            System.out.println(innerException);
            innerException = innerException.getCause();
        }
        return;
    }

    /**
     * Record this run as successful.
     */
    private static void recordSuccess() {
        System.out.println("SUCCESS");
        status = 0;
        return;
    }

    /**
     * Record this run as failure.
     *
     * @param ex
     */
    private static void recordFailure(Exception ex) {
        if (ex != null) {
            if (ex instanceof JMSException) {
                processJMSException((JMSException) ex);
            } else {
                System.out.println(ex);
            }
        }
        System.out.println("FAILURE");
        status = -1;
        return;
    }

}
