package cn.com.chaoba.rxjavademo.combining;

import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.functions.Func3;

public class ZipActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("zipWith");
        mLButton.setOnClickListener(e -> {
            zipWithObserver().compose(bindToLifecycle()).subscribe(new Action1<String>() {
                @Override
                public void call(String s) {
                    log("zipWith:" + s);
                }
            });
        });
        mRButton.setText("zip");
        mRButton.setOnClickListener(e -> {
            zipWithIterableObserver().compose(bindToLifecycle()).subscribe(new Action1<String>() {
                @Override
                public void call(String s) {
                    log("zip:" + s);
                }
            });
        });
    }

    private Observable<String> zipWithObserver() {
        return createObserver(2).zipWith(createObserver(3),
                new Func2<String, String, String>() {
                    @Override
                    public String call(String s, String s2) {
                        return s + "-" + s2;
                    }
                });
    }

    private Observable<String> zipWithIterableObserver() {
        return Observable.zip(createObserver(2),
                createObserver(3), createObserver(4),
                new Func3<String, String, String, String>() {
                    @Override
                    public String call(String s, String s2, String s3) {
                        return s + "-" + s2 + "-" + s3;
                    }
                });
    }

    private Observable<String> createObserver(int index) {
        return Observable.interval(100, TimeUnit.MILLISECONDS).take(index)
                .map(new Func1<Long, String>() {
                    @Override
                    public String call(Long aLong) {
                        return index + ":" + aLong;
                    }
                });
    }
}
