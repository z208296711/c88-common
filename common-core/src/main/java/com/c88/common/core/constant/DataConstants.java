package com.c88.common.core.constant;

public class DataConstants {

    //共用
    public static final int SUCCESS = 0;
    public static final int _SUCCESS = 100000;
    public static final int OPERATION_SUCCESS = 100001;
    public static final int REGISTER_SUCCESS = 100002;
    public static final int UPDATE_SUCCESS = 100003;
    public static final int DELETE_SUCCESS = 100004;
    public static final int CREATE_SUCCESS = 100005;

    public static final int FAILED = -100000;
    public static final int ORPERATION_FAILED = -100001;
    public static final int REGISTER_FAILED = -100002;
    public static final int UPDATE_FAILED = -100003;
    public static final int DELETE_FAILED = -100004;
    public static final int CREATE_FAILED = -100005;
    public static final int PARAMETER_EXCEPTION = -100006;
    public static final int DATA_NO_CHANGE = -100007;
    public static final int INPUT_FORMAT_ERROR = -100008;
    public static final int DATA_REQUIRED = -100009;
    public static final int UPDATE_REJECT = -100010;
    //    public static final int WEBSITE_CLOSED = -100011;
    public static final int FILED_REQUIRED = -100011;
    public static final int FIELD_LESSLAN_ZERO = -100012;
    public static final int FIELD_NOT_EXIST = -100013;
    public static final int VERIFY_CODE_ERROR = -100014;
    public static final int UPLOAD_IMAGE_FORMAT_ERROR = -100015;
    public static final int UPLOAD_OVER_SIZE = -100016;
    public static final int EXCEL_EXPORT_SIZE_OVER_LIMIT = -100017;
    public static final int REDIS_KEY_EXPIRED = -100018;
    public static final int VERIFY_CODE_SEND_ERROR = -100019;
    public static final int EMAIL_INPUT_ERROR = -100020;

    //投注，活動相關
    public static final int BET_SUCCESS = 200000;
    public static final int BET_CANCEL_SUCCESS = 200001;
    public static final int CHASE_SUCCESS = 200002;
    public static final int CHASE_CANCEL_SUCCESS = 200003;

    public static final int BET_FAILED = -200000;
    public static final int BET_CANCEL_FAILED = -200001;
    public static final int BET_AGENT_FORBIDDEN= -200002;
    public static final int BET_AMOUT_INSUFFICIENT= -200003;
    public static final int CHASE_FAILED = -200004;
    public static final int CHASE_CANCEL_FAILED = -200005;
    public static final int CHASE_OVER_MAX_PERIOD = -200006;
    public static final int CHASE_UNDER_MIN_PERIOD= -200007;
    public static final int ENTER_CORRECT_REBATE = -200008;
    public static final int GIFT_GET_FAILED = -200009;
    public static final int GIFT_GET_ALEADY = -200010;
    public static final int GIFT_DEPOSIT_INSUFFICIENT = -200011;
    public static final int GIFT_SAME_IP = -200012;
    public static final int WHEEL_CREDIT_INSUFFICIENT = -200013;
    public static final int WHEEL_ACTIVITY_CLOSED = -200014;
    public static final int GAME_CREATE_FAILED = -200015;
    public static final int DAILY_SIGN_FAILED = -200016;
    public static final int DAILY_SIGN_SUBSTANDARD = -200017;
    public static final int DAILY_SIGN_ALEADY = -200018;
    public static final int DAILY_SIGN_NON_CONTINUOUS = -200019;
    public static final int DAILY_SIGN_IP_LIMITED = -200020;
    public static final int DAILY_SIGN_DEVICE_LIMITED = -200021;
    public static final int DAILY_SIGN_CLOSED = -200022;
    public static final int DAILY_SIGN_NOT_ENTERED = -200023;
    public static final int DAILY_SIGN_DAY_ABNORMAL = -200024;
    public static final int DAILY_SIGN_FINISHED = -200025;
    public static final int RP_ACTIVITY_NOT_OPEN = -200026;
    public static final int RP_ACTIVITY_NOT_FOUND = -200027;
    public static final int DELETABLE_BETS_NOT_FOUND = -200028;
    public static final int ISSUE_IS_ALREADY_DELETED = -200029;
    public static final int BET_ID_ERROR = -200030;
    public static final int BET_IS_ALREADY_CANCEL = -200031;
    public static final int ISSUE_NOT_FOUND = -200032;
    public static final int ISSUE_IS_ALREADY_OPENED = -200033;
    public static final int PLEASE_ENTER_ACTIONNO = -200034;
    public static final int PLEASE_ENTER_LOTTERY_TYPE = -200035;
    public static final int LOTTERY_TYPE_NOT_FOUND = -200036;
    public static final int ACTIONNO_NOT_FOUND_IN_THIS_LOTTERY_TYPE = -200037;

