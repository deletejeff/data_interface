package com.xichu.data_interface.enums;

public enum ResultEnum {
    ERROR("-1", "error"),
    PARAM_ERROR("-10001", "param error"),
    SIGN_ERROR("-10002", "sign error"),
    FAILURE("10001", "fail"),
    SAVE_FAILURE("10002", "save data fail but send success"),
    SEND_FAILURE("10003", "send fail"),
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
