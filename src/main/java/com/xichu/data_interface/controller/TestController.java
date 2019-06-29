package com.xichu.data_interface.controller;

import com.xichu.data_interface.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping("/1")
    public String test(){
        return testService.getAll().get(0);
    }
}
