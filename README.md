# feedme-tcp-kafka
A Spring Boot-based microservice that is fed by an external service sending messages sequentially through a TCP socket. These TCP messages are pushed down a Kafka queue such that they be consumed concurrently. 

# Instructions
1. Clone the repo: 
`git clone https://github.com/dipeti/feedme-tcp-kafka.git`
2. Build the app (java 11 required): 
`mvn clean install`
3. Create a docker image
`docker build -t feedme .`

4. If you wish to spin up the whole infrastructure you will need to complete the instructions in [feedme-kafka-mongodb](https://github.com/dipeti/feedme-kafka-mongodb) too.
5. Replace the values for Kafka bootstrap server address with your host's ip address in `docker-compose.yml` file.  
6. Spin up all services: `docker-compose up`
