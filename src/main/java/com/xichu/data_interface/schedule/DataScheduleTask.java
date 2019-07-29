package com.xichu.data_interface.schedule;

import com.xichu.data_interface.service.DataReceiveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@Configuration
@EnableScheduling
public class DataScheduleTask {

    private final DataReceiveService dataReceiveService;

    public DataScheduleTask(DataReceiveService dataReceiveService) {
        this.dataReceiveService = dataReceiveService;
    }

    //3.添加定时任务
    @Scheduled(cron = "0 09 23 * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    private void configureTasks() {
        System.err.println("执行定时清理历史数据时间: " + LocalDateTime.now());
        dataReceiveService.deleteHistoryData();
        dataReceiveService.deleteHistoryPayData();
    }
}
