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