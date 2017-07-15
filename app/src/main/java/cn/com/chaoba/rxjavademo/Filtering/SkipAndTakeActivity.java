package cn.com.chaoba.rxjavademo.filtering;

import android.os.Bundle;
import android.view.View;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.functions.Action1;

public class SkipAndTakeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("Skip");
        mLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipObserver().subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer i) {
                        log("Skip:" + i);
                    }
                });
            }
        });
        mRButton.setText("Take");
        mRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeObserver().subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer i) {
                        log("Take:" + i);
                    }
                });
            }
        });
    }

    private Observable<Integer> skipObserver() {
        return Observable.just(0, 1, 2, 3, 4, 5).skip(2);
    }

    private Observable<Integer> takeObserver() {
        return Observable.just(0, 1, 2, 3, 4, 5).take(2);
    }


}
