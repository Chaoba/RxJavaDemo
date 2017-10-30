package cn.com.chaoba.rxjavademo.aggregate;

import android.os.Bundle;

import java.util.ArrayList;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Action2;
import rx.functions.Func0;
import rx.functions.Func2;

public class Reducectivity extends BaseActivity {
    ArrayList<Integer> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (int i = 0; i < 10; i++) {
            list.add(2);
        }
        mLButton.setText("reduce");
        mLButton.setOnClickListener(e -> {
            reduceObserver().compose(bindToLifecycle()).subscribe(new Action1<Integer>() {
                @Override
                public void call(Integer i) {
                    log("reduce:" + i);
                }
            });
        });
        mRButton.setText("collect");
        mRButton.setOnClickListener(e -> {
            collectObserver().compose(bindToLifecycle()).subscribe(new Action1<ArrayList<Integer>>() {
                @Override
                public void call(ArrayList<Integer> integers) {
                    log("collect:" + integers);
                }
            });
        });
    }

    private Observable<Integer> reduceObserver() {
        return Observable.from(list).reduce((new Func2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer x, Integer y) {
                return x * y;
            }
        }));
    }

    private Observable<ArrayList<Integer>> collectObserver() {
        return Observable.from(list).collect(
                new Func0<ArrayList<Integer>>() {
                    @Override
                    public ArrayList<Integer> call() {
                        return new ArrayList<>();
                    }
                }, new Action2<ArrayList<Integer>, Integer>() {
                    @Override
                    public void call(ArrayList<Integer> integers, Integer integer) {
                        integers.add(integer);
                    }
                });
    }

}

