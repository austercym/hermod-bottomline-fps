import com.ibm.mq.jms.MQQueueSender;
import com.ibm.msg.client.wmq.WMQConstants;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

public class send {

    private static int status = 1;

    public static void main(String[] args) {

        // Variables
        MQQueueConnection connection = null;
        MQQueueSession session = null;
        MQQueue queue = null;
        MQQueueSender sender = null;

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
            connection = (MQQueueConnection) cf.createQueueConnection();
            session = (MQQueueSession) connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            queue = (MQQueue) session.createQueue("ORUN.HOST.ASYN.SEND.QR");
            sender = (MQQueueSender) session.createSender(queue);

            TextMessage message = session
                    .createTextMessage(
                            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                    "<ns1:PaymentRequestIN xmlns:ns1=\"http://www.bottomline.com/directFPS/FPSMessages\"\n" +
                                    "                      xmlns:ns4=\"http://www.bottomline.com/directFPS/FPSElements\">\n" +
                                    "<ns4:MTI>9200</ns4:MTI>\n" +
                                    "<ns4:ProcessingCode>101010</ns4:ProcessingCode>\n" +
                                    "<ns4:Amount>4023</ns4:Amount>\n" +
                                    "<ns4:DateSent>20160425</ns4:DateSent>\n" +
                                    "<ns4:SettlementDate>20160422</ns4:SettlementDate>\n" +
                                    "<ns4:SettlementCycleId>003</ns4:SettlementCycleId>\n" +
                                    "<ns4:TransactionReferenceNumber>FPS16042500001E4</ns4:TransactionReferenceNumber>\n" +
                                    "<ns4:SubmittingMember>01000005</ns4:SubmittingMember>\n" +
                                    "<ns4:BeneficiaryCustomerAccountNumber>98765432</ns4:BeneficiaryCustomerAccountNumber>\n" +
                                    "<ns4:OriginatingCreditInstitution>400328     </ns4:OriginatingCreditInstitution>\n" +
                                    "<ns4:OriginatingCustomerAccountNumber>10000001</ns4:OriginatingCustomerAccountNumber>\n" +
                                    "<ns4:ChargingInformation>001003SHA</ns4:ChargingInformation>\n" +
                                    "<ns4:Currency>826</ns4:Currency>\n" +
                                    "<ns4:PaymentData>001003002</ns4:PaymentData>\n" +
                                    "<ns4:BeneficiaryCreditInstitution>909000</ns4:BeneficiaryCreditInstitution>\n" +
                                    "<ns4:SendingFPSInstitution>400328     </ns4:SendingFPSInstitution>\n" +
                                    "<ns4:ReceivingMember>01000099</ns4:ReceivingMember>\n" +
                                    "<ns4:OriginatingCustomerAccountName>MR MRS SMITH</ns4:OriginatingCustomerAccountName>\n" +
                                    "<ns4:OriginatingCustomerAccountAddress>Somewhere</ns4:OriginatingCustomerAccountAddress>\n" +
                                    "<ns4:BeneficiaryCustomerAccountName>MISS CHRISTINE JONES</ns4:BeneficiaryCustomerAccountName>\n" +
                                    "<ns4:ReferenceInformation>BIRTHDAY GIFT</ns4:ReferenceInformation>\n" +
                                    "</ns1:PaymentRequestIN>\n"
);

            // Start the connection
            connection.start();

            // And, send the message
            sender.send(message);
            System.out.println("Sent message:\n" + message);

            recordSuccess();
        }
        catch (JMSException jmsex) {
            recordFailure(jmsex);
        }
        finally {
            if (sender != null) {
                try {
                    sender.close();
                }
                catch (JMSException jmsex) {
                    System.out.println("Sender could not be closed.");
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
