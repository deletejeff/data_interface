package com.xichu.data_interface.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xichu.data_interface.bean.DataReceiveBean;
import com.xichu.data_interface.bean.ResultMap;
import com.xichu.data_interface.enums.ResultEnum;
import com.xichu.data_interface.service.DataReceiveService;
import com.xichu.data_interface.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@RequestMapping("/data_receive")
public class DataReceiveController {
    private final DataReceiveService dataReceiveService;

    public DataReceiveController(DataReceiveService dataReceiveService) {
        this.dataReceiveService = dataReceiveService;
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public ResultMap send(HttpServletRequest request, HttpServletResponse response) {
        try {
            //获取请求数据
            Object obj = request.getAttribute("receiveData");
            String requestData = obj != null ? obj.toString() : "";
            if(StringUtils.isEmpty(request)){
                return ResultUtils.fail(ResultEnum.PARAM_ERROR);
            }
            DataReceiveBean dataReceiveBean = JSONObject.parseObject(requestData, DataReceiveBean.class);
            //签名规则：userid + username + orgid + meterCode + counterNum + fromTime + toTime + “gasTransDetail”
            String str = dataReceiveBean.getUserid() + dataReceiveBean.getUsername() + dataReceiveBean.getOrgid() +
                    dataReceiveBean.getMeterCode() + dataReceiveBean.getCounterNum() + dataReceiveBean.getFromTime() +
                    dataReceiveBean.getToTime() + "gasTransDetail";
            String md5DigestAsHex = DigestUtils.md5DigestAsHex(str.getBytes(StandardCharsets.UTF_8));
            log.info(String.format("接收MD5值：%s", dataReceiveBean.getSign()));
            log.info(String.format("后台MD5值：%s", md5DigestAsHex));
            //校验签名值
            if(!md5DigestAsHex.equals(dataReceiveBean.getSign())){
                return ResultUtils.fail(ResultEnum.SIGN_ERROR);
            }
            boolean res;
            //保存数据
            res = dataReceiveService.save(dataReceiveBean);
            if (!res){
                return ResultUtils.fail(ResultEnum.SAVE_FAILURE);
            }
            //发送数据到终端设备
            res = dataReceiveService.send(JSON.toJSONString(dataReceiveBean));
            if(!res){
                return ResultUtils.fail(ResultEnum.SEND_FAILURE);
            }
            return ResultUtils.success();
        }catch (Exception e){
            log.error("", e);
            return ResultUtils.error();
        }

    }
}
