package com.c88.common.xxljob.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum XxlJobPathEnum {

    LOGIN("/login", "登录"),
    START("/jobinfo/start", "啟動"),
    STOP("/jobinfo/stop", "停止"),
    ADD("/jobinfo/add", "添加Job"),
    UPDATE("/jobinfo/update", "更新Job"),
    REMOVE("/jobinfo/remove", "删除Job"),
    PAGE_JOB("/jobinfo/pageList", "查询Job"),
    PAGE_GROUP("/jobgroup/pageList", "查询Job组");

    private final String path;

    private final String desc;

    private static final Map<String, XxlJobPathEnum> map = Stream.of(values()).collect(Collectors.toMap(XxlJobPathEnum::getPath, Function.identity()));

    public static XxlJobPathEnum fromIntValue(int value) {
        return map.get(value);
    }
}
