package com.c88.common.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameUtil {

    public static String maskCardNumber(String card) {
        int len = card == null ? 0 : card.length();
        if (len >= 6) {
            card = append(card.substring(0, 3), "***********", card.substring(len - 3));
        }
        return card;
    }

    public static String sha1Base64(String message) {
        byte rc[] = null;
        try {
            byte buf[] = message.getBytes("UTF-8");
            rc = sha1(buf);
        } catch (Exception e) {
        }
        return encodeBase64(rc);
    }

    public static String encodeBase64(byte data[]) {
        String str = Base64.encodeBase64String(data);
        //byte rc[] = Base64.encodeBase64(data);
        //return new String(rc);
        return str.trim();// 不可 .toUpperCase()
    }

    //    @NotImplementYet
    public static byte[] sha1(byte input[]) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
        byte ret[] = messageDigest.digest(input);
        return ret;
    }

    static public String calcHmacSha256(String secretKey, String message) {
        String hmacSha256;
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
            mac.init(secretKeySpec);
            hmacSha256 = Hex.encodeHexString(mac.doFinal(message.getBytes("UTF-8")));
        } catch (Exception e) {
            throw new RuntimeException("Failed to calculate hmac-sha256", e);
        }
        return hmacSha256;
    }

    public static Map<String, String> urlToMap(String url) {
        List<NameValuePair> params = URLEncodedUtils.parse(url, StandardCharsets.UTF_8);
        Map<String, String> map = new HashMap<>();
        for (NameValuePair param : params) {
            map.put(param.getName(), param.getValue());
        }
        return map;
    }

    public static String append(Object... strings) {
        StringBuffer sb = new StringBuffer();
        int len = strings == null ? 0 : strings.length;

        for (int i = 0; i < len; ++i) {
            if (strings[i] != null) {
                sb.append(strings[i]);
            }
        }

        return sb.toString();
    }

    public double fFloor(double value, int precision) {
        double c = Math.pow(10, precision);
        return Math.floor(value * c) / c;
    }

    public static String trim(String s) {
        return s == null ? "" : s.trim();
    }

    public static String getMD5(String str) {
        String ret = null;
        try {
            // 生成一個MD5加密計算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");

            ret = (new HexBinaryAdapter()).marshal(md.digest(str.getBytes())).toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static long getIPInt(String ipAddress4) {
        String[] address = ipAddress4.split("\\.");
//        String[] address =
        int i, d, len = address == null ? 0 : Math.min(address.length, 4);
        long num = 0;
        for (i = 0; i < len; i++) {
            if (i > 0)
                num <<= 8;
            num |= Integer.parseInt(address[i]);
//            num |= Util.parseInt(address[i], 0);
        }
        return num;
    }


    public static double parseDouble(String string, double defaultValue) {
        if (StringUtils.isEmpty(string)) {
            return defaultValue;
        } else {
            double rc = defaultValue;

            try {
                rc = Double.parseDouble(string.trim());
            } catch (Throwable var6) {
            }

            return rc;
        }
    }

    static GameUtil jspUtil;

    public synchronized static GameUtil getUtil() {
        if (jspUtil != null)
            return jspUtil;
        return jspUtil = new GameUtil();
    }

    public static int parseInt(String string, int defaultValue) {
        if (string == null)
            return defaultValue;
        int rc, radix = 10;
        try {
            rc = string.length();
            if (rc > 2 && Character.toUpperCase(string.charAt(1)) == 'X' && string.charAt(0) == '0') {
                string = string.substring(2);
                radix = 16;
            }
            rc = Integer.parseInt(string.trim(), radix);
        } catch (Throwable t) {
            rc = defaultValue;
        }
        return rc;
    }

    public String getIPIntValue(String ipAddress4) {
        return String.valueOf(getIPInt(ipAddress4));
    }

    public static String objToGetString(Object obj, String... fields) {
        StringBuilder sb = new StringBuilder();
        String jsonStr = objToJsonString(obj, fields);
        JSONObject fastJson = JSONObject.parseObject(jsonStr, Feature.OrderedField);
        fastJson.forEach((k, v) -> sb.append(k).append("=").append(String.valueOf(v)).append("&"));
        return sb.toString().substring(0, sb.length() - 1);
    }

    public static String objToJsonString(Object obj, String... fields) {
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
        if (fields != null && fields.length > 0) {
            for (String field : fields) {
                filter.getExcludes().add(field);
            }
        }
        String jsonStr = JSON.toJSONString(obj, filter, SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullBooleanAsFalse,
                SerializerFeature.PrettyFormat, SerializerFeature.SortField);
        return jsonStr;
    }

    public static double round(double value, int precision) {
        double c = Math.pow(10, precision);
        return Math.round(value * c) / c;
    }
}
