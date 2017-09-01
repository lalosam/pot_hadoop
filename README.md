# pot_hadoop
Proof of Technologies on hadoop ecosystem


## Kafka

Implement a kafka producer and a kafka consumer to send and retrieve messages in parallel using AKKA to create the threads. 

**main-class:** com.rojosam.LoadActors

### Generate messages:

**Example:** 10 700 produce localhost:9092,localhost1:9092

    1. Threads number to send messages in parallel (AKKA actors).
    2. Number of messages sended by thread.
    3. Action: "produce" to send messages. 
    4. Kafka broker list.

With this parameters you will send 7,000 (10 threads * 700 messages each one) total messages.

### Consume messages:

**Example:** 5 0 consume localhost:9092,localhost1:9092

    1. Threads number to consume messages in parallel (AKKA actors).
    2. <place holder> Not matter
    3. Action: "consume" to retrieve messages. 
    4. Kafka broker list.

With this parameters you will instantiate 5 kafka consumers. If your topic has less number of partitions not all the threads will retrieve data in parallel, only one *consumer* by partition at the same time.