package com.orders;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    @KafkaListener(topics = "orders", groupId = "order_group")
    public void consumeOrder(String order) {
        System.out.println("Received Order: " + order);
    }

}
