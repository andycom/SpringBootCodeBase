package com.fancv.config;

import com.fancv.job.JobRunnerDispatcher;
import com.fancv.job.LtsSchedulerConfig;
import com.github.ltsopensource.spring.JobClientFactoryBean;
import com.github.ltsopensource.spring.JobTrackerFactoryBean;
import com.github.ltsopensource.spring.TaskTrackerAnnotationFactoryBean;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.Properties;

/**
 *
 */
@Configuration
@AutoConfigureBefore(LtsSchedulerConfig.class)
@Slf4j
@Order(1)
@Data
public class LtsConfig {

    @Value("${lts.jobclient.cluster-name}")
    private String clusterName;
    @Value("${lts.jobclient.node-group}")
    private String nodeGroup;
    @Value("${lts.jobTracker.listenPort}")
    private int listenPort;
    
    @Value("${lts.taskTracker.nodeGroup}")
    private String commonNodeGroup;

    @Value("${lts.jobclient.registry-address}")
    private String registryAddress;

    /**
     * lts 落库配置
     * Task Queue
     * Task log
     */
    @Value("${lts.jdbc.url}")
    private String jdbcUrl;
    @Value("${lts.jdbc.username}")
    private String userName;
    @Value("${lts.jdbc.password}")
    private String password;

    /**
     * 默认 TaskTracker
     */
    @Bean(initMethod = "start")
    public TaskTrackerAnnotationFactoryBean taskTracker() {
        Properties properties = new Properties();
        properties.setProperty("job.fail.store", "leveldb");
        properties.setProperty("jobtracker.job.retry.interval.millis", "60");

        TaskTrackerAnnotationFactoryBean factoryBean = new TaskTrackerAnnotationFactoryBean();
        factoryBean.setJobRunnerClass(JobRunnerDispatcher.class);
        factoryBean.setBizLoggerLevel("INFO");
        factoryBean.setClusterName(clusterName);
        factoryBean.setRegistryAddress(registryAddress);
        factoryBean.setNodeGroup(commonNodeGroup);
        factoryBean.setWorkThreads(16);
        factoryBean.setConfigs(properties);

        return factoryBean;
    }

    @Bean(initMethod = "start", destroyMethod = "destroy")
    public JobClientFactoryBean jobClient(){
        Properties properties = new Properties();
        properties.setProperty("job.fail.store", "leveldb");
        properties.setProperty("job.max.retry.times", "0");
        properties.setProperty("job.submit.maxQPS", "100000");

        JobClientFactoryBean jobClientFactoryBean = new JobClientFactoryBean();
        jobClientFactoryBean.setNodeGroup(nodeGroup);
        jobClientFactoryBean.setRegistryAddress(registryAddress);
        jobClientFactoryBean.setClusterName(clusterName);
        jobClientFactoryBean.setUseRetryClient(false);
        jobClientFactoryBean.setConfigs(properties);

        return jobClientFactoryBean;
    }

    @Bean(initMethod = "start")
    @Order(1)
    public JobTrackerFactoryBean jobTracker() {
        Properties properties = new Properties();
        properties.setProperty("job.logger", "mysql");
        properties.setProperty("job.queue", "mysql");
        properties.setProperty("jdbc.url", jdbcUrl);
        properties.setProperty("jdbc.username", userName);
        properties.setProperty("jdbc.password", password);
        properties.setProperty("job.max.retry.times", "0");
        properties.setProperty("druid.maxWait", "120000");
        properties.setProperty("minIdle", "5");
        properties.setProperty("druid.maxActive", "10");
        properties.setProperty("druid.validationQuery", "SELECT 'x'");
        properties.setProperty("druid.minEvictableIdleTimeMillis", "300000");

        JobTrackerFactoryBean factoryBean = new JobTrackerFactoryBean();
        factoryBean.setRegistryAddress(registryAddress);
        factoryBean.setClusterName(clusterName);
        factoryBean.setListenPort(listenPort);
        factoryBean.setConfigs(properties);
        log.info("jobTracker 初始化");
        return factoryBean;
    }

}
