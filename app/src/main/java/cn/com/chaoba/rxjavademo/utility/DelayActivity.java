package cn.com.chaoba.rxjavademo.utility;

import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class DelayActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("delay");
        mLButton.setOnClickListener(e -> {
            log("start subscrib:" + getCurrentTime());
            delayObserver().subscribe(i -> log("delay:" + (getCurrentTime() - i)));
        });
        mRButton.setText("delaySubscription");
        mRButton.setOnClickListener(e -> {
            log("start subscrib:" + getCurrentTime());
            delaySubscriptionObserver().subscribe(i -> log("delaySubscription:" + i));
        });
    }

    private Observable<Long> delayObserver() {
        return createObserver(2).delay(2000, TimeUnit.MILLISECONDS);
    }

    private Observable<Long> delaySubscriptionObserver() {
        return createObserver(2).delaySubscription(2000, TimeUnit.MILLISECONDS);
    }

    private Observable<Long> createObserver(int index) {
        return Observable.create(new Observable.OnSubscribe<Long>() {
            @Override
            public void call(Subscriber<? super Long> subscriber) {
                log("subscrib:" + getCurrentTime());
                for (int i = 1; i <= index; i++) {
                    subscriber.onNext(getCurrentTime());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).subscribeOn(Schedulers.newThread());
    }

    private long getCurrentTime() {
        return System.currentTimeMillis()/1000;
    }

}
