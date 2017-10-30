package cn.com.chaoba.rxjavademo.filtering;

import android.os.Bundle;
import android.view.View;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.functions.Action1;

public class DistinctActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("distinct");
        mLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                distinctObserver().compose(bindToLifecycle()).subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer i) {
                        log("distinct:" + i);
                    }
                });
            }
        });
        mRButton.setText("UntilChanged");
        mRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                distinctUntilChangedObserver().compose(bindToLifecycle()).subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer i) {
                        log("UntilChanged:" + i);
                    }
                });
            }
        });
    }

    private Observable<Integer> distinctObserver() {
        return Observable.just(1, 2, 3, 4, 5, 4, 3, 2, 1).distinct();

    }

    private Observable<Integer> distinctUntilChangedObserver() {
        return Observable.just(1, 2, 3, 3, 3, 1, 2, 3, 3).distinctUntilChanged();

    }


}
