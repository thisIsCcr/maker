package com.crsm.maker.quartz.controller;

import com.crsm.maker.base.BaseController;
import com.crsm.maker.quartz.BaseJob;
import com.crsm.maker.quartz.entity.JobEntity;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 提供定时任务操作
 * creat by Ccr on 2019/1/6
 **/
@Slf4j
@RestController
@RequestMapping(value = "/job")
public class JobController extends BaseController {

    @Autowired
    private Scheduler scheduler;

    /**
     * 获取所有作业详情
     * @return
     * @throws Exception
     */
    @GetMapping("list")
    public String getSchedulerJobInfo() throws Exception {
        List<JobEntity> jobInfos = new ArrayList<>();
        List<String> triggerGroupNames = scheduler.getTriggerGroupNames();
        for (String triggerGroupName : triggerGroupNames) {
            Set<TriggerKey> triggerKeySet = scheduler.getTriggerKeys(GroupMatcher.triggerGroupEquals(triggerGroupName));
            for (TriggerKey triggerKey : triggerKeySet) {
                Trigger t = scheduler.getTrigger(triggerKey);
                if (t instanceof CronTrigger) {
                    CronTrigger trigger = (CronTrigger) t;
                    JobKey jobKey = trigger.getJobKey();
                    JobDetail jd = scheduler.getJobDetail(jobKey);
                    JobEntity jobInfo = new JobEntity();
                    jobInfo.setJobName(jobKey.getName());

                    jobInfo.setJobGroup(jobKey.getGroup());
                    //执行器名称
                    jobInfo.setTriggerName(triggerKey.getName());
                    //执行器组名称
                    jobInfo.setTriggerGroupName(triggerKey.getGroup());
                    //时间Cron表达式
                    jobInfo.setCronExpr(trigger.getCronExpression());
                    //下次运行时间
                    jobInfo.setNextFireTime(trigger.getNextFireTime());
                    //上次运行时间
                    jobInfo.setPreviousFireTime(trigger.getPreviousFireTime());
                    //启动时间
                    jobInfo.setStartTime(trigger.getStartTime());
                    //结束时间
                    jobInfo.setEndTime(trigger.getEndTime());
                    jobInfo.setJobClass(jd.getJobClass().getCanonicalName());
                    // jobInfo.setDuration(Long.parseLong(jd.getDescription()));
                    Trigger.TriggerState triggerState = scheduler
                            .getTriggerState(trigger.getKey());
                    jobInfo.setJobStatus(triggerState.toString());// NONE无,
                    // NORMAL正常,
                    // PAUSED暂停,
                    // COMPLETE完全,
                    // ERROR错误,
                    // BLOCKED阻塞
                    JobDataMap map = scheduler.getJobDetail(jobKey).getJobDataMap();
                    if (null != map && map.size() != 0) {
                        jobInfo.setCount(Integer.parseInt((String) map.get("count")));
                        jobInfo.setJobDataMap(map);
                    } else {
                        jobInfo.setJobDataMap(new JobDataMap());
                    }
                    jobInfos.add(jobInfo);
                }
            }
        }
        return success(jobInfos);
    }

