package com.crsm.maker.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * quartz执行器基类
 * creat by Ccr on 2019/1/6
 **/
public interface BaseJob extends Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext)throws JobExecutionException;
}
