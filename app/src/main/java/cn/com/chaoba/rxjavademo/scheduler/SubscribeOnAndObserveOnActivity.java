package cn.com.chaoba.rxjavademo.scheduler;

import android.os.Bundle;
import android.view.View;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class SubscribeOnAndObserveOnActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                log("start:" + Thread.currentThread()
                        .getName());
                subscriber.onNext(1);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.newThread())
                .map(new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer) {
                        log(integer + ":" + Thread.currentThread()
                                .getName());
                        return integer + 1;
                    }
                })
                .observeOn(Schedulers.io())
                .map(new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer) {
                        log(integer + ":" + Thread.currentThread()
                                .getName());
                        return integer + 1;
                    }
                })
                .subscribeOn(Schedulers.computation())
                .compose(bindToLifecycle()).subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log("action:" + Thread.currentThread()
                                .getName());
                    }
                });

        mLButton.setText("observeOn");
        mLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        log("start:" + Thread.currentThread()
                                .getName());
                        subscriber.onNext(1);
                        subscriber.onCompleted();
                    }
                })
                        .observeOn(Schedulers.newThread())
                        .map(new Func1<Integer, Integer>() {
                            @Override
                            public Integer call(Integer integer) {
                                log(integer + ":" + Thread.currentThread()
                                        .getName());
                                return integer + 1;
                            }
                        })
                        .observeOn(Schedulers.io())
                        .map(new Func1<Integer, Integer>() {
                            @Override
                            public Integer call(Integer integer) {
                                log(integer + ":" + Thread.currentThread()
                                        .getName());
                                return integer + 1;
                            }
                        })
                        .observeOn(Schedulers.computation())
                        .compose(bindToLifecycle()).subscribe(new Action1<Integer>() {
                            @Override
                            public void call(Integer integer) {
                                log("action:" + Thread.currentThread()
                                        .getName());
                            }
                        });
            }
        });

        mRButton.setText("subscribeOn");
        mRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        log("start:" + Thread.currentThread()
                                .getName());
                        subscriber.onNext(1);
                        subscriber.onCompleted();
                    }
                })
                        .subscribeOn(Schedulers.newThread())
                        .map(new Func1<Integer, Integer>() {
                            @Override
                            public Integer call(Integer integer) {
                                log(integer + ":" + Thread.currentThread()
                                        .getName());
                                return integer + 1;
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .map(new Func1<Integer, Integer>() {
                            @Override
                            public Integer call(Integer integer) {
                                log(integer + ":" + Thread.currentThread()
                                        .getName());
                                return integer + 1;
                            }
                        })
                        .subscribeOn(Schedulers.computation())
                        .compose(bindToLifecycle()).subscribe(new Action1<Integer>() {
                            @Override
                            public void call(Integer integer) {
                                log("action:" + Thread.currentThread()
                                        .getName());
                            }
                        });
            }
        });


    }

}