    //會員中心相關
    public static final int LOGIN_SUCCESS = 300000;
    public static final int LOGOUT_SUCCESS = 300001;
    public static final int DOWNLINE_ADD_SUCCESS = 300002;

    public static final int EMAIL_SUBJECT = 310000;
    public static final int EMAIL_CONTENT = 310001;

    public static final int LOGIN_INFO_NULL = -300000;
    public static final int LOGIN_INFO_FORMAT_ERROR = -300001;
    public static final int USER_REALNAME_OUT_OF_RANGE = -300069;
    public static final int LOGIN_INFO_ERROR = -300002;
    public static final int LOGIN_FAILED = -300003;
    public static final int LOGIN_CAPTCHA_ERROR = -300004;
    public static final int LOGIN_INVAILD = -300005;
    public static final int LOGIN_DUPLICATE = -300006;
    public static final int LOGIN_OVERTIME = -300007;
    public static final int PASSWORD_INPUT_OLD = -300008;
    public static final int PASSWORD_INPUT_NEW = -300009;
    public static final int OLD_PASSWORD_ERROR= -300010;
    public static final int PASSWORD_FORMAT_ERROR = -300011;

    // 新舊密碼不得相同
    public static final int PASSWORD_DUPLICATE = -300012;
    public static final int PASSWORD_UPDATE_FAILED = -300013;
    public static final int PASSWORD_WITHDRAWAL_DUPLICATE = -300014;
    public static final int USERNAME_INPUT = -300015;
    public static final int USERNAME_EXIST = -300016;
    public static final int USERNAME_NOT_EXIST = -300017;
    public static final int REALNAME_ERROR = -300018;
    public static final int IDENTITY_ERROR = -300019;
    public static final int IDENTITY_EXIST = -300020;
    public static final int MOBILE_ERROR = -300021;
    public static final int MOBILE_EXIST = -300022;
    public static final int EMAIL_ERROR = -300023;
    public static final int EMAIL_EXIST = -300024;
    public static final int REFERRAL_INPUT = -300025;
    public static final int FUND_INSUFFICIENT = -300026;
    public static final int AUTH_FAILED = -300027;
    public static final int REGISTER_IP_LIMITED = -300028;
    public static final int REGISTER_DEVICE_LIMITED = -300029;
    public static final int DOWNLINE_ADD_FAILED = -300030;
    public static final int REFERRAL_ADD_FAILED = -300031;
    public static final int REGISTER_REQUIRED = -300032;
    public static final int TOKEN_INVALID = -300033;
    public static final int FANDIAN_FAILED = -300034;
    public static final int JNFANDIAN_FAILED = -300035;
    public static final int DZFANDIAN_FAILED = -300036;
    public static final int TUFANDIAN_FAILED = -300037;
    public static final int QPFANDIAN_FAILED = -300038;
    public static final int CODE_FAILED = -300039;
    public static final int REALNAME_ISEXISTED_ERROR = -300040;
    public static final int REALNAME_DIFFERENT_ERROR = -300041;
    public static final int PHONE_DIFFERENT_ERROR = -300042;
    public static final int FANDIAN_MAX_EXCEED = -300043;
    public static final int JNFANDIAN_MAX_EXCEED = -300044;
    public static final int DZFANDIAN_MAX_EXCEED = -300045;
    public static final int TUFANDIAN_MAX_EXCEED = -300046;
    public static final int QPFANDIAN_MAX_EXCEED = -300047;
    public static final int FANDIAN_SUB_FAILED = -300048;
    public static final int JNFANDIAN_SUB_FAILED = -300049;
    public static final int DZFANDIAN_SUB_FAILED = -300050;
    public static final int TUFANDIAN_SUB_FAILED = -300051;
    public static final int QPFANDIAN_SUB_FAILED = -300052;
    public static final int REALNAME_EXIST = -300053;
    public static final int AGENT_NOT_EXIST = -300054;
    public static final int PASSWORD_ERROR = -300055;
    public static final int BULLETIN_SORT_ERROR = -300056;
    public static final int ARTCOMPANY_SORT_ERROR = -300057;
    public static final int THIRDPARTY_SORT_ERROR = -300058;
    public static final int ODDS_LESS_THAN_1 = -300059;
    public static final int ODDS_ERROR = -300060;
    public static final int INSERT_ERROR_USERNAME_NOT_FOUND = -300061;
    public static final int ILLEGAL_LOGIN_IP = -300062;
    public static final int LOGIN_LIMIT_ERROR = -300063;
    public static final int QQ_ERROR = -300064;
    public static final int DJFANDIAN_MAX_EXCEED = -300065;
    public static final int BUFANDIAN_MAX_EXCEED = -300066;
    public static final int DJFANDIAN_FAILED = -300067;
    public static final int BUFANDIAN_FAILED = -300068;
    public static final int USERNAME_PASSWORD_DUPLICATE_ERROR = -300070;
    public static final int WITHDRAW_OVER_TIMES = -300071;
    public static final int PASSWORD_NOT_THE_SAME = -300072;
    public static final int FORCE_LOG_OUT = -300073;
    public static final int TOKEN_EXPIRED = -300074;
    public static final int ALREADY_OTHER_LOGIN = -300075;
    public static final int LOGIN_COUNT_OVER_TIMES = -300076;
    public static final int AGENT_LOGIN_ERROR = -300077;
    // 帳號或密碼錯誤
    public static final int USER_ACCOUNT_OR_PASSWORD_ERROR = -300078;

