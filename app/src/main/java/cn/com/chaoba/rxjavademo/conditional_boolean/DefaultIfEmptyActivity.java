package cn.com.chaoba.rxjavademo.conditional_boolean;

import android.os.Bundle;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

public class DefaultIfEmptyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("empty");
        mLButton.setOnClickListener(e -> {
            emptyObserver().compose(bindToLifecycle()).subscribe(new Action1<Integer>() {
                @Override
                public void call(Integer i) {
                    log("empty:" + i);
                }
            });
        });
        mRButton.setText("notEmpty");
        mRButton.setOnClickListener(e -> {
            notEmptyObserver().compose(bindToLifecycle()).subscribe(new Action1<Integer>() {
                @Override
                public void call(Integer i) {
                    log("notEmpty:" + i);
                }
            });
        });
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
        return Observable.just(1).defaultIfEmpty(10);
    }
}


