package cn.com.chaoba.rxjavademo.filtering;

import android.os.Bundle;
import android.view.View;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class ElementAtAndFilterActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("elementAt");
        mLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elementAtObserver().subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer i) {
                        log("elementAt:" + i);
                    }
                });
            }
        });
        mRButton.setText("Filter");
        mRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterObserver().subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer i) {
                        log("Filter:" + i);
                    }
                });
            }
        });
    }

    private Observable<Integer> elementAtObserver() {
        return Observable.just(0, 1, 2, 3, 4, 5).elementAt(2);
    }

    private Observable<Integer> filterObserver() {
        return Observable.just(0, 1, 2, 3, 4, 5).filter(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return integer < 3;
            }
        });
    }


}
