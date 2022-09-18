package com.fancv.shardingdemo.Monitor;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.spi.database.metadata.DataSourceMetaData;
import org.apache.shardingsphere.underlying.executor.hook.SQLExecutionHook;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 拦截sql语句进行分析
 *
 * @author lixu
 */
@Slf4j
public class SlowSqlMonitor implements SQLExecutionHook {

    private static final ThreadLocal<Context> CONTEXT = new ThreadLocal<>();

    /**
     * 单次sql执行的检测时间阀值
     */
    private long executionTime = 3 * 1000;

    @Override
    public void start(String s, String s1, List<Object> list, DataSourceMetaData dataSourceMetaData, boolean b, Map<String, Object> map) {
        try {
            //设置当前耗时
            CONTEXT.set(new Context(s, s1, list, System.currentTimeMillis()));
        } catch (Exception e) {
        }
    }

    @Override
    public void finishSuccess() {
        try {
            Context context = CONTEXT.get();
            long startTime = context.getStartTime();
            long endTime = System.currentTimeMillis();

            String sql = parse(context.getSql(), context.getParams());
            log.info(String.format("慢sql：耗时[%d]毫秒，当前sql[%s]", endTime - startTime, sql), new Throwable());
        } catch (Exception e) {
        } finally {
            CONTEXT.remove();
        }
    }

    @Override
    public void finishFailure(Exception cause) {
        try {
            log.info("异常sql：当前sql[{}]", cause.getMessage());
        } catch (Exception e) {
        } finally {
            CONTEXT.remove();
        }
    }

    private String parse(String sql, List params) {

        for (Object obj : params) {
            if (null == obj) {
                sql = sql.replaceFirst("[?]", "null");
            } else {
                sql = sql.replaceFirst("[?]", String.valueOf(obj));
            }
        }

        return sql;
    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    static class Context {

        /**
         * 对应的数据库
         */
        private String dataBase;
        /**
         * sql语句
         */
        private String sql;
        /**
         * 参数
         */
        private List params;
        /**
         * 耗时
         */
        private long startTime;
    }
}
