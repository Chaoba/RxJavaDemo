package cn.com.chaoba.rxjavademo.connectable;

import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.observables.ConnectableObservable;
import rx.schedulers.Schedulers;

public class RefCountActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConnectableObservable<Long> obs = publishObserver();

        mLButton.setText("refCount");
        mLButton.setOnClickListener(e -> obs.refCount().subscribe(aLong -> {
            log("refCount:" + aLong);
        }));
    }

    private ConnectableObservable<Long> publishObserver() {
        Observable<Long> obser = Observable.interval(1, TimeUnit.SECONDS);
        obser.observeOn(Schedulers.newThread());
        return obser.publish();
    }

}
