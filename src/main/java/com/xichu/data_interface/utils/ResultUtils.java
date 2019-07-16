package com.xichu.data_interface.utils;

import com.alibaba.fastjson.JSON;
import com.xichu.data_interface.bean.ResultMap;
import com.xichu.data_interface.enums.ResultEnum;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResultUtils {
    public static ResultMap success(){
        ResultMap resultMap = new ResultMap(ResultEnum.SUCCESS);
        log.info("回应数据:" + JSON.toJSONString(resultMap));
        return resultMap;
    }
    public static ResultMap success(Object obj){
        ResultMap resultMap = new ResultMap(ResultEnum.SUCCESS);
        resultMap.setData(obj);
        log.info("回应数据:" + JSON.toJSONString(resultMap));
        return resultMap;
    }
    public static ResultMap fail(){
        ResultMap resultMap = new ResultMap(ResultEnum.FAILURE);
        log.info("回应数据:" + JSON.toJSONString(resultMap));
        return resultMap;
    }
    public static ResultMap fail(ResultEnum resultEnum){
        ResultMap resultMap = new ResultMap(resultEnum);
        log.info("回应数据:" + JSON.toJSONString(resultMap));
        return resultMap;
    }
    public static ResultMap error(){
        ResultMap resultMap = new ResultMap(ResultEnum.ERROR);
        log.info("回应数据:" + JSON.toJSONString(resultMap));
        return resultMap;
    }
}
