package com.c88.common.web.util;

import cn.hutool.json.JSONObject;
import com.c88.common.core.constant.SecurityConstants;

import static com.c88.common.core.constant.SecurityConstants.AFFILIATE_ID;
import static com.c88.common.core.constant.SecurityConstants.MEMBER_ID;


/**
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2022/2/12 20:14
 */
public class AffiliateUtils {

    /**
     * 获取当前登录代理ID
     *
     * @return
     */
    public static Long getAffiliateId() {
        Long affiliateId = null;
        JSONObject jwtPayload = JwtUtils.getJwtPayload();
        System.out.println(jwtPayload);
        if (jwtPayload != null) {
            affiliateId = jwtPayload.getLong(AFFILIATE_ID);
        }
        return affiliateId;
    }

    /**
     * 解析JWT获取获取用户名
     *
     * @return
     */
    public static String getUsername() {
        String username = "";
        JSONObject jwtPayload = JwtUtils.getJwtPayload();
        System.out.println(jwtPayload);
        if (jwtPayload != null) {
            username = jwtPayload.getStr("username");
        }
        return username;
    }
}
