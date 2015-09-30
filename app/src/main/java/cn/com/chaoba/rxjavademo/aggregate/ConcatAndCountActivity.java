package cn.com.chaoba.rxjavademo.aggregate;

import android.os.Bundle;

import cn.com.chaoba.rxjavademo.BaseActivity;
import rx.Observable;


public class ConcatAndCountActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLButton.setText("concat");
        mLButton.setOnClickListener(e -> concatObserver().subscribe(i -> log("concat:" + i)));
        mRButton.setText("count");
        mRButton.setOnClickListener(e -> countObserver().subscribe(i -> log("count:" + i)));
    }

    private Observable<Integer> concatObserver() {
        Observable<Integer> obser1 = Observable.just(1, 2, 3);
        Observable<Integer> obser2 = Observable.just(4, 5, 6);
        Observable<Integer> obser3 = Observable.just(7, 8, 9);
        return Observable.concat(obser1, obser2, obser3);
    }
    private Observable<Integer> countObserver() {
        return Observable.just(1, 2, 3).count();
    }
}


