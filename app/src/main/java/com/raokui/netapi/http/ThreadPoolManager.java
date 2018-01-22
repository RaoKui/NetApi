package com.raokui.netapi.http;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by 饶魁 on 2018/1/22.
 */

public class ThreadPoolManager {

    private static ThreadPoolManager instance = new ThreadPoolManager();

    public static ThreadPoolManager getInstance() {
        return instance;
    }


    // 把任务加入请求队列
    private LinkedBlockingQueue<Runnable> mQueue = new LinkedBlockingQueue<>();

    public void execute(Runnable runnable) {
        if (runnable != null) {
            try {
                mQueue.put(runnable);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    // 把队列中的任务放到线程池中
    //TODO 自定义线程池
    private ThreadPoolExecutor mThreadPoolExecutor;

    private ThreadPoolManager() {
        mThreadPoolExecutor = new ThreadPoolExecutor(4, 20, 15,
                TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(4), mRejectedExecutionHandler);
        mThreadPoolExecutor.execute(mRunnable);
    }

    // 拒绝策略

    private RejectedExecutionHandler mRejectedExecutionHandler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            // r：超时的线程
            //TODO 任务加个计时器，超过多少就不放进去
            try {
                mQueue.put(r);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };


    // 自动开始任务
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            while (true) {// 阻塞队列，不存在性能损耗
                Runnable runnable = null;
                // 从队列中取出请求
                try {
                    runnable = mQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (runnable != null) {
                    runnable.run();
                }

            }
        }
    };

}
