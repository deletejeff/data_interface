package com.xichu.data_interface.bean;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

@Data
public class PaySendBean {
    /**
     * 主键id
     */
    private String id;
    /**
     * 用户id
     */
    private String userid;
    /**
     * 用户名称
     */
    private String username;
    /**
     * 调用接口的机构号，JK：金卡，XF：先锋
     */
    private String orgid;
    /**
     * 用户表号
     */
    private String meterCode;
    /**
     * 柜台号
     */
    private String counterNum;
    /**
     * 缴费时间
     */
    private String payTime;
    /**
     * 购气数量
     */
    private String payNum;
    /**
     * 单价
     */
    private String unitPrice;
    /**
     * 购气金额
     */
    private String payMoney;
    /**
     * 合计应收金额
     */
    private String totalAccountReceivable;
    /**
     * 合计实收金额
     */
    private String totalAccountPayable;
    /**
     * 操作员
     */
    private String opName;
    /**
     * 发票提取码
     */
    private String invoiceExtractionCode;
    /**
     * 小票二维码地址
     */
    private String qrcodeUrl;
    /**
     * 数据
     */
    private JSONArray data;
    /**
     * 签名
     */
    private String sign;
}
