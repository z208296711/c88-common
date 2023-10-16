package com.c88.amqp.producer;

import com.c88.amqp.EventType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.c88.amqp.BroadcastConfig.TOPIC_EXCHANGE_EVENT;

@Slf4j
@Service
public class MessageProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(EventType eventType, Object message) {
        rabbitTemplate.convertAndSend(TOPIC_EXCHANGE_EVENT, eventType.getKey(), message);
    }
}