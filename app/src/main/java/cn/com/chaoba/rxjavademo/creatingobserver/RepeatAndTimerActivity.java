package cn.com.chaoba.rxjavademo.creatingobserver;

import android.os.Bundle;
import android.view.View;

import java.util.concurrent.TimeUnit;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


public class RepeatAndTimerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("repeat");
        mLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeatObserver().subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer i) {
                        log("repeat:" + i);
                    }
                });
            }
        });
        mRButton.setText("timer");
        mRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timerObserver().subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        log("timer:" + aLong);
                    }
                });
            }
        });
    }

    private Observable<Integer> repeatObserver() {
        return Observable.just(1,2,3).repeat(3);
    }

    private Observable<Long> timerObserver() {
        //timer by default operates on the computation Scheduler
        return Observable.timer(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread());
    }


}
