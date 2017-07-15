package cn.com.chaoba.rxjavademo.filtering;

import android.os.Bundle;
import android.view.View;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class FirstActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("First");
        mLButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Observable.just(0, 1, 2, 3, 4, 5).first(new Func1<Integer, Boolean>() {
                            @Override
                            public Boolean call(Integer integer) {
                                return integer > 3;
                            }
                        }).subscribe(new Action1<Integer>() {
                            @Override
                            public void call(Integer i) {
                                log("First:" + i);
                            }
                        });
                    }
                });


        mRButton.setText("Blocking");
        mRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = Observable.just(0, 1, 2, 3, 4, 5)
                        .toBlocking()
                        .first(new Func1<Integer, Boolean>() {
                            @Override
                            public Boolean call(Integer i) {
                                return i > 1;
                            }
                        });
                log("blocking:" + result);
            }
        });
    }

}
