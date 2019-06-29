package com.xichu.data_interface;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication(scanBasePackages = "com.xichu")
@MapperScan(basePackages = "com.xichu.data_interface.dao")
@ServletComponentScan
public class DataInterfaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataInterfaceApplication.class, args);
    }

}
