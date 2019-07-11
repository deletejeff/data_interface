package com.xichu.data_interface.common;

import java.util.ResourceBundle;

public class PropsUtil {

    public static final String SN1;
    public static final String SN2;
    public static final String SN3;
    public static final String SN4;
    public static final String SN5;
    public static final String SN6;
    public static final String SN7;
    public static final String SN8;
    public static final String SN9;
    public static final String SN10;

    public static final String JK_COUNTER_NUM1;
    public static final String JK_COUNTER_NUM2;
    public static final String JK_COUNTER_NUM3;
    public static final String JK_COUNTER_NUM4;
    public static final String JK_COUNTER_NUM5;
    public static final String JK_COUNTER_NUM6;
    public static final String JK_COUNTER_NUM7;
    public static final String JK_COUNTER_NUM8;
    public static final String JK_COUNTER_NUM9;
    public static final String JK_COUNTER_NUM10;

    public static final String XF_COUNTER_NUM1;
    public static final String XF_COUNTER_NUM2;
    public static final String XF_COUNTER_NUM3;
    public static final String XF_COUNTER_NUM4;
    public static final String XF_COUNTER_NUM5;
    public static final String XF_COUNTER_NUM6;
    public static final String XF_COUNTER_NUM7;
    public static final String XF_COUNTER_NUM8;
    public static final String XF_COUNTER_NUM9;
    public static final String XF_COUNTER_NUM10;


    public static final String JK="JK";
    public static final String XF="XF";

    static {
        //获取当前启动环境
//        String activeProfile = SpringContextUtil.getActiveProfile();
        //读取配置对应环境的配置文件
//        ResourceBundle resourceBundle = ResourceBundle.getBundle("config-" + activeProfile);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
        SN1 = resourceBundle.getString("sn1");
        SN2 = resourceBundle.getString("sn2");
        SN3 = resourceBundle.getString("sn3");
        SN4 = resourceBundle.getString("sn4");
        SN5 = resourceBundle.getString("sn5");
        SN6 = resourceBundle.getString("sn6");
        SN7 = resourceBundle.getString("sn7");
        SN8 = resourceBundle.getString("sn8");
        SN9 = resourceBundle.getString("sn9");
        SN10 = resourceBundle.getString("sn10");

        JK_COUNTER_NUM1 = resourceBundle.getString("jk_counter_num_1");
        JK_COUNTER_NUM2 = resourceBundle.getString("jk_counter_num_2");
        JK_COUNTER_NUM3 = resourceBundle.getString("jk_counter_num_3");
        JK_COUNTER_NUM4 = resourceBundle.getString("jk_counter_num_4");
        JK_COUNTER_NUM5 = resourceBundle.getString("jk_counter_num_5");
        JK_COUNTER_NUM6 = resourceBundle.getString("jk_counter_num_6");
        JK_COUNTER_NUM7 = resourceBundle.getString("jk_counter_num_7");
        JK_COUNTER_NUM8 = resourceBundle.getString("jk_counter_num_8");
        JK_COUNTER_NUM9 = resourceBundle.getString("jk_counter_num_9");
        JK_COUNTER_NUM10 = resourceBundle.getString("jk_counter_num_10");

        XF_COUNTER_NUM1 = resourceBundle.getString("xf_counter_num_1");
        XF_COUNTER_NUM2 = resourceBundle.getString("xf_counter_num_2");
        XF_COUNTER_NUM3 = resourceBundle.getString("xf_counter_num_3");
        XF_COUNTER_NUM4 = resourceBundle.getString("xf_counter_num_4");
        XF_COUNTER_NUM5 = resourceBundle.getString("xf_counter_num_5");
        XF_COUNTER_NUM6 = resourceBundle.getString("xf_counter_num_6");
        XF_COUNTER_NUM7 = resourceBundle.getString("xf_counter_num_7");
        XF_COUNTER_NUM8 = resourceBundle.getString("xf_counter_num_8");
        XF_COUNTER_NUM9 = resourceBundle.getString("xf_counter_num_9");
        XF_COUNTER_NUM10 = resourceBundle.getString("xf_counter_num_10");
    }
}
