package com.c88.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaMessageProducer<T> {

    private final KafkaTemplate<String, T> kafkaTemplate;

    public void sendMessage(String topic, T message) {
        sendMessage(topic, null, message);
    }

    public void sendMessage(String topic, String key, T message) {
        if (message == null) {
            log.warn("message is blank");
            return;
        }
        ListenableFuture<SendResult<String, T>> future = key != null ? kafkaTemplate.send(topic, key, message) : kafkaTemplate.send(topic, message);
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, T> result) {
                log.info("Sent topic=[{}] message=[{}] with offset=[{}]", topic, message, result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Sent topic=[{}] message=[{}]  error", topic, message, ex);
            }
        });
    }

}
