package cn.com.chaoba.rxjavademo.transforming;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func2;

public class ScanActivity extends BaseActivity {
    ArrayList<Integer> list = new ArrayList<>(2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (int i = 0; i < 10; i++) {
            list.add(2);
        }
        mLButton.setText("scan");
        mLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanObserver().compose(bindToLifecycle()).subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer i) {
                        log("scan:" + i);
                    }
                });
            }
        });


    }

    private Observable<Integer> scanObserver() {
        return Observable.from(list).scan(new Func2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer x, Integer y) {
                return x * y;
            }
        });
    }

}

