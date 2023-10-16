package com.c88.common.core.constant;

public interface SecurityConstants {

    /**
     * 认证请求头key
     */
    String AUTHORIZATION_KEY = "Authorization";

    /**
     * JWT令牌前缀
     */
    String JWT_PREFIX = "Bearer ";

    /**
     * JWT令牌前缀
     */
    String REDIS_JWT_PREFIX = "auth:";


    /**
     * Basic认证前缀
     */
    String BASIC_PREFIX = "Basic ";

    /**
     * JWT载体key
     */
    String JWT_PAYLOAD_KEY = "payload";

    /**
     * JWT ID 唯一标识
     */
    String JWT_JTI = "jti";

    /**
     * JWT ID 唯一标识
     */
    String JWT_EXP = "exp";

    /**
     * 黑名单token前缀
     */
    String TOKEN_BLACKLIST_PREFIX = "auth:token:blacklist:";

    /**
     * 登入token前缀
     */
    String TOKEN_LOGIN_PREFIX = "auth:token:login:";

    String USER_ID_KEY = "userId";

    String MEMBER_ID = "memberId";

    String AFFILIATE_ID = "affiliateId";

    String USER_NAME_KEY = "user_name";

    String CLIENT_ID_KEY = "client_id";

    /**
     * JWT存储权限前缀
     */
    String AUTHORITY_PREFIX = "ROLE_";

    /**
     * JWT存储权限属性
     */
    String JWT_AUTHORITIES_KEY = "authorities";

    String GRANT_TYPE_KEY = "grant_type";

    String REFRESH_TOKEN_KEY = "refresh_token";

    /**
     * 认证身份标识
     */
    String AUTHENTICATION_IDENTITY_KEY = "authenticationIdentity";

    String APP_API_PATTERN = "/*/h5/**";

    String ACTUATOR_API_PATTERN  = "/actuator/**";

    String LOGOUT_PATH = "/auth/oauth/logout";

    /**
     * 新增菜单路径,新增不存在的路由会导致系统无法访问，线上禁止新增菜单的操作
     */
    String SAVE_MENU_PATH = "/admin/api/v1/menus";

    /**
     * 验证码key前缀
     */
    String VALIDATE_CODE_PREFIX = "VALIDATE_CODE:";

    /**
     * 短信验证码key前缀
     */
    String SMS_CODE_PREFIX = "SMS_CODE:";

    /**
     * 接口文档 Knife4j 测试客户端ID
     */
    String TEST_CLIENT_ID = "client";

    /**
     * 系统管理 web 客户端ID
     */
    String ADMIN_CLIENT_ID = "c88-admin-web";

    /**
     * 移动端（H5/Android/IOS）會員端ID
     */
    String MEMBER_CLIENT_ID = "member";

    /**
     * 小程序端（微信小程序、....） 客户端ID
     */
    String AFFILIATE_CLIENT_ID = "c88-affiliate";

}
