package com.kobeszu.alert.current;

import com.kobeszu.alert.constant.RobotConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * @author kobeszu@163.com
 * @date 2021-04-21 16:34
 */
public class RobotAlarmThreadPool {

    private static final Logger logger = LoggerFactory.getLogger(RobotAlarmThreadPool.class);

    private final String POOL_NAME = "robot_alarm_task";

    private static final int CPU_SIZE = Runtime.getRuntime().availableProcessors();

    private ThreadPoolExecutor pool;

    private RobotAlarmThreadPool() {
        pool = new ThreadPoolExecutor( CPU_SIZE * 4,  CPU_SIZE * 4, 0, TimeUnit.SECONDS,
                new LinkedBlockingQueue(Integer.MAX_VALUE / 10), new NamedThreadFactory(POOL_NAME), new MyAbortPolicy(POOL_NAME));
    }

    /**
     * 类级的内部类，也就是静态的成员式内部类，该内部类的实例与外部类的实例
     * 没有绑定关系，而且只有被调用到才会装载，从而实现了延迟加载
     */
    private static class ThreadPoolSingleHolder {
        private static RobotAlarmThreadPool alarmThreadPool = new RobotAlarmThreadPool();
    }


    /**
     * 静态初始化器，由JVM来保证线程安全
     */
    public static ThreadPoolExecutor getPool() {
        return ThreadPoolSingleHolder.alarmThreadPool.pool;
    }

    public static class MyAbortPolicy implements RejectedExecutionHandler {
        private String poolName;

        public MyAbortPolicy(String poolName) {
            this.poolName = poolName;
        }

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            logger.warn("[{}][{}][队列溢出，执行拒绝策略]Task error:{}", RobotConstant.ERROR_CONTEXT, poolName, e.toString(), e);
        }
    }

}
