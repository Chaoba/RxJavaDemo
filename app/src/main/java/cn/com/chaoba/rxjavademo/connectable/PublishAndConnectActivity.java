package cn.com.chaoba.rxjavademo.connectable;

import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.observables.ConnectableObservable;
import rx.schedulers.Schedulers;

public class PublishAndConnectActivity extends BaseActivity {
    Subscription mSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConnectableObservable<Long> obs = publishObserver();
        Action1 action2 = o -> log("action2:" + o);
        Action1 action1 = o -> {
            log("action1:" + o);
            if ((long) o == 3) obs.subscribe(action2);
        };
        obs.subscribe(action1);

        mLButton.setText("start");
        mLButton.setOnClickListener(e -> mSubscription = obs.connect());
        mRButton.setText("stop");
        mRButton.setOnClickListener(e -> {
            if (mSubscription != null) {
                mSubscription.unsubscribe();
            }
        });
    }

    private ConnectableObservable<Long> publishObserver() {
        Observable<Long> obser = Observable.interval(1, TimeUnit.SECONDS);
        obser.observeOn(Schedulers.newThread());
        return obser.publish();
    }

}
