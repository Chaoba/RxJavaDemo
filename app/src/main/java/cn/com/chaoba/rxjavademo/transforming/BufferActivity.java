package cn.com.chaoba.rxjavademo.transforming;

import android.os.Bundle;
import android.view.View;

import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class BufferActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("buffer");
        mLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bufferObserver().subscribe(new Action1<List<Integer>>() {
                    @Override
                    public void call(List<Integer> integers) {
                        log("buffer:" + integers);
                    }
                });
            }
        });
        mRButton.setText("bufferTime");
        mRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bufferTimeObserver().subscribe(new Action1<List<Long>>() {
                    @Override
                    public void call(List<Long> longs) {
                        log("bufferTime:" + longs);
                    }
                });
            }
        });
    }

    private Observable<List<Integer>> bufferObserver() {
        return Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9)
                .buffer(2, 3);
    }

    private Observable<List<Long>> bufferTimeObserver() {
        return Observable.interval(1, TimeUnit.SECONDS)
                .buffer(3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread());
    }

}
