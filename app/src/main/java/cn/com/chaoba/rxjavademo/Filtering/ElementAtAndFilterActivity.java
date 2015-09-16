package cn.com.chaoba.rxjavademo.filtering;

import android.os.Bundle;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;

public class ElementAtAndFilterActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("elementAt");
        mLButton.setOnClickListener(e -> elementAtObserver().subscribe(i -> log("elementAt:" + i)));
        mRButton.setText("Filter");
        mRButton.setOnClickListener(e -> filterObserver().subscribe(i -> log("Filter:" + i)));
    }

    private Observable<Integer> elementAtObserver() {
        return Observable.just(0, 1, 2, 3, 4, 5).elementAt(2);
    }

    private Observable<Integer> filterObserver() {
        return Observable.just(0, 1, 2, 3, 4, 5).filter(i -> i < 3);
    }


}
