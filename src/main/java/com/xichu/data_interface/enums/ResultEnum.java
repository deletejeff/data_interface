package com.xichu.data_interface.enums;

public enum ResultEnum {
    ERROR("-1", "error"),
    PARAM_ERROR("-10001", "param error"),
    PARAM_USERID_NOTNULL("-20001", "参数 userid 不能为空"),
    PARAM_USERNAME_NOTNULL("-20002", "参数 username 不能为空"),
    PARAM_USERORGID_NOTNULL("-20003", "参数 orgid 不能为空"),
    PARAM_METERCODE_NOTNULL("-20004", "参数 meterCode 不能为空"),
    PARAM_COUNTERNUM_NOTNULL("-20005", "参数 counterNum 不能为空"),
    PARAM_FROMTIME_NOTNULL("-20006", "参数 fromTime 不能为空"),
    PARAM_TOTIME_NOTNULL("-20007", "参数 toTime 不能为空"),
    SIGN_ERROR("-20008", "sign 错误"),
    FAILURE("10001", "failure"),
    SAVE_FAILURE("10002", "保存数据失败，推送成功"),
    SEND_FAILURE("10003", "推送失败"),
    SEND_TO_LARGE_FAILURE("10004", "安卓设备最大推送消息为4000字节，已截取部分数据推送"),
    SUCCESS("0", "success"),
    ;
    private String code;
    private String message;
    ResultEnum(String code, String message){
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
