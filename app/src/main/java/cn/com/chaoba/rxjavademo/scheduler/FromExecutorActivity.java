package cn.com.chaoba.rxjavademo.scheduler;

import android.os.Bundle;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.Scheduler;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class FromExecutorActivity extends BaseActivity {

    class SimpleThreadFactory implements ThreadFactory {
        public Thread newThread(Runnable r) {
            return new Thread(r);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Executor executor = new ThreadPoolExecutor(
                2,  //corePoolSize
                2,  //maximumPoolSize
                2000L, TimeUnit.MILLISECONDS, //keepAliveTime, unit
                new LinkedBlockingQueue<>(1000),  //workQueue
                new SimpleThreadFactory()
        );
        Scheduler scheduler = Schedulers.from(executor);
        Observable.interval(1, TimeUnit.SECONDS).take(5)
                .observeOn(scheduler)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long l) {
                        log(l + "-" + Thread.currentThread().getName());
                    }
                });

    }
}