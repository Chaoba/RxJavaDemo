package cn.com.chaoba.rxjavademo.subscriber;

import android.os.Bundle;
import android.view.View;

import java.util.concurrent.TimeUnit;

import cn.com.chaoba.rxjavademo.BaseActivity;
import io.reactivex.Flowable;
import io.reactivex.subscribers.DisposableSubscriber;

public class DisposableSubscriberActivity extends BaseActivity {
    DisposableSubscriber<Long> subscriber = new DisposableSubscriber<Long>() {
        @Override
        public void onNext(Long t) {
            log("onNext:" + t);
            if (t == 3) {
                cancel();
            }
        }

        @Override
        public void onError(Throwable t) {
            log("onError:" + t.getMessage());
        }

        @Override
        public void onComplete() {
            log("onComplete");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("Resource");
        mLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Flowable.interval(1, TimeUnit.SECONDS).subscribe(subscriber);

            }
        });
        mRButton.setText("dispose");
        mRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subscriber.dispose();
            }
        });
    }

}
