package cn.com.chaoba.rxjavademo.transforming;

import android.os.Bundle;
import android.view.View;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.observables.GroupedObservable;

public class GroupbyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("groupBy");
        mLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupByObserver().compose(bindToLifecycle()).subscribe(new Action1<GroupedObservable<Integer, Integer>>() {
                    @Override
                    public void call(GroupedObservable<Integer, Integer> groupedObservable) {
                        groupedObservable.count().compose(bindToLifecycle()).subscribe(new Action1<Integer>() {
                            @Override
                            public void call(Integer integer) {
                                log("key" + groupedObservable.getKey()
                                        + " contains:" + integer + " numbers");
                            }
                        });
                    }
                });
            }
        });
        mRButton.setText("groupByKeyValue");
        mRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupByStringObserver().compose(bindToLifecycle()).subscribe(new Action1<GroupedObservable<Integer, String>>() {
                    @Override
                    public void call(GroupedObservable<Integer, String> groupedObservable) {
                        if (groupedObservable.getKey() == 0) {
                            groupedObservable.compose(bindToLifecycle()).subscribe(new Action1<String>() {
                                @Override
                                public void call(String s) {
                                    log(s);
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    private Observable<GroupedObservable<Integer, Integer>> groupByObserver() {
        return Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9)
                .groupBy(new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer) {
                        return integer % 2;
                    }
                });
    }

    private Observable<GroupedObservable<Integer, String>> groupByStringObserver() {
        return Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9)
                .groupBy(new Func1<Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer) {
                        return integer % 2;
                    }
                }, new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        return "groupByKeyValue:" + integer;
                    }
                });
    }
}