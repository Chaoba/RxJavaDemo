package cn.com.chaoba.rxjavademo.combining;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class CombineLatestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("combineList");
        mLButton.setOnClickListener(e -> combineListObserver().subscribe(i -> log("combineList:" + i)));
        mRButton.setText("CombineLatest");
        mRButton.setOnClickListener(e -> combineLatestObserver().subscribe(i -> log("CombineLatest:" + i)));
    }

    private Observable<Integer> createObserver(int index) {
        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 1; i < 6; i++) {
                    subscriber.onNext(i * index);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).subscribeOn(Schedulers.newThread());
    }

    private Observable<Integer> combineLatestObserver() {
        return Observable.combineLatest(createObserver(1), createObserver(2), (num1, num2) -> {
            log("left:" + num1 + " right:" + num2);
            return num1 + num2;
        });
    }

    List<Observable<Integer>> list = new ArrayList<>();

    private Observable<Integer> combineListObserver() {
        for (int i = 1; i < 5; i++) {
            list.add(createObserver(i));
        }
        return Observable.combineLatest(list, args -> {
            int temp = 0;
            for (Object i : args) {
                log(i);
                temp += (Integer) i;
            }
            return temp;
        });
    }


}