    // 該管理員已存在
    public static final int ADMIN_EXISTS = -300079;

    // 該管理員不存在
    public static final int ADMIN_NOT_EXISTS = -300080;
    public static final int EMAIL_NOT_FOUND_ERROR =-300081;
    public static final int WITHDRAWAL_PASSWORD_DUPLICATE = -300082;

    // 充值鎖定中
    public static final int RECHARGE_LOCKED = -300083;

    // 此會員群組未設置充值通道
    public static final int MEMBER_GROUP_NOT_SET_CHANNEL = -320000;

    // 銀行縮寫已存在，請使用其他名稱
    public static final int BANK_NICKNAME_EXIST = -320001;

    // 銀行名稱已存在，請使用其他名稱
    public static final int BANK_NAME_EXIST = -320002;

    // 銀行英文名稱已存在，請使用其他名稱
    public static final int BANK_ENGLISH_NAME_EXIST = -320003;

    // 提款顯示已存在，請使用其他名稱
    public static final int WITHDRAWAL_NAME_EXIST = -320004;

    // 充值顯示已存在，請使用其他名稱
    public static final int RECHARGE_NAME_EXIST = -320005;

    // 群組名稱已存在，請使用其他名稱
    public static final int GROUP_NAME_EXIST = -320006;

    // 商戶充值類型單筆下限為%d
    public static final int COMMERCIAL_UNIT_MIN_AMOUNT = -320007;

    // 商戶充值類型單筆上限為%d
    public static final int COMMERCIAL_UNIT_MAX_AMOUNT = -320008;

