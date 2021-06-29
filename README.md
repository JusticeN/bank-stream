# bank-stream
sample stream app with kafka and Spring boot

There is a scenario where thousands of trades are flowing into one store, assume any way of transmission of trades (could be receiving the trades via Kafka).
We need to create a one trade store, which stores the trade (any kind of database)

# Getting Started

## Start kafka and zoookeeper
``docker-compose -f kafka-single-broker.yml up``
run on *localhost:9092*

## frontoffice 
#### build
``cd frontoffice && gradle build``
#### run
in the backoffice diretory run
``java -jar backoffice\build\libs\backoffice-0.0.1-SNAPSHOT.jar``
port: 8080
POST /trade
body TradeDto Object

## back office
#### build
``cd backoffice && gradle build``
#### run
in the backoffice diretory run
``java -jar build\libs\backoffice-0.0.1-SNAPSHOT.jar``
port : 8082
h2 console: http://localhost:8082/h2-console


##
for more details see the architecture document "*Trade stream Architecture.docx*"
