package cn.com.chaoba.rxjavademo.aggregate;

import android.os.Bundle;

import java.util.ArrayList;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;

public class Reducectivity extends BaseActivity {
    ArrayList<Integer> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (int i = 0; i < 10; i++) {
            list.add(2);
        }
        mLButton.setText("reduce");
        mLButton.setOnClickListener(e -> reduceObserver().subscribe(i -> log("reduce:" + i)));
        mRButton.setText("collect");
        mRButton.setOnClickListener(e -> collectObserver().subscribe(i -> log("collect:" + i)));
    }

    private Observable<Integer> reduceObserver() {
        return Observable.from(list).reduce((x, y) -> x * y);
    }
    private Observable<ArrayList<Integer>> collectObserver() {
         return Observable.from(list).collect(() -> new ArrayList<>(), (integers, integer) -> integers.add(integer));
    }

}

