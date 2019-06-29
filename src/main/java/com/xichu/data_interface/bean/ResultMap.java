package com.xichu.data_interface.bean;

import com.xichu.data_interface.enums.ResultEnum;
import lombok.Data;

@Data
public class ResultMap {
    private String code;
    private String message;
    public ResultMap(String code, String message){
        this.code = code;
        this.message = message;
    }

    public ResultMap(ResultEnum resultEnum){
        this.code = resultEnum.code;
        this.message = resultEnum.message;
    }
    public ResultMap(){}
}