    //金錢，銀行卡相關
    public static final int DEPOSIT_SUCCESS = 400000;
    public static final int WITHDRAWAL_SUCCESS = 400001;
    public static final int FUND_CHANGE_SUCCESS = 400002;
    public static final int DEPOSIT_PROCESSING = 400003;
    public static final int WITHDRAWAL_PROCESSING = 400004;
    public static final int BANK_NUMBER_REQUIRED = 400005;
    public static final int BANK_NAME_REQUIRED = 400006;
    public static final int BANK_WITHDRAWAL_PASSWORD_REQUIRED = 400007;
    public static final int BANK_BRANCH_REQUIRED = 400008;
    public static final int FUND_SHARE_CONFIRM = 400009;
    public static final int TRANSFER_IN_SELECT = 400010;
    public static final int TRANSFER_OUT_SELECT = 400011;
    public static final int BANK_SELECT_REQUIRED = 400012;
    public static final int FUND_SHARE_SUCCESS = 400013;
    public static final int RECHARGE_AMOUNT = 400014;
    public static final int AMOUNT_ARRIVED = 400015;
    public static final int RECHARE_SUCCESS_NOTICE = 400016;
    public static final int WITHDRAW_SUCCESS_NOTICE = 400017;
    public static final int WITHDRAW_FAIL_NOTICE = 4000031;
    public static final int SYSTEM_NOTICE = 4000032;


    public static final int WITHDRAW_FAIL_CONTENT = 4000033;
    public static final int WITHDRAW_SUCCESS_TITLE_NOTICE = 4000034;
    public static final int WITHDRAW_SUCCESS_CONTENT = 4000035;

    public static final int WITHDRAW_AMOUNT = 400018;
    public static final int BINDING_PHONE_SUCCESS = 400019;
    public static final int BINDING_MAIL_SUCCESS = 400020;



    public static final int DEPOSIT_FAILED = -400000;
    public static final int WITHDRAWAL_FAILED = -400001;
    public static final int FUND_CHANGE_FAILED = -400002;
    public static final int BANKCARD_NAME_UNMATCHED = -400003;
    public static final int BANKCARD_BIND_FAILED = -400004;
    public static final int BANKCARD_UNBIND = -400005;
    public static final int FUND_SHARE_FAILED = -400006;
    public static final int FUND_GIVE_BACK_FAILED = -400007;
    public static final int CHANNEL_DISABLED = -400008;
    public static final int AMOUNT_ERROR = -400009;
    public static final int AUTH_FAIL = -400010;
    public static final int COIN_NOT_ENOUGH = -400011;
    public static final int LIQUIDITY_ERROR = -400012;
    public static final int WITHDRAW_NOT_ENOUGH = -400013;
    public static final int COIN_PASSWORD_SAME_ERROR = -400014;
    public static final int DEPOSIT_BANK_ERROR = -400014;
    public static final int BANK_CARD_DUPLICATE = -400016;
    public static final int BANK_CARD_OVER_LIMIT = -400017;
    public static final int WITHDRAW_NOT_ENOUGH_SHARE_ERROR = -400018;
    public static final int BANK_BIND_DUPLICATE = -400019;
    public static final int BANKCARD_ACCOUNT_USED = -400020;
    public static final int SINGLE_WITHDRAW_MIN_ERROR = -400021;
    public static final int SINGLE_WITHDRAW_MAX_ERROR = -400022;
    public static final int DAILY_WITHDRAW_AMOUNT_LIMINT_ERROR = -400023;
    public static final int DAILY_WITHDRAW_TIMES_ERROR = -400024;
    public static final int COIN_PASSWORD_ERROR = -400025;

    //後台相關
    public static final int ADMIN_TICKET_NUMBER_NOT_EXIST = -500000;
    public static final int ADMIN_INPUT_INFO_ERROR = -500001;
    public static final int ADMIN_DATA_FORMAT_ERROR = -500002;
    public static final int ADMIN_DATA_OVERSIZED = -500003;
    public static final int ADMIN_TIME_FORMAT_ERROR = -500004;
    public static final int ADMIN_TIME_INFO_ERROR = -500005;
    public static final int ADMIN_REBATE_SETUP_ERROR = -500006;
    public static final int ADMIN_REBATE_OVER_LIMIT = -500007;
    public static final int ADMIN_FILED_INPUT_ERROR = -500008;
    public static final int ADMIN_FILED_VALUE_ERROR = -500009;
    public static final int ADMIN_CHANCE_SUM_ERROR = -500010;

