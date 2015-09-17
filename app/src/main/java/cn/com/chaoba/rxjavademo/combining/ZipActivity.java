package cn.com.chaoba.rxjavademo.combining;

import android.os.Bundle;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class ZipActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("zipWith");
        mLButton.setOnClickListener(e -> zipWithObserver().subscribe(i -> log("zipWith:" + i + "\n")));
        mRButton.setText("zip");
        mRButton.setOnClickListener(e -> zipWithIterableObserver().subscribe(i -> log("zip:" + i + "\n")));
    }

    private Observable<String> zipWithObserver() {
        return createObserver(2).zipWith(createObserver(3), (s, s2) -> s + "-" + s2);
    }

    private Observable<String> zipWithIterableObserver() {
        return Observable.zip(createObserver(2), createObserver(3), createObserver(4), (s, s2, s3) -> s + "-" + s2 + "-" + s3);
    }

    private Observable<String> createObserver(int index) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                for (int i = 1; i <= index; i++) {
                    log("emitted:" + index + "-" + i);
                    subscriber.onNext(index + "-" + i);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).subscribeOn(Schedulers.newThread());
    }
}
