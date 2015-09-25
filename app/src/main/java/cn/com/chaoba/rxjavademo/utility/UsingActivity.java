package cn.com.chaoba.rxjavademo.utility;

import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.Subscriber;

public class UsingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Observable<Long> observable = usingObserver();
        Subscriber subscriber = new Subscriber() {
            @Override
            public void onCompleted() {
                log("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                log("onError");
            }

            @Override
            public void onNext(Object o) {
                log("onNext"+o);
            }
        };
        mLButton.setText("using");
        mLButton.setOnClickListener(e -> observable.subscribe(subscriber));
        mRButton.setText("unSubscrib");
        mRButton.setOnClickListener(e -> subscriber.unsubscribe());
    }

    private Observable<Long> usingObserver() {
        return Observable.using(() -> new Animal(), i -> Observable.timer(5000,TimeUnit.MILLISECONDS), o -> o.relase());
    }

    private class Animal {
        Subscriber subscriber = new Subscriber() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                log("animal eat");
            }
        };

        public Animal() {
            log("create animal");
            Observable.interval(1000, TimeUnit.MILLISECONDS)
                    .subscribe(subscriber);
        }

        public void relase() {
            log("animal released");
            subscriber.unsubscribe();
        }
    }
}
