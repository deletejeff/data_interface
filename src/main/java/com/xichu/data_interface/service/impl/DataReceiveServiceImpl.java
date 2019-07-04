package com.xichu.data_interface.service.impl;

import com.xichu.data_interface.bean.DataReceiveBean;
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
    public boolean send(String data) {
        List<String> alias = new ArrayList<>();
        alias.add("123");
        log.info("极光推送：设备号【123】, title【】, 消息title【】, 数据：【" + data +"】");
        return JpushClientUtil.sendToRegistrationId(alias, "测试", "测试信息", "测试信息", "https://www.baidu.com") > 0;
//        return JpushClientUtil.sendToAllAndroid("测试", "测试信息", "测试信息", "https://www.baidu.com") > 0;
    }

    @Override
    public boolean save(DataReceiveBean dataReceiveBean) {
        return dataReceiveDao.saveData(dataReceiveBean) > 0;
    }
}
