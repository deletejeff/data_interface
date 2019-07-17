package com.xichu.data_interface.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xichu.data_interface.bean.DataQueryBean;
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
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/data_receive")
public class DataReceiveController {
    private final DataReceiveService dataReceiveService;

    private static final String md5_param = "gasTransDetail";

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
            dataReceiveBean.setId(UUID.randomUUID().toString().replaceAll("-",""));
            //签名规则：userid + username + orgid + meterCode + counterNum + fromTime + toTime + “gasTransDetail”
            String str = dataReceiveBean.getUserid() + dataReceiveBean.getUsername() + dataReceiveBean.getOrgid() +
                    dataReceiveBean.getMeterCode() + dataReceiveBean.getCounterNum() + dataReceiveBean.getFromTime() +
                    dataReceiveBean.getToTime() + md5_param;
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
//            if(!StringUtils.isEmpty(sendMsg) && sendMsg.getBytes().length > 4000){
//                log.info("数据超过4000字节，android设备不能接收，发送的消息长度为：" + sendMsg.length());
//                DataReceiveBean dataReceiveBean2 = new DataReceiveBean();
//                JSONArray jsonArray = new JSONArray();
//                for (Object o : dataReceiveBean.getData()) {
//                    if(JSON.toJSONString(dataReceiveBean2).getBytes().length + o.toString().getBytes().length >= 1500){
//                        break;
//                    }
//                    jsonArray.add(o);
//                    dataReceiveBean2.setData(jsonArray);
//                }
//                dataReceiveBean.setData(dataReceiveBean2.getData());
//                sendMsg = JSON.toJSONString(dataReceiveBean);
//                log.info("截取后的消息长度为：" + sendMsg.length());
//                resMessage = ResultEnum.SEND_TO_LARGE_FAILURE.getMessage();
//            }
            boolean res;
            //发送数据到终端设备
            res = dataReceiveService.send(sendMsg,dataReceiveBean.getOrgid(), dataReceiveBean.getCounterNum());
            if(!res){
                return ResultUtils.fail(ResultEnum.SEND_FAILURE);
            }
            //保存数据
            res = dataReceiveService.save(dataReceiveBean);
            if (!res) {
                return ResultUtils.fail(ResultEnum.SAVE_FAILURE);
            }
            return ResultUtils.success();
        }catch (Exception e){
            log.error("系统异常", e);
            return ResultUtils.error();
        }

    }


    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public ResultMap query(HttpServletRequest request, HttpServletResponse response) {
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
            String md5DigestAsHex = DigestUtils.md5DigestAsHex((id + md5_param).getBytes(StandardCharsets.UTF_8));
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
