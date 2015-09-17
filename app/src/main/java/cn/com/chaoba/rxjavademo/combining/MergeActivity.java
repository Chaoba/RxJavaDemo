package cn.com.chaoba.rxjavademo.combining;

import android.os.Bundle;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.Subscriber;

public class MergeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("Merge");
        mLButton.setOnClickListener(e -> mergeObserver().subscribe(i -> log("Merge:" + i)));
        mRButton.setText("mergeDelayError");
        mRButton.setOnClickListener(e -> mergeDelayErrorObserver().subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                log("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                log("mergeDelayError:" + e);

            }

            @Override
            public void onNext(Integer integer) {
                log("mergeDelayError:" + integer);

            }
        }));
    }

    private Observable<Integer> mergeObserver() {
        return Observable.merge(Observable.just(1, 2, 3), Observable.just(4, 5, 6));
    }

    private Observable<Integer> mergeDelayErrorObserver() {
        return Observable.mergeDelayError(Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 5; i++) {
                    if (i == 3) {
                        subscriber.onError(new Throwable("error"));
                    }
                    subscriber.onNext(i);

                }
            }
        }), Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 5; i++) {
                    subscriber.onNext(5 + i);
                }
                subscriber.onCompleted();
            }
        }));
    }


}
