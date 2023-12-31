package com.c88.common.web.valid;

import com.c88.common.web.annotation.Mobile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class MobileValidator implements ConstraintValidator<Mobile, String> {
    /**
     * 手机验证规则
     */
    private Pattern pattern;

    @Override
    public void initialize(Mobile mobile) {
        pattern = Pattern.compile(mobile.regexp());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return pattern.matcher(value).matches();
    }
}
