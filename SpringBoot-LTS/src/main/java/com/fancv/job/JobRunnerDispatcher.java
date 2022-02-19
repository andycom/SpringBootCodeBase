package com.fancv.job;

import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.tasktracker.Result;
import com.github.ltsopensource.tasktracker.runner.JobContext;
import com.github.ltsopensource.tasktracker.runner.JobRunner;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * 迁移
 */
@Component
public class JobRunnerDispatcher implements JobRunner, ApplicationContextAware {

    private static final ConcurrentHashMap<String, JobRunner> JOB_RUNNER_MAP = new ConcurrentHashMap<String, JobRunner>();

    @Override
    public Result run(JobContext jobContext) throws Throwable {
        /* 根据type选择对应的JobRunner运行 */
        Job job = jobContext.getJob();
        String type = job.getParam("type");

        JobRunner jobRunner = JOB_RUNNER_MAP.get(type.toUpperCase());
        if (jobRunner == null) {
            throw new RuntimeException("未找到任务执行器:" + type.toUpperCase());
        }

        return jobRunner.run(jobContext);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        
        Map<String, JobRunner> map = applicationContext.getBeansOfType(JobRunner.class);

        for (Map.Entry<String, JobRunner> entry : map.entrySet()) {
            JOB_RUNNER_MAP.put(entry.getKey().toUpperCase(), entry.getValue());
        }
    }

}