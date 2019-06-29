package com.xichu.data_interface.utils;

import com.xichu.data_interface.bean.ResultMap;
import com.xichu.data_interface.enums.ResultEnum;

public class ResultUtils {
    public static ResultMap success(){
        return new ResultMap(ResultEnum.SUCCESS);
    }
    public static ResultMap fail(){
        return new ResultMap(ResultEnum.FAILURE);
    }
    public static ResultMap fail(ResultEnum resultEnum){
        return new ResultMap(resultEnum);
    }
    public static ResultMap error(){
        return new ResultMap(ResultEnum.ERROR);
    }
}
