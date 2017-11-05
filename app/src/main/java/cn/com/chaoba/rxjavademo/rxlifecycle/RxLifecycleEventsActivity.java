package cn.com.chaoba.rxjavademo.rxlifecycle;

import android.os.Bundle;
import android.view.View;

import com.trello.rxlifecycle.RxLifecycle;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.concurrent.TimeUnit;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

public class RxLifecycleEventsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("onPause");
        mLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable.interval(500, TimeUnit.MILLISECONDS)
                        .compose(RxLifecycle.bindUntilEvent(lifecycle(), ActivityEvent.PAUSE))
                        .doOnUnsubscribe(new Action0() {
                            @Override
                            public void call() {
                                log("pause onUnsubscribe");
                            }
                        })
                        .subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                log(aLong);
                            }
                        });
            }
        });

        mRButton.setText("onDestory");
        mRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable.interval(500, TimeUnit.MILLISECONDS)
                        .compose(RxLifecycle.bindUntilEvent(lifecycle(),
                                ActivityEvent.DESTROY))
                        .doOnUnsubscribe(new Action0() {
                            @Override
                            public void call() {
                                log("destroy onUnsubscribe");
                            }
                        })
                        .subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                log(aLong);
                            }
                        });
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        log("onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        log("onDestroy");
    }
}