    /**
     * 添加任务
     * @param jobClassName
     * @param jobGroupName
     * @param cronExpression
     * @throws Exception
     */
    @PostMapping(value = "/addjob")
    public void addjob(@RequestParam(value = "jobClassName") String jobClassName,
                       @RequestParam(value = "jobGroupName") String jobGroupName,
                       @RequestParam(value = "cronExpression") String cronExpression) throws Exception {
        // 启动调度器
        scheduler.start();
        //构建job信息
        JobDetail jobDetail = JobBuilder.newJob(getClass(jobClassName).getClass()).withIdentity(jobClassName, jobGroupName).build();
        //表达式调度构建器(即任务执行的时间)
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
        //按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobClassName, jobGroupName).withSchedule(scheduleBuilder).build();
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            System.out.println("创建定时任务失败" + e);
            throw new Exception("创建定时任务失败");
        }
        log.info("添加定时器成功：{}",jobGroupName);
    }

    /**
     * 暂停任务
     * @param jobClassName
     * @param jobGroupName
     * @throws Exception
     */
    @PostMapping(value = "/pausejob")
    public void pausejob(@RequestParam(value = "jobClassName") String jobClassName, @RequestParam(value = "jobGroupName") String jobGroupName) throws Exception {
        JobKey Key=JobKey.jobKey(jobClassName, jobGroupName);
        scheduler.getJobDetail(Key);
        scheduler.pauseJob(JobKey.jobKey(jobClassName, jobGroupName));
        log.info("暂停任务成功：{}",jobGroupName);
    }


    /**
     *
     * @param jobClassName
     * @param jobGroupName
     * @param operatType 操作类型、0：暂停任务、1：恢复任务
     * @return
     * @throws SchedulerException
     */
    @PostMapping("/pauseOrResumeJob")
    public String pauseOrResumeJob(@RequestParam(value = "jobClassName") String jobClassName,
                                   @RequestParam(value = "jobGroupName") String jobGroupName,
                                   @RequestParam("operatType")Integer operatType) throws SchedulerException {
        /*JobKey jKey=JobKey.jobKey(jobClassName, jobGroupName);
        JobDetail detail=scheduler.getJobDetail(jKey);*/
        //获取触发器
        TriggerKey tKey=TriggerKey.triggerKey(jobClassName, jobGroupName);
        if(tKey!=null){
            Trigger.TriggerState state=scheduler.getTriggerState(tKey);
            if(!(Trigger.TriggerState.PAUSED==state || Trigger.TriggerState.NORMAL==state)){
                throw new SchedulerException("失败，当前状态不无法操作");
            }
        }
        if(operatType==0){
            scheduler.pauseJob(JobKey.jobKey(jobClassName, jobGroupName));
        }else{
            scheduler.resumeJob(JobKey.jobKey(jobClassName, jobGroupName));
        }
        return success();
    }

    /**
     * 恢复任务
     * @param jobClassName
     * @param jobGroupName
     * @throws Exception
     */
    @PostMapping(value = "/resumejob")
    public void resumejob(@RequestParam(value = "jobClassName") String jobClassName, @RequestParam(value = "jobGroupName") String jobGroupName) throws Exception {
        scheduler.resumeJob(JobKey.jobKey(jobClassName, jobGroupName));
        log.info("恢复任务成功：{}",jobGroupName);
    }



    /**
     * 修改任务时间
     * @param jobClassName
     * @param jobGroupName
     * @param cronExpression
     * @throws Exception
     */
    @PostMapping(value = "/reschedulejob")
    public void rescheduleJob(@RequestParam(value = "jobClassName") String jobClassName,
                              @RequestParam(value = "jobGroupName") String jobGroupName,
                              @RequestParam(value = "cronExpression") String cronExpression) throws Exception {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobClassName, jobGroupName);
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
            log.info("修改任务时间成功：{}",jobGroupName);
        } catch (SchedulerException e) {
            System.out.println("更新定时任务失败" + e);
            throw new Exception("更新定时任务失败");
        }
    }


    /**
     * 移除任务
     * @param jobClassName
     * @param jobGroupName
     * @throws Exception
     */
    @PostMapping(value = "/deletejob")
    public void deletejob(@RequestParam(value = "jobClassName") String jobClassName, @RequestParam(value = "jobGroupName") String jobGroupName) throws Exception {
        scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName, jobGroupName));
        scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName, jobGroupName));
        scheduler.deleteJob(JobKey.jobKey(jobClassName, jobGroupName));
        log.info("移除任务成功：{}",jobGroupName);
    }


    /**
     * 必须是Job（BaseJob）的子类，
     * @param classname
     * @return
     * @throws Exception
     */
    public static BaseJob getClass(String classname) throws Exception {
        Class<?> class1 = Class.forName(classname);
        return (BaseJob) class1.newInstance();
    }


}
