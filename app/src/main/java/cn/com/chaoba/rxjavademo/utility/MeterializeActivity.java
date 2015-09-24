package cn.com.chaoba.rxjavademo.utility;

import android.os.Bundle;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Notification;
import rx.Observable;

public class MeterializeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("meterialize");
        mLButton.setOnClickListener(e -> meterializeObserver().subscribe(i -> log("meterialize:" + i.getValue() + " type" + i.getKind())));
        mRButton.setText("deMeterialize");
        mRButton.setOnClickListener(e -> deMeterializeObserver().subscribe(i->log("deMeterialize:"+i)));
    }

    private Observable<Notification<Integer>> meterializeObserver() {
        return Observable.just(1, 2, 3).materialize();
    }

    private Observable<Integer> deMeterializeObserver() {
        return meterializeObserver().dematerialize();
    }

}