    //第三方相關
    public static final int THIRD_LOGIN_FAILED = -600000;
    public static final int THIRD_USER_INFO_ERROR = -600001;
    public static final int THIRD_AMOUNT_ERROR = -600002;
    public static final int THIRD_AMOUNT_TRANSFER_MAX_REACH = -600003;
    public static final int THIRD_GUEST_FORBBIDEN = -600004;
    public static final int THIRD_FUND_TRANSFER_ERROR = -600005;
    public static final int THIRD_ORDER_SUBMIT_ALEADY = -600006;
    public static final int THIRD_SETUP_NOT_EXIST = -600007;
    public static final int THIRD_CHANNEL_NOT_EXIST = -600008;
    public static final int THIRD_PAY_FAILED = -600009;
    public static final int ALI_PAY_ONE_MIN_ERROR = -600010;
    public static final int THIRD_PAY_RECHARGE_ZEROR_ERROR = -600011;
    public static final int THIRD_REGIST_FAILED = -600012;
    public static final int THIRD_BALANCE_EMPTY = -600013;
    public static final int THIRD_TRANSFER_AMOUNT_ZERO = -600014;
    public static final int THIRD_PARTY_CHANNEL_EMPTY = -600015;
    public static final int THIRD_PARTY_RATIO_EMPTY = -600016;
    public static final int PAYMENT_PROXY_COMMON_ERROR = -600017;
    public static final int THIRD_TRANSFER_INTERVAL = -600018;
    public static final int RECHARGE_OVER_LIMIT = -600019;
    public static final int RECHARGE_LESS_LIMIT = -600020;
    public static final int THIRD_IS_MAINTENANCE = -600021;
    public static final int THIRD_AMOUNT_TRANSFER_MIN_REACH = -600022;
    public static final int FUNCTION_IS_MAINTENANCE = -600023;


    public static final int NO_SIGN_MISSION = -700000;
    public static final int SIGN_MISSION_ERROR = -700001;
    public static final int SIGN_RECHAREGE_REQUIRED = -700002;
    public static final int SIGN_RECHARGE_AMOUNT_ERROR = -700003;
    public static final int SIGN_PAY_ERROR = -700004;
    public static final int SIGNED = -701001;
    public static final int SIGN_NOT_ALLOWED = -701002;
    public static final int SIGN_MISSION_COMPLET = -701003;
    public static final int NOT_CONTINUE_SIGN = -701004;
    public static final int SIGN_IP_ERROR = -701005;
    public static final int SIGN_DEVICE_ERROR = -701006;
    public static final int SIGN_MISSION_OVER_TIME = -702000;
    public static final int SIGN_MISSION_NOT_RESET = -702001;
    public static final int SIGN_MISSION_NOT_RESET_BY_TIME = -702002;
    public static final int SIGN_MISSION_NOT_RESET_BY_MISSION = -702003;

    public static final int SIGN_TIME_NOT_YET = -702004;
    public static final int SIGN_NOT_INPUT = -709009;
    public static final int SIGN_DAYS_ERROR = -710000;
    public static final int SIGN_DATE_ERROR = -710001;
    public static final int SIGN_DATE_RANGE_ERROR = -710002;
    public static final int RP_NUM_ERROR = -703001;
    public static final int RP_OVER_ALLRP_ERROR = -703002;
    public static final int RP_AMOUNT_OVER_100000 = -703003;
    public static final int RP_REWARD_OVER_TIME  = -701007;

