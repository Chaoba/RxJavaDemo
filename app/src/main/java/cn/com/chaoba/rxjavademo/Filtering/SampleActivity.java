package cn.com.chaoba.rxjavademo.filtering;

import android.os.Bundle;
import android.view.View;

import java.util.concurrent.TimeUnit;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.functions.Action1;

public class SampleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("sample");
        mLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sampleObserver().compose(bindToLifecycle()).subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long i) {
                        log("sample:" + i);
                    }
                });
            }
        });
        mRButton.setText("throttleFirst");
        mRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                throttleFirstObserver().compose(bindToLifecycle()).subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long i) {
                        log("throttleFirst:" + i);
                    }
                });
            }
        });
    }

    private Observable<Long> sampleObserver() {
        return Observable.interval(200, TimeUnit.MILLISECONDS)
                .sample(1000, TimeUnit.MILLISECONDS);
    }

    private Observable<Long> throttleFirstObserver() {
        return Observable.interval(200, TimeUnit.MILLISECONDS)
                .throttleFirst(1000, TimeUnit.MILLISECONDS);
    }


}
