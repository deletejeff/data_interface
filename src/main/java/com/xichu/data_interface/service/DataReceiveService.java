package com.xichu.data_interface.service;

import com.xichu.data_interface.bean.DataReceiveBean;

public interface DataReceiveService {
    boolean send(String data);

    boolean save(DataReceiveBean dataReceiveBean);
}
