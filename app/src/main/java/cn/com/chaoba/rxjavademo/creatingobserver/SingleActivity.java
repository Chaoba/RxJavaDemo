package cn.com.chaoba.rxjavademo.creatingobserver;

import android.os.Bundle;
import android.view.View;

import java.util.concurrent.TimeUnit;

import cn.com.chaoba.rxjavademo.BaseActivity;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class SingleActivity extends BaseActivity {
    Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("Single");
        mLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Single.just(1)
                        .subscribe(new SingleObserver<Integer>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                log("onSubscribe");
                            }

                            @Override
                            public void onSuccess(@NonNull Integer integer) {
                                log("onSuccess:" + integer);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                            }
                        });
            }
        });
        mRButton.setText("Disposable");
        mRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Single.just(1)
                        .delay(1, TimeUnit.SECONDS)
                        .subscribe(new SingleObserver<Integer>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                log("onSubscribe");
                                disposable = d;
                            }

                            @Override
                            public void onSuccess(@NonNull Integer integer) {
                                log("onSuccess:" + integer);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                            }
                        });
                log("start dispose");
                disposable.dispose();
            }
        });
    }

}
