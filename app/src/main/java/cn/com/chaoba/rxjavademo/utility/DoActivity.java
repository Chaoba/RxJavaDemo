package cn.com.chaoba.rxjavademo.utility;

import android.os.Bundle;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Notification;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;

public class DoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("do");
        mLButton.setOnClickListener(e -> {
            doOnEachObserver().compose(bindToLifecycle()).subscribe(new Action1<Integer>() {
                @Override
                public void call(Integer i) {
                    log("do:" + i);
                }
            });
        });
        mRButton.setText("doOnError");
        mRButton.setOnClickListener(e -> {
            doOnErrorObserver().compose(bindToLifecycle()).subscribe(new Subscriber<Integer>() {
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
            });
        });
    }

    private Observable<Integer> doOnEachObserver() {
        return Observable.just(1, 2, 3)
                .doOnEach(new Action1<Notification<? super Integer>>() {
                    @Override
                    public void call(Notification<? super Integer> notification) {
                        log("doOnEach send " + notification.getValue()
                                + " type:" + notification.getKind());
                    }
                })
                .doOnNext(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log("doOnNext send " + integer);
                    }
                })
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        log("on subscribe");
                    }
                })
                .doOnUnsubscribe(new Action0() {
                    @Override
                    public void call() {
                        log("on unSubscribe");
                    }
                })
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        log("onCompleted");
                    }
                });
    }

    private Observable<Integer> doOnErrorObserver() {
        return createObserver()
                .doOnEach(new Action1<Notification<? super Integer>>() {
                    @Override
                    public void call(Notification<? super Integer> notification) {
                        log("doOnEach send " + notification.getValue()
                                + " type:" + notification.getKind());
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        log("OnError:" + throwable.getMessage());
                    }
                })
                .doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        log("OnTerminate");
                    }
                })
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        log("doAfterTerminate");
                    }
                });
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
