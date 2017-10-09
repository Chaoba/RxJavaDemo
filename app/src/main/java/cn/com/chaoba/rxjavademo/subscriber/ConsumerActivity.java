package cn.com.chaoba.rxjavademo.subscriber;

import android.os.Bundle;
import android.view.View;

import cn.com.chaoba.rxjavademo.BaseActivity;
import io.reactivex.Single;
import io.reactivex.functions.BiConsumer;

public class ConsumerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("BiConsumerError");
        mLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Single.error(new RuntimeException("test error"))
                        .subscribe(new BiConsumer<Object, Throwable>() {
                            @Override
                            public void accept(Object v, Throwable e) throws Exception {
                                log(e);
                            }
                        });
            }
        });
        mRButton.setText("BiConsumer");
        mRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Single.just(1).subscribe(new BiConsumer<Integer, Throwable>() {
                    @Override
                    public void accept(Integer v, Throwable e) throws Exception {
                        log(v);
                    }
                });
            }
        });
    }

}
