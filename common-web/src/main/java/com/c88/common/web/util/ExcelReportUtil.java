package com.c88.common.web.util;

public class ExcelReportUtil {
    public static class ColumnNames {
        public static final String[] WITHDRAW = {
                "会员帐号", "提取金额", "提款单号", "会员标签", "最后备注", "风控维度", "状态",
                "申请/一审/二审时间", "一审人员", "一审速度", "二审人员", "二审速度", "出款人员"};
        public static final String[] REMIT = {
                "提款单号", "会员帐号", "帐号类型", "提取金额", "会员等级", "渠道", "银行帐号/提币地址", "开户名", "备注", "状态",
                "申请时间", "审核人", "审核通过时间", "处理时间", "处理速度", "出款方式", "出款人"};
        public static final String[] WITHDRAW_AGENT = {
                "代理帐号", "提取金额", "提款单号", "最后备注", "风控维度", "状态",
                "申请/一审/二审时间", "一审人员", "一审速度", "二审人员", "二审速度", "出款人员"};
    }


}
