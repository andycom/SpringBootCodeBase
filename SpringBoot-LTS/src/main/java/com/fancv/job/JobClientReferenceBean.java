package com.fancv.job;

import com.github.ltsopensource.core.commons.utils.DateUtils;
import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.jobclient.JobClient;
import com.github.ltsopensource.jobclient.RetryJobClient;
import com.github.ltsopensource.jobclient.domain.Response;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Robert HG (254963746@qq.com) on 4/9/16.
 */
public class JobClientReferenceBean implements InitializingBean {

    /**
     * 自己的业务类,就可以这样引用了
     */
    @Autowired
    @Qualifier("jobClient_1")
    private JobClient jobClient_1;

    @Override
    public void afterPropertiesSet() throws Exception {

        JobClient jobClient = new RetryJobClient();
        jobClient.setNodeGroup("test_jobClient");
        jobClient.setClusterName("test_cluster");
        jobClient.setRegistryAddress("zookeeper://127.0.0.1:2181");
        jobClient.start();

// 提交任务
        Job job = new Job();
        job.setTaskId("3213213123");
        job.setParam("shopId", "11111");
        job.setTaskTrackerNodeGroup("test_trade_TaskTracker");
        job.setCronExpression("0 0/1 * * * ?");  // 支持 cronExpression表达式
        Response response = jobClient.submitJob(job);
    }
}
