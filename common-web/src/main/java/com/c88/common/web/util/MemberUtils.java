package com.c88.common.web.util;

import cn.hutool.json.JSONObject;
import com.c88.common.core.constant.SecurityConstants;

import static com.c88.common.core.constant.SecurityConstants.MEMBER_ID;


/**
 * @author <a href="mailto:xianrui0365@163.com">haoxr</a>
 * @date 2022/2/12 20:14
 */
public class MemberUtils {

    /**
     * 获取当前登录会员的ID
     *
     * @return
     */
    public static Long getMemberId() {
        Long memberId = null;
        JSONObject jwtPayload = JwtUtils.getJwtPayload();
        if (jwtPayload != null) {
            memberId = jwtPayload.getLong(MEMBER_ID);
        }
        return memberId;
    }

    /**
     * 解析JWT获取获取用户名
     *
     * @return
     */
    public static String getUsername() {
        String username = "";
        JSONObject jwtPayload = JwtUtils.getJwtPayload();
        if (jwtPayload != null) {
            username = jwtPayload.getStr(SecurityConstants.USER_NAME_KEY);
        }
        return username;
    }
}
