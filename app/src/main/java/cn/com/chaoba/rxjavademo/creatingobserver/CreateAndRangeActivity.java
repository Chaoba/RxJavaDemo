package cn.com.chaoba.rxjavademo.creatingobserver;

import android.os.Bundle;
import android.view.View;

import java.util.Random;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

public class CreateAndRangeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("Create");
        mLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createObserver().subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        log("onComplete!");
                    }

                    @Override
                    public void onError(Throwable e) {
                        log("onError:" + e.getMessage());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        log("onNext:" + integer);
                    }
                });
            }
        });
        mRButton.setText("Range");
        mRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rangeObserver().subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log(integer);
                    }
                });
            }
        });
    }

    private Observable<Integer> createObserver() {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    for (int i = 0; i < 5; i++) {
                        int temp = new Random().nextInt(10);
                        if (temp > 8) {
                            //if value>8, we make an error
                            subscriber.onError(new Throwable("value >8"));
                            break;
                        } else {
                            subscriber.onNext(temp);
                        }
                        // on error,complete the job
                        if (i == 4) {
                            subscriber.onCompleted();
                        }
                    }
                }
            }
        });
    }

    private Observable<Integer> rangeObserver() {
        return Observable.range(10, 5);
    }


}
