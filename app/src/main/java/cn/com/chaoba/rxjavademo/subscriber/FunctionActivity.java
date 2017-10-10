package cn.com.chaoba.rxjavademo.subscriber;

import android.os.Bundle;
import android.view.View;

import cn.com.chaoba.rxjavademo.BaseActivity;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

public class FunctionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("BiFunction");
        mLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable.just(1, 2, 3, 4)
                        .reduce(new BiFunction<Integer, Integer, Integer>() {
                            @Override
                            public Integer apply(Integer t1, Integer t2) {
                                return t1 + t2;
                            }
                        })
                        .subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(Integer integer) throws Exception {
                                log(integer);
                            }
                        });
            }
        });
    }

}
