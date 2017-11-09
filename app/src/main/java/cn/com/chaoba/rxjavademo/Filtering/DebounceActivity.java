package cn.com.chaoba.rxjavademo.filtering;

import android.os.Bundle;
import android.view.View;

import java.util.concurrent.TimeUnit;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class DebounceActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("throttleWithTimeout");
        mLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                throttleWithTimeoutObserver().compose(bindToLifecycle()).subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer i) {
                        log("throttleWithTimeout:" + i);
                    }
                });
            }
        });

        mRButton.setText("debounce");
        mRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                debounceObserver().compose(bindToLifecycle()).subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long i) {
                        log("debounce:" + i);
                    }
                });
            }
        });


    }

    private Observable<Long> debounceObserver() {
        return Observable.interval(1000, TimeUnit.MILLISECONDS)
                .debounce(new Func1<Long, Observable<Long>>() {
                    @Override
                    public Observable<Long> call(Long aLong) {
                        return Observable.timer(aLong % 2 * 1500,
                                TimeUnit.MILLISECONDS);
                    }
                });
    }

    private Observable<Integer> throttleWithTimeoutObserver() {
        return createObserver().throttleWithTimeout(200, TimeUnit.MILLISECONDS);

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