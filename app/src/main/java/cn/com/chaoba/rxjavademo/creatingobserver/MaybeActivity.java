package cn.com.chaoba.rxjavademo.creatingobserver;

import android.os.Bundle;
import android.view.View;

import cn.com.chaoba.rxjavademo.BaseActivity;
import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class MaybeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("Maybe");
        mLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Maybe.just("1")
                        .subscribe(new MaybeObserver<String>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                log("onSubscribe");
                            }

                            @Override
                            public void onSuccess(@NonNull String s) {
                                log("onSuccess:" + s);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                log("onError");
                            }

                            @Override
                            public void onComplete() {
                                log("onComplete");
                            }
                        });
            }
        });
        mRButton.setText("Maybe");
        mRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Maybe.create(new MaybeOnSubscribe<String>() {
                    @Override
                    public void subscribe(@NonNull MaybeEmitter<String> e) throws Exception {
                        if(!e.isDisposed()){
                            e.onSuccess("1");
                            e.onComplete();
                        }

                    }
                }).subscribe(new MaybeObserver<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        log("onSubscribe");
                    }

                    @Override
                    public void onSuccess(@NonNull String s) {
                        log("onSuccess:" + s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        log("onError:" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        log("onComplete");
                    }
                });
            }
        });
    }

}
