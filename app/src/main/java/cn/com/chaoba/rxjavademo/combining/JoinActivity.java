package cn.com.chaoba.rxjavademo.combining;

import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

public class JoinActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("join");
        mLButton.setOnClickListener(e -> {
            joinObserver().compose(bindToLifecycle()).subscribe(new Action1<String>() {
                @Override
                public void call(String s) {
                    log("join:" + s);
                }
            });
        });
        mRButton.setText("groupJoin");
        mRButton.setOnClickListener(e -> {
            groupJoinObserver().compose(bindToLifecycle()).subscribe(new Action1<Observable<String>>() {
                @Override
                public void call(Observable<String> stringObservable) {
                    stringObservable.first().compose(bindToLifecycle()).subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            log("groupJoin:" + s);
                        }
                    });
                }
            });
        });
    }

    @NonNull
    private Observable<String> getLeftObservable() {
        return Observable.just("a", "b", "c");
    }

    @NonNull
    private Observable<Long> getRightObservable() {
        return Observable.just(1l, 2l, 3l);
    }

    private Observable<String> joinObserver() {
        return getLeftObservable().join(
                getRightObservable(),
                new Func1<String, Observable<Long>>() {
                    @Override
                    public Observable<Long> call(String s) {
                        return Observable.timer(1000, TimeUnit.MILLISECONDS);
                    }
                },
                new Func1<Long, Observable<Long>>() {
                    @Override
                    public Observable<Long> call(Long s) {
                        return Observable.timer(1000, TimeUnit.MILLISECONDS);
                    }
                },
                new Func2<String, Long, String>() {
                    @Override
                    public String call(String left, Long right) {
                        return left + ":" + right;
                    }
                }
        );
    }


    private Observable<Observable<String>> groupJoinObserver() {
        return getLeftObservable().groupJoin(
                getRightObservable(),
                new Func1<String, Observable<Long>>() {
                    @Override
                    public Observable<Long> call(String s) {
                        return Observable.timer(1000, TimeUnit.MILLISECONDS);
                    }
                },
                new Func1<Long, Observable<Long>>() {
                    @Override
                    public Observable<Long> call(Long s) {
                        return Observable.timer(1000, TimeUnit.MILLISECONDS);
                    }
                },
                new Func2<String, Observable<Long>, Observable<String>>() {
                    @Override
                    public Observable<String> call(String left, Observable<Long> longObservable) {
                        return longObservable.map(new Func1<Long, String>() {
                            @Override
                            public String call(Long right) {
                                return left + ":" + right;
                            }
                        });
                    }
                }
        );
    }


}
