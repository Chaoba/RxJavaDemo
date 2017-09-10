package cn.com.chaoba.rxjavademo.creatingobserver;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.com.chaoba.rxjavademo.BaseActivity;
import cn.com.chaoba.rxjavademo.R;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class NullActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("Null");
        mLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable.just(null).subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Object o) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        log(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
                Observable.just(1)
                        .map(new Function<Integer, Object>() {
                            @Override
                            public Object apply(@NonNull Integer integer) throws Exception {
                                return null;
                            }
                        })
                        .subscribe(new Observer<Object>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@NonNull Object o) {

                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                log(e.getMessage());
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        });
        mRButton.setText("Range");
        mRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
