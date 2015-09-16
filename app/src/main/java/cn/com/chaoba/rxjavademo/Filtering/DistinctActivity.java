package cn.com.chaoba.rxjavademo.filtering;

import android.os.Bundle;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;

public class DistinctActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("distinct");
        mLButton.setOnClickListener(e -> distinctObserver().subscribe(i -> log("distinct:" + i)));
        mRButton.setText("UntilChanged");
        mRButton.setOnClickListener(e -> distinctUntilChangedObserver().subscribe(i -> log("UntilChanged:" + i)));
    }

    private Observable<Integer> distinctObserver() {
        return Observable.just(1, 2, 3, 4, 5, 4, 3, 2, 1).distinct();

    }

    private Observable<Integer> distinctUntilChangedObserver() {
        return Observable.just(1, 2, 3, 3, 3, 1, 2, 3, 3).distinctUntilChanged();

    }


}
