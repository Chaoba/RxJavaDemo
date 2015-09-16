package cn.com.chaoba.rxjavademo.filtering;

import android.os.Bundle;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;

public class SkipAndTakeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("Skip");
        mLButton.setOnClickListener(e -> skipObserver().subscribe(i -> log("Skip:" + i)));
        mRButton.setText("Take");
        mRButton.setOnClickListener(e -> takeObserver().subscribe(i -> log("Take:" + i)));
    }

    private Observable<Integer> skipObserver() {
        return Observable.just(0, 1, 2, 3, 4, 5).skip(2);
    }
    private Observable<Integer> takeObserver() {
        return Observable.just(0, 1, 2, 3, 4, 5).take(2);
    }


}
