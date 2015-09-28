package cn.com.chaoba.rxjavademo.conditional_boolean;

import android.os.Bundle;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.Subscriber;

public class DefaultIfEmptyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("empty");
        mLButton.setOnClickListener(e -> emptyObserver().subscribe(i -> log("empty:" + i)));
        mRButton.setText("notEmpty");
        mRButton.setOnClickListener(e -> notEmptyObserver().subscribe(i -> log("notEmpty:" + i)));
    }

    private Observable<Integer> emptyObserver() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onCompleted();
            }
        }).defaultIfEmpty(10);
    }

    private Observable<Integer> notEmptyObserver() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(1);
                subscriber.onCompleted();
            }
        }).defaultIfEmpty(10);
    }
}


