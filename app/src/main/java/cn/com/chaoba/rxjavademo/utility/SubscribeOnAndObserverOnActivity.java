package cn.com.chaoba.rxjavademo.utility;

import android.os.Bundle;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SubscribeOnAndObserverOnActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("observerOn");
        mLButton.setOnClickListener(e -> observerOnObserver().subscribe(i -> log("observerOn:" + Thread.currentThread().getName())));
        mRButton.setText("subscribeOn");
        mRButton.setOnClickListener(e -> subscribeOnObserver().subscribe(i -> log("subscribeOn:" + Thread.currentThread().getName())));
    }

    private Observable<Integer> observerOnObserver() {
        return createObserver()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread());
    }

    private Observable<Integer> subscribeOnObserver() {
        return createObserver()
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.immediate());
    }

    private Observable<Integer> createObserver() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                log("on subscrib:" + Thread.currentThread().getName());
                subscriber.onNext(1);
                subscriber.onCompleted();
            }
        });
    }
}
