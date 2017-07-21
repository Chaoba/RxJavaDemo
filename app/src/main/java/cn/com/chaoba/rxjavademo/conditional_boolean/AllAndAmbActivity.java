package cn.com.chaoba.rxjavademo.conditional_boolean;

import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class AllAndAmbActivity extends BaseActivity {
    boolean tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("all");
        mLButton.setOnClickListener(e -> {
            allObserver().subscribe(new Action1<Boolean>() {
                @Override
                public void call(Boolean aBoolean) {
                    log("all:" + aBoolean);
                }
            });
            notAllObserver().subscribe(new Action1<Boolean>() {
                @Override
                public void call(Boolean aBoolean) {
                    log("not all:" + aBoolean);
                }
            });
        });
        mRButton.setText("amb");
        mRButton.setOnClickListener(e -> {
            ambObserver().subscribe(new Action1<Integer>() {
                @Override
                public void call(Integer i) {
                    log("amb:" + i);
                }
            });
        });
    }

    private Observable<Boolean> allObserver() {
        Observable<Integer> just;
        just = Observable.just(1, 2, 3, 4, 5);
        return just.all(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return integer < 6;
            }
        });
    }

    private Observable<Boolean> notAllObserver() {
        Observable<Integer> just;
        just = Observable.just(1, 2, 3, 4, 5, 6);
        return just.all(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return integer < 6;
            }
        });
    }

    private Observable<Integer> ambObserver() {
        Observable<Integer> delay3 = Observable.just(1, 2, 3).delay(3000, TimeUnit.MILLISECONDS);
        Observable<Integer> delay2 = Observable.just(4, 5, 6).delay(2000, TimeUnit.MILLISECONDS);
        Observable<Integer> delay1 = Observable.just(7, 8, 9).delay(1000, TimeUnit.MILLISECONDS);
        return Observable.amb(delay1, delay2, delay3);
    }
}


