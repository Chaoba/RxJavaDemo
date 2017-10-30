package cn.com.chaoba.rxjavademo.custom;

import android.os.Bundle;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.Observable.Operator;
import rx.Observable.Transformer;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

public class CustomActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("lift");
        mLButton.setOnClickListener(e -> {
            liftObserver().compose(bindToLifecycle()).subscribe(new Action1<String>() {
                @Override
                public void call(String s) {
                    log("lift:" + s);
                }
            });
        });
        mRButton.setText("compose");
        mRButton.setOnClickListener(e -> {
            composeObserver().compose(bindToLifecycle()).subscribe(new Action1<String>() {
                @Override
                public void call(String s) {
                    log("compose:" + s);
                }
            });
        });
    }

    private Observable<String> liftObserver() {
        Operator<String, String> myOperator = new Operator<String, String>() {
            @Override
            public Subscriber<? super String> call(
                    Subscriber<? super String> subscriber) {
                return new Subscriber<String>(subscriber) {
                    @Override
                    public void onCompleted() {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onCompleted();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onError(e);
                        }
                    }

                    @Override
                    public void onNext(String s) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext("myOperator:" + s);
                        }
                    }
                };
            }

        };
        return Observable.just(1, 2, 3)
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        return "map1:" + integer;
                    }
                })
                .lift(myOperator)
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String integer) {
                        return "map2:" + integer;
                    }
                });
    }

    private Observable<String> composeObserver() {
        Transformer<Integer, String> myTransformer =
                new Transformer<Integer, String>() {
                    @Override
                    public Observable<String> call(
                            Observable<Integer> integerObservable) {
                        return integerObservable
                                .map(integer -> "myTransforer:" + integer)
                                .doOnNext(s -> log("doOnNext:" + s));
                    }
                };
        return Observable.just(1, 2, 3).compose(myTransformer);
    }

}
