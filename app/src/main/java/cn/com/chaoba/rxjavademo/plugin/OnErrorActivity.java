package cn.com.chaoba.rxjavademo.plugin;

import android.os.Bundle;
import android.view.View;

import cn.com.chaoba.rxjavademo.BaseActivity;
import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;

public class OnErrorActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("No Handler");
        mLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Single.error(new RuntimeException("test error")).subscribe();
            }
        });
        mRButton.setText("Set Handler");
        mRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        log(throwable.getClass().getName() + " - " + throwable.getMessage());
                    }
                });
                Single.error(new RuntimeException("test error")).subscribe();
            }
        });
    }

}