    public static final int R_AWARD_NOT_FOUND = -780001;
    public static final int R_AWARD_JOINED_SAME_VENDOR = -780002;
    public static final int R_AWARD_TARGET_NOT_INCLUDED = -780003;
    public static final int R_AWARD_NOT_YET = -780004;
    public static final int R_AWARD_ENDED = -780005;
    public static final int R_AWARD_JOIN_TIMES_OVER_LIMIT = -780006;
    public static final int R_AWARD_WITHDRAW_LIMIT_REMAINS = -780007;


    public static final int DRAW_NOT_EXIST = -800001;
    public static final int APK_NOT_EXIST = -800002;
    public static final int VERIFY_ALREADY = -800003;

    public static final int RAPID_HITS = -999995;
    public static final int API_LIMIT = -999996;
    public static final int SYSTEM_ERROR = -999997;
    public static final int SERVER_UPGRADE = -999998;
    public static final int UNKNOW = -999999;

    public static final int LIQ_TYPE_101 = 101;
    public static final int LIQ_TYPE_102 = 102;
    public static final int LIQ_TYPE_103 = 103;
    public static final int LIQ_TYPE_104 = 104;
    public static final int LIQ_TYPE_105 = 105;
    public static final int LIQ_TYPE_106 = 106;
    public static final int LIQ_TYPE_107 = 107;
    public static final int LIQ_TYPE_108 = 108;
    public static final int LIQ_TYPE_109 = 109;
    public static final int LIQ_TYPE_110 = 110;
    public static final int LIQ_TYPE_111 = 111;
    public static final int LIQ_TYPE_112 = 112;
    public static final int LIQ_TYPE_113 = 113;
    public static final int LIQ_TYPE_114 = 114;
    public static final int LIQ_TYPE_115 = 115;
    public static final int LIQ_TYPE_116 = 116;
    public static final int LIQ_TYPE_117 = 117;
    public static final int LIQ_TYPE_118 = 118;
    public static final int LIQ_TYPE_119 = 119;
    public static final int LIQ_TYPE_120 = 120;
    public static final int LIQ_TYPE_121 = 121;
    public static final int LIQ_TYPE_122 = 122;
    public static final int LIQ_TYPE_123 = 123;
    public static final int LIQ_TYPE_124 = 124;
    public static final int LIQ_TYPE_125 = 125;
    public static final int LIQ_TYPE_126 = 126;
    public static final int LIQ_TYPE_127 = 127;
    public static final int LIQ_TYPE_128 = 128;
    public static final int LIQ_TYPE_129 = 129;
    public static final int LIQ_TYPE_130 = 130;

    public static final int MEMBER_RECHARGE_STATE_8800 = 8800;
    public static final int MEMBER_RECHARGE_STATE_8801 = 8801;
    public static final int MEMBER_RECHARGE_STATE_8802 = 8802;
    public static final int MEMBER_RECHARGE_STATE_8803 = 8803;
    public static final int MEMBER_RECHARGE_STATE_8804 = 8804;


    //提款風控相關
    public static final int RISK_GROUP_NOT_FOUND = -9700000;
    public static final int WRONG_RISK_PROCESS_TYPE = -9800000;
    public static final int WITHDRAWAL_NOT_FOUND = -9800001;
    public static final int SECOND_VERIFY_NOT_FOUND = -9800002;
    public static final int WITHDRAW_RISK_WRONG_ADMIN = -9800003;

    public static final int BIND_PROMOTION_SEND = -666680;
    public static final int VENDOR_NOT_IN_SETTING = -666681;
    public static final int USER_NOT_FOUND = -666682;
    public static final int UN_BIND_PHONE = -666683;
    public static final int UN_BIND_EMAIL = -666684;
    public static final int UN_BIND_BANK_CARD = -666685;


    public static final int GIFT_SAME_USERNAME = -200118;

