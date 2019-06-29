package com.xichu.data_interface.service.impl;

import com.xichu.data_interface.dao.TestDao;
import com.xichu.data_interface.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private TestDao testDao;
    public List<String> getAll(){
        return testDao.getAll();
    }
}
