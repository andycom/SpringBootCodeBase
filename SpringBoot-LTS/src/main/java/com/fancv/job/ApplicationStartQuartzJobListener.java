package com.fancv.job;

import com.fancv.tracker.JobRunnerImpl;
import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.jobclient.JobClient;
import com.github.ltsopensource.jobclient.domain.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 监听器提交定时任务
 * 此时定时任务提交成功
 */
@Component
@Slf4j
public class ApplicationStartQuartzJobListener implements ApplicationListener<ContextRefreshedEvent> {

    public static final String FANCV_TASK = "FANCV_TASK";

    @Autowired
    private JobClient jobClient;

    @Value("${lts.taskTracker.nodeGroup}")
    private String taskTrackerNodeGroup;

    @Value("${sheduler.task.switch: false}")
    private boolean taskSwitch;


    /**
     * 提交定时任务
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            Job fancvJob = JobFactory.getInstance(JobRunnerImpl.class.getSimpleName().toUpperCase(), FANCV_TASK);
            if (fancvJob != null) {
                fancvJob.setCronExpression("0 */1 * * * ?");
                fancvJob.setTaskTrackerNodeGroup(taskTrackerNodeGroup);
                if (taskSwitch) {
                    Response response = jobClient.submitJob(fancvJob);
                    if (response.isSuccess()) {
                        log.info("JobListener 任务提交成功");
                    } else {
                        log.info("JobListener 任务提交失败：{}", response.getMsg());
                    }
                } else {
                    jobClient.cancelJob(fancvJob.getTaskId(), taskTrackerNodeGroup);
                    log.info("任务配置取消");
                }
            }
            System.out.println("任务已经启动...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
