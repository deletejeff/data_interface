package com.xichu.data_interface.service.impl;

import com.xichu.data_interface.bean.DataPojoBean;
import com.xichu.data_interface.common.PropsUtil;
import com.xichu.data_interface.dao.DataReceiveDao;
import com.xichu.data_interface.service.DataReceiveService;
import com.xichu.data_interface.utils.JpushClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DataReceiveServiceImpl implements DataReceiveService {

    private final DataReceiveDao dataReceiveDao;

    public DataReceiveServiceImpl(DataReceiveDao dataReceiveDao) {
        this.dataReceiveDao = dataReceiveDao;
    }

    @Override
    public boolean send(String data, String orgid, String counterNum) {
        boolean res = false;
        List<String> alias = new ArrayList<>();
        if(PropsUtil.JK.equals(orgid)){
            if(PropsUtil.JK_COUNTER_NUM1.equals(counterNum)){
                alias.add(PropsUtil.SN1);
            }else if(PropsUtil.JK_COUNTER_NUM2.equals(counterNum)){
                alias.add(PropsUtil.SN2);
            }else if(PropsUtil.JK_COUNTER_NUM3.equals(counterNum)){
                alias.add(PropsUtil.SN3);
            }else if(PropsUtil.JK_COUNTER_NUM4.equals(counterNum)){
                alias.add(PropsUtil.SN4);
            }else if(PropsUtil.JK_COUNTER_NUM5.equals(counterNum)){
                alias.add(PropsUtil.SN5);
            }else if(PropsUtil.JK_COUNTER_NUM6.equals(counterNum)){
                alias.add(PropsUtil.SN6);
            }else if(PropsUtil.JK_COUNTER_NUM7.equals(counterNum)){
                alias.add(PropsUtil.SN7);
            }else if(PropsUtil.JK_COUNTER_NUM8.equals(counterNum)){
                alias.add(PropsUtil.SN8);
            }else if(PropsUtil.JK_COUNTER_NUM9.equals(counterNum)){
                alias.add(PropsUtil.SN9);
            }else if(PropsUtil.JK_COUNTER_NUM10.equals(counterNum)){
                alias.add(PropsUtil.SN10);
            }
        }else if(PropsUtil.XF.equals(orgid)){
            if(PropsUtil.XF_COUNTER_NUM1.equals(counterNum)){
                alias.add(PropsUtil.SN1);
            }else if(PropsUtil.XF_COUNTER_NUM2.equals(counterNum)){
                alias.add(PropsUtil.SN2);
            }else if(PropsUtil.XF_COUNTER_NUM3.equals(counterNum)){
                alias.add(PropsUtil.SN3);
            }else if(PropsUtil.XF_COUNTER_NUM4.equals(counterNum)){
                alias.add(PropsUtil.SN4);
            }else if(PropsUtil.XF_COUNTER_NUM5.equals(counterNum)){
                alias.add(PropsUtil.SN5);
            }else if(PropsUtil.XF_COUNTER_NUM6.equals(counterNum)){
                alias.add(PropsUtil.SN6);
            }else if(PropsUtil.XF_COUNTER_NUM7.equals(counterNum)){
                alias.add(PropsUtil.SN7);
            }else if(PropsUtil.XF_COUNTER_NUM8.equals(counterNum)){
                alias.add(PropsUtil.SN8);
            }else if(PropsUtil.XF_COUNTER_NUM9.equals(counterNum)){
                alias.add(PropsUtil.SN9);
            }else if(PropsUtil.XF_COUNTER_NUM10.equals(counterNum)){
                alias.add(PropsUtil.SN10);
            }
        }

        if(alias.size() > 0){
            for (String s : alias) {
                log.info("极光推送：设备号【" + s + "】");
            }
            res = JpushClientUtil.sendToRegistrationId(alias, "测试", "测试信息", data, "https://www.baidu.com") > 0;
//            res = JpushClientUtil.sendToAllAndroid("测试", "测试信息", data, "https://www.baidu.com") > 0;
            log.info("推送成功");
        }else{
            log.info("通过参数orgid：" + orgid + "，counterNum：" + counterNum + "，未对应上推送设备");
            res = false;
        }
        return res;

    }

    @Override
    public boolean save(DataPojoBean dataPojoBean) {
        return dataReceiveDao.saveData(dataPojoBean) > 0;
    }
}
