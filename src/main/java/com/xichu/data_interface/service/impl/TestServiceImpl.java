package com.xichu.data_interface.service.impl;

import com.xichu.data_interface.dao.TestDao;
import com.xichu.data_interface.service.TestService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {
    private final TestDao testDao;

    public TestServiceImpl(TestDao testDao) {
        this.testDao = testDao;
    }

    public List<String> getAll(){
        return testDao.getAll();
    }
}
