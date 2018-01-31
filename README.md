# hermod-bottomline-fps
Connector between Bottomline and Kafka

This Module allows the connectivity between the core system and the BottomLine Faster payments gateway.

For Testing:

Send a message without use MQ:

`curl -H "Content-Type:text/plain" -d "@fps20022_paymentrequest_in_01_008.xml" http://localhost:9595/sip`

To connect to Bottomline, a new user *ipagoo* must be created (to set User ID into MQ connection).

Create jar file and execute scripts to start and stop process:

`mvn clean` 

`mvn initialize`
 
`mvn install -P integration-test`


`./start_connector.sh`

`./stop_connector.sh`

