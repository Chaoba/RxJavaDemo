package cn.com.chaoba.rxjavademo.conditional_boolean;

import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;

public class TakeUntilTakeWhileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("takeUntil");
        mLButton.setOnClickListener(e -> takeUntilObserver().subscribe(i -> log("takeUntil:" + i)));
        mRButton.setText("takeWhile");
        mRButton.setOnClickListener(e -> takeWhileObserver().subscribe(i -> log("takeWhile:" + i)));
    }

    private Observable<Long> takeUntilObserver() {
        return Observable.interval(1, TimeUnit.SECONDS).takeUntil(Observable.timer(3, TimeUnit.SECONDS));
    }

    private Observable<Long> takeWhileObserver() {
        return Observable.interval(1, TimeUnit.SECONDS).takeWhile(aLong -> aLong < 5);
    }
}


