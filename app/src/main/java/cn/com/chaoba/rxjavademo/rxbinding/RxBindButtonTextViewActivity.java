package cn.com.chaoba.rxjavademo.rxbinding;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.functions.Action1;
import rx.functions.Func1;

public class RxBindButtonTextViewActivity extends BaseActivity {
    long lastTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxView.clicks(mLButton)
                .throttleWithTimeout(200, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        log("click");
                    }
                });

        RxTextView.textChanges(mResultView)
                .map(new Func1<CharSequence, Integer>() {
                    @Override
                    public Integer call(CharSequence charSequence) {
                        return charSequence.length();
                    }
                })
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer > 10;
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.e(TAG, "Text changed to much times: " + integer);
                    }
                });


        mRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long currentTime = System.currentTimeMillis();
                long duration = currentTime - lastTime;
                lastTime = currentTime;
                if (duration < 200) {
                    return;
                }
                log("right click" + duration);
            }
        });

    }

}