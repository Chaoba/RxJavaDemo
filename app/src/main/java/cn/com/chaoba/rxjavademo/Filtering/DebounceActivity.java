package cn.com.chaoba.rxjavademo.filtering;

import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DebounceActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("throttleWithTimeout");
        mLButton.setOnClickListener(e -> throttleWithTimeoutObserver().subscribe(i -> log("throttleWithTimeout:" + i)));
        mRButton.setText("debounce");
        mRButton.setOnClickListener(e -> debounceObserver().subscribe(i -> log("debounce:" + i)));
    }

    private Observable<Integer> debounceObserver() {
        return Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9).debounce(integer -> {
            log(integer);
            return Observable.create(new Observable.OnSubscribe<Integer>() {
                @Override
                public void call(Subscriber<? super Integer> subscriber) {
                    if (integer % 2 == 0 && !subscriber.isUnsubscribed()) {
                        log("complete:" + integer);
                        subscriber.onCompleted();
                    }
                }
            });
        })
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<Integer> throttleWithTimeoutObserver() {
        return createObserver().throttleWithTimeout(200, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread());

    }

    private Observable<Integer> createObserver() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 10; i++) {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(i);
                    }
                    int sleep = 100;
                    if (i % 3 == 0) {
                        sleep = 300;
                    }
                    try {
                        Thread.sleep(sleep);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.computation());
    }
}