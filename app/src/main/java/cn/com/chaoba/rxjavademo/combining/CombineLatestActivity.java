package cn.com.chaoba.rxjavademo.combining;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func2;
import rx.functions.FuncN;

public class CombineLatestActivity extends BaseActivity {
    List<Observable<Long>> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("combineList");
        mLButton.setOnClickListener(e -> {
            list.clear();
            combineListObserver().compose(bindToLifecycle()).subscribe(new Action1<String>() {
                @Override
                public void call(String i) {
                    log("combineList:" + i);
                }
            });
        });
        mRButton.setText("CombineLatest");
        mRButton.setOnClickListener(e -> {
            combineLatestObserver().compose(bindToLifecycle()).subscribe(new Action1<String>() {
                @Override
                public void call(String i) {
                    log("CombineLatest" + i);
                }
            });
        });
    }

    private Observable<Long> createObserver(int index) {
        return Observable.interval(500 * index, TimeUnit.MILLISECONDS);
    }

    private Observable<String> combineLatestObserver() {
        return Observable.combineLatest(createObserver(1), createObserver(2),
                new Func2<Long, Long, String>() {
                    @Override
                    public String call(Long num1, Long num2) {
                        return ("left:" + num1 + " right:" + num2);
                    }
                });
    }


    private Observable<String> combineListObserver() {
        for (int i = 1; i < 3; i++) {
            list.add(createObserver(i));
        }
        return Observable.combineLatest(list, new FuncN<String>() {
            @Override
            public String call(Object... args) {
                String temp = "";
                for (Object i : args) {
                    temp = temp + ":" + i;
                }
                return temp;
            }
        });
    }


}
