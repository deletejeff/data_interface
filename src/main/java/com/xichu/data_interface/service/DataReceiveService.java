package com.xichu.data_interface.service;

import com.xichu.data_interface.bean.DataReceiveBean;
import com.xichu.data_interface.bean.PayReceiveBean;

public interface DataReceiveService {
    int send(String data, String orgid, String counterNum);

    boolean save(DataReceiveBean dataReceiveBean);

    DataReceiveBean queryById(String id);

    void deleteHistoryData();

    boolean savePay(PayReceiveBean payReceiveBean);

    void deleteHistoryPayData();
}
