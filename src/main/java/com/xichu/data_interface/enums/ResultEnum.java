package com.xichu.data_interface.enums;

public enum ResultEnum {
    ERROR("-1", "error"),
    PARAM_ERROR("-10001", "param_error"),
    SIGN_ERROR("-10002", "sign_error"),
    FAILURE("10001", "fail"),
    SAVE_FAILURE("10002", "save_fail"),
    SEND_FAILURE("10003", "send_fail"),
    SUCCESS("0", "success"),
    ;
    public String code;
    public String message;
    ResultEnum(String code, String message){
        this.code = code;
        this.message = message;
    }
}
