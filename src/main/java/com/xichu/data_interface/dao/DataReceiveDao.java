package com.xichu.data_interface.dao;

import com.xichu.data_interface.bean.DataReceiveBean;
import com.xichu.data_interface.bean.PayReceiveBean;

public interface DataReceiveDao {
    int saveData(DataReceiveBean dataReceiveBean);

    DataReceiveBean queryById(String id);

    void deleteHistoryData();

    int savePayData(PayReceiveBean payReceiveBean);

    void deleteHistoryPayData();
}
