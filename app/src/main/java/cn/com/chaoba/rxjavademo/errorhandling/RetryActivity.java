package cn.com.chaoba.rxjavademo.errorhandling;

import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.functions.Func2;

public class RetryActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("retry");
        mLButton.setOnClickListener(e -> {
            retryObserver().subscribe(new Subscriber<Integer>() {
                @Override
                public void onCompleted() {
                    log("retry-onCompleted");
                }

                @Override
                public void onError(Throwable e) {
                    log("retry-onError:" + e.getMessage());
                }

                @Override
                public void onNext(Integer s) {
                    log("retry-onNext:" + s);
                }
            });
        });
        mRButton.setText("retryWhen");
        mRButton.setOnClickListener(e -> {
            retryWhenObserver().subscribe(new Subscriber<Integer>() {
                @Override
                public void onCompleted() {
                    log("retryWhen-onCompleted");
                }

                @Override
                public void onError(Throwable e) {
                    log("retryWhen-onError:" + e.getMessage());
                }

                @Override
                public void onNext(Integer s) {
                    log("retryWhen-onNext:" + s);
                }
            });
        });
    }

    private Observable<Integer> retryObserver() {
        return createObserver().retry(2);
    }

    private Observable<Integer> retryWhenObserver() {
        return createObserver().retryWhen(
                new Func1<Observable<? extends Throwable>, Observable<?>>() {
                    @Override
                    public Observable<?> call(Observable<? extends Throwable> observable) {
                        return observable.zipWith(Observable.just(1, 2, 3),
                                new Func2<Throwable, Integer, String>() {
                                    @Override
                                    public String call(Throwable throwable, Integer integer) {
                                        return throwable.getMessage() + integer;
                                    }
                                })
                                .flatMap(new Func1<String, Observable<Long>>() {
                                    @Override
                                    public Observable<Long> call(String s) {
                                        log(s);
                                        return Observable.timer(1, TimeUnit.SECONDS);
                                    }
                                });
                    }
                });
    }

    private Observable<Integer> createObserver() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                log("subscribe");
                for (int i = 0; i < 3; i++) {
                    if (i == 2) {
                        subscriber.onError(new Exception("Exception"));
                    } else {
                        subscriber.onNext(i);
                    }
                }
            }
        });
    }

}
