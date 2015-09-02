package cn.com.chaoba.rxjavademo.transforming;

import android.os.Bundle;

import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class BufferActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("buffer");
        mLButton.setOnClickListener(e -> bufferObserver().subscribe(i -> log("buffer:" + i)));
        mRButton.setText("bufferTime");
        mRButton.setOnClickListener(e -> bufferTimeObserver().subscribe(i -> log("bufferTime:" + i)));
    }

    private Observable<List<Integer>> bufferObserver() {
        return Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9).buffer(2, 3);
    }

    private Observable<List<Long>> bufferTimeObserver() {
        return Observable.interval(1, TimeUnit.SECONDS).buffer(3, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread());
    }

}
