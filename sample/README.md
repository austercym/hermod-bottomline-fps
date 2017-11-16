# SAMPLE

This is a sample app that can send an receive a message from the Bottomline simulator.

WARNING: The code is ugly. Sorry!
Hardcoded message is incorrect (
Receiving not tested yet! Working on that... (it seems that we have the wrong queue name)

# HOW I RUN IT
Probably not the best way but:

SEND
```
ipagoo@hdp-node1:~$ /usr/jdk64/jdk1.8.0_112/bin/java -cp TestJMS.jar:com.ibm.mq.allclient.jar:jms.jar send
Sent message:

  JMSMessage class: jms_text
  JMSType:          null
  JMSDeliveryMode:  2
  JMSDeliveryDelay: 0
  JMSDeliveryTime:  1510830167934
  JMSExpiration:    0
  JMSPriority:      4
  JMSMessageID:     ID:414d512042542e4f52554e2e55412e462848d65902530720
  JMSTimestamp:     1510830167934
  JMSCorrelationID: null
  JMSDestination:   queue:///ORUN.HOST.ASYN.SEND.QR
  JMSReplyTo:       null
  JMSRedelivered:   false
    JMSXAppID: TestJMS
    JMSXDeliveryCount: 0
    JMSXUserID: ipagoo
    JMS_IBM_PutApplType: 28
    JMS_IBM_PutDate: 20171116
    JMS_IBM_PutTime: 11024796
<?xml version="1.0" encoding="UTF-8"?>
<ns1:PaymentRequestIN xmlns:ns1="http://www.bottomline.com/di ...
SUCCESS
```

RECEIVE
```
ipagoo@hdp-node1:~$ /usr/jdk64/jdk1.8.0_112/bin/java -cp TestJMS.jar:com.ibm.mq.allclient.jar:jms.jar receive
```