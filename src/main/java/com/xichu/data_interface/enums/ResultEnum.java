package com.xichu.data_interface.enums;

public enum ResultEnum {
    ERROR("-1", "error"),
    PARAM_ERROR("-10001", "param error"),
    SIGN_ERROR("-10002", "sign 错误"),
    FAILURE("10001", "失败"),
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
