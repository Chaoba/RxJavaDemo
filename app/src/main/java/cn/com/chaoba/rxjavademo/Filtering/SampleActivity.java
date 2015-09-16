package cn.com.chaoba.rxjavademo.filtering;

import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.Subscriber;

public class SampleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("sample");
        mLButton.setOnClickListener(e -> sampleObserver().subscribe(i -> log("sample:" + i)));
        mRButton.setText("throttleFirst");
        mRButton.setOnClickListener(e -> throttleFirstObserver().subscribe(i -> log("throttleFirst:" + i)));
    }

    private Observable<Integer> sampleObserver() {
        return createObserver().sample(1000, TimeUnit.MILLISECONDS);
    }

    private Observable<Integer> throttleFirstObserver() {
        return createObserver().throttleFirst(1000, TimeUnit.MILLISECONDS);
    }


    private Observable<Integer> createObserver() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 20; i++) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
            }
        });
    }

}
