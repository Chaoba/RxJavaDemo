package cn.com.chaoba.rxjavademo.transforming;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import cn.com.chaoba.rxjavademo.BaseActivity;
import cn.com.chaoba.rxjavademo.R;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class FlatMapActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("flatMap");
        mLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flatMapObserver().subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        log(s);
                    }
                });
            }
        });
        mRButton.setText("flatMapIterable");
        mRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flatMapIterableObserver().subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log("flatMapIterable:" + integer);
                    }
                });
            }
        });
    }

    private Observable<String> flatMapObserver() {
        return Observable.just(1, 2, 3)
                .flatMap(new Func1<Integer, Observable<String>>() {
                    @Override
                    public Observable<String> call(Integer integer) {
                        return Observable.just("flat map:" + integer);
                    }
                });
    }

    private Observable<? extends Integer> flatMapIterableObserver() {
        return Observable.just(1, 2, 3)
                .flatMapIterable(
                        new Func1<Integer, Iterable<Integer>>() {
                            @Override
                            public Iterable<Integer> call(Integer integer) {
                                ArrayList<Integer> s = new ArrayList<>();
                                for (int i = 0; i < integer; i++) {
                                    s.add(integer);
                                }
                                return s;
                            }
                        }
                );
    }


}