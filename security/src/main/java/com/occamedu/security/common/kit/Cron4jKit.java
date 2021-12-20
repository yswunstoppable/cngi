package com.occamedu.security.common.kit;

import it.sauronsoftware.cron4j.Scheduler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author fye
 * @email fh941005@163.com
 * @date 2020-06-10 00:09
 * @description
 */
public class Cron4jKit {
    private static final Map<String, Scheduler> MAPCP = new ConcurrentHashMap<>();

    /**
     * @param configName 任务名称
     * @param cron       表达式调试任务
     * @param daemon     表示调试线程是否设置为守护线程，默认值为 true，守护线程会在 tomcat 关闭时自动关闭
     * @param task       执行的任务
     */
    public static void put(String configName, String cron, boolean daemon, Runnable task) {
        Scheduler scheduler = new Scheduler();
        if (task == null) {
            throw new IllegalStateException("Task 不能为空");
        }
        scheduler.schedule(cron, task);
        scheduler.setDaemon(daemon);
        MAPCP.put(configName, scheduler);
        start(configName);
    }

    private static Scheduler get(String configName) {
        return MAPCP.get(configName);
    }

    public static void start(String configName) {
        final Scheduler scheduler = get(configName);
        if (scheduler != null && !scheduler.isStarted()) {
            scheduler.start();
        }
    }

    public static void start() {
        for (final String configName : MAPCP.keySet()) {
            start(configName);
        }
    }

    public static void stop(String configName) {
        final Scheduler scheduler = get(configName);
        if (scheduler != null && scheduler.isStarted()) {
            scheduler.stop();
        }
    }

    public static void stop() {
        for (final String configName : MAPCP.keySet()) {
            stop(configName);
        }
    }

    public static void remove(String configName) {
        stop(configName);
        MAPCP.remove(configName);
    }

    public static void remove() {
        stop();
        MAPCP.clear();
    }
}
