package cn.com.chaoba.rxjavademo.subscriber;

import android.os.Bundle;
import android.view.View;

import java.util.concurrent.TimeUnit;

import cn.com.chaoba.rxjavademo.BaseActivity;
import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subscribers.ResourceSubscriber;

public class ResourceSubscriberActivity extends BaseActivity {
    ResourceSubscriber<Long> subscriber = new ResourceSubscriber<Long>() {
        @Override
        public void onNext(Long t) {
            log("onNext:" + t);
        }

        @Override
        public void onError(Throwable t) {
            log("onError:" + t.getMessage());
            dispose();
        }

        @Override
        public void onComplete() {
            log("onComplete");
            dispose();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("Resource");
        mLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Disposable disposable = Flowable.interval(1, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        log("added resource:" + aLong);
                    }
                });
                subscriber.add(disposable);
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
