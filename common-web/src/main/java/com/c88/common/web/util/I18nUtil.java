package com.c88.common.web.util;

import com.c88.common.web.component.CustomMessageSource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Data
@Component
public class I18nUtil {

    private static CustomMessageSource customMessageSource;

    public static String get(Integer code) {
        return customMessageSource.get(code.toString());
    }

    public static String get(String code) {
        return customMessageSource.get(code);
    }

    public static String get(String code, Object... objects) {
        return customMessageSource.get(code, objects);
    }

    @Resource
    public void setCustomMessageSource(CustomMessageSource customMessageSource) {
        I18nUtil.customMessageSource = customMessageSource;
    }
}
