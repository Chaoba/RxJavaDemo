package cn.com.chaoba.rxjavademo.creatingobserver;

import android.os.Bundle;
import android.view.View;

import cn.com.chaoba.rxjavademo.BaseActivity;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class FlowableActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("Flowable");
        mLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long time = System.currentTimeMillis();
                Flowable.range(1, 10000)
                        .subscribeOn(Schedulers.io())
                        .map(new Function<Integer, Integer>() {
                            @Override
                            public Integer apply(@NonNull Integer i) throws Exception {
                                try {
                                    Thread.sleep(1);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                return i;
                            }
                        })
                        .subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(Integer integer) throws Exception {
                                log(integer);
                                if (integer == 10000) {
                                    log(System.currentTimeMillis() - time);
                                }
                            }
                        });
            }
        });
        mRButton.setText("Observable");
        mRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long time = System.currentTimeMillis();
                Observable.range(1, 10000)
                        .subscribeOn(Schedulers.io())
                        .map(new Function<Integer, Integer>() {
                            @Override
                            public Integer apply(@NonNull Integer i) throws Exception {
                                try {
                                    Thread.sleep(1);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                return i;
                            }
                        })
                        .subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(Integer integer) throws Exception {
                                log(integer);
                                if (integer == 10000) {
                                    log(System.currentTimeMillis() - time);
                                }
                            }
                        });
            }
        });
    }

}
