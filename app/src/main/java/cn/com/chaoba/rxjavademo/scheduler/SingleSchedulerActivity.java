package cn.com.chaoba.rxjavademo.scheduler;

import android.os.Bundle;
import android.view.View;

import org.reactivestreams.Publisher;

import cn.com.chaoba.rxjavademo.BaseActivity;
import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class SingleSchedulerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("Single scheduler");
        mLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Flowable.just(1, 2, 3)
                        .flatMap(new Function<Integer, Publisher<String>>() {
                            @Override
                            public Publisher<String> apply(@NonNull Integer integer) throws
                                    Exception {
                                return Flowable.just(integer + "-1", integer + "-2", integer + "-3")
                                        .subscribeOn(Schedulers.io());
                            }
                        })
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                log(s);
                            }
                        });
            }
        });
        mRButton.setText("Set Handler");
        mRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Flowable.just(1, 2, 3)
                        .flatMap(new Function<Integer, Publisher<String>>() {
                            @Override
                            public Publisher<String> apply(@NonNull Integer integer) throws
                                    Exception {
                                return Flowable.just(integer + "-1", integer + "-2", integer + "-3")
                                        .subscribeOn(Schedulers.single());
                            }
                        })
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                log(s);
                            }
                        });
            }
        });
    }

}
