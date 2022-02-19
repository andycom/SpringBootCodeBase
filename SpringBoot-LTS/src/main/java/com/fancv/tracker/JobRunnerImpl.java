package com.fancv.tracker;

import com.github.ltsopensource.core.domain.Action;
import com.github.ltsopensource.core.logger.Logger;
import com.github.ltsopensource.core.logger.LoggerFactory;
import com.github.ltsopensource.spring.boot.annotation.JobRunner4TaskTracker;
import com.github.ltsopensource.tasktracker.Result;
import com.github.ltsopensource.tasktracker.logger.BizLogger;
import com.github.ltsopensource.tasktracker.runner.JobContext;
import com.github.ltsopensource.tasktracker.runner.JobRunner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Robert HG (254963746@qq.com) on 4/9/16.
 */
@Component
@Slf4j
public class JobRunnerImpl implements JobRunner {


    @Override
    public Result run(JobContext jobContext) throws Throwable {
        try {
            BizLogger bizLogger = jobContext.getBizLogger();

            // TODO 业务逻辑
            log.info("我要执行：" + jobContext);
            // 会发送到 LTS (JobTracker上)
            bizLogger.info("业务日志");
            log.info("cron 定时任务开始执行");

        } catch (Exception e) {
            log.info("Run job failed!", e);
            e.printStackTrace();
            return new Result(Action.EXECUTE_FAILED, e.getMessage());

        }
        return new Result(Action.EXECUTE_SUCCESS, "执行成功了，哈哈");
    }
}
