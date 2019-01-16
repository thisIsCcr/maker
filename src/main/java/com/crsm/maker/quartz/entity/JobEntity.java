package com.crsm.maker.quartz.entity;

import lombok.Data;
import org.quartz.JobDataMap;

import java.util.Date;

/**
 * Created  by Ccr on 2019/1/14
 **/
@Data
public class JobEntity {
    private int jobId;

    private String jobType;

    private String jobGroup;

    private String jobName;

    private String triggerName;

    private String triggerGroupName;

    private String cronExpr;

    private Date previousFireTime;

    private Date nextFireTime;

    private String jobStatus;

    private long runTimes;

    private long duration;

    private Date startTime;

    private Date endTime;

    private String jobMemo;

    private String jobClass;

    private String jobMethod;

    private String jobObject;

    private int count;

    private JobDataMap jobDataMap;

}
