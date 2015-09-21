package cn.com.chaoba.rxjavademo.errorhandling;

import android.os.Bundle;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.Subscriber;

public class OnExceptionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("onException-true");
        mLButton.setOnClickListener(e -> onExceptionResumeObserver(true).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                log("onException-true-onCompleted\n");
            }

            @Override
            public void onError(Throwable e) {
                log("onException-true-onError:" + e.getMessage());
            }

            @Override
            public void onNext(String s) {
                log("onException-true-onNext:" + s);
            }
        }));
        mRButton.setText("onException-false");
        mRButton.setOnClickListener(e -> onExceptionResumeObserver(false).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                log("onException-false-onCompleted\n");
            }

            @Override
            public void onError(Throwable e) {
                log("onException-false-onError:" + e.getMessage());
            }

            @Override
            public void onNext(String s) {
                log("onException-false-onNext:" + s);
            }
        }));
    }

    private Observable<String> onExceptionResumeObserver(boolean isException) {
        return createObserver(isException).onExceptionResumeNext(Observable.just("7", "8", "9"));
    }


    private Observable<String> createObserver(Boolean createExcetion) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                for (int i = 1; i <= 6; i++) {
                    if (i < 3) {
                        subscriber.onNext("onNext:" + i);
                    } else if (createExcetion) {
                        subscriber.onError(new Exception("Exception"));
                    } else {
                        subscriber.onError(new Throwable("Throw error"));

                    }
                }
            }
        });
    }
}
