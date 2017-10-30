package cn.com.chaoba.rxjavademo.transforming;

import android.os.Bundle;
import android.view.View;

import java.util.concurrent.TimeUnit;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.functions.Action1;

public class WindowActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("window");
        mLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                windowCountObserver()
                        .compose(bindToLifecycle()).subscribe(new Action1<Observable<Integer>>() {
                            @Override
                            public void call(Observable<Integer> i) {
                                log(i.getClass().getName());
                                i.compose(bindToLifecycle()).subscribe(new Action1<Integer>() {
                                    @Override
                                    public void call(Integer j) {
                                        log("window:" + j);
                                    }
                                });
                            }
                        });
            }
        });
        mRButton.setText("Time");
        mRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                windowTimeObserver().compose(bindToLifecycle()).subscribe(new Action1<Observable<Long>>() {
                    @Override
                    public void call(Observable<Long> i) {
                        log(System.currentTimeMillis() / 1000);
                        i.compose(bindToLifecycle()).subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long j) {
                                log("windowTime:" + j);
                            }
                        });
                    }
                });
            }
        });
    }

    private Observable<Observable<Integer>> windowCountObserver() {
        return Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9).window(3);
    }

    private Observable<Observable<Long>> windowTimeObserver() {
        return Observable.interval(1000, TimeUnit.MILLISECONDS)
                .window(3000, TimeUnit.MILLISECONDS);
    }


}