    // 發放分紅時，代理筆數不能為空
    public static final int AGENT_COUNT_CAN_NOT_BE_NULL = -1200004;
    // 重新計算發生錯誤
    public static final int COMMISSION_RECALCULATE_EXCEPTION = -1200000;
    // 發放分紅發生錯誤
    public static final int COMMISSION_PAYMENT_EXCEPTION = -1200001;
    // 產生分紅審核單發生錯誤
    public static final int COMMISSION_REVIEW_EXCEPTION = -1200002;
    // 系統正在執行中
    public static final int COMMISSION_UNDER_PROCESSING = 1299999;
    // 未定義的預設錯誤
    public static final int COMMISSION_PROCESS_UNDEFINED_EXCEPTION = -1200003;
    // 系統正在發放分紅
    public static final int COMMISSION_IS_PAYING = -1299998;
    // 系統正在重新計算
    public static final int COMMISSION_IS_RECALCULATING = -1299997;




    //代理相關

    public static final int AGENT_ACCOUNT_THE_SAME = 1100000;
    public static final int AGENT_ACCOUNT_NOT_FOUND = 1100001;
    public static final int AGENT_ACCOUNT_NOT_ENABLE = 1100002;
    public static final int AGENT_PARAMETER_ERROR = 1110001;
    public static final int AGENT_MONEY_RANGE_CONFLICT = 1110002;
    public static final int AGENT_GROUP_HAS_MEMBER = 1120001;

    public static final int AGENT_NOT_DEFINED_DEFAULT_ERROR = 1199999;
    
    // 代理帳變紀錄
    public static final int AGENT_WITHDRAWAL = 1130000; // 會員提款
    public static final int AGENT_DIVIDENDS = 1130001; // 代理分潤
    public static final int AGENT_TRANSFER = 1130002; // 轉帳給下級
    public static final int AGENT_TRANSFER_FROM_SUPERVISOR = 1130003; // 上級轉帳
    public static final int AGENT_ALL = 1130004; // 全部帳變類型
    public static final int  AGENT_DEDUCT_CREDIT = 1130005; // 管理員扣除
    public static final int AGENT_ADD_CREDIT = 1130006; // 管理員充值

    public static final int AGENT_WITHDRAWAL_FAILED = -1130005;// 提現失敗，返還凍結款
    
    public static final int AGENT_WALLET_TYPE_WITHDRAWAL = 1140000; // 提現申請 
    public static final int AGENT_WALLET_TYPE_DIVIDENDS = 1140001; // 分紅
    public static final int AGENT_WALLET_TYPE_TRANSFER = 1140002; // 轉帳給下級
    public static final int AGENT_WALLET_TYPE_TRANSFER_FROM_SUPERVISOR = 1140003; // 上級代理轉入   
    public static final int AGENT_WALLET_TYPE_ADD_CREDIT = 1140004; // 管理員充值
    public static final int AGENT_WALLET_TYPE_DEDUCT_CREDIT = 1140005; // 管理員扣除
    public static final int AGENT_WALLET_TYPE_ACTIVITY = 1140011; // 活動贈送
    public static final int AGENT_WALLET_TYPE_TEST = 1140012; // 測試分數
    public static final int AGENT_WALLET_TYPE_SUPPORT = 1140013; // 代理扶持
    public static final int AGENT_WALLET_TYPE_OFFLINE_WITHDRAW = 1140014; // 代理扶持

    public static final int AGENT_WALLET_TYPE_RANSFER_FAILED_PHP =-1140003; // 轉帳給下級失敗
    public static final int AGENT_WALLET_WITHDRAWAL_FAILED= -1140002; // 提款失敗
    public static final int AGENT_WALLET_TYPE_TRANSFER_FAILED =  -1140001; //轉帳給下級會員失敗
    public static final int AGENT_WALLET_TYPE_WITHDRAWAL_REVIEW_FAILED = -1140000; // 提款審核失敗


    public static final int CHANNEL_CLOSE = -600202;

    public static final int SEO_ARTICLE_TAG_DUPLICATE = 1140007;
    public static final int SEO_ARTICLE_GROUP_DUPLICATE = 1140006;
    public static final int SEO_ARTICLE_TAG_NOT_EXISTS = 1140008;
    public static final int SEO_ARTICLE_GROUP_NOT_EXISTS = 1140009;
    public static final int SEO_ARTICLE_NOT_EXISTS = 1140010;
}
