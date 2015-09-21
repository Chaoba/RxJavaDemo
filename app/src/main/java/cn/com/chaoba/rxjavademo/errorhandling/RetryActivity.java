package cn.com.chaoba.rxjavademo.errorhandling;

import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.Subscriber;

public class RetryActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("retry");
        mLButton.setOnClickListener(e -> retryObserver().subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                log("retry-onCompleted\n");
            }

            @Override
            public void onError(Throwable e) {
                log("retry-onError:" + e.getMessage()+"\n");
            }

            @Override
            public void onNext(Integer s) {
                log("retry-onNext:" + s);
            }
        }));
        mRButton.setText("retryWhen");
        mRButton.setOnClickListener(e -> retryWhenObserver().subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                log("retryWhen-onCompleted\n");
            }

            @Override
            public void onError(Throwable e) {
                log("retryWhen-onError:" + e.getMessage()+"\n");
            }

            @Override
            public void onNext(Integer s) {
                log("retryWhen-onNext:" + s);
            }
        }));
    }

    private Observable<Integer> retryObserver() {
        return createObserver().retry(2);
    }

    private Observable<Integer> retryWhenObserver() {
        return createObserver().retryWhen(observable -> observable.zipWith(Observable.just(1, 2, 3),
                (throwable, integer) -> throwable.getMessage() + integer)
                .flatMap(throwable -> {
                    log(throwable);
                    return Observable.timer(1, TimeUnit.SECONDS);
                }));

    }

    private Observable<Integer> createObserver() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {

            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                log("subscribe");
                for (int i = 0; i < 3; i++) {
                    if (i == 2) {
                        subscriber.onError(new Exception("Exception-"));
                    } else {
                        subscriber.onNext(i);
                    }
                }
            }
        });
    }

}
