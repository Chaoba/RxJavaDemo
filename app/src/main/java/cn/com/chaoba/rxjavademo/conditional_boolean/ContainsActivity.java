package cn.com.chaoba.rxjavademo.conditional_boolean;

import android.os.Bundle;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

public class ContainsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("contains");
        mLButton.setOnClickListener(e -> {
            containsObserver().compose(bindToLifecycle()).subscribe(new Action1<Boolean>() {
                @Override
                public void call(Boolean i) {
                    log("contains:" + i);
                }
            });
            notContainsObserver().compose(bindToLifecycle()).subscribe(new Action1<Boolean>() {
                @Override
                public void call(Boolean i) {
                    log("not contains:" + i);
                }
            });
        });
        mRButton.setText("isEmpty");
        mRButton.setOnClickListener(e -> {
            emptyObserver().compose(bindToLifecycle()).subscribe(new Action1<Boolean>() {
                @Override
                public void call(Boolean i) {
                    log("isEmpty:" + i);
                }
            });
        });
    }

    private Observable<Boolean> containsObserver() {
        return Observable.just(1, 2, 3).contains(3);
    }

    private Observable<Boolean> notContainsObserver() {
        return Observable.just(1, 2, 3).contains(4);
    }

    private Observable<Boolean> emptyObserver() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onCompleted();
            }
        }).isEmpty();
    }
}


