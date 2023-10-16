package com.c88.common.core.result;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author haoxr
 * @date 2020-06-23
 **/
@AllArgsConstructor
@NoArgsConstructor
public enum ResultCode implements IResultCode, Serializable {

    SUCCESS("00000", "SUCCESS"),

    USER_ERROR("A0001", "用户端错误"),
    USER_LOGIN_ERROR("A0200", "用户登录异常"),

    USER_NOT_EXIST("A0201", "用户不存在"),
    USER_ACCOUNT_LOCKED("A0202", "用户账户被冻结"),
    USER_ACCOUNT_INVALID("A0203", "用户账户已作废"),
    USER_IS_EXIST("A0204", "用户已存在"),

    USERNAME_OR_PASSWORD_ERROR("A0210", "用户名或密码错误"),
    PASSWORD_ENTER_EXCEED_LIMIT("A0211", "用户输入密码次数超限"),
    CLIENT_AUTHENTICATION_FAILED("A0212", "客户端认证失败"),
    WITHDRAW_PASSWORD_ERROR("A0213", "提款密码错误"),
    OLD_PASSWORD_CONFIRM_ERROR("A0214", "舊密碼輸入錯誤"),
    PASSWORD_NEW_AND_CONFIRM_INCONSISTENT("A0215", "新密碼與確認密碼不相同"),
    OLD_PASSWORD_NEW_PASSWORD_MATCH("A0216", "舊密碼與新密碼相同"),
    BANK_CARD_DUPLICATE("A0217", "銀行卡重複"),
    REAL_NAME_IS_NOT_THE_SAME("A0219", "真實名不一致"),

    AFFILIATE_NOT_EXIST("A0501", "代理不存在"),
    AFFILIATE_ACCOUNT_LOCKED("A0502", "代理帳戶被冻结"),
    AFFILIATE_ACCOUNT_INVALID("A0503", "代理帳戶已作废"),
    AFFILIATE_IS_EXIST("A0504", "代理已存在"),
    AFFILIATE_MOBILE_IS_BIND("A0505", "代理手機已綁定"),
    AFFILIATE_EMAIL_IS_BIND("A0506", "代理信箱已綁定"),
    AFFILIATE_CAN_NOT_ADD_LEVEL("A0507", "此代理帳號層級設定無法再新增下級"),


    TOKEN_INVALID_OR_EXPIRED("A0230", "token无效或已过期"),
    TOKEN_ACCESS_FORBIDDEN("A0231", "token已被禁止访问"),

    AUTHORIZED_ERROR("A0300", "访问权限异常"),
    ACCESS_UNAUTHORIZED("A0301", "访问未授权"),
    FORBIDDEN_OPERATION("A0302", "演示环境禁止修改、删除重要数据，请本地部署后测试"),


    PARAM_ERROR("A0400", "用户请求参数错误"),
    RESOURCE_NOT_FOUND("A0401", "请求资源不存在"),
    PARAM_IS_NULL("A0410", "请求必填参数为空"),

    USER_UPLOAD_FILE_ERROR("A0700", "用户上传文件异常"),
    USER_UPLOAD_FILE_TYPE_NOT_MATCH("A0701", "用户上传文件类型不匹配"),
    USER_UPLOAD_FILE_SIZE_EXCEEDS("A0702", "用户上传文件太大"),
    USER_UPLOAD_IMAGE_SIZE_EXCEEDS("A0703", "用户上传图片太大"),

    SYSTEM_EXECUTION_ERROR("B0001", "系统执行出错"),
    SYSTEM_EXECUTION_TIMEOUT("B0100", "系统执行超时"),
    SYSTEM_ORDER_PROCESSING_TIMEOUT("B0100", "系统订单处理超时"),

    SYSTEM_DISASTER_RECOVERY_TRIGGER("B0200", "系统容灾功能被出发"),
    FLOW_LIMITING("B0210", "系统限流"),
    DEGRADATION("B0220", "系统功能降级"),

    SYSTEM_RESOURCE_ERROR("B0300", "系统资源异常"),
    SYSTEM_RESOURCE_EXHAUSTION("B0310", "系统资源耗尽"),
    SYSTEM_RESOURCE_ACCESS_ERROR("B0320", "系统资源访问异常"),
    SYSTEM_READ_DISK_FILE_ERROR("B0321", "系统读取磁盘文件失败"),

