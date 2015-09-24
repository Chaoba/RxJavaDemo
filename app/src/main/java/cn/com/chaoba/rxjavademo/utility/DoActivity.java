package cn.com.chaoba.rxjavademo.utility;

import android.os.Bundle;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.Subscriber;

public class DoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("do");
        mLButton.setOnClickListener(e -> doOnEachObserver().subscribe(i -> log("do:" + i)));
        mRButton.setText("doOnError");
        mRButton.setOnClickListener(e -> doOnErrorObserver().subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                log("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                log("subscriber onError:" + e.getMessage());
            }

            @Override
            public void onNext(Integer integer) {
                log("subscriber onNext:" + integer);
            }
        }));
    }

    private Observable<Integer> doOnEachObserver() {
        return Observable.just(1, 2, 3)
                .doOnEach(notification -> log("doOnEach send " + notification.getValue() + " type:" + notification.getKind()))
                .doOnNext(aInteger -> log("doOnNext send " + aInteger))
                .doOnSubscribe(() -> log("on subscribe"))
                .doOnUnsubscribe(() -> log("on unsubscribe\n"))
                .doOnCompleted(() -> log("onCompleted"));

    }

    private Observable<Integer> doOnErrorObserver() {
        return createObserver()
                .doOnEach(notification -> log("doOnEach send " + notification.getValue() + " type:" + notification.getKind()))
                .doOnError(throwable -> log("OnError:" + throwable.getMessage()))
                .doOnTerminate(() -> log("OnTerminate"))
                .finallyDo(() -> log("finallyDo"));
    }

    private Observable<Integer> createObserver() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 1; i <= 5; i++) {
                    if (i <= 3) {
                        subscriber.onNext(i);
                    } else {
                        subscriber.onError(new Throwable("num>3"));
                    }
                }
            }
        });
    }


}
