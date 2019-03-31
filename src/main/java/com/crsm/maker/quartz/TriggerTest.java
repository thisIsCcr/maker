package com.crsm.maker.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * creat by Ccr on 2019/3/31
 **/
@Slf4j
public class TriggerTest implements BaseJob {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("触发时间：{}",new SimpleDateFormat("YYYY-MM-dd hh:mm:ss").format(new Date()));
    }
}