    CALL_THIRD_PARTY_SERVICE_ERROR("C0001", "调用第三方服务出错"),
    MIDDLEWARE_SERVICE_ERROR("C0100", "中间件服务出错"),
    INTERFACE_NOT_EXIST("C0113", "接口不存在"),

    MESSAGE_SERVICE_ERROR("C0120", "消息服务出错"),
    MESSAGE_DELIVERY_ERROR("C0121", "消息投递出错"),
    MESSAGE_CONSUMPTION_ERROR("C0122", "消息消费出错"),
    MESSAGE_SUBSCRIPTION_ERROR("C0123", "消息订阅出错"),
    MESSAGE_GROUP_NOT_FOUND("C0124", "消息分组未查到"),

    DATABASE_ERROR("C0300", "数据库服务出错"),
    DATABASE_TABLE_NOT_EXIST("C0311", "表不存在"),
    DATABASE_COLUMN_NOT_EXIST("C0312", "列不存在"),
    DATABASE_DUPLICATE_COLUMN_NAME("C0321", "多表关联中存在多个相同名称的列"),
    DATABASE_DEADLOCK("C0331", "数据库死锁"),
    DATABASE_PRIMARY_KEY_CONFLICT("C0341", "主键冲突"),
    DATABASE_COLUMN_DUPLICATE("C0351", "欄位資料重複"),

    INTERNAL_SERVICE_CALLEE_ERROR("D0001", "內部服務被呼叫者失敗"),
    INTERNAL_SERVICE_CALLER_ERROR("D0002", "內部服務呼叫者失敗"),
    
    ROLE_DELETE_ERROR("D0003", "该角色已分配用户，无法删除"),

    RECHARGE_AMOUNT_INVALID("P0001", "充值金額限制"),
    ORDER_COMPLETED("P0002", "訂單已完成"),
    COMPANY_CARD_ACHIEVE_DAILY_MAX_AMOUNT("P0003", "自營卡達當日存款上限"),
    NO_COMPANY_CARD_CAN_USED("P0004", "目前无适用通道，请联系客服处理"),
    BANK_CARD_CODE_DUPLICATE("P0005", "銀行代號重複"),
    SOME_ORDER_IN_PROGRESS("P0006", "尚有充值單處理中"),
    VIP_SIZE_LIMIT("P0007", "會員等級數量上限最多10個，無法再新增！"),
    WORD_SIZE_LIMIT("P0008", "字數過多，請刪減內文！"),
    DAILY_WITHDRAW_AMOUNT_INVALID("P0009", "超過單日提款上限"),
    RECHARGE_RECHARGE_AWARD_DAY_INVALID("P0010", "存送優惠超過日上限"),
    RECHARGE_RECHARGE_AWARD_WEEK_INVALID("P0011", "存送優惠超過週上限"),
    RECHARGE_RECHARGE_AWARD_MONTH_INVALID("P0012", "存送優惠超過月上限"),
    RECHARGE_RECHARGE_AWARD_TOTAL_INVALID("P0013", "存送優惠超過累積上限"),

    TRANSFER_OVER_LIMIT("P0014", "超過轉帳限額"),




    MEMBER_ALREADY_LEVEL_UP("Q0001","該已會員升級"),
    HAVE_APPROVE_RECORD("Q0002", "升降級紀錄尚未審核完畢"),

    SMS_ERROR("Q0003", "驗證碼過期或錯誤, 請重新獲取"),

    PROMOTION_CATEGORY_USING("M0001","優惠活動使用中"),
    NO_POSITION_RECEIVE_LEVEL_UP_GIFT("M0002", "暫時無資格,領取晉級禮包"),
    RECEIVED_LEVEL_UP_GIFT("M0003", "已領取過該晉級禮包"),

