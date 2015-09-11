package cn.com.chaoba.rxjavademo.transforming;

import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class WindowActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("window");
        mLButton.setOnClickListener(e -> windowCountObserver().subscribe(i -> {
            log(i);
            i.subscribe((j -> log("window:" + j)));
        }));
        mRButton.setText("Time");
        mRButton.setOnClickListener(e -> wondowTimeObserver().subscribe(i -> {
            log(i);
            i.observeOn(AndroidSchedulers.mainThread()).subscribe((j -> log("wondowTime:" + j)));
        }));
    }

    private Observable<Observable<Integer>> windowCountObserver() {
        return Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9).window(3);
    }

    private Observable<Observable<Long>> wondowTimeObserver() {
        return Observable.interval(1000, TimeUnit.MILLISECONDS)
                .window(3000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread());
    }


}