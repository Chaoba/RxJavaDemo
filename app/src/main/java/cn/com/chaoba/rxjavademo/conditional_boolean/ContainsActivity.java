package cn.com.chaoba.rxjavademo.conditional_boolean;

import android.os.Bundle;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.Subscriber;

public class ContainsActivity extends BaseActivity {
    boolean tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("contains");
        mLButton.setOnClickListener(e -> containsObserver().subscribe(i -> log("contains:" + i)));
        mRButton.setText("isEmpty");
        mRButton.setOnClickListener(e -> defaultObserver().subscribe(i -> log("isEmpty:" + i)));
    }

    private Observable<Boolean> containsObserver() {
        if (tag) {
            return Observable.just(1, 2, 3).contains(3);
        }
        tag = true;
        return Observable.just(1, 2, 3).contains(4);
    }

    private Observable<Boolean> defaultObserver() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onCompleted();
            }
        }).isEmpty();
    }
}


