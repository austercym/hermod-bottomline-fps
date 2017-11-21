# hermod-bottonline-fps
Connector between Bottonline and Kafka

This Module allows the connectivity between the core system and the BottomLine Faster payments gateway.

For Testing:

Send a message without use MQ:

`curl -H "Content-Type:text/plain" -d "@fps20022_paymentrequest_in_01_008.xml" http://localhost:9595/sip`


