package com.c88.common.core.util;

public class Validate {

    public static boolean preMatch(String pattern, String content) {
        boolean rc = content != null && content.matches(pattern);
        return rc;
    }

    public static boolean username(String value) {
        // 20190121 change user name length from 4 to 12
        return preMatch("^[a-zA-Z0-9_]{4,16}", value);
        // 20190327 change user name length from 4 to 16 for macco
        //return preMatch("^[a-zA-Z0-9_]{4,12}", value);
    }

    public static boolean name_lenght(String value) {
        return value.length()>=2&&value.length()<=5;
        //return preMatch("^[\\x7f-\\xff]+$", value);
    }
    
    public static boolean name(String value) {
        return preMatch("^[\\u4e00-\\u9fa5]{2,5}$$", value);
        //return preMatch("^[\\x7f-\\xff]+$", value);
    }

    public static boolean password(String value) {
        // 20190121 change password rule
        //return preMatch("^[a-zA-Z0-9_]{6,16}", value);

        return preMatch("^[a-zA-Z0-9_\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)\\?\\<\\>\\'\\~\\`\\{\\}]{6,16}", value);
    }

    public static boolean number(String value) {
        return preMatch("^[1-9]{1}[0-9]{0,}", value);
    }

    public static boolean number_float(String value, int length) {
        //return preMatch("^[0-9]{1,}(\\.)?\\d{0,'.length.'}", value);
        return preMatch("^[0-9]{1,}(\\.)?\\d{0," + length + "}", value);
    }

    public static boolean reg_code(String value) {
        return preMatch("^[A-Z0-9]+", value);
    }

    public static boolean qq(String value) {
        return preMatch("^[1-9]\\d{4,12}", GameUtil.trim(value));
    }

    public static boolean phone(String value) {
        //return preMatch("^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}", value);
        return preMatch("^\\d{10,11}", value);
    }

    public static boolean email(String value) {
        return preMatch("([\\w\\-\\.]+\\@[\\w\\-]+\\.[\\w\\-]+)", value);
    }
   
}
