package com.crsm.maker.quartz.entity;

import com.alibaba.fastjson.annotation.JSONField;
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

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date previousFireTime;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date nextFireTime;

    private String jobStatus;

    private long runTimes;

    private long duration;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    private String jobMemo;

    private String jobClass;

    private String jobMethod;

    private String jobObject;

    private int count;

    private JobDataMap jobDataMap;

}
