package com.crsm.maker.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * 测试用例
 * creat by Ccr on 2019/1/6
 **/
@Slf4j
public class HelloJob implements BaseJob {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.warn("Hello Job执行时间："+new Date());
    }
}
