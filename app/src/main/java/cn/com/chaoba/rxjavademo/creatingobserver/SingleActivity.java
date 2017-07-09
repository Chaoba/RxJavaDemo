package cn.com.chaoba.rxjavademo.creatingobserver;

import android.os.Bundle;
import android.view.View;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Single;
import rx.SingleSubscriber;

public class SingleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("Single data");
        mLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Single.create(new Single.OnSubscribe<Integer>() {
                    @Override
                    public void call(SingleSubscriber<? super Integer> singleSubscriber) {
                        if (!singleSubscriber.isUnsubscribed()) {
                            singleSubscriber.onSuccess(1);
                        }
                    }
                }).subscribe(new SingleSubscriber<Integer>() {
                    @Override
                    public void onSuccess(Integer value) {
                        log(value);
                    }

                    @Override
                    public void onError(Throwable error) {
                        log(error.getMessage());
                    }
                });
            }
        });
        mRButton.setText("Single error");
        mRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Single.create(new Single.OnSubscribe<Integer>() {
                    @Override
                    public void call(SingleSubscriber<? super Integer> singleSubscriber) {
                        if (!singleSubscriber.isUnsubscribed()) {
                            singleSubscriber.onError(new Throwable("Single error"));
                        }
                    }
                }).subscribe(new SingleSubscriber<Integer>() {
                    @Override
                    public void onSuccess(Integer value) {
                        log(value);
                    }

                    @Override
                    public void onError(Throwable error) {
                        log(error.getMessage());
                    }
                });
            }
        });
    }


}
