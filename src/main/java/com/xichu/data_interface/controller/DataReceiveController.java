package com.xichu.data_interface.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xichu.data_interface.bean.*;
import com.xichu.data_interface.enums.ResultEnum;
import com.xichu.data_interface.service.DataReceiveService;
import com.xichu.data_interface.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/data_receive")
public class DataReceiveController {
    private final DataReceiveService dataReceiveService;

    private static final String md5_param_query = "gasTransDetail";
    private static final String md5_param_pay = "gasPay";

    public DataReceiveController(DataReceiveService dataReceiveService) {
        this.dataReceiveService = dataReceiveService;
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public ResultMap query(HttpServletRequest request, HttpServletResponse response) {
        try {
            //获取请求数据
            Object obj = request.getAttribute("receiveData");
            String requestData = obj != null ? obj.toString() : "";
            if(StringUtils.isEmpty(request)){
                return ResultUtils.fail(ResultEnum.PARAM_ERROR);
            }
            DataReceiveBean dataReceiveBean = JSONObject.parseObject(requestData, DataReceiveBean.class);
            dataReceiveBean.setId(UUID.randomUUID().toString().replaceAll("-",""));
            //签名规则：userid + username + orgid + meterCode + counterNum + fromTime + toTime + “gasTransDetail”
            String str = dataReceiveBean.getUserid() + dataReceiveBean.getUsername() + dataReceiveBean.getOrgid() +
                    dataReceiveBean.getMeterCode() + dataReceiveBean.getCounterNum() + dataReceiveBean.getFromTime() +
                    dataReceiveBean.getToTime() + md5_param_query;
            String md5DigestAsHex = DigestUtils.md5DigestAsHex(str.getBytes(StandardCharsets.UTF_8));
            log.info(String.format("接收MD5值：%s", dataReceiveBean.getSign()));
            log.info(String.format("后台MD5值：%s", md5DigestAsHex));
            String sign = dataReceiveBean.getSign();
            //校验必填参数
            if(StringUtils.isEmpty(dataReceiveBean.getUserid())){
                return ResultUtils.fail(ResultEnum.PARAM_USERID_NOTNULL);
            }
            if(StringUtils.isEmpty(dataReceiveBean.getUsername())){
                return ResultUtils.fail(ResultEnum.PARAM_USERNAME_NOTNULL);
            }
            if(StringUtils.isEmpty(dataReceiveBean.getOrgid())){
                return ResultUtils.fail(ResultEnum.PARAM_USERORGID_NOTNULL);
            }
            if(StringUtils.isEmpty(dataReceiveBean.getMeterCode())){
                return ResultUtils.fail(ResultEnum.PARAM_METERCODE_NOTNULL);
            }
            if(StringUtils.isEmpty(dataReceiveBean.getCounterNum())){
                return ResultUtils.fail(ResultEnum.PARAM_COUNTERNUM_NOTNULL);
            }
            if(StringUtils.isEmpty(dataReceiveBean.getFromTime())){
                return ResultUtils.fail(ResultEnum.PARAM_FROMTIME_NOTNULL);
            }
            if(StringUtils.isEmpty(dataReceiveBean.getToTime())){
                return ResultUtils.fail(ResultEnum.PARAM_TOTIME_NOTNULL);
            }
            //校验签名值
            if(StringUtils.isEmpty(sign) || !md5DigestAsHex.toUpperCase().equals(sign.toUpperCase())){
                return ResultUtils.fail(ResultEnum.SIGN_ERROR);
            }
            String sendMsg = dataReceiveBean.getId();
            int resCode;
            //发送数据到终端设备
            resCode = dataReceiveService.send(sendMsg,dataReceiveBean.getOrgid(), dataReceiveBean.getCounterNum());
            if(resCode != 1){
                if(resCode == 1011){
                    return ResultUtils.fail(ResultEnum.SEND_AUDIENCE_FAILURE);
                }else if(resCode == -1){
                    return ResultUtils.fail(ResultEnum.SEND_COUNTERNUM_FAILURE);
                }else{
                    return ResultUtils.fail(String.valueOf(resCode), ResultEnum.SEND_FAILURE.getMessage());
                }
            }
            //保存数据
            boolean res = dataReceiveService.save(dataReceiveBean);
            if (!res) {
                return ResultUtils.fail(ResultEnum.SAVE_FAILURE);
            }
            return ResultUtils.success();
        }catch (Exception e){
            log.error("系统异常", e);
            return ResultUtils.error();
        }

    }


    @RequestMapping(value = "/queryById", method = RequestMethod.POST)
    public ResultMap queryById(HttpServletRequest request, HttpServletResponse response) {
        try {
            //获取请求数据
            Object obj = request.getAttribute("receiveData");
            String requestData = obj != null ? obj.toString() : "";
            if(StringUtils.isEmpty(requestData)){
                return ResultUtils.fail(ResultEnum.PARAM_ERROR);
            }
            JSONObject jsonObject = JSON.parseObject(requestData);
            String id = jsonObject.getString("id");
            String sign = jsonObject.getString("sign");
            if(StringUtils.isEmpty(id) || StringUtils.isEmpty(sign)){
                return ResultUtils.fail(ResultEnum.PARAM_ERROR);
            }
            String md5DigestAsHex = DigestUtils.md5DigestAsHex((id + md5_param_query).getBytes(StandardCharsets.UTF_8));
            log.info(String.format("接收MD5值：%s", sign));
            log.info(String.format("后台MD5值：%s", md5DigestAsHex));
            if(StringUtils.isEmpty(sign) || !md5DigestAsHex.toUpperCase().equals(sign.toUpperCase())){
                return ResultUtils.fail(ResultEnum.SIGN_ERROR);
            }
            DataReceiveBean dataReceiveBean = dataReceiveService.queryById(id);
            DataQueryBean dataQueryBean = null;
            if (dataReceiveBean != null) {
                dataQueryBean = new DataQueryBean();
                dataQueryBean.setId(dataReceiveBean.getId());
                dataQueryBean.setUserid(dataReceiveBean.getUserid());
                dataQueryBean.setUsername(dataReceiveBean.getUsername());
                dataQueryBean.setOrgid(dataReceiveBean.getOrgid());
                dataQueryBean.setMeterCode(dataReceiveBean.getMeterCode());
                dataQueryBean.setCounterNum(dataReceiveBean.getCounterNum());
                dataQueryBean.setFromTime(dataReceiveBean.getFromTime());
                dataQueryBean.setToTime(dataReceiveBean.getToTime());
                dataQueryBean.setQrcodeUrl(dataReceiveBean.getQrcodeUrl());
                dataQueryBean.setData(JSONArray.parseArray(dataReceiveBean.getData()));
            }
            return ResultUtils.success(dataQueryBean);
        }catch (Exception e){
            log.error("系统异常", e);
            return ResultUtils.error();
        }
    }

    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public ResultMap pay(HttpServletRequest request, HttpServletResponse response) {
        try {
//获取请求数据
            Object obj = request.getAttribute("receiveData");
            String requestData = obj != null ? obj.toString() : "";
            if(StringUtils.isEmpty(request)){
                return ResultUtils.fail(ResultEnum.PARAM_ERROR);
            }
            PayReceiveBean payReceiveBean = JSONObject.parseObject(requestData, PayReceiveBean.class);
            payReceiveBean.setId(UUID.randomUUID().toString().replaceAll("-",""));
            //签名规则：userid + username + orgid + meterCode + counterNum + fromTime + toTime + “gasTransDetail”
            String str = payReceiveBean.getUserid() + payReceiveBean.getUsername() + payReceiveBean.getOrgid() +
                    payReceiveBean.getMeterCode() + payReceiveBean.getCounterNum() + payReceiveBean.getPayTime() +
                    payReceiveBean.getPayNum() + payReceiveBean.getUnitPrice() + payReceiveBean.getPayMoney() +
                    payReceiveBean.getTotalAccountReceivable() + payReceiveBean.getTotalAccountPayable() + payReceiveBean.getOpName() +
                    payReceiveBean.getInvoiceExtractionCode() + payReceiveBean.getQrcodeUrl() + md5_param_pay;
            String md5DigestAsHex = DigestUtils.md5DigestAsHex(str.getBytes(StandardCharsets.UTF_8));
            log.info(String.format("接收MD5值为：%s", payReceiveBean.getSign()));
            log.info(String.format("后台MD5值为：%s", md5DigestAsHex));
            String sign = payReceiveBean.getSign();
            //校验必填参数
            if(StringUtils.isEmpty(payReceiveBean.getUserid())){
                return ResultUtils.fail(ResultEnum.PARAM_USERID_NOTNULL);
            }
            if(StringUtils.isEmpty(payReceiveBean.getUsername())){
                return ResultUtils.fail(ResultEnum.PARAM_USERNAME_NOTNULL);
            }
            if(StringUtils.isEmpty(payReceiveBean.getOrgid())){
                return ResultUtils.fail(ResultEnum.PARAM_USERORGID_NOTNULL);
            }
            if(StringUtils.isEmpty(payReceiveBean.getMeterCode())){
                return ResultUtils.fail(ResultEnum.PARAM_METERCODE_NOTNULL);
            }
            if(StringUtils.isEmpty(payReceiveBean.getCounterNum())){
                return ResultUtils.fail(ResultEnum.PARAM_COUNTERNUM_NOTNULL);
            }
            if(StringUtils.isEmpty(payReceiveBean.getPayTime())){
                return ResultUtils.fail(ResultEnum.PARAM_PAYTIME_NOTNULL);
            }
            if(StringUtils.isEmpty(payReceiveBean.getTotalAccountReceivable())){
                return ResultUtils.fail(ResultEnum.PARAM_TOTALACCOUNTRECEIVABLE_NOTNULL);
            }
            if(StringUtils.isEmpty(payReceiveBean.getTotalAccountPayable())){
                return ResultUtils.fail(ResultEnum.PARAM_TOTALACCOUNTPAYABLE_NOTNULL);
            }
            if(StringUtils.isEmpty(payReceiveBean.getOpName())){
                return ResultUtils.fail(ResultEnum.PARAM_OPNAME_NOTNULL);
            }
            if(StringUtils.isEmpty(payReceiveBean.getInvoiceExtractionCode())){
                return ResultUtils.fail(ResultEnum.PARAM_INVOICEEXTRACTIONCODE_NOTNULL);
            }
            if(StringUtils.isEmpty(payReceiveBean.getQrcodeUrl())){
                return ResultUtils.fail(ResultEnum.PARAM_QRCODEURL_NOTNULL);
            }
            //校验签名值
            if(StringUtils.isEmpty(sign) || !md5DigestAsHex.toUpperCase().equals(sign.toUpperCase())){
                return ResultUtils.fail(ResultEnum.SIGN_ERROR);
            }
            int resCode;
            JSONArray jsonArray = JSONArray.parseArray(payReceiveBean.getData());
            PaySendBean paySendBean = new PaySendBean();
            BeanUtils.copyProperties(payReceiveBean, paySendBean);
            paySendBean.setData(jsonArray);
            String sendMsg = JSON.toJSONString(paySendBean);
            //发送数据到终端设备
            resCode = dataReceiveService.send(sendMsg,payReceiveBean.getOrgid(), payReceiveBean.getCounterNum());
            if(resCode != 1){
                if(resCode == 1011){
                    return ResultUtils.fail(ResultEnum.SEND_AUDIENCE_FAILURE);
                }else if(resCode == -1){
                    return ResultUtils.fail(ResultEnum.SEND_COUNTERNUM_FAILURE);
                }else{
                    return ResultUtils.fail(String.valueOf(resCode), ResultEnum.SEND_FAILURE.getMessage());
                }
            }
            //保存数据
            boolean res = dataReceiveService.savePay(payReceiveBean);
            if (!res) {
                return ResultUtils.fail(ResultEnum.SAVE_FAILURE);
            }
            return ResultUtils.success();
        }catch (Exception e){
            log.error("系统异常", e);
            return ResultUtils.error();
        }
    }

    public static void main(String[] args) {
        String requestData = "{ \"sign\":\"d41d8cd98f00b204e9800998ecf8427e\", \"userid\":\"8005000001\", \"username\":\"薛西林\", \"orgid\":\"XF\", \"meterCode\":\"\", \"qrcodeUrl\":\"http://www.baidu.com\", \"counterNum\":\"001\", \"fromTime\":\"2013-12-31 13:14:31\", \"toTime\":\"2019-07-09 14:21:20\", \"data\":[  {  \"chargeNum\":\"1\",  \"price\":\"1.00\",  \"chargeDate\":\"2019-07-03\",  \"chargeMoney\":\"100.00\"  },  {  \"chargeNum\":\"2\",  \"price\":\"1.00\",  \"chargeDate\":\"2019-07-04\",  \"chargeMoney\":\"200.00\"  } ]}";
        DataReceiveBean dataReceiveBean = JSONObject.parseObject(requestData, DataReceiveBean.class);
//        "userid":"8005000001", "username":"薛西林", "orgid":"XF", "meterCode":"", "qrcodeUrl":"http://www.baidu.com", "counterNum":"001", "fromTime":"2013-12-31 13:14:31", "toTime":"2019-07-09 14:21:20"
        String str = dataReceiveBean.getUserid() + dataReceiveBean.getUsername() + dataReceiveBean.getOrgid() +
                dataReceiveBean.getMeterCode() + dataReceiveBean.getCounterNum() + dataReceiveBean.getFromTime() +
                dataReceiveBean.getToTime() + "gasTransDetail";
        String str2 = "8005000001薛西林XF0012013-12-31 13:14:312019-07-09 14:21:20gasTransDetail";
        String md5DigestAsHex = DigestUtils.md5DigestAsHex(str.getBytes(StandardCharsets.UTF_8));
        String md5DigestAsHex2 = DigestUtils.md5DigestAsHex(str2.getBytes(StandardCharsets.UTF_8));
        System.out.println(md5DigestAsHex);
        System.out.println(md5DigestAsHex2);
    }
}
