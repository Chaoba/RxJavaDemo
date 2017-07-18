package cn.com.chaoba.rxjavademo.utility;

import android.os.Bundle;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Notification;
import rx.Observable;
import rx.functions.Action1;

public class MaterializeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("materialize");
        mLButton.setOnClickListener(e -> {
            materializeObserver().subscribe(new Action1<Notification<Integer>>() {
                @Override
                public void call(Notification<Integer> i) {
                    log("materialize:" + i.getValue() + " type" + i.getKind());
                }
            });
        });
        mRButton.setText("deMeterialize");
        mRButton.setOnClickListener(e -> {
            deMaterializeObserver().subscribe(new Action1<Integer>() {
                @Override
                public void call(Integer i) {
                    log("deMeterialize:" + i);
                }
            });
        });
    }

    private Observable<Notification<Integer>> materializeObserver() {
        return Observable.just(1, 2, 3).materialize();
    }

    private Observable<Integer> deMaterializeObserver() {
        return materializeObserver().dematerialize();
    }

}
