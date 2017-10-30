package cn.com.chaoba.rxjavademo.combining;

import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class StartWithAndSwitchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("StartWith");
        mLButton.setOnClickListener(e -> {
            startWithObserver().compose(bindToLifecycle()).subscribe(new Action1<Integer>() {
                @Override
                public void call(Integer i) {
                    log("StartWith:" + i);
                }
            });
        });
        mRButton.setText("switch");
        mRButton.setOnClickListener(e -> {
            switchObserver().compose(bindToLifecycle()).subscribe(new Action1<String>() {
                @Override
                public void call(String s) {
                    log("switch:" + s);
                }
            });
        });
    }

    private Observable<Integer> startWithObserver() {
        return Observable.just(1, 2, 3).startWith(-1, 0);
    }

    private Observable<String> createObserver(Long index) {
        return Observable.interval(1000, 1000, TimeUnit.MILLISECONDS).take(5)
                .map(new Func1<Long, String>() {
                    @Override
                    public String call(Long aLong) {
                        return index + "-" + aLong;
                    }
                });
    }

    private Observable<String> switchObserver() {
        return Observable.switchOnNext(Observable
                .interval(3000, TimeUnit.MILLISECONDS)
                .take(3)
                .map(new Func1<Long, Observable<String>>() {
                    @Override
                    public Observable<String> call(Long aLong) {
                        return createObserver(aLong);
                    }
                }));
    }


}
