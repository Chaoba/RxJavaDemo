package cn.com.chaoba.rxjavademo.Filtering;

import android.os.Bundle;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.Subscriber;
import rx.observables.BlockingObservable;

public class FirstActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("First");
        mLButton.setOnClickListener(e -> FirstObserver().subscribe(i -> log("First:" + i)));
        mRButton.setText(" Blocking");
        mRButton.setOnClickListener(e -> {
            log("blocking:" + FilterObserver().first(i -> i > 1));
        });
    }

    private Observable<Integer> FirstObserver() {
        return Observable.just(0, 1, 2, 3, 4, 5).first(i -> i > 1);
    }

    private BlockingObservable<Integer> FilterObserver() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 5; i++) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (!subscriber.isUnsubscribed()) {
                        log("onNext:" + i);
                        subscriber.onNext(i);
                    }
                }
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        }).toBlocking();
    }
}
