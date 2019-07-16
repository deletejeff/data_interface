package com.xichu.data_interface.bean;

import com.xichu.data_interface.enums.ResultEnum;
import lombok.Data;

@Data
public class ResultMap {
    private String code;
    private String message;
    private Object data;
    public ResultMap(String code, String message, String data){
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResultMap(ResultEnum resultEnum){
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
    }
    public ResultMap(){}
}
