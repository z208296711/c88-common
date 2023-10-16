package com.c88.common.web.component;

import java.util.Locale;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomMessageSource {

    private final MessageSource messageSource;

    @Autowired
    public CustomMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String get(Integer code) {
        return messageSource.getMessage(code.toString(), null, getCurrentLocale());
    }

    public String get(String code) {
        return messageSource.getMessage(code, null, getCurrentLocale());
    }

    public String get(String code, Object... objects) {
        return messageSource.getMessage(code, objects, getCurrentLocale());
    }

    public String get(Integer code, Object... objects) {
        return messageSource.getMessage(code.toString(), objects, getCurrentLocale());
    }

    public String get(Integer code, Locale locale) {
        return messageSource.getMessage(code.toString(), null, locale);
    }

    public String get(Integer code, Object[] objects, Locale locale) {
        return messageSource.getMessage(code.toString(), objects, locale);
    }

    private Locale getCurrentLocale() {
        // takes the current active locale
        Locale locale = LocaleContextHolder.getLocale();
        switch (locale.getLanguage()) {
            case "zh":
            case "zh_TW":
            case "zh_CN":
            case "vi":
                break;
            default:
                locale = new Locale("vi");
        }
        /*
        if (locale.getDisplayLanguage().equalsIgnoreCase("np")) {
            locale = new Locale("np", "Nepal");
        }
        */
        return locale;
    }

    public String getErrorMessage(String originMessage, Integer errorCode, Object... args) {
        if (errorCode == null) return originMessage;
        String message = get(errorCode);
        return message == null ? originMessage
                : (args != null && args.length > 0 ? String.format(message, args)
                : message);
    }

    public String getErrorMessage(Integer errorCode, Object... args) {
        String message = get(errorCode);
        return message == null ? null
                : (args != null && args.length > 0 ? String.format(message, args)
                : message);
    }

}