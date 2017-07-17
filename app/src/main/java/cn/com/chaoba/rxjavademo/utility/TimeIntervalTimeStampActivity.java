package cn.com.chaoba.rxjavademo.utility;

import android.os.Bundle;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.schedulers.TimeInterval;
import rx.schedulers.Timestamped;

public class TimeIntervalTimeStampActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("timeInterval");
        mLButton.setOnClickListener(e -> {
            timeIntervalObserver().subscribe(new Action1<TimeInterval<Integer>>() {
                @Override
                public void call(TimeInterval<Integer> i) {
                    log("timeInterval:" + i.getValue() + "-" + i.getIntervalInMilliseconds());
                }
            });
        });
        mRButton.setText("timeStamp");
        mRButton.setOnClickListener(e -> {
            timeStampObserver().subscribe(new Action1<Timestamped<Integer>>() {
                @Override
                public void call(Timestamped<Integer> i) {
                    log("timeStamp:" + i.getValue() + "-" + i.getTimestampMillis());
                }
            });
        });
    }

    private Observable<TimeInterval<Integer>> timeIntervalObserver() {
        return createObserver().timeInterval();
    }

    private Observable<Timestamped<Integer>> timeStampObserver() {
        return createObserver().timestamp();
    }

    private Observable<Integer> createObserver() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i <= 3; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.newThread());
    }
}
