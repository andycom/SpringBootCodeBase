package com.fancv.service;

import com.alibaba.fastjson.JSON;
import com.fancv.config.LtsConfig;
import com.fancv.job.JobFactory;
import com.fancv.tracker.TimerJobRunnerImpl;
import com.github.ltsopensource.core.commons.utils.StringUtils;
import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.core.exception.JobSubmitException;
import com.github.ltsopensource.jobclient.JobClient;
import com.github.ltsopensource.jobclient.domain.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 */

@Service
@Slf4j
public class LTSService {

    @Autowired
    LtsConfig ltsConfig;


    @Autowired
    JobClient jobClient;

    /**
     * 定时触发任务 或者立即执行任务
     * @param triggerTime
     * @return
     * @throws ParseException
     * @throws JobSubmitException
     */

    public Response submitWithTrigger(String triggerTime) throws ParseException, JobSubmitException {
        Job job = JobFactory.getInstance(TimerJobRunnerImpl.class.getSimpleName().toUpperCase(), "FANCV_TASK_TIMER");
        job.setTaskId(StringUtils.generateUUID());
        job.setParam("timer", "fancv.com");
        job.setMaxRetryTimes(5);
        job.setTaskTrackerNodeGroup(ltsConfig.getCommonNodeGroup());
        job.setNeedFeedback(true);
        /**
         * 这里可以兼容多种格式
         * 用户输入实现多种时间格式兼容 或者用户页面输入
         */
        if (triggerTime != null && !"".equals(triggerTime.trim())) {
            job.setTriggerTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(triggerTime).getTime());
        }
        Response response = jobClient.submitJob(job);
        log.info("{}", JSON.toJSONString(response));
        return response;
    }

    /**
     *提交重复任务
     */
    public void submitRepeatJob() {
        Job job = JobFactory.getInstance(TimerJobRunnerImpl.class.getSimpleName().toUpperCase(), "FANCV_TASK_REPEAT");
        // 一共执行5次
        job.setRepeatCount(5);
        // 10s执行一次
        job.setRepeatInterval(10 * 1000L);
        Response response = jobClient.submitJob(job);
    }
}
