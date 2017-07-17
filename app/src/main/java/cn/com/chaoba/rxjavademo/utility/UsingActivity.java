package cn.com.chaoba.rxjavademo.utility;

import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;

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
                log("onNext" + o);
            }
        };
        mLButton.setText("using");
        mLButton.setOnClickListener(e -> {
            observable.subscribe(subscriber);
        });
        mRButton.setText("unSubscrib");
        mRButton.setOnClickListener(e -> {
            subscriber.unsubscribe();
        });
    }

    private Observable<Long> usingObserver() {
        return Observable.using(new Func0<Animal>() {
            @Override
            public Animal call() {
                return new Animal();
            }
        }, new Func1<Animal, Observable<? extends Long>>() {
            @Override
            public Observable<? extends Long> call(Animal animal) {
                return Observable.timer(5000, TimeUnit.MILLISECONDS);
            }
        }, new Action1<Animal>() {
            @Override
            public void call(Animal animal) {
                animal.release();
            }
        });
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

        public void release() {
            log("animal released");
            subscriber.unsubscribe();
        }
    }
}
