package cn.com.chaoba.rxjavademo.combining;

import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class JoinActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("join");
        mLButton.setOnClickListener(e -> joinObserver().subscribe(i -> log("join:" + i + "\n")));
        mRButton.setText("groupJoin");
        mRButton.setOnClickListener(e -> groupJoinObserver().subscribe(i -> i.subscribe(j -> log("groupJoin:" + j + "\n"))));
    }

    private Observable<String> createObserver() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                for (int i = 1; i < 5; i++) {
                    subscriber.onNext("Right-" + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).subscribeOn(Schedulers.newThread());
    }


    private Observable<String> joinObserver() {
        return Observable.just("Left-").join(createObserver(),
                integer -> Observable.timer(3000, TimeUnit.MILLISECONDS),
                integer -> Observable.timer(2000, TimeUnit.MILLISECONDS),
                (i, j) -> i + j
        );
    }

    private Observable<Observable<String>> groupJoinObserver() {
        return Observable.just("Left-")
                .groupJoin(createObserver(),
                        s -> Observable.timer(3000, TimeUnit.MILLISECONDS),
                        s -> Observable.timer(2000, TimeUnit.MILLISECONDS),
                        (s, stringObservable) -> stringObservable.map(str -> s + str));
    }


}
