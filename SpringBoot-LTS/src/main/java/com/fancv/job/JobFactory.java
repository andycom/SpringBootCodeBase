package com.fancv.job;

import com.github.ltsopensource.core.domain.Job;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;


/**
 * 任务封装
 */
@Component
public class JobFactory {

    private static String taskTrackerNodeGroup;

    @Value("${lts.taskTracker.nodeGroup}")
    private void setDatabase(String nodeGroup) {
        taskTrackerNodeGroup = nodeGroup;
    }

    private static void init(Job job) {
        job.setTaskTrackerNodeGroup(taskTrackerNodeGroup);
        job.setNeedFeedback(true);
        // 当任务队列中存在这个任务的时候，是否替换更新
        job.setReplaceOnExist(true);
        if (StringUtils.isAnyEmpty(job.getTaskId())) {
            job.setTaskId(UUID.randomUUID().toString());
        }
    }

    public static Job getInstance(String type) {
        return getInstance(type, null);
    }

    public static Job getInstance(String type, String taskId) {
        Job job = new Job();
        job.setTaskId(taskId);
        job.setParam("type", type);
        init(job);
        return job;
    }

}
