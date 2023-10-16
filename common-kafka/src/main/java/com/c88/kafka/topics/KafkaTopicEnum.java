package com.c88.kafka.topics;

import lombok.Getter;

public enum KafkaTopicEnum {

    SAVE_BET_ORDER("save-bet-order"),

    RISK_CONTROL_BET_ORDER("riskControlBetOrder");

    @Getter
    private final String topic;

    KafkaTopicEnum(String topic) {
        this.topic = topic;
    }
}
