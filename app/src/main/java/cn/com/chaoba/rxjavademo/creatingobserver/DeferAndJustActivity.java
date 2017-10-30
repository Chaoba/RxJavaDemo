package cn.com.chaoba.rxjavademo.creatingobserver;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func0;

public class DeferAndJustActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Observable<Long> deferObservable = getDefer();
        Observable<Long> justObservable = getJust();
        mLButton.setText("Defer");
        mRButton.setText("Just");
        mLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deferObservable.compose(bindToLifecycle()).subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long time) {
                        log("defer:" + time);
                    }
                });
            }
        });

        mRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                justObservable.compose(bindToLifecycle()).subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long time) {
                        log("just:" + time);
                    }
                });
            }
        });
    }

    @NonNull
    private Observable<Long> getJust() {
        return Observable.just(System.currentTimeMillis());
    }

    @NonNull
    private Observable<Long> getDefer() {
        return Observable.defer(new Func0<Observable<Long>>() {
            @Override
            public Observable<Long> call() {
                return getJust();
            }
        });
    }

}
