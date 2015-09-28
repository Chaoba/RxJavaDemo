package cn.com.chaoba.rxjavademo.conditional_boolean;

import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;

public class AllAndAmbActivity extends BaseActivity {
    boolean tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("all");
        mLButton.setOnClickListener(e -> allObserver().subscribe(i -> log("all:" + i)));
        mRButton.setText("amb");
        mRButton.setOnClickListener(e -> ambObserver().subscribe(i -> log("amb:" + i)));
    }

    private Observable<Boolean> allObserver() {
        Observable<Integer> just;
        if (tag) {
            just = Observable.just(1, 2, 3, 4, 5);
        } else {
            just = Observable.just(1, 2, 3, 4, 5, 6);
        }
        tag = true;
        return just.all(integer -> integer < 6);
    }

    private Observable<Integer> ambObserver() {
        Observable<Integer> delay3 = Observable.just(1, 2, 3).delay(3000, TimeUnit.MILLISECONDS);
        Observable<Integer> delay2 = Observable.just(4, 5, 6).delay(2000, TimeUnit.MILLISECONDS);
        Observable<Integer> delay1 = Observable.just(7, 8, 9).delay(1000, TimeUnit.MILLISECONDS);
        return Observable.amb(delay1, delay2, delay3);
    }
}