    RECEIVED_WEEKLY_FREE_BET_GIFT("M0004", "已領取過該週免費籌碼"),
    NO_POSITION_RECEIVE_WEEKLY_FREE_BET_GIFT("M0005", "暫時無資格, 領取該週免費籌碼"),
    RECEIVED_MONTHLY_FREE_BET_GIFT("M0006", "已領取過該月免費籌碼"),
    NO_POSITION_RECEIVE_MONTHLY_FREE_BET_GIFT("M0007", "暫時無資格, 領取該月免費籌碼"),
    NOT_RED_ENVELOPE("M0008", "無此紅包代碼"),
    ACTIVITY_TIME_OUT("M0009", "活動時間外，無法領取獎勵"),
    REGISTER_TIME_OUT("M0010", "註冊時間外，無法領取獎勵"),
    YESTERDAY_RECHARGE_AMOUNT_INSUFFICIENT("M0011", "昨日充值要求不足"),
    DAY_RECHARGE_AMOUNT_INSUFFICIENT("M0012", "當日充值要求不足"),
    NEED_PHONE("M0013", "需手機驗證"),
    NEED_WITHDRAW_CARD("M0014", "需綁定提款方式"),
    LINK_PROBLEM_USERNAME("M0015", "拒絕關聯帳戶"),
    VIP_VALID_FAIL("M0016", "會員等級驗證失敗"),
    TAG_VALID_FAIL("M0017", "會員標籤驗證失敗"),
    EXCEED_DAILY_TOTAL_LIMIT("M0018", "超過每日領取上限"),
    EXCEED_TOTAL_LIMIT("M0019", "超過領取上限"),
    IN_DRAW_INTERVAL("M0020", "還在抽獎間隔範圍內"),
    COIN_IS_NOT_ENOUGH("M0021", "金幣額度不足"),
    NOT_ACHIEVE_RECHARGE_AMOUNT("M0022", "未滿足充值要求"),
    NOT_ACHIEVE_VALID_BET_AMOUNT("M0023", "未滿足有效投注要求"),
    OVER_DRAW_TIMES("M0024", "超過抽獎上限"),
    TOTAL_RECHARGE_AMOUNT_INSUFFICIENT("M0025", "累積充值要求不足"),
    USED_RED_ENVELOPE("M0026", "紅包代碼已使用"),
    NOT_USE_RED_ENVELOPE("M0027", "該紅包代碼無法使用"),
    NEED_REVIEW_CODE("M0028", "需先處理紅包代碼"),
    LAST_WEEK_RECHARGE_AMOUNT_INSUFFICIENT("M0029", "上週充值要求不足"),
    DRAW_ITEM_REFRESHED("M0030", "獎項已更新，請重新整理"),
    NEED_FIRST_DELETE_DRAW_ITEM_BY_RECHARGE_AWARD("M0031", "請先刪除獎品才可刪除存送優惠模組"),
    RECHARGE_AWARD_PERSONAL_CANCEL("M0032", "個人存送優惠已取消"),


    IS_AFFILIATE_LOW_LEVEL("Z0001", "不可為自己代理帳號的下級玩家"),
    MEMBER_ALREADY_BINDING_BY_AFFILIATE("Z0002", "綁定過的會員 不應可以重複綁在多個代理下"),
    AFFILIATE_MEMBER_LABEL_LIMIT_EXCEEDED("Z0003", "代理會員標籤超過上限"),
    AFFILIATE_MEMBER_LABEL_NAME_EXIST("Z0004", "代理會員標籤名稱已存在"),
    TARGET_IS_NOT_BELONG_THIS_AFFILIATE("Z0005", "此代理不屬於該團隊"),
    MEMBER_NOT_EXIST_AFFILIATE_BELOW("Z0006", "玩家不存在該登入代理"),
    AFFILIATE_BALANCE_NOT_ENOUGH("Z0007", "代理餘額不足"),
    MEMBER_IS_NOT_BELONG_AFFILIATE("Z0008", "此玩家不屬於該代理"),
    AFFILIATE_CAN_NOT_TRANSFER_TO_MYSELF("Z0009", "代理轉帳失敗 不得轉給自己"),
    AFFILIATE_COMMISSION_SENT("Z0010", "佣金已發放"),
    AFFILIATE_COMMISSION_NOT_ISSUE_YET("Z0011", "上期佣金審核單尚未結算，需結算上期佣金審核單，才會產出此期佣金審核單"),
    AFFILIATE_COMMISSION_NOT_ISSUE_YET_FOR_ADJUST("Z0012", "上月佣金尚未結算，結算後再進行缺額調整"),


    PLATFORM_MAINTAIN_TASK_NOT_SETTING("G0001", "平台維護排程尚未設定"),
    REFRESH_LIMIT("G0002", "請勿連續點擊，請於 30 秒後再次點擊"),
    PLATFORM_TRANSFER_LIMIT("G0003", "請勿連續點擊"),






    PLACEHOLDER("placeholder", "當最後一個，避免使用");

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    private String code;

    private String msg;

    @Override
    public String toString() {
        return "{" +
                "\"code\":\"" + code + '\"' +
                ", \"msg\":\"" + msg + '\"' +
                '}';
    }


    public static ResultCode getValue(String code){
        for (ResultCode value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return SYSTEM_EXECUTION_ERROR; // 默认系统执行错误
    }
}
