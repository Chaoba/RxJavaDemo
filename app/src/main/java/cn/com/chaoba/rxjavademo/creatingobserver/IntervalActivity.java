package cn.com.chaoba.rxjavademo.creatingobserver;

import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class IntervalActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Observable<Long> observable = interval();

        Subscriber<Long> subscriber = new Subscriber<Long>() {
            @Override
            public void onCompleted() {
                log("onCompleted" );
            }

            @Override
            public void onError(Throwable e) {
                log("onError:" + e.getMessage());
            }

            @Override
            public void onNext(Long aLong) {
                log("interval:" + aLong);
            }

        };
        mLButton.setText("Interval");
        mRButton.setText("UnSubsCribe");
        mLButton.setOnClickListener(e -> observable.subscribe(subscriber));
        mRButton.setOnClickListener(e -> subscriber.unsubscribe());
    }

    private Observable<Long> interval() {
        return Observable.interval(1, TimeUnit.SECONDS)
        //interva operates by default on the computation Scheduler,so observe on main Thread
        .observeOn(AndroidSchedulers.mainThread());
    }


}
