package com.fancv.job;

import com.fancv.tracker.JobRunnerImpl;
import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.jobclient.JobClient;
import com.github.ltsopensource.jobclient.domain.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import javax.annotation.PostConstruct;

/**
 * 定时任务提交
 * 此时lts初始化未完成 提交失败
 */
@Configuration
@Lazy
@Slf4j
public class LtsSchedulerConfig {

    
    public static final String FANCV_TASK = "FANCV_TASK";
    
    @Autowired
    private JobClient jobClient;

    @Value("${lts.taskTracker.nodeGroup}")
    private String taskTrackerNodeGroup;

    @Value("${sheduler.task.switch: false}")
    private boolean taskSwitch;

    @PostConstruct
    public void init() throws InterruptedException {
        Job fancvJob = JobFactory.getInstance(JobRunnerImpl.class.getSimpleName().toUpperCase(), FANCV_TASK);
        if (fancvJob != null) {
            fancvJob.setCronExpression("0 */2 * * * ?");
            fancvJob.setTaskTrackerNodeGroup(taskTrackerNodeGroup);
            if (taskSwitch) {
                Thread.sleep(1000);
                Response response = jobClient.submitJob(fancvJob);
                if (response.isSuccess()) {
                    log.info("任务提交成功");
                } else {
                    log.info("任务提交失败：{}", response.getMsg());
                }
            } else {
                jobClient.cancelJob(fancvJob.getTaskId(), taskTrackerNodeGroup);
                log.info("任务配置取消");
            }
        }
    }
}
