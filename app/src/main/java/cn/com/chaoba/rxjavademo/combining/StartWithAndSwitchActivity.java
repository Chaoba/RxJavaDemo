package cn.com.chaoba.rxjavademo.combining;

import android.os.Bundle;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class StartWithAndSwitchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("StartWith");
        mLButton.setOnClickListener(e -> startWithObserver().subscribe(i -> log("StartWith:" + i)));
        mRButton.setText("switch");
        mRButton.setOnClickListener(e -> switchObserver().subscribe(i -> log("switch:" + i)));
    }

    private Observable<Integer> startWithObserver() {
        return Observable.just(1, 2, 3).startWith(-1, 0);
    }

    private Observable<String> switchObserver() {
        return Observable.switchOnNext(Observable.create(
                new Observable.OnSubscribe<Observable<String>>() {
                    @Override
                    public void call(Subscriber<? super Observable<String>> subscriber) {
                        for (int i = 1; i < 3; i++) {
                            subscriber.onNext(createObserver(i));
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        ));
    }

    private Observable<String> createObserver(int index) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                for (int i = 1; i < 5; i++) {
                    subscriber.onNext(index + "-" + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).subscribeOn(Schedulers.newThread());
    }


}
