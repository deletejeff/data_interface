package com.xichu.data_interface.service.impl;

import com.xichu.data_interface.bean.DataReceiveBean;
import com.xichu.data_interface.dao.DataReceiveDao;
import com.xichu.data_interface.service.DataReceiveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DataReceiveServiceImpl implements DataReceiveService {

    @Autowired
    private DataReceiveDao dataReceiveDao;

    @Override
    public boolean send() {
        return true;
    }

    @Override
    public boolean save(DataReceiveBean dataReceiveBean) {
        return dataReceiveDao.saveData(dataReceiveBean) > 0;
    }
}
