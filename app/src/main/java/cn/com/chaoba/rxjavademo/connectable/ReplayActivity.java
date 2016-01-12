package cn.com.chaoba.rxjavademo.connectable;

import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.observables.ConnectableObservable;
import rx.schedulers.Schedulers;

public class ReplayActivity extends BaseActivity {
    Subscription mSubscription;
    ConnectableObservable<Long> obs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Action1 action2 = o -> log("action2:" + o);
        Action1 action1 = o -> {
            log("action1:" + o);
            if ((long) o == 5) obs.subscribe(action2);
        };


        mLButton.setText("relayCount");
        mLButton.setOnClickListener(e -> {
            obs = relayCountObserver();
            obs.subscribe(action1);
            log("relayCount");
            mSubscription = obs.connect();
        });
        mRButton.setText("relayTime");
        mRButton.setOnClickListener(e -> {
            obs = relayTimeObserver();
            obs.subscribe(action1);
            log("relayTime");
            mSubscription = obs.connect();
        });
    }

    private ConnectableObservable<Long> relayCountObserver() {
        Observable<Long> obser = Observable.interval(1, TimeUnit.SECONDS);
        obser.observeOn(Schedulers.newThread());
        return obser.replay(2);
    }

    private ConnectableObservable<Long> relayTimeObserver() {
        Observable<Long> obser = Observable.interval(1, TimeUnit.SECONDS);
        obser.observeOn(Schedulers.newThread());
        return obser.replay(3, TimeUnit.SECONDS);
    }

}
