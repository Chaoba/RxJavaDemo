package cn.com.chaoba.rxjavademo.creatingobserver;

import android.os.Bundle;
import android.view.View;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Completable;
import rx.CompletableSubscriber;
import rx.Subscription;

public class CompletableActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("Error");
        mLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Completable.error(new Throwable("Completable error"))
                        .subscribe(new CompletableSubscriber() {
                            @Override
                            public void onCompleted() {
                                log("onCompleted");
                            }

                            @Override
                            public void onError(Throwable e) {
                                log(e.getMessage());
                            }

                            @Override
                            public void onSubscribe(Subscription d) {
                            }
                        });
            }
        });
        mRButton.setText("complete");
        mRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Completable.complete()
                        .subscribe(new CompletableSubscriber() {
                            @Override
                            public void onCompleted() {
                                log("onCompleted");
                            }

                            @Override
                            public void onError(Throwable e) {
                                log(e.getMessage());
                            }

                            @Override
                            public void onSubscribe(Subscription d) {
                            }
                        });
            }
        });
    }


}
