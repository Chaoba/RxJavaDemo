package cn.com.chaoba.rxjavademo.conditional_boolean;

import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class TakeUntilTakeWhileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("takeUntil");
        mLButton.setOnClickListener(e -> {
            takeUntilObserver().subscribe(new Action1<Long>() {
                @Override
                public void call(Long i) {
                    log("takeUntil:" + i);
                }
            });
        });
        mRButton.setText("takeWhile");
        mRButton.setOnClickListener(e -> {
            takeWhileObserver().subscribe(new Action1<Long>() {
                @Override
                public void call(Long i) {
                    log("takeWhile:" + i);
                }
            });
        });
    }

    private Observable<Long> takeUntilObserver() {
        return Observable.interval(1, TimeUnit.SECONDS)
                .takeUntil(Observable.timer(3, TimeUnit.SECONDS));
    }

    private Observable<Long> takeWhileObserver() {
        return Observable.interval(1, TimeUnit.SECONDS)
                .takeWhile(new Func1<Long, Boolean>() {
                    @Override
                    public Boolean call(Long aLong) {
                        return aLong < 5;
                    }
                });
    }
}


