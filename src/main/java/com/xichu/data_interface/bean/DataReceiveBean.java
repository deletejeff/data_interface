package com.xichu.data_interface.bean;

import lombok.Data;

@Data
public class DataReceiveBean {
    /**
     * 主键id
     */
    private Integer id;
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
     * 开始时间
     */
    private String fromTime;
    /**
     * 结束时间
     */
    private String toTime;
    /**
     * 数据
     */
    private String data;
    /**
     * 签名
     */
    private String sign;
}
