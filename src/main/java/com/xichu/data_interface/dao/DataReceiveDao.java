package com.xichu.data_interface.dao;

import com.xichu.data_interface.bean.DataReceiveBean;

public interface DataReceiveDao {
    int saveData(DataReceiveBean dataReceiveBean);

    DataReceiveBean queryById(String id);
}
