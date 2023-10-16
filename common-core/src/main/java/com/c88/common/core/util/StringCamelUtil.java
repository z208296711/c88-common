package com.c88.common.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCamelUtil {
    /**
     * 下劃線轉駝峰
     * @param str
     * @return
     */
    public static StringBuffer camel(StringBuffer str) {
        //利用正則刪除下劃線，把下劃線後一位改成大寫
        Pattern pattern = Pattern.compile("_(\\w)");
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer(str);
        if(matcher.find()) {
            sb = new StringBuffer();
            //將當前匹配子串替換為指定字串，並且將替換後的子串以及其之前到上次匹配子串之後的字串段新增到一個StringBuffer物件裡。
            //正則之前的字元和被替換的字元
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
            //把之後的也新增到StringBuffer物件裡
            matcher.appendTail(sb);
        }else {
            return sb;
        }
        return camel(sb);
    }


    /**
     * 駝峰轉下劃線
     * @param str
     * @return
     */
    public static StringBuffer underline(StringBuffer str) {
        Pattern pattern = Pattern.compile("[A-Z]");
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer(str);
        if(matcher.find()) {
            sb = new StringBuffer();
            //將當前匹配子串替換為指定字串，並且將替換後的子串以及其之前到上次匹配子串之後的字串段新增到一個StringBuffer物件裡。
            //正則之前的字元和被替換的字元
            matcher.appendReplacement(sb,"_"+matcher.group(0).toLowerCase());
            //把之後的也新增到StringBuffer物件裡
            matcher.appendTail(sb);
        }else {
            return sb;
        }
        return underline(sb);
    }
}
