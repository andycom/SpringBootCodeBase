package com.fancv.tracker;

import com.github.ltsopensource.core.domain.Action;
import com.github.ltsopensource.tasktracker.Result;
import com.github.ltsopensource.tasktracker.logger.BizLogger;
import com.github.ltsopensource.tasktracker.runner.JobContext;
import com.github.ltsopensource.tasktracker.runner.JobRunner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 触发立刻执行的任务 或者定点时间执行的任务
 */
@Component
@Slf4j
public class TimerJobRunnerImpl implements JobRunner {


    @Override
    public Result run(JobContext jobContext) throws Throwable {
        try {
            BizLogger bizLogger = jobContext.getBizLogger();

            // TODO 业务逻辑
            log.info("我要执行：" + jobContext);
            // 会发送到 LTS (JobTracker上)
            bizLogger.info("业务日志");
            log.info("定时任务开始执行");

        } catch (Exception e) {
            log.info("Run job failed!", e);
            e.printStackTrace();
            return new Result(Action.EXECUTE_FAILED, e.getMessage());

        }
        return new Result(Action.EXECUTE_SUCCESS, "执行成功了，哈哈");
    }
}
