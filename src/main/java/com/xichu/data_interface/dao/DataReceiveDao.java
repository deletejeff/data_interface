package com.xichu.data_interface.dao;

import com.xichu.data_interface.bean.DataPojoBean;

public interface DataReceiveDao {
    int saveData(DataPojoBean dataPojoBean);
}
