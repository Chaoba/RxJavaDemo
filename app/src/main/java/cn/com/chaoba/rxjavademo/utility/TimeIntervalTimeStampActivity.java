package cn.com.chaoba.rxjavademo.utility;

import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.TimeInterval;
import rx.schedulers.Timestamped;

public class TimeIntervalTimeStampActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("timeInterval");
        mLButton.setOnClickListener(e -> {
            timeIntervalObserver().compose(bindToLifecycle()).subscribe(new Action1<TimeInterval<Long>>() {
                @Override
                public void call(TimeInterval<Long> i) {
                    log("timeInterval:" + i.getValue() + "-"
                            + i.getIntervalInMilliseconds());
                }
            });
        });
        mRButton.setText("timeStamp");
        mRButton.setOnClickListener(e -> {
            timeStampObserver().compose(bindToLifecycle()).subscribe(new Action1<Timestamped<Long>>() {
                @Override
                public void call(Timestamped<Long> i) {
                    log("timeStamp:" + i.getValue() + "-"
                            + i.getTimestampMillis());
                }
            });
        });
    }

    private Observable<TimeInterval<Long>> timeIntervalObserver() {
        return Observable.interval(1, TimeUnit.SECONDS).take(3).timeInterval();
    }

    private Observable<Timestamped<Long>> timeStampObserver() {
        return Observable.interval(1, TimeUnit.SECONDS).take(3).timestamp();
    }

}
