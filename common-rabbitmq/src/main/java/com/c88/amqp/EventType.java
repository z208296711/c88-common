package com.c88.amqp;

import lombok.Getter;

public enum EventType {
    LOGIN_SUCCESS("event.login.success"),// 會員登入成功
    LOGIN_ERROR("event.login.error"),// 會員登入錯誤


    SAVE_BET_ORDER("event.save.betOrder"),// 更新注單

    DEPOSIT_SUCCESS("event.deposit.success"),// 存款成功
    DEPOSIT_ERROR("event.deposit.fail"),
    ANNO_LOG("event.anno.log");// 操作日誌

    @Getter
    String key;

    EventType(String key) {
        this.key = key;
    }
}
