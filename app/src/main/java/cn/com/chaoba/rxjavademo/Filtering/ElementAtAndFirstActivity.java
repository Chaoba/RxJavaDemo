package cn.com.chaoba.rxjavademo.Filtering;

import android.os.Bundle;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;

public class ElementAtAndFirstActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("elementAt");
        mLButton.setOnClickListener(e -> elementAtObserver().subscribe(i -> log("elementAt:" + i)));
        mRButton.setText("first");
        mRButton.setOnClickListener(e -> FirstdObserver().subscribe(i -> log("first:" + i)));
    }

    private Observable<Integer> elementAtObserver() {
        return Observable.just(0, 1, 2, 3, 4, 5).elementAt(2);
    }

    private Observable<Integer> FirstdObserver() {
        return Observable.just(0, 1, 2, 3, 4, 5).first();
    }


}
