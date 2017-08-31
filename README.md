# pot_hadoop
Proof of Technologies on hadoop ecosystem


**main-class:** com.rojosam.LoadActors

##Generate messages:

**Example:** 10 700000 produce localhost:9092,localhost1:9092

    1. Threads number to send messages in parallel.
    2. Number of messages sended by thread.
    3. Action: *produce* to send messages. 
    4. Kafka broker list.

##Consume messages:

**Example:** 5 0 consume localhost:9092,localhost1:9092

    1. Threads number to consume messages in parallel.
    2. <place holder> Not matter
    3. Action: *consume* to retrieve messages. 
    4. Kafka broker list.
