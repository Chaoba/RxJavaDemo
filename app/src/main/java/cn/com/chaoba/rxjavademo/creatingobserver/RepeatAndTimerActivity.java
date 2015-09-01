package cn.com.chaoba.rxjavademo.creatingobserver;

import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;


public class RepeatAndTimerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("repeat");
        mLButton.setOnClickListener(e -> repeatObserver().subscribe(i -> log("repeat:" + i)));
        mRButton.setText("timer");
        mRButton.setOnClickListener(e -> timerObserver().subscribe(i -> log("timer:" + i)));
    }

    private Observable<Integer> repeatObserver() {
        return Observable.just(1).repeat(5);
    }

    private Observable<Long> timerObserver() {
        //timer by default operates on the computation Scheduler
        return Observable.timer(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread());
    }


}
