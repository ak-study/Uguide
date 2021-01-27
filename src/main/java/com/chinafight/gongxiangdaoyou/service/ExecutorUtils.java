package com.chinafight.gongxiangdaoyou.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: ak
 * @date: 2020/11/16 16:51
 * @description:
 */
@Slf4j
public class ExecutorUtils {
    private static ThreadPoolExecutor service;
    private static final ReentrantLock LOCK = new ReentrantLock();
    private static final Condition CONDITION = LOCK.newCondition();
    /**
     * 线程池信息
     */
    private static final Long MAX_WAITING_TIME = 2000L;
    private static final Integer POOL_SIZE = 4;
    private static final Integer MAX_POOL_SIZE = 4;
    private static final Integer ALIVE_TIME = 0;

    static class MyThreadFactory implements ThreadFactory {
        private static final AtomicInteger THREAD_ID = new AtomicInteger();
        private static final String POLL_NAME_PREFIX = "Common-ThreadPool-";

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setName(POLL_NAME_PREFIX + THREAD_ID.getAndIncrement());
            log.info("创建线程{}", thread.getName());
            //设置异常处理器
            thread.setUncaughtExceptionHandler(new MyExceptionHandler());
            return thread;
        }
    }

    static class MyExceptionHandler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            log.error("线程{}内发生异常异常:\n", t.getName());
            e.printStackTrace();
        }
    }


    /**
     * 进入等待队列
     */
    public static void startWaiting() {
        try {
            LOCK.tryLock(MAX_WAITING_TIME, TimeUnit.SECONDS);
        } catch (InterruptedException interruptedException) {
            log.error(interruptedException.getMessage());
        }
        try {
            CONDITION.wait();
        } catch (Exception e) {
            log.error("beginWait发生错误", e);
        } finally {
            LOCK.unlock();
        }
    }

    /**
     * 唤醒等待队列的线程
     */
    public static void notifyWaitingThread() {
        try {
            LOCK.tryLock(MAX_WAITING_TIME, TimeUnit.SECONDS);
        } catch (InterruptedException interruptedException) {
            log.error(interruptedException.getMessage());
        }
        try {
            CONDITION.signalAll();
        } catch (Exception e) {
            log.error("notifyWaitingThread发生错误", e);
        } finally {
            LOCK.unlock();
        }
    }

    /**
     * 单任务执行
     */
    public static void execute(Runnable runnable) {
        new Thread(runnable).start();
    }

    /**
     * 异步执行任务列表
     */
    public static void asyncExecuteTasks(List<Runnable> runnableList) {
        long start = System.currentTimeMillis();
        CountDownLatch latch = new CountDownLatch(runnableList.size());
        buildThreadPool();
        for (Runnable runnable : runnableList) {
            service.execute(() -> {
                try {
                    runnable.run();
                } finally {
                    latch.countDown();
                    if (latch.getCount() == 0) {
                        log.info("任务耗时: {}秒", (System.currentTimeMillis() - start) / 1000d);
                        service.shutdown();
                    }
                }
            });
        }

    }

    /**
     * 同步执行任务列表
     *
     * @param runnableList 任务列表
     * @param lastTask     最后一个任务
     * @param lock         锁
     */
    public static void syncExecuteTasks(List<Runnable> runnableList, Runnable lastTask, Lock lock) {
        ArrayList<Runnable> targetTasks = new ArrayList<>();
        for (Runnable runnable : runnableList) {
            Runnable task = () -> {
                lock.lock();
                try {
                    runnable.run();
                } finally {
                    lock.unlock();
                }
            };
            targetTasks.add(task);
        }
        asyncExecuteTasks(targetTasks, lastTask);
    }

    /**
     * 主线程将等待所有线程执行完毕才会继续执行
     *
     * @param runnableList 任务列表
     * @param lastTask     最后一个任务
     */
    @SneakyThrows
    public static void asyncExecuteTasks(List<Runnable> runnableList, Runnable lastTask) {
        long start = System.currentTimeMillis();
        buildThreadPool();
        CountDownLatch latch = new CountDownLatch(runnableList.size());
        for (Runnable runnable : runnableList) {
            service.execute(() -> {
                try {
                    runnable.run();
                } finally {
                    latch.countDown();
                }
            });
        }
        //主线程等待所有任务执行完成
        latch.await();
        if (lastTask != null) {
            service.execute(lastTask);
        }
        log.info("任务耗时: {}秒", (System.currentTimeMillis() - start) / 1000d);
        service.shutdown();
    }

    /**
     * 带返回值的任务执行
     *
     * @param callableList 任务列表
     * @param lastTask     最后一个任务
     */
    @SneakyThrows
    public static void asyncExecuteTasks(List<Callable<Object>> callableList, Callable<Object> lastTask) {
        long start = System.currentTimeMillis();
        buildThreadPool();
        List<Future<Object>> futures = service.invokeAll(callableList);
        for (Future<Object> future : futures) {
            future.get();
        }
        if (lastTask != null) {
            Future<Object> future = service.submit(lastTask);
            log.info("执行结果{}", future.get());
        }
        log.info("任务耗时: {}秒", (System.currentTimeMillis() - start) / 1000d);
        service.shutdown();
    }

    /**
     * 最后一个任务延时后让主线程自己执行
     *
     * @param runnableList 任务集合
     * @param lastTask     最后一个任务
     * @param delay        延时时间
     * @param timeUnit     延时时间单位
     */
    @SneakyThrows
    public static void asyncExecuteTasks(List<Runnable> runnableList, Runnable lastTask, Long delay, TimeUnit timeUnit) {
        asyncExecuteTasks(runnableList, null);
        timeUnit.sleep(delay);
        lastTask.run();
    }

    private static void buildThreadPool() {
        if (service == null || service.isShutdown()) {
            service = new ThreadPoolExecutor(POOL_SIZE, MAX_POOL_SIZE, ALIVE_TIME,
                    TimeUnit.SECONDS, new LinkedBlockingQueue<>(),
                    new MyThreadFactory());
            service.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        }
    }


}
