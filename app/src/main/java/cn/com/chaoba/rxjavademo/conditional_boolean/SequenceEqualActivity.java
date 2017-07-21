package cn.com.chaoba.rxjavademo.conditional_boolean;

import android.os.Bundle;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.functions.Action1;

public class SequenceEqualActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("equal");
        mLButton.setOnClickListener(e -> {
            equalObserver().subscribe(new Action1<Boolean>() {
                @Override
                public void call(Boolean i) {
                    log("equal:" + i);
                }
            });
        });
        mRButton.setText("notEqual");
        mRButton.setOnClickListener(e -> {
            notEqualObserver().subscribe(new Action1<Boolean>() {
                @Override
                public void call(Boolean i) {
                    log("notEqual:" + i);
                }
            });
        });
    }

    private Observable<Boolean> equalObserver() {
        return Observable.sequenceEqual(Observable.just(1, 2, 3),
                Observable.just(1, 2, 3));
    }

    private Observable<Boolean> notEqualObserver() {
        return Observable.sequenceEqual(Observable.just(1, 2, 3),
                Observable.just(1, 2));
    }
}


