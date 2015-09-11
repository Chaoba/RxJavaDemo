package cn.com.chaoba.rxjavademo.transforming;

import android.os.Bundle;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.Subscriber;
import rx.observables.GroupedObservable;

public class GroupbyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("groupBy");
        mLButton.setOnClickListener(e -> groupByObserver().subscribe(new Subscriber<GroupedObservable<Integer, Integer>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GroupedObservable<Integer, Integer> groupedObservable) {
                groupedObservable.count().subscribe(integer -> log("key" + groupedObservable.getKey() + " contains:" + integer + " numbers"));
            }
        }));
        mRButton.setText("groupByKeyValue");
        mRButton.setOnClickListener(e -> groupByKeyValueObserver().subscribe(new Subscriber<GroupedObservable<Integer, String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(GroupedObservable<Integer, String> integerIntegerGroupedObservable) {
                if (integerIntegerGroupedObservable.getKey() == 0) {
                    integerIntegerGroupedObservable.subscribe(integer -> log(integer));
                }
            }
        }));
    }

    private Observable<GroupedObservable<Integer, Integer>> groupByObserver() {
        return Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9).groupBy(integer -> integer % 2);
    }

    private Observable<GroupedObservable<Integer, String>> groupByKeyValueObserver() {
        return Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9)
                .groupBy(integer -> integer % 2, integer -> "groupByKeyValue:" + integer);
    